package Models;

import java.io.Serializable;

public class Transaction implements Serializable {

    private int Id;
    private double Amount;
    private CategoryModel CategoryModel;
    private String Description;
    private String Date;
    private int AccountId;

    public Transaction() { }

    public Transaction(double amount) {
        Amount = amount;
    }

    public Transaction(int id, double amount, CategoryModel categoryModel, String description, String date, int accountId) {
        Id = id;
        Amount = amount;
        CategoryModel = categoryModel;
        Description = description;
        Date = date;
        AccountId = accountId;
    }

    public Transaction(double amount, CategoryModel categoryModel, String description, String date, int accountId) {
        Amount = amount;
        CategoryModel = categoryModel;
        Description = description;
        Date = date;
        AccountId = accountId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public CategoryModel getCategoryModel() {
        return CategoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        CategoryModel = categoryModel;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }
}
