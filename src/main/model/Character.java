package model;

public class Character {
    static int SCREEN_SIZE_WIDTH = 1000;
    static int SCREEN_SIZE_HEIGHT = 1000;
    String name;
    int locationX;
    int locationY;


    // EFFECTS: Set character name to the given name and places him at the starting location
    public Character(String name) {
        this.name = name;
        locationX = 0;
        locationY = SCREEN_SIZE_HEIGHT / 2;
    }
}


/*
// Represents an account having an id, owner name and balance (in dollars)
public class Account {
    private static int nextAccountId = 1;  // tracks id of next account created
    private int id;                        // account id
    private String name;                   // the account owner name
    private double balance;                // the current balance of the account

    */
/*
     * REQUIRES: accountName has a non-zero length
     * EFFECTS: name on account is set to accountName; account id is a
     *          positive integer not assigned to any other account;
     *          if initialBalance >= 0 then balance on account is set to
     *          initialBalance, otherwise balance is zero.
     *//*

    public Account(String accountName, double initialBalance) {
        id = nextAccountId++;
        name = accountName;
        if (initialBalance >= 0) {
            balance = initialBalance;
        } else {
            balance = 0;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    */
/*
     * REQUIRES: amount >= 0
     * MODIFIES: this
     * EFFECTS: amount is added to balance and updated
     * 			balance is returned
     *//*

    public double deposit(double amount) {
        balance = balance + amount;
        return balance;
    }

    */
/*
     * REQUIRES: amount >= 0 and amount <= getBalance()
     * MODIFIES: this
     * EFFECTS: amount is withdrawn from account and updated
     * 		    balance is returned
     *//*

    public double withdraw(double amount) {
        balance = balance - amount;
        return balance;
    }

    */
/*
     * EFFECTS: returns a string representation of account
     *//*

    @Override
    public String toString() {
        String balanceStr = String.format("%.2f", balance);  // get balance to 2 decimal places as a string
        return "[ id = " + id + ", name = " + name + ", "
                + "balance = $" + balanceStr + "]";
    }
}
*/
