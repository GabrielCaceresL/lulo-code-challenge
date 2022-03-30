import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class RunClassTest {

    @Test
    void accountExists() {
        RunClass runClass = new RunClass();
        Account account = new Account(1, true, 100);
        InputObject inputObject = new InputObject();
        inputObject.setAccount(account);
        runClass.validateInputObject(inputObject);
        assertTrue(runClass.accountExists(account));

    }

}