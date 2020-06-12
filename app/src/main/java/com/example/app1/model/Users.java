package com.example.app1.model;

public class Users {
    private int ID;
    private String userName;
    private String passWord;
    private String address;
    private String phoneNumber;
    private String create_at;

    public Users() {
    }

    public Users(int ID, String userName, String passWord, String address, String phoneNumber, String create_at) {
        this.ID = ID;
        this.userName = userName;
        this.passWord = passWord;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.create_at = create_at;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String toString (){return ID+":"+userName+","+passWord+","+address+","+phoneNumber+","+create_at.toString()+",";}
}

