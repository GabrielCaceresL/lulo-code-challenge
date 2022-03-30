import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Transaction.
 *
 * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
 */
public class Transaction implements Comparable<Transaction> {

    /**
     * account identifier.
     */
    @JsonProperty("id")
    private int id;

    /**
     * merchant merchant.
     */
    @JsonProperty("merchant")
    private String merchant;

    /**
     * amount transaction cost.
     */
    @JsonProperty("amount")
    private int amount;

    /**
     * time transaction date.
     */
    @JsonProperty("time")
    private Date time;

    public Transaction() {
    }

    public Transaction(int id, String merchant, int amount, Date time) {
        this.id = id;
        this.merchant = merchant;
        this.amount = amount;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", merchant='" + merchant + '\'' +
                ", amount=" + amount +
                ", time=" + time +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public int compareTo(Transaction t1) {
        if (getTime() == null || t1.getTime() == null) {
            return 0;
        }
        return getTime().compareTo(t1.getTime());
    }
}
