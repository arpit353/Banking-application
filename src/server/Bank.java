package server;

import exceptions.*;
import interfaces.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.io.*;
import java.util.*;

//Bank Class which implements all the methods defined in the BankInterface
public class Bank extends UnicastRemoteObject implements BankInterface {

    private List<Account> accounts; // users accounts
    private List<Session> sessions, deadSessions;

    public Bank() throws RemoteException
    {
        super();
        //set up ArrayLists and create test accounts
        accounts = new ArrayList<>();
        sessions = new ArrayList<>();
        deadSessions = new ArrayList<>();

        File x = new File("C:\\Users\\Arpit\\Desktop\\parallel project\\Banking Application\\src\\data.txt");

		if( x.exists() ){

			Scanner s;
			System.out.println(x.getName()+" exists!");

			try{
				s = new Scanner(new File("data.txt"));

				while(s.hasNext()){
	
					String a = s.next();
					String b = s.next();
					Double c = Double.parseDouble(s.next());

					accounts.add(new Account(a,b,c));
			}

			}
			catch(Exception e){
				System.out.println("Could not find file");
			}

		}
/*
        accounts.add(new Account("user1", "pass1"));
        accounts.add(new Account("user2", "pass2"));
        accounts.add(new Account("user3", "pass3"));
*/

    }

    public static void main(String args[]) throws Exception {
        try {
            //Set up securitymanager for server, and specify path to the policy file
            System.setSecurityManager(new SecurityManager());
            System.out.println("\n--------------------\nSecurity Manager Set");

            //Add bank to the RMI registry so it can be located by the client
            String name = "Bank";
            BankInterface bank = new Bank();
            Registry registry = LocateRegistry.getRegistry(Integer.parseInt(args[0]));
            registry.rebind(name, bank);
            System.out.println("Bank Server Bound");
            System.out.println("Server Stared\n--------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long login(String username, String password) throws RemoteException, InvalidLoginException {
        //Loop through the accounts to find the correct one for given username and password
        for(Account acc : accounts) {
            if(username.equals(acc.getUserName()) && password.equals(acc.getPassword())){
                System.out.println(">> Account " + acc.getAccountNumber() + " logged in");
                //Create a new session on Successfull login, and return ID to the client
                Session s = new Session(acc);
                sessions.add(s);
                return s.sessionId;
            }
        }
        //Throw exception if login details are not valid
        throw new InvalidLoginException();
    }

    @Override
    public double deposit(int accountnum, double amount, long sessionID) throws RemoteException, InvalidSessionException {
        //Check if user session is active, based on sessionID passed by client
        if(checkSessionActive(sessionID)) {
            Account account;
            try {
                //Get the correct account
                account = getAccount(accountnum);
                account.setBalance(account.getBalance() + amount);
                //Create transaction object for this transaction and add to the account
                Transaction t = new Transaction(account, "Deposit");
                t.setAmount(amount);
                account.addTransaction(t);

                System.out.println(">> E" + amount + " deposited to account " + accountnum + "\n");
				
                try{
                    	
                    final Formatter f;
					f = new Formatter("data.txt");
					System.out.println("You created a file");

					for(Account acc : accounts) {
						System.out.println(acc.getUserName()+"\n");
						System.out.println(acc.getPassword()+"\n");
						System.out.println(acc.getBalance()+"\n");
						f.format("%s %s %f \n",acc.getUserName(),acc.getPassword(),acc.getBalance());
					}
					
					f.close();
				}
				catch(Exception e){
					System.out.println("You got an error "+e.toString());
				}

                //return balance to client
                return account.getBalance();
            } catch (InvalidAccountException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public double withdraw(int accountnum, double amount, long sessionID) throws RemoteException,
            InsufficientFundsException, InvalidSessionException {
        //Check if user session is active, based on sessionID passed by client
        if(checkSessionActive(sessionID)) {
            try {
                //Get correct user account based on accountnum
                Account account = getAccount(accountnum);
                //Check if withdrawal can be made, based on account balance
                if (account.getBalance() > 0 && account.getBalance() - amount >= 0) {
                    account.setBalance(account.getBalance() - amount);

                    //create new Transaction and add to account
                    Transaction t = new Transaction(account, "Withdrawal");
                    t.setAmount(amount);
                    account.addTransaction(t);

                    System.out.println(">> E" + amount + " withdrawn from account " + accountnum + "\n");

                    for(Account acc : accounts) {

                    	System.out.println(acc.getUserName()+"\n");
                    	System.out.println("Hello");
                    }


                    try{
                    	final Formatter f;
						f = new Formatter("data.txt");
						System.out.println("You created a file");

						for(Account acc : accounts) {
							System.out.println(acc.getUserName()+"\n");
							System.out.println(acc.getPassword()+"\n");
							System.out.println(acc.getBalance()+"\n");
							f.format("%s %s %f \n",acc.getUserName(),acc.getPassword(),acc.getBalance());
						}
					
						f.close();
					}
					catch(Exception e){
						System.out.println("You got an error "+e.toString());
					}

                    //return updated balance
                    return account.getBalance();
                }
            } catch (InvalidAccountException e) {
                e.printStackTrace();
            }
        }
        //Throw exception if account doesn't have enough money to withdraw
        throw new InsufficientFundsException();
    }

    @Override
    public Account inquiry(int accountnum, long sessionID) throws RemoteException, InvalidSessionException {
        //Check if session is active based on sessionID that is passed in
        if(checkSessionActive(sessionID)) {
            try {
                //Get account and return to the client
                Account account = getAccount(accountnum);
                System.out.println(">> Balance requested for account " + accountnum + "\n");
                return account;
            } catch (InvalidAccountException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Statement getStatement(int accountnum, Date from, Date to, long sessionID) throws RemoteException,
           InvalidSessionException, StatementException {
        //Check if the session is active based on sessionID from client
        if(checkSessionActive(sessionID)) {
            try {
                //Get correct user account
                Account account = getAccount(accountnum);

                System.out.println(">> Statement requested for account " + accountnum +
                        " between " + from.toString() + " " + to.toString() + "\n");
                //Create new statement using the account and the dates passed from the client
                Statement s = new Statement(account, from, to);
                return s;
            } catch (InvalidAccountException e) {
                e.printStackTrace();
            }
        }
        //throw exception if statement cannot be generated
        throw new StatementException("Could not generate statement for given account and dates");
    }

    @Override
    public Account accountDetails(long sessionID) throws InvalidSessionException {
        //Get account details based on the session ID
        //Each session has an associated account, so the accounts can be retrieved based on a session
        //Used on the client for looking up accounts
        for(Session s:sessions){
            if(s.getClientId() == sessionID){
                return s.getAccount();
            }
        }
        //Throw exception if session isn't valid
        throw new InvalidSessionException();
    }

    private Account getAccount(int acnum) throws InvalidAccountException{
        //Loop through the accounts to find one corresponding to account number passed from the client
        //and return it
        for(Account acc:accounts){
            if(acc.getAccountNumber() == acnum){
                return  acc;
            }
        }
        //Throw exception if account does not exist
        throw new InvalidAccountException(acnum);
    }

    private boolean checkSessionActive(long sessID) throws InvalidSessionException{
        //Loop through the sessions
        for(Session s : sessions){
            //Checks if the sessionID passed from client is in the sessions list and active
            if(s.getClientId() == sessID && s.isAlive()) {
                //Prints session details and returns true if session is alive
                System.out.println(">> Session " + s.getClientId() + " running for " + s.getTimeAlive() + "s");
                System.out.println(">> Time Remaining: " + (s.getMaxSessionLength() - s.getTimeAlive()) + "s");
                return true;
            }
            //If session is in list, but timed out, add it to deadSessions list
            //This flags timed out sessions for removeAll
            //They will be removed next time this method is called
            if(!s.isAlive()) {
                System.out.println("\n>> Cleaning up timed out sessions");
                System.out.println(">> SessionID: " + s.getClientId());
                deadSessions.add(s);
            }
        }
        System.out.println();

        // cleanup dead sessions by removing them from sessions list
        sessions.removeAll(deadSessions);

        //throw exception if sessions passed to client is not valid
        throw new InvalidSessionException();
    }
}
