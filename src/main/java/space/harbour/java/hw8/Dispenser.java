package space.harbour.java.hw8;

import java.util.HashMap;
import java.util.Map;

public class Dispenser {
    private final int denomination;
    private int count;
    private String name;

    Dispenser nextDispenser;

    public Dispenser(int denomination, int count, String name) {
        // Size of bills: 1, 5, 10, 20, 50 euros
        this.denomination = denomination;
        // Number of bills in this dispenser
        this.count = count;
        this.name = name;
    }

    public void setNextDispenser(Dispenser nextDispenser) {
        this.nextDispenser = nextDispenser;
    }

    public Map<Integer, Integer> withdrawMoney(
            int withdrawAmount, Map<Integer, Integer> previousResult) {

        Map<Integer, Integer> result = new HashMap<>();
        if (withdrawAmount >= denomination) {
            // Calculate and withdraw money
            int withdrawQty = Math.min(withdrawAmount / denomination, count);
            count -= withdrawQty;
            System.out.println("Withdraw " + withdrawQty + " notes from " + name);

            // Write withdrawal details into hashmap
            result = mapPutAndIncrement(previousResult, denomination, withdrawQty);

            // Processing remainder
            int remainder = withdrawAmount - withdrawQty * denomination;
            System.out.println("Remainder: " + remainder);
            if (remainder > 0) {
                if (denomination == 5) {
                    return result;
                } else {
                    nextDispenser.withdrawMoney(remainder, result);
                }
            }
        } else if (denomination == 5) {
            return result;
        } else {
            nextDispenser.withdrawMoney(withdrawAmount, previousResult);
        }
        return result;
    }

    // Method to put and aggregate value, to allow for multiple dispenser with same denomination
    public Map<Integer, Integer> mapPutAndIncrement(Map<Integer, Integer> map, int key, int value) {
        Integer prev = map.get(key);
        int newValue = value;
        if (prev != null) {
            newValue += prev;
        }
        map.put(key, newValue);
        return map;
    }

    public boolean hasNext() {
        return nextDispenser != null;
    }

    public Dispenser getNextDispenser() {
        return nextDispenser;
    }

    public int getDenomination() {
        return denomination;
    }

    public int getCount() {
        return count;
    }
}