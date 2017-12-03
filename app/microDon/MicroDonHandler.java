package microDon;

import microDon.clients.BankinClient;
import microDon.clients.models.AuthenticateResponse;
import microDon.clients.models.Bank;
import microDon.factories.TransactionFactory;
import microDon.models.Transaction;
import microDon.models.User;
import play.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static microDon.factories.TransactionFactory.fromBankin;

/**
 * Handles presentation of Post resources, which map to JSON.
 */
public class MicroDonHandler {


    private final BankinClient bankinClient;

    private final UsersProvider usersProvider;

    @Inject
    public MicroDonHandler(BankinClient bankinClient, UsersProvider usersProvider) {
        this.bankinClient = bankinClient;
        this.usersProvider = usersProvider;

    }

    public CompletionStage<List<Bank>> getbanks() {
		return bankinClient.getBanks();
    }


    /**
     * From a given user's id, calls the bankin API to get his transactions and round them
     * @param id user's id
     * @return the lis of users transactions rounded
     */
    public CompletionStage<List<Transaction>> getRoundedUsersTransactions(String id) {
        Optional<User> optUser = usersProvider.getUserById(id);
        if (!optUser.isPresent()) {
            throw new UserNotFoundException();
        }

        User user = optUser.get();
        AuthenticateResponse auth = bankinClient.authenticateUser(user.getEmail(), user.getPassword()).toCompletableFuture().join();

        return bankinClient.listTransactions(auth.getAccessToken())
                .thenApply(res -> res.getResources().stream()
                        .map(TransactionFactory::fromBankin)
                        .collect(Collectors.toList())
                );

    }
}
