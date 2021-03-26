package com.afq.streetbank.Model;

public class Item {

    String key;

    String item;
    String userName;
    String desc;
    int price;
    String contact;

    boolean isDonate;
    boolean isSell;
    boolean isRent;

    public Item() {
    }

    public Item(String key, String item, String userName, String desc, int price, boolean isDonate, boolean isSell, boolean isRent) {
        this.key = key;
        this.item = item;
        this.userName = userName;
        this.desc = desc;
        this.price = price;
        this.isDonate = isDonate;
        this.isSell = isSell;
        this.isRent = isRent;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getkey() {
        return key;
    }

    public void setkey(String key) {
        this.key = key;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isDonate() {
        return isDonate;
    }

    public void setDonate(boolean donate) {
        isDonate = donate;
    }

    public boolean isSell() {
        return isSell;
    }

    public void setSell(boolean sell) {
        isSell = sell;
    }

    public boolean isRent() {
        return isRent;
    }

    public void setRent(boolean rent) {
        isRent = rent;
    }
}
