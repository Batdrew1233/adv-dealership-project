package com.pluralsight.contract;

public abstract class Contract {
    private String date;
    private String name;
    private String email;
    private boolean isSold;

    public Contract(String date, String name, String email, boolean isSold) {
        this.date = date;
        this.name = name;
        this.email = email;
        this.isSold = isSold;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public abstract double getTotalPrice(double price);

    public abstract double getMonthlyPayment(double price);
}
