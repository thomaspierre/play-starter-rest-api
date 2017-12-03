package microDon.clients;

import microDon.clients.models.Bank;
import microDon.clients.models.AuthenticateResponse;
import microDon.clients.models.GetBanksResponse;
import microDon.clients.models.User;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import v1.post.PostRepository;

import javax.inject.Inject;
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
    public BankinClient(PostRepository repository, HttpExecutionContext ec, WSClient client, Logger logger,
                           Configuration configuration) {
        this.wsClient = client;
        this.bankinUrl = configuration.getString("bankin.api.url");
        this.clientId = configuration.getString("bankin.api.clientId");
        this.clientSecret = configuration.getString("bankin.api.clientSecret");
        this.version = configuration.getString("bankin.api.version");

    }


    private void apiAuthentication(WSRequest request) {
        request.setQueryParameter("client_id", clientId)
                .setQueryParameter("client_secret", clientSecret)
                .setHeader("Bankin-Version", version);
    }

    public CompletionStage<List<Bank>> getBanks() {
        WSRequest request = wsClient.url(String.format("%sbanks", bankinUrl));

        apiAuthentication(request);

        Logger.debug(request.getUrl());
        return request.get().thenApply(wsResponse -> {

            GetBanksResponse res = Json.fromJson(wsResponse.asJson(), GetBanksResponse.class);
            return res.getResources();
        });
    }

    public CompletionStage<AuthenticateResponse> authenticateUser(String email, String password) {
        WSRequest request = wsClient.url(String.format("%sauthenticate", bankinUrl));

        request.setQueryParameter("email", email);
        request.setQueryParameter("password", password);
        apiAuthentication(request);

        return request.post("")
                .thenApply(wsResponse -> Json.fromJson(wsResponse.asJson(), AuthenticateResponse.class));
    }

}
