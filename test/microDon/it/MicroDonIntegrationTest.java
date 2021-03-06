package microDon.it;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;

/**
 * Author: tpierre
 */
public class MicroDonIntegrationTest extends WithApplication {

	@Override
	protected Application provideApplication() {
		return new GuiceApplicationBuilder().build();
	}

	@Test
	public void test_User_Transactions_Status_Code() {


		Http.RequestBuilder request = new Http.RequestBuilder()
			 .method(GET)
			 .uri("/microdon/users/1/transactions");

		Result result = route(app, request);
		assertThat(result.status(), equalTo(OK));
	}

		@Test
	public void test_User_Transactions() {


		Http.RequestBuilder request = new Http.RequestBuilder()
			 .method(GET)
			 .uri("/microdon/users/1/transactions");

		Result result = route(app, request);
		final String body = contentAsString(result);

		assertThat(body, containsString("amount"));
	}



}
