package server;

import exceptions.InvalidLoginException;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.rmi.RemoteException;
import static org.junit.Assert.*;

//Test for bank server methods
public class BankTest {
    Bank bankserv = new Bank();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    public BankTest() throws RemoteException {

    }

    //Test login
    @Test
    public void login() throws Exception {
        long sessid=bankserv.login("user1","pass1");
        assertNotNull(sessid);
    }

    //Test getAccout
    @Test
    public void getAcc() throws Exception {
        long sessionid=bankserv.login("user1","pass1");
        Account acc = bankserv.accountDetails(sessionid);
        assertNotNull(acc);
    }

    //Negative test for login
    @Test
    public void loginfail()throws Exception{
        expectedException.expect(InvalidLoginException.class);
        bankserv.login("user2","pass3");

    }

    //Test for depost
    @Test
    public void deposit() throws Exception {
        long sessionid = bankserv.login("user1","pass1");
        Account acc = bankserv.accountDetails(sessionid);
        double expbalance = 200.0;
        bankserv.deposit(acc.getAccountNumber(),200,sessionid);
        Integer isEqual = Double.compare(bankserv.inquiry(acc.getAccountNumber(),sessionid).getBalance(), expbalance);
        assertTrue(isEqual.equals(0));
    }

    //Test for withdraw
    @Test
    public void withdraw() throws Exception {
        long sessionid = bankserv.login("user1","pass1");
        Account acc = bankserv.accountDetails(sessionid);
        bankserv.deposit(acc.getAccountNumber(),200,sessionid);
        bankserv.withdraw(acc.getAccountNumber(),200,sessionid);
        Integer isEqual= Double.compare(bankserv.inquiry(acc.getAccountNumber(),sessionid).getBalance(),0.0);
        assertTrue(isEqual.equals(0));
    }
}
