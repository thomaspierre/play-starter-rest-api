package microDon.factories;

import microDon.models.Transaction;

public class TransactionFactory {

    /**
     * Map a {@link microDon.clients.models.Transaction} to a {@link Transaction}
     * @param t the transaction to map
     * @return the mapped transaction
     */
    public static Transaction fromBankin(microDon.clients.models.Transaction t) {
        Transaction transaction = new Transaction();

        transaction.setId(t.getId());
        transaction.setDescription(t.getDescription());
        transaction.setAmount(t.getAmount());
        transaction.setDate(t.getDate());
        transaction.setUpdatedAt(t.getUpdatedAt());
        transaction.setIsDeleted(t.isDeleted());
        transaction.setCategory(t.getCategory());
        transaction.setAccount(t.getAccount());

        return transaction;

    }

    /**
     * From a given {@link Transaction }, round the positive amount to the superior decade.
     * If the amount is null or negative, no update is made
     * @param transaction the transaction
     * @return the transaction updated
     */
    public static Transaction roundAmount(Transaction transaction) {
        if (transaction.getAmount() != null && transaction.getAmount() < 0) {
            transaction.setAmount( - round( - transaction.getAmount()));
        }
        return transaction;
    }

    /**
     * Round a given {@link Double} to the superior decade
     * @param amount the given double
     * @return the double rounded
     */
    private static Double round(Double amount) {
        if (amount == null) {
            return null;
        }

        int temp = amount.intValue();
        temp = ((temp + 9) / 10) * 10;

        return (double) temp;
    }
}
