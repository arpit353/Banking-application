package interfaces;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface StatementInterface extends Serializable {

    public int getAccountnum();  // returns account number associated with this statement
    public Date getStartDate(); // returns start Date of StatementInterface
    public Date getEndDate(); // returns end Date of StatementInterface
    public String getAccoutName(); // returns name of account holder
    public List getTransations(); // returns list of Transaction objects that encapsulate details about each transaction

}