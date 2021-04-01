package com.bharat.parkinglot.model;

public class ApplePay implements PaymentMode {

    @Override
    public boolean pay() {
        return false;
    }

    @Override
    public String type() {
        return "Apple Pay";
    }

}
