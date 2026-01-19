package types;

// keeps track of the money loss/gain
public final class Money {
    private static int amount = 1000; // global, persists for entire app run

    private Money() {} // prevent instantiation

    // accesess the amount of money you have
    public static int getAmount() {
        return amount;
    }

    // giving amount a set amount of money
    public static void setAmount(int newAmount) {
        amount = newAmount;
    }

    // adding to the amount keeping track of the money gained and lossed
    public static void add(int delta) {
        amount += delta;
    }
}
