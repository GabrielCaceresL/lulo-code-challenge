import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * InputObject.
 *
 * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
 */
public class InputObject {

    /**
     * account.
     */
    @JsonProperty("account")
    private Account account;

    /**
     * transaction.
     */
    @JsonProperty("transaction")
    private Transaction transaction;


    @Override
    public String toString() {
        return "ConsoleInput{" +
                "account=" + account +
                ", transaction=" + transaction +
                '}';
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
