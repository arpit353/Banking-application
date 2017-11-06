package server;

import interfaces.StatementInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Statement implements StatementInterface, Serializable {
    //Instance variables for each Statement object
    private List<Transaction> relevantTransactions;
    private Date startDate, endDate;
    private Account account;

    public Statement(Account account, Date start, Date end){
        this.relevantTransactions = new ArrayList<>();
        this.account = account;
        this.startDate = start;
        this.endDate = end;
    }

    @Override
    public int getAccountnum() {
        return this.account.getAccountNumber();
    }

    @Override
    public Date getStartDate() {
        return this.startDate;
    }

    @Override
    public Date getEndDate() {
        return this.endDate;
    }

    @Override
    public String getAccoutName() {
        return account.getUserName();
    }

    @Override
    public List getTransations() {
        //Get all the relevantTransactions for the dates passed in
        this.account.getTransactions().stream()
                //filter transactions based on if the date is before the given date or not
                .filter(transactions -> transactions.getDate().after(this.startDate) && transactions.getDate().before(this.endDate))
                //create list from these filtered transactions
                .collect(Collectors.toList())
                //for each transaction in the list, add it to the relevantTransactions list that will be returned
                .forEach(date -> relevantTransactions.add(date));

        return this.relevantTransactions;
    }
}
