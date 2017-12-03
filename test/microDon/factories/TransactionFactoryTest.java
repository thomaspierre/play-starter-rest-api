package microDon.factories;

import microDon.models.Transaction;
import org.junit.Assert;
import org.junit.Test;

public class TransactionFactoryTest {

    @Test
    public void roundTest() {
        Transaction transaction = new Transaction();
        transaction.setAmount(57D);

        Transaction res = TransactionFactory.roundAmount(transaction);

        Assert.assertEquals(60D, res.getAmount(), 0);
    }
}
