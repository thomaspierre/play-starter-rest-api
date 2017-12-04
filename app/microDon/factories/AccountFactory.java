package microDon.factories;

import microDon.models.Account;

public class AccountFactory {

    /**
     * Map a Bankin Account to a {@link Account}
     * @param account account to convert
     * @return the new account
     */
    public static Account fromBankin(microDon.clients.models.Account account) {
        Account res = new Account();
        res.setId(account.getId());
        res.setName(account.getName());

        return res;
    }
}
