package Models;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class AccountModel implements Serializable {
    //to send data
    private int id;
    private String accountName;
    private  String accountType;
    private double amount;
    private String accountNumber;
    private String accountDescription;
    private double balance;

    public AccountModel() {

    }
    public AccountModel(int id, String accountName, String accountType, double amount, String accountNumber, String accountDescription) {

        this.id = id;
        this.accountName = accountName;
        this.accountType = accountType;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.accountDescription = accountDescription;
    }


    public AccountModel( String accountName, String accountType, double amount, String accountNumber, String accountDescription) {

        this.accountName = accountName;
        this.accountType = accountType;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.accountDescription = accountDescription;
    }
    public AccountModel( int id, String accountName, String accountType, double amount, String accountNumber) {
        this.id = id;
        this.accountName = accountName;
        this.accountType = accountType;
        this.amount = amount;
        this.accountNumber = accountNumber;

    }
    public AccountModel( String accountName, String accountType, double amount, String accountNumber) {
        this.accountName = accountName;
        this.accountType = accountType;
        this.amount = amount;
        this.accountNumber = accountNumber;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountDescription() {
        return accountDescription;
    }

    public void setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
