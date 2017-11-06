package client;

import exceptions.*;
import interfaces.BankInterface;
import server.Account;
import server.Statement;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;

//Client program, which connects to the bank using RMI and class methods of the remote bank object
public class ATM {
    static int serverAddress, serverPort, account;
    static String operation, username, password;
    static long sessionID, id=0;
    static double amount;
    static BankInterface bank;
    static Date startDate, endDate;

    public static void main (String args[]) {
        try {
            //Parse the command line arguments into the progam
            getCommandLineArguments(args);
            //Set up the rmi registry and get the remote bank object from it
            String name = "Bank";
            String serverIP = "192.168.43.158";
            //String serverIP = "localhost";
            Registry registry = LocateRegistry.getRegistry(serverIP,serverPort);
            bank = (BankInterface) registry.lookup(name);
            System.out.println("\n----------------\nClient Connected" + "\n----------------\n");
        } catch (InvalidArgumentException ie){
            ie.printStackTrace();
            System.out.println(ie);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
        double balance;

        //Switch based on the operation
        switch (operation){
            case "login":
                try {
                    //Login with username and password
                    id = bank.login(username, password);
                    Account acc = bank.accountDetails(id);
                    //Print account details
                    System.out.println("--------------------------\nAccount Details:\n--------------------------\n" +
                                       "Account Number: " + acc.getAccountNumber() +
                                       "\nSessionID: " + id +
                                       "\nUsername: " + acc.getUserName() +
                                       "\nBalance: " + acc.getBalance() +
                                       "\n--------------------------\n");
                    System.out.println("Session active for 5 minutes");
                    System.out.println("Use SessionID " + id + " for all other operations");
                //Catch exceptions that can be thrown from the server
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InvalidLoginException e) {
                    e.printStackTrace();
                } catch (InvalidSessionException e) {
                    e.printStackTrace();
                }
                break;

            case "deposit":
                try {
                    //Make bank deposit and get updated balance
                    balance = bank.deposit(account, amount, sessionID);
                    System.out.println("Successfully deposited E" + amount + " into account " + account);
                    System.out.println("New balance: E" + balance);
                //Catch exceptions that can be thrown from the server
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InvalidSessionException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case "withdraw":
                try {
                    //Make bank withdrawal and get updated balance
                    balance = bank.withdraw(account, amount, sessionID);
                    System.out.println("Successfully withdrew E" + amount + " from account " + account +
                                       "\nRemaining Balance: E" + balance);
                //Catch exceptions that can be thrown from the server
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InvalidSessionException e) {
                    System.out.println(e.getMessage());
                } catch (InsufficientFundsException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case "inquiry":
                try {
                    //Get account details from bank
                    Account acc = bank.inquiry(account,sessionID);
                    System.out.println("--------------------------\nAccount Details:\n--------------------------\n" +
                            "Account Number: " + acc.getAccountNumber() +
                            "\nUsername: " + acc.getUserName() +
                            "\nBalance: E" + acc.getBalance() +
                            "\n--------------------------\n");
                //Catch exceptions that can be thrown from the server
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InvalidSessionException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case "statement":
                Statement s = null;
                try {
                    //Get statement for required dates
                    s = (Statement) bank.getStatement(account, startDate, endDate, sessionID);

                    //format statement for printing to the window
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    System.out.print("-----------------------------------------------------------------------\n");
                    System.out.println("Statement for Account " + account + " between " +
                                       dateFormat.format(startDate) + " and " + dateFormat.format(endDate));
                    System.out.print("-----------------------------------------------------------------------\n");
                    System.out.println("Date\t\t\tTransaction Type\tAmount\t\tBalance");
                    System.out.print("-----------------------------------------------------------------------\n");

                    for(Object t : s.getTransations()) {
                        System.out.println(t);
                    }
                    System.out.print("-----------------------------------------------------------------------\n");
                //Catch exceptions that can be thrown from the server
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InvalidSessionException e) {
                    System.out.println(e.getMessage());
                } catch (StatementException e) {
                    System.out.println(e.getMessage());
                }
                break;

            default:
                //Catch all case for operation that isn't one of the above
                System.out.println("Operation not supported");
                break;
        }
    }

    public static void getCommandLineArguments(String args[]) throws InvalidArgumentException{
        //Makes sure server, port and operation are entered as arguments
        if(args.length < 4) {
            throw new InvalidArgumentException();
        }

        //Parses arguments from command line
        //arguments are in different places based on operation, so switch needed here
        serverPort = Integer.parseInt(args[1]);
        operation = args[2];
        switch (operation){
            case "login":
                username = args[3];
                password = args[4];
                break;
            case "withdraw":
            case "deposit":
                amount = Double.parseDouble(args[4]);
                account = Integer.parseInt(args[3]);
                sessionID = Long.parseLong(args[5]);
                break;
            case "inquiry":
                account = Integer.parseInt(args[3]);
                sessionID = Long.parseLong(args[4]);
                break;
            case "statement":
                account = Integer.parseInt(args[3]);
               /* startDate = new Date(args[4]);
                endDate = new Date(args[5]); */

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                try{
                    Date sdate = dateFormat.parse("03/11/2017");
                    Date edate = dateFormat.parse("08/11/2017");
                    startDate = sdate;
                    endDate = edate;    
                }
                catch(Exception e)
                {

                }
                

                /*
                startDate = new Date(2017,11,Integer.parseInt(args[4]));
                endDate = new Date(2017,11,Integer.parseInt(args[5]));
                */

                sessionID = Long.parseLong(args[6]);
                break;
        }
    }
}
