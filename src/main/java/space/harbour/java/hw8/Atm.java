package space.harbour.java.hw8;

import java.util.HashMap;
import java.util.Map;

public class Atm {
    // Make sure you can have 2x 20 euro dispensers in a chain
    static Dispenser dispenser50e = new Dispenser(50, 5, "Dispenser 50e");
    static Dispenser dispenser20e = new Dispenser(20, 2, "Dispenser 20e");
    static Dispenser secondDispenser20e = new Dispenser(20, 30, "2nd Dispenser 20e");
    static Dispenser dispenser10e = new Dispenser(10, 5, "Dispenser 10e");
    static Dispenser dispenser5e = new Dispenser(5, 1, "Dispenser 5e");



    public static int getBalance() {
        // User ITERATOR pattern to get balance of the ATM
        dispenser50e.setNextDispenser(dispenser20e);
        dispenser20e.setNextDispenser(secondDispenser20e);
        secondDispenser20e.setNextDispenser(dispenser10e);
        dispenser10e.setNextDispenser(dispenser5e);

        int totalBalance = 0;
        boolean hasNext = true;
        Dispenser currentDispenser = dispenser50e;
        while (hasNext) {
            totalBalance += currentDispenser.getDenomination() * currentDispenser.getCount();
            hasNext = currentDispenser.hasNext();
            currentDispenser = currentDispenser.getNextDispenser();
        }
        return totalBalance;
    }

    public static Map<Integer, Integer> giveMeMoney(int amount) {
        dispenser50e.setNextDispenser(dispenser20e);
        dispenser20e.setNextDispenser(secondDispenser20e);
        secondDispenser20e.setNextDispenser(dispenser10e);
        dispenser10e.setNextDispenser(dispenser5e);

        Map<Integer, Integer> initialResult = new HashMap<>();

        return dispenser50e.withdrawMoney(amount, initialResult);
    }

    public static void main(String[] args) {
        System.out.println("Initial balance of ATM: " + getBalance());
        System.out.println(giveMeMoney(100));
        System.out.println(giveMeMoney(500));
        System.out.println("Remaining balance of ATM: " + getBalance());
    }
}
