package com.university.arrays.addresses.dto;

public class Address {
    private String district;
    private String street;
    private String houseNumber;
    private int year;

    // Конструктор
    public Address() {}
    public Address(String district, String street, String houseNumber, int year) {
        this.district = district;
        this.street = street;
        this.houseNumber = houseNumber;
        this.year = year;
    }

    // Getters/Setters
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getHouseNumber() { return houseNumber; }
    public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}

