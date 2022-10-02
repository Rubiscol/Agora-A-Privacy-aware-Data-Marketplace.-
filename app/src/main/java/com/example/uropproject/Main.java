package com.example.uropproject;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.math.ec.ECPoint;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.math.BigInteger;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;


public class Main {
    public static Integer label=1;
    public static ECPoint g= ECNamedCurveTable.getParameterSpec("secp256k1").getG();
    public static Integer securityParameter;
    public static Integer n;
    public static Integer devicecounter=0;
    public static ArrayList<Pair<BigInteger, BigInteger>> msk;
    public static Pair<BigInteger, BigInteger> fsk;
    public static Triplet<ArrayList<BigInteger>, ECPoint,ECPoint> fpkTriplet;
    public static Integer currentlabel=1;
    public static void initiateLabel(){
        label=1;
    }
    private static void updateLabel(){
        label=label+1;
    }
    public static String contractAddress ="0x6A7EBac0771D919Cfe2e93c60AE64e81B66d660f";
    public static void updateDevicecounter(){
        if(devicecounter>=n){
            resetLabelDevicecounter();
            updateLabel();
        }
        else{
            devicecounter++;
        }

    }
    public static void resetLabelDevicecounter(){
        devicecounter=0;
    }


    public static BigInteger nextRandomBigInteger(Integer securityParameter) {
        BigInteger n=BigInteger.valueOf(2).pow(securityParameter);
        Random rand = new Random();
        BigInteger result = new BigInteger(n.bitLength(), rand);
        while( result.compareTo(n) >= 0 ) {
            result = new BigInteger(n.bitLength(), rand);
        }
        return result;
    }

    public static Pair<ECPoint, ECPoint> getut(Integer t) throws NoSuchAlgorithmException {
        BigInteger h1=SHA256Calculator.doSHA256(t);
        BigInteger h2=SHA256Calculator.doSHA256(h1);
        Pair<ECPoint, ECPoint> ut=new Pair<ECPoint, ECPoint>(g.multiply(h1).normalize(), g.multiply(h2).normalize());
        return ut;
    }

}