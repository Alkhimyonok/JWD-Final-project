package com.epam.jwd.web_app.bean;

public class BankBill {
    private long id;
    private long code;
    private long bill;

    public BankBill(long id, long code) {
        this.id = id;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getBill() {
        return bill;
    }

    public void setBill(long bill) {
        this.bill = bill;
    }
}
