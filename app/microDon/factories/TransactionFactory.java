package microDon.factories;

import microDon.models.Transaction;

public class TransactionFactory {

    public static Transaction fromBankin(microDon.clients.models.Transaction t) {
        Transaction transaction = new Transaction();

        transaction.setId(t.getId());
        transaction.setDescription(t.getDescription());
        transaction.setAmount(t.getAmount());
        transaction.setDate(t.getDate());
        transaction.setUpdatedAt(t.getUpdatedAt());
        transaction.setDeleted(t.getDeleted());
        transaction.setCategory(t.getCategory());
        transaction.setAccount(t.getAccount());

        return transaction;

    }
}
