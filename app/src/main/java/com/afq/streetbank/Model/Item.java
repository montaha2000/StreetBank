package com.afq.streetbank.Model;

public class Item {

    String key;

    String img;
    String item;
    String userName;
    String desc;
    double price;

    boolean isBorrow;
    boolean isSell;
    boolean isRent;

    public Item() {
    }

    public Item(String key, String img, String item, String userName, String desc, double price, boolean isBorrow, boolean isSell, boolean isRent) {
        this.key = key;
        this.img = img;
        this.item = item;
        this.userName = userName;
        this.desc = desc;
        this.price = price;
        this.isBorrow = isBorrow;
        this.isSell = isSell;
        this.isRent = isRent;
    }

    public String getkey() {
        return key;
    }

    public void setkey(String key) {
        this.key = key;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isBorrow() {
        return isBorrow;
    }

    public void setBorrow(boolean borrow) {
        isBorrow = borrow;
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
