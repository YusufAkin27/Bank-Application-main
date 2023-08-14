package com.example.finalproject.account.entity.enums;

public enum Maturity {
    OTUZ_GUN(30),
    ALTMIS_GUN(60),
    DOKSAN_GUN(90),
    YUZ_SEKSEN_GUN(180);

    private final int value;

    Maturity(int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }

}
