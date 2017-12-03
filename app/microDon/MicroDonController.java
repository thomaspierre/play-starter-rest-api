package microDon;

import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import v1.post.PostAction;
import v1.post.PostResource;
import v1.post.PostResourceHandler;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;


public class MicroDonController extends Controller {

    private HttpExecutionContext ec;
    private MicroDonHandler handler;

    @Inject
    public MicroDonController(HttpExecutionContext ec, MicroDonHandler handler) {
        this.ec = ec;
        this.handler = handler;
    }


	public CompletionStage<Result> listBanks() {
		return handler.getbanks().thenApplyAsync(res -> {
			return ok(Json.toJson(res));
		}, ec.current());
	}

	public CompletionStage<Result> getRoundedUsersTransactions(String id) {
        return handler.getRoundedUsersTransactions(id).thenApplyAsync(res -> {
            return ok(Json.toJson(res));
        }, ec.current());
    }


}
