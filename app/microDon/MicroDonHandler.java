package microDon;

import microDon.clients.BankinClient;
import microDon.clients.models.*;
import microDon.exceptions.UserNotFoundException;
import microDon.factories.AccountFactory;
import microDon.factories.TransactionFactory;
import microDon.models.Transaction;
import microDon.models.User;
import play.Logger;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;


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

    public CompletionStage<List<Bank>> getBanks() {
		return bankinClient.getBanks();
    }


    /**
     * From a given user's id, calls the bankin API to get his transactions and round them
     * @param id user's id
     * @return the lis of users transactions rounded
     */
    public CompletionStage<List<Transaction>> getRoundedUsersTransactions(String id) {
        Logger.debug("getRoundedUsersTransactions -> user id : " + id);
        Optional<User> optUser = usersProvider.getUserById(id);
        if (!optUser.isPresent()) {
            throw new UserNotFoundException();
        }

        User user = optUser.get();
        AuthenticateResponse auth = bankinClient.authenticateUser(user.getEmail(), user.getPassword()).toCompletableFuture().join();

        CompletionStage<List<Transaction>> transactionsFuture = bankinClient.listTransactions(auth.getAccessToken())
                .thenApply(res -> {
                    if (res == null || res.getResources() == null) {
                        return new ArrayList<>();
                    }
                    return res.getResources().stream()
                                    .map(TransactionFactory::fromBankin)
                                    .map(TransactionFactory::roundAmount)
                                    .collect(Collectors.toList());
                        }
                );

        CompletionStage<ListResponse<Account>> accountsFuture = bankinClient.listAccounts(auth.getAccessToken());

        return transactionsFuture.thenCombine(accountsFuture, MicroDonHandler::combineTransaction);

    }

    private static List<Transaction> combineTransaction(List<Transaction> transactions, ListResponse<Account> accounts) {
        transactions.forEach(t -> {
            Optional<microDon.models.Account> optAccount = findAccountById(t.getAccount().getId(), accounts.getResources())
                    .map(AccountFactory::fromBankin);
            t.setAccount(optAccount.orElse(t.getAccount()));
        });
        return transactions;
    }

    /**
     * Search an {@link Account} by his id from a given list
     * @param accountId the given id
     * @param accounts a list of accounts
     * @return the account found
     */
    private static Optional<Account> findAccountById(Long accountId, List<Account> accounts) {
        if (accountId == null) {
            return Optional.empty();
        }
        return accounts.stream()
                .filter(account -> accountId.equals(account.getId()))
                .findFirst();
    }

    /**
     * Aggregate all users Transactions' amounts
     * @param startingDate
     * @param endDate
     * @return
     */
    public Map<String, List<Double>> aggregateTransactionsByUser(LocalDate startingDate,
                                                                                  LocalDate endDate) {
        List<User> users = usersProvider.list();

        Map<String, List<Double>> map = new HashMap<>();
        users.forEach(user -> map.put(user.getUuid(), getRoundedUsersTransactions(user.getUuid())
                .toCompletableFuture().join().stream()
                .map(Transaction::getAmount)
                .collect(Collectors.toList())));

        return map;
    }

}
