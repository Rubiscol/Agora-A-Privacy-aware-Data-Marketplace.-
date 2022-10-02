package com.example.uropproject;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.math.ec.ECPoint;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

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
    public static String contractAddress ="0x9F1bdE3436586CeDDe4D1afEdf50982e8EA4B1B6";
    public static Agreegator contract =
            Agreegator.load(contractAddress, Web3j.build(new HttpService("https://goerli.infura.io/v3/ae2a80a1510d49bf92153f328ab6fa0a")),
            Credentials.create("4bbe1fe43741f6143dcdd4af42ed6c9ab3e53a8bbb7ecfbb6aeb5c7aa8d7863f"),  DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
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
        ECPoint x =g.multiply(h1).normalize();
        ECPoint y =g.multiply(h2).normalize();
        Pair<ECPoint, ECPoint> ut=new Pair<ECPoint, ECPoint>(x,y);
        return ut;
    }

}