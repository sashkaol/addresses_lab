package com.university.arrays.addresses.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Address {

    @Valid
    @NotNull
    private String district;

    @Valid
    @NotNull
    private String street;

    @Valid
    @NotNull
    private String houseNumber;

    @Valid
    @Min(value = 1800)
    @Max(value = 2025)
    private int year;

    public Address() {}
    public Address(String district, String street, String houseNumber, int year) {
        this.district = district;
        this.street = street;
        this.houseNumber = houseNumber;
        this.year = year;
    }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getHouseNumber() { return houseNumber; }
    public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}

