package microDon;

import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;


public class MicroDonController extends Controller {

    private HttpExecutionContext ec;
    private MicroDonHandler handler;

    @Inject
    public MicroDonController(HttpExecutionContext ec, MicroDonHandler handler) {
        this.ec = ec;
        this.handler = handler;
    }


	public CompletionStage<Result> listBanks() {
		return handler.getBanks().thenApplyAsync(res -> {
			return ok(Json.toJson(res));
		}, ec.current());
	}

	public CompletionStage<Result> getRoundedUsersTransactions(String id) {
        return handler.getRoundedUsersTransactions(id).thenApplyAsync(res -> {
            return ok(Json.toJson(res));
        }, ec.current());
    }


}
