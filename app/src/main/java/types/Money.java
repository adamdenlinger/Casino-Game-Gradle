package types;

public final class Money {
    private static int amount = 1000; // global, persists for entire app run

    private Money() {} // prevent instantiation

    public static int getAmount() {
        return amount;
    }

    public static void setAmount(int newAmount) {
        amount = newAmount;
    }

    public static void add(int delta) {
        amount += delta;
    }
}
