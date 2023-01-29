package com.booker.testing.utilities.commons;

public class Utilities {

     public static String getIdRandom(){
        String result = (int)(Math.random()*100+1)+100000+"";
        return result;
    }
}
