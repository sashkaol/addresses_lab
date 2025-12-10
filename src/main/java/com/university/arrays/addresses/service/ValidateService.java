package com.university.arrays.addresses.service;

import com.university.arrays.addresses.dto.Address;
import org.springframework.stereotype.Service;

@Service
public class ValidateService {

    public boolean buildingYearIsCorrect(int year) {
        return year >= 1800 && year <= 2025;
    }

    public boolean districtIsCorrect(String district) {
        return district != null && !district.trim().isEmpty();
    }

    public boolean streetIsCorrect(String street) {
        return street != null && !street.trim().isEmpty();
    }

    public boolean houseNumberIsCorrect(String houseNumber) {
        return houseNumber != null && !houseNumber.trim().isEmpty();
    }

    public boolean rowIsCorrect(Address address) {
        return buildingYearIsCorrect(address.getYear())
                && districtIsCorrect(address.getDistrict())
                && streetIsCorrect(address.getStreet())
                && houseNumberIsCorrect(address.getHouseNumber());
    }

}
