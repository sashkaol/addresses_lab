package com.university.arrays.addresses.exception;

public class InvalidAddressFormatException extends RuntimeException {
    public InvalidAddressFormatException(String error) {
        super(error);
    }
}
