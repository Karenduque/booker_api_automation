package com.booker.testing.utilities.commons;

public class Utilities {

     public static String getIdRandom(){
        String result = java.util.UUID.randomUUID().toString();
        return result;
    }
}
