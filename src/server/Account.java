package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Account class, which holds user details, transactions and balance
public class Account implements Serializable {
    //Instance variables for each account object
    private double balance;
    private String username, password;
    private int accountNumber;
    private List<Transaction> transactions;

    //static variable to control account numbers
    private static int nextAcNum = 88769912;

    public Account (String uName, String pass) {
        this.transactions = new ArrayList<>();
        this.username = uName;
        this.password = pass;
        this.accountNumber = nextAcNum;
        this.balance = 0;

        //increment account number, so next one will be updated
        nextAcNum++;
    }

    public Account (String uName, String pass, double bal) {
        this.transactions = new ArrayList<>();
        this.username = uName;
        this.password = pass;
        this.accountNumber = nextAcNum;
        this.balance = bal;

        //increment account number, so next one will be updated
        nextAcNum++;
    }

    //add new transactions to the account
    public void addTransaction(Transaction t) {
        this.transactions.add(t);
    }

    //getters and setters
    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<Transaction> getTransactions(){
        return this.transactions;
    }

    @Override
    public String toString() {
        return this.accountNumber + " " + this.balance;
    }
}
