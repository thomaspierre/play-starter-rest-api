package microDon.factories;

import microDon.models.Transaction;
import org.junit.Assert;
import org.junit.Test;

public class TransactionFactoryTest {

    @Test
    public void roundTest_Nominal() {
        Transaction transaction = new Transaction();
        transaction.setAmount(-57D);

        Transaction res = TransactionFactory.roundAmount(transaction);

        Assert.assertEquals(-60D, res.getAmount(), 0);
    }

    @Test
    public void roundTest_credit() {
        Transaction transaction = new Transaction();
        transaction.setAmount(57D);

        Transaction res = TransactionFactory.roundAmount(transaction);

        Assert.assertEquals(57D, res.getAmount(), 0);
    }
}
