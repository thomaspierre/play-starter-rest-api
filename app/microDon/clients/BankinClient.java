package microDon.clients;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import microDon.ErrorDuringProcessingException;
import microDon.UserNotFoundException;
import microDon.clients.models.*;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * Author: tpierre
 */
public class BankinClient {


    private final WSClient wsClient;
    private final ObjectMapper mapper;

    private final String bankinUrl;
    private final String clientId;
    private final String clientSecret;
    private final String version;

    @Inject
    public BankinClient(WSClient client, ObjectMapper mapper,
                           Configuration configuration) {
        this.wsClient = client;
        this.mapper = mapper;
        this.mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

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
     * @return a {@link ListResponse<Transaction>   }
     */
    public CompletionStage<ListResponse<Transaction>> listTransactions(String usersToken) {
        WSRequest request = wsClient.url(String.format("%stransactions", bankinUrl));

        apiAuthentication(request);
        clientAuthentication(request, usersToken);

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
}
