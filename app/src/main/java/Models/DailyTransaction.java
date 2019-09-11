package Models;

import java.util.ArrayList;

public class DailyTransaction {

    String date;
    ArrayList<Transaction> transactions;

    public DailyTransaction(String date, ArrayList<Transaction> transactions) {
        this.date = date;
        this.transactions = transactions;
    }

    public DailyTransaction(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
}
