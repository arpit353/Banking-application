package server;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

//Tests for statement methods
public class StatementTest {
    private Account acc1;


    //Set up data used in test
    @Before
    public void setUp() throws Exception {
        acc1 = new Account("user1","12");
        Transaction transac1 = new Transaction(acc1,"Deposit");
        Transaction transac2 = new Transaction(acc1,"Deposit");
        Transaction transac3 = new Transaction(acc1,"Withdrawal");
        Transaction transac4 = new Transaction(acc1,"Deposit");
        acc1.addTransaction(transac1);
        acc1.addTransaction(transac2);
        acc1.addTransaction(transac3);
        acc1.addTransaction(transac4);

    }

    @Test
    //Tests that valid transactions are returned
    public void getTransations() throws Exception {

        Statement state = new Statement(acc1, new Date("02/12/2017"), new Date("02/15/2017"));
        List<Transaction> transactions = state.getTransations();
        System.out.println(transactions.size());
        assertEquals(transactions.size(),4);
    }

}
