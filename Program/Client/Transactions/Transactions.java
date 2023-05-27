package Program.Client.Transactions;

public class Transactions {
    public static double putMoneyIntoTheAccount(double balance, double moneyToPut) {
        return balance + moneyToPut;
    }

    public static boolean isItPossibleToWithdrawMoneyFromTheAccount(double balance, double moneyToWithdraw) {
        return balance - moneyToWithdraw >= 0;
    }

    public static double  withdrawMoneyFromTheAccount(double balance, double moneyToWithdraw) {
        return balance - moneyToWithdraw;
    }
}
