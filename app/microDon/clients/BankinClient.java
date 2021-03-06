package microDon.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import microDon.clients.models.*;
import microDon.exceptions.ErrorDuringProcessingException;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * Author: tpierre
 */
public class BankinClient {


    private final WSClient wsClient;

    private final String bankinUrl;
    private final String clientId;
    private final String clientSecret;
    private final String version;

    @Inject
    public BankinClient(WSClient client,
                           Configuration configuration) {
        this.wsClient = client;

        this.bankinUrl = configuration.getString("bankin.api.url");
        this.clientId = configuration.getString("bankin.api.clientId");
        this.clientSecret = configuration.getString("bankin.api.clientSecret");
        this.version = configuration.getString("bankin.api.version");

    }

    public CompletionStage<List<Bank>> getBanks() {
        WSRequest request = wsClient.url(String.format("%sbanks", bankinUrl));

        apiAuthentication(request);

        Logger.debug(request.getUrl());
        return request.get().thenApply(wsResponse -> {

            ListBanksResponse res = Json.fromJson(wsResponse.asJson(), ListBanksResponse.class);
            return res.getResources();
        });
    }

    /**
     * Calls Bankin API to authenticate a user with given credentials
     * @param email user's email
     * @param password user's password
     * @return a {@link AuthenticateResponse}
     */
    public CompletionStage<AuthenticateResponse> authenticateUser(String email, String password) {
        WSRequest request = wsClient.url(String.format("%sauthenticate", bankinUrl));

        request.setQueryParameter("email", email);
        request.setQueryParameter("password", password);
        apiAuthentication(request);

        return request.post("")
                .thenApply(wsResponse -> Json.fromJson(wsResponse.asJson(), AuthenticateResponse.class));
    }

    /**
     * Calls Bankin API to get a user's transactions from his secret token
     * @param usersToken user's secret token
     * @param startingDate transactions starting date
     * @param endDate transactions' ending date
     * @return a {@link ListResponse<Transaction>   }
     */
    public CompletionStage<ListResponse<Transaction>> listTransactions(String usersToken,
                                                                       LocalDate startingDate,
                                                                       LocalDate endDate) {
        WSRequest request = wsClient.url(String.format("%stransactions", bankinUrl));

        apiAuthentication(request);
        clientAuthentication(request, usersToken);

        if (startingDate != null) {
            request.setQueryParameter("since", formatLocaDate(startingDate));
        }

        if (endDate != null) {
            request.setQueryParameter("until", formatLocaDate(endDate));
        }

        return request.get()
                .thenApply(wsResponse -> {
                    try {
                        return Json.mapper()
                                .readValue(wsResponse.asJson().traverse(),
                                        new TypeReference<ListResponse<Transaction>>() {});
                    } catch (IOException e) {
                        Logger.error(e.getMessage());
                        throw new ErrorDuringProcessingException();
                    }
                });
    }

    /**
     * Add API credentials to a given {@link WSRequest}
     * @param request the given request
     */
    private void apiAuthentication(WSRequest request) {
        request.setQueryParameter("client_id", clientId)
                .setQueryParameter("client_secret", clientSecret)
                .setHeader("Bankin-Version", version);
    }

    /**
     * Add client token to a given {@link WSRequest}
     * @param request the given request
     */
    private void clientAuthentication(WSRequest request, String usersToken) {
        request.setHeader("Authorization", "Bearer " + usersToken);
    }

    /**
     * List accounts from a given user's token
     * @param usersToken the user token
     * @return a list of accounts
     */
    public CompletionStage<ListResponse<Account>> listAccounts(String usersToken) {
        WSRequest request = wsClient.url(String.format("%saccounts", bankinUrl));

        apiAuthentication(request);
        clientAuthentication(request, usersToken);

        return request.get()
                .thenApply(wsResponse -> {
                    try {
                        return Json.mapper()
                                .readValue(wsResponse.asJson().traverse(),
                                        new TypeReference<ListResponse<Account>>() {});
                    } catch (IOException e) {
                        Logger.error(e.getMessage());
                        throw new ErrorDuringProcessingException();
                    }
                });
    }

    /**
     * Format a {@link LocalDate} to Bankin API format
     * @param date the date to format
     * @return given date in string
     */
    private String formatLocaDate(LocalDate date) {
       return date.format(DateTimeFormatter.ISO_DATE);
    }
}
