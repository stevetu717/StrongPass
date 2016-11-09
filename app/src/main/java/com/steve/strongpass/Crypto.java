package com.steve.strongpass;

/**
 * Created by Steve on 11/9/2016.
 */

public final class Crypto {
    private static int keysPerSecond;
    private String charSetString;
    public static char[] charSetArray;
    public static int passwordLength;

    private Crypto(){
        final int keysPerSecond = 2600000;
        charSetString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_+=~`[]{}|\\:;\"\'<>,.?/";
        charSetArray = charSetString.toCharArray();
        passwordLength = 12;
    }

    public static String timeToCrack(int passwordLength){
        double numCombinations = 0;
        int charsetLength = Crypto.charSetArray.length;
        for (int x=1; x<= passwordLength; x++)
            numCombinations = numCombinations + Math.pow(charsetLength, x);

        int seconds = (int) numCombinations/keysPerSecond;
        int r_years = (int)Math.floor(seconds / 31556926);
        int r_days = (int)Math.floor((seconds % 31556926) / 86400);
        int r_hours =(int) Math.floor(((seconds % 31556926) % 86400) / 3600);
        int r_mins =(int) Math.floor((((seconds % 31556926) % 86400) % 3600) / 60);
        int r_secs =(int) Math.floor((((seconds % 31556926) % 86400) % 3600) % 60);
        String total = "";

        if ( r_years >= 1000 ) {
            total = Crypto.reduceYears(r_years);
            //total = "More than 100 Years";
        } else if (r_years != 0) {
            total =  Integer.toString(r_years) + " years ";
        }

        if ( r_days != 0 && r_years < 1000 ) {
            total = total + Integer.toString(r_days) + " days ";
        }

        if ( r_hours != 0 && r_years < 1000 ) {
            total = total + Integer.toString(r_hours) + " hours ";
        }

        if ( r_mins != 0 && r_years < 1000 ) {
            total = total + Integer.toString(r_mins) + " minutes ";
        }

        if ( r_secs !=0 && r_years < 1000 ) {
            total = total + Integer.toString(r_secs) + " seconds ";
        }

        return total;
    }

    private static String reduceYears(int amount) {
        int thousand =    1000;
        int million =     1000000;
        int billion =     1000000000;
        long deathOfSun =  5000000000L;

        if ( amount < million ) {
            amount = Math.round(amount / thousand);
            return amount + " thousand years";
        } else if ( amount < billion ) {
            amount = Math.round(amount / million);
            return amount + " million years";
        } else if( amount < deathOfSun){
            amount = Math.round(amount / billion);
            return amount + " million years";
        } else{
            return "Until the death of our Sun";
        }

    }

}
