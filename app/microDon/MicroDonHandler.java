package microDon;

import com.palominolabs.http.url.UrlBuilder;
import microDon.clients.BankinClient;
import microDon.clients.models.GetBanksResponse;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.Http;
import v1.post.PostData;
import v1.post.PostRepository;
import v1.post.PostResource;

import javax.inject.Inject;
import java.nio.charset.CharacterCodingException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

/**
 * Handles presentation of Post resources, which map to JSON.
 */
public class MicroDonHandler {


    private final BankinClient bankinClient;

    @Inject
    public MicroDonHandler(BankinClient bankinClient) {
        this.bankinClient = bankinClient;

    }

    public CompletionStage<List<Bank>> getbanks() {
		return bankinClient.getBanks();
    }


}
