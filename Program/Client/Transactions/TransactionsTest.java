package Program.Client.Transactions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void putMoneyIntoTheAccount() {
        double balance = 100.00;
        double moneyToDeposit = 50.00;
        double expResult = 150.00;
        double result = Transactions.putMoneyIntoTheAccount(balance,moneyToDeposit);
        assertEquals(expResult,result);
    }

    @Test
    void isItPossibleToWithdrawMoneyFromTheAccount() {
        double balance = 100.00;
        double moneyToDeposit = 150.00;
        boolean expResult = false;
        boolean result = Transactions.isItPossibleToWithdrawMoneyFromTheAccount(balance,moneyToDeposit);
        assertEquals(expResult,result);
    }

    @Test
    void withdrawMoneyFromTheAccount() {
        double balance = 100.00;
        double moneyToDeposit = 50.00;
        double expResult = 50.00;
        double result = Transactions.withdrawMoneyFromTheAccount(balance,moneyToDeposit);
        assertEquals(expResult,result);
    }
}