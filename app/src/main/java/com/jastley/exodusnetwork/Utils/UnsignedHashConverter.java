package com.jastley.exodusnetwork.Utils;

public class UnsignedHashConverter {

    public static String convert(Long hash){

        Long toSubtract = 4294967296L;
        Long unsignedVal = hash - toSubtract;
        return String.valueOf(unsignedVal);
    }

    public static String getPrimaryKey(String itemHash) {

        Long converted = Long.valueOf(itemHash);
        if(converted > 2147483647L){
            return UnsignedHashConverter.convert(Long.valueOf(itemHash));
        }
        else {
            return itemHash;
        }
    }
}
