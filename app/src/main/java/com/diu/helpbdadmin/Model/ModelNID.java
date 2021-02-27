package com.diu.helpbdadmin.Model;

public class ModelNID {
    private String applied;
    private String balance;
    private String bankAccount;
    private String district;
    private String gotFund;
    private String name;
    private String nidNo;
    private String union;
    private String upazila;
    private String fatherName;
    private String motherName;
    private String birthDate;

    public ModelNID() {
    }

    public ModelNID(String applied, String balance, String bankAccount, String district, String gotFund, String name, String nidNo, String union, String upazila, String fatherName, String motherName, String birthDate) {
        this.applied = applied;
        this.balance = balance;
        this.bankAccount = bankAccount;
        this.district = district;
        this.gotFund = gotFund;
        this.name = name;
        this.nidNo = nidNo;
        this.union = union;
        this.upazila = upazila;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.birthDate = birthDate;
    }

    public String getApplied() {
        return applied;
    }

    public void setApplied(String applied) {
        this.applied = applied;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGotFund() {
        return gotFund;
    }

    public void setGotFund(String gotFund) {
        this.gotFund = gotFund;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNidNo() {
        return nidNo;
    }

    public void setNidNo(String nidNo) {
        this.nidNo = nidNo;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public String getUpazila() {
        return upazila;
    }

    public void setUpazila(String upazila) {
        this.upazila = upazila;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
