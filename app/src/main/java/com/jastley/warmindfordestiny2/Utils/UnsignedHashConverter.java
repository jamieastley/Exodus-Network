package com.jastley.warmindfordestiny2.Utils;

public class UnsignedHashConverter {

    public static String convert(Long hash){

        Long toSubtract = 4294967296L;
        Long unsignedVal = hash - toSubtract;
        return String.valueOf(unsignedVal);
    }
}
