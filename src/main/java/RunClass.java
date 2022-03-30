import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Date;

/**
 * MainClass.
 *
 * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
 */
public class RunClass {

    /**
     * Json handling utility.
     */
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    /**
     * account list.
     */
    private static final List<Account> accounts = new ArrayList<>();
    /**
     * transaction list.
     */
    private static final List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        new RunClass();
    }

    /**
     * receive the account and transaction
     * @inheritDoc
     * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
     */
    public RunClass() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String json = sc.nextLine();
            if (!json.isEmpty()) {
                try {
                    InputObject inputObject = JSON_MAPPER.readValue(json, InputObject.class);
                    System.out.println(validateInputObject(inputObject));
                } catch (IOException e) {
                    System.out.println("Wrong input. invalid Json.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }

            }
        }
    }

    /**
     * route the account or transaction to validation
     * @inheritDoc
     * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
     * @return String with the account processed
     */
    public String validateInputObject(InputObject inputObject) {
        Account account = inputObject.getAccount();
        Transaction transaction = inputObject.getTransaction();
        if (inputObject.getAccount() != null) {
            validateFieldsAccount(account);
            if (accountExists(account)) {
                addViolations(account, "account-already-initialized");
            } else {
                account.setViolations(new ArrayList<>());
                accounts.add(account);
            }
            return account.toString();
        }
        if (inputObject.getTransaction() != null) {
            return validateFieldsTransaction(transaction).toString();
        }
        return "{}";
    }

    /**
     * check if the account exits
     * @inheritDoc
     * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
     * @return exist account
     */
    public boolean accountExists(Account account) {
        return accounts.stream().anyMatch(account1 -> account.getId() == account.getId());
    }

    /**
     * find account in the account list
     * @inheritDoc
     * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
     * @return existing account
     */
    public Account getAccountById(int idAccount) {
        return accounts
                .stream()
                .filter(account -> account.getId() == idAccount)
                .findFirst()
                .orElse(null);
    }

    /**
     * add violations to the account
     * @inheritDoc
     * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
     */
    public void addViolations(Account account, String violation) {
        ArrayList<String> violations = new ArrayList<>();
        violations.add(violation);
        account.setViolations(violations);
    }

    /**
     * Check if values are negative
     * @inheritDoc
     * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
     */
    public void validateFieldsAccount(Account account) {
        if (account.getId() < 1) {
            throw new IllegalArgumentException("Wrong input. Id can´t be negative");
        }
        if (account.getAvailableLimit() < 1) {
            throw new IllegalArgumentException("Wrong input. Available limit can´t be negative");
        }
    }

    /**
     * Check values negative and business rules
     * @inheritDoc
     * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
     * @return account processed
     */
    public Account validateFieldsTransaction(Transaction transaction) {
        Collections.sort(transactions);
        if (transaction.getAmount() < 1) {
            throw new IllegalArgumentException("Wrong input. Amount can´t be negative");
        }

        Account account = getAccountById(transaction.getId());
        if (account == null) {
            return accountNotInitialized(transaction.getId());
        } else if (!account.isActiveCard()) {
            addViolations(account, "card-not-active");
        } else if (transaction.getAmount() > account.getAvailableLimit()) {
            addViolations(account, "insufficient-limit");
        } else if (moreTransactionInterval(transaction)) {
            addViolations(account, "high-frequency-small-interval");
        } else if (similarTransactionInterval(transaction)) {
            addViolations(account, "doubled-transaction");
        } else {
            account.setAvailableLimit(account.getAvailableLimit() - transaction.getAmount());
        }

        transactions.add(transaction);

        return account;
    }

    /**
     * Check if account is initialized
     * @inheritDoc
     * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
     * @return account with violation added
     */
    public Account accountNotInitialized(int id) {
        Account account = new Account(id, false, 0);
        addViolations(account, "account-not-initialized");
        return account;
    }

    /**
     * Check the frequency and interval of transactions
     * @inheritDoc
     * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
     * @return if the frequency and interval are valid
     */
    public boolean moreTransactionInterval(Transaction transaction) {
        List<Transaction> transactionsById = transactions
                .stream()
                .filter(transaction1 -> transaction1.getId() == transaction.getId())
                .collect(Collectors.toList());
        if (transactionsById.size() >= 2) {
            double timeDiff = diffMinTime(transaction.getTime(), transactionsById.get(transactionsById.size() - 2).getTime());
            return timeDiff <= 2;
        } else {
            return false;
        }
    }

    /**
     * Validates if exist a similar transaction with an invalid interval
     * @inheritDoc
     * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
     * @return if transaction is valid
     */
    public boolean similarTransactionInterval(Transaction transaction) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transaction.getId() == transactions.get(i).getId()
                    && transaction.getAmount() == transactions.get(i).getAmount()
                    && transaction.getMerchant().equals(transactions.get(i).getMerchant())
                    && diffMinTime(transaction.getTime(), transactions.get(i).getTime()) <= 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * calculate the time difference between dates
     * @inheritDoc
     * @author Gabriel Caceres Leguizamon <galejandrocl@gmail.com>
     * @return difference in minutes between dates
     */
    public double diffMinTime(Date date1, Date date2) {
        // calculate the time difference
        long diff = date1.getTime() - date2.getTime();
        // convert the time difference in minutes
        double mins = ((double) diff / (1000 * 60));
        return mins;
    }


}
