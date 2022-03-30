import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * InputObject.
 *
 * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
 */
public class Account {

    /**
     * id account identifier.
     */
    @JsonProperty("id")
    private int id;

    /**
     * activeCard card status.
     */
    @JsonProperty("active-card")
    private boolean activeCard;

    /**
     * availableLimit available balance.
     */
    @JsonProperty("available-limit")
    private int availableLimit;

    /**
     * violations business rules.
     */
    private ArrayList<String> violations;

    public Account(int id, boolean activeCard, int availableLimit) {
        this.id = id;
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
    }

    public Account() {
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", activeCard=" + activeCard +
                ", availableLimit=" + availableLimit +
                ", violations=" + violations +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActiveCard() {
        return activeCard;
    }

    public void setActiveCard(boolean activeCard) {
        this.activeCard = activeCard;
    }

    public int getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(int availableLimit) {
        this.availableLimit = availableLimit;
    }

    public ArrayList<String> getViolations() {
        return violations;
    }

    public void setViolations(ArrayList<String> violations) {
        this.violations = violations;
    }
}
