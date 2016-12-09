package com.steve.strongpass;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Vector;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Created by Steve on 11/9/2016.
 */

public final class Crypto {
    private static final int keysPerSecond = 13000000;
    private static final String charSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_+=~`[]{}|\\:;\"\'<>,.?/";
    private static char[] lower = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static char[] upper = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static char[] num = {'0','1','2','3','4','5','6','7','8','9'};
    private static char[] symbol = {'!','@','#','$','%','^','&','*','(',')','-','_','+','=','~','`','[',']','{','}','|','\\',':',';','\"','\'','<','>',',','.','?','/' };
    private static int passwordLength = 16;

    private Crypto(){
    }

    public static String timeToCrack(int passwordLength){
        if (passwordLength == 0){
            return "";
        }
        BigInteger kps = new BigInteger(Integer.toString(keysPerSecond));
        BigInteger kpms =  new BigInteger(Integer.toString(keysPerSecond / 1000));
        BigInteger charSetLength = new BigInteger(Integer.toString(charSet.length()));
        BigInteger numCombinations = charSetLength.pow(passwordLength);
        //numCombinations = numCombinations.divide(new BigInteger("2"));
        BigInteger seconds =  numCombinations.divide(kps);
        BigInteger milliseconds = numCombinations.divide(kpms);
        System.out.println(seconds.toString());
        System.out.println(milliseconds.toString());

        BigInteger years = seconds.divide(new BigInteger("31536000"));

        if(years.compareTo(new BigInteger("1000")) == 1){
            return reduceYears(years);
        }else {
            int r_years = (int) Math.floor(seconds.longValue() / 31536000);
            int r_days = (int) Math.floor((seconds.longValue() % 31536000) / 86400);
            int r_hours = (int) Math.floor(((seconds.longValue() % 31536000) % 86400) / 3600);
            int r_mins = (int) Math.floor((((seconds.longValue() % 31536000) % 86400) % 3600) / 60);
            int r_secs = (int) Math.floor((((seconds.longValue() % 31536000) % 86400) % 3600) % 60);
            String total = "";

            if (milliseconds.intValue() < 60000 && r_mins < 1) {
                if(milliseconds.intValue() == 0)
                    return "less than a millisecond";
                else if(milliseconds.intValue() < 1000)
                    return Integer.toString(milliseconds.intValue()) +  " milliseconds";
                else
                    return Integer.toString(seconds.intValue()) + " second and " + Integer.toString(milliseconds.intValue() % 1000) + " milliseconds";
            }

            if (r_years != 0) {
                total = Integer.toString(r_years) + " years ";
            }

            if (r_days != 0 && r_years < 1000) {
                total = total + Integer.toString(r_days) + " days ";
            }

            if (r_hours != 0 && r_years < 1000) {
                total = total + Integer.toString(r_hours) + " hours ";
            }

            if (r_mins != 0 && r_years < 1000) {
                total = total + Integer.toString(r_mins) + " minutes ";
            }

            if (r_secs != 0 && r_years < 1000) {
                total = total + Integer.toString(r_secs) + " seconds ";
            }

            return total;
        }
    }


    private static String reduceYears(BigInteger years) {
        BigInteger thousand = new BigInteger("1000");
        BigInteger million =  new BigInteger("1000000");
        BigInteger billion =  new BigInteger("1000000000");
        BigInteger trillion =  new BigInteger("1000000000000");
        BigInteger quadrillion =  new BigInteger("1000000000000000");
        BigInteger quintillion =  new BigInteger("1000000000000000000");


        if (years.compareTo(million) == -1) {
            return years.divide(thousand).toString() + " thousand years";
        }
        else if ( years.compareTo(billion) == -1 ) {
            return years.divide(million).toString() + " million years";
        }
        else if( years.compareTo(trillion) == -1){
            return years.divide(billion).toString() + " billion years";
        }
        else if( years.compareTo(quadrillion) == -1) {
            return years.divide(trillion).toString() + " trillion years";
        }
        else if( years.compareTo(quintillion) == -1) {
            return years.divide(quadrillion).toString() + " quadrillion years";
        }
        else{
            return "a long ass time";
        }

    }


    public static String generate(){
        char[] password = new char[passwordLength];
        Vector<Integer> takenPositions = new Vector<>();
        SecureRandom random = new SecureRandom();
        int r;

        r = random.nextInt(passwordLength);
        password[r] = lower[random.nextInt(lower.length)];
        takenPositions.add(r);

        r = random.nextInt(passwordLength);
        while(takenPositions.contains(r)){
            r = random.nextInt(passwordLength);
        }
        password[r] = upper[random.nextInt(upper.length)];
        takenPositions.add(r);

        r = random.nextInt(passwordLength);
        while(takenPositions.contains(r)){
            r = random.nextInt(passwordLength);
        }
        password[r] = num[random.nextInt(num.length)];
        takenPositions.add(r);

        r = random.nextInt(passwordLength);
        while(takenPositions.contains(r)){
            r = random.nextInt(passwordLength);
        }
        password[r] = symbol[random.nextInt(symbol.length)];
        takenPositions.add(r);

        for(int i = 0; i < passwordLength-1; i++){
            if(!takenPositions.contains(i)){
                password[i] = charSet.charAt(random.nextInt(charSet.length()));
            }
        }


        return new String(password);
    }

    public static int strength(String password) {
        int length = password.length(),
            uppercase = 0,
            lowercase = 0,
            numbers = 0,
            symbols = 0,
            score = 0;

        for (int i = 0; i < length; i++) {
            if (Character.isUpperCase(password.charAt(i)))
                uppercase++;
            else if (Character.isLowerCase(password.charAt(i)))
                lowercase++;
            else if (Character.isDigit(password.charAt(i)))
                numbers++;
            else
                symbols++;
        }

        score += length * 4;
        score += (length - uppercase) * 2;
        score += (length - lowercase) * 2;
        score += numbers * 4;
        score += symbols * 6;
        score += (length - uppercase) * 2;
        if(uppercase > 0 && lowercase > 0 && numbers > 0 && symbols > 0)
            score += 8;
        if(uppercase + lowercase == length)
            score -= length;
        if(numbers == length)
            score -= length;


        return score;

    }

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte salt[] = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String generateHash(String input, byte[] salt)
    {
        String generatedHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedHash = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedHash;
    }

    public static boolean hasUpper(String input){

        for (int i = 0; i < input.length(); i++) {
            if (Character.isUpperCase(input.charAt(i)))
                return true;
        }
        return false;
    }

    public static boolean hasLower(String input){

        for (int i = 0; i < input.length(); i++) {
            if (Character.isLowerCase(input.charAt(i)))
                return true;
        }
        return false;
    }

    public static boolean hasNumber(String input){

        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i)))
                return true;
        }
        return false;
    }

    public static boolean hasSymbol(String input){

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isLetterOrDigit(input.charAt(i)))
                return true;
        }
        return false;
    }

}
