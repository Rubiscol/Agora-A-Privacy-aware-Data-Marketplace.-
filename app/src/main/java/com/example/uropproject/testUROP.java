package com.example.uropproject;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECCurve;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.javatuples.Triplet;

import javafx.util.Pair;

public class testUROP {
    public final static BigInteger TWO = BigInteger.valueOf(2);
    public final static BigInteger THREE = BigInteger.valueOf(3);
    public static Integer securityParameter;
    public static Integer n;
    public static ArrayList<Pair<BigInteger, BigInteger>> msk;
    public static Pair<BigInteger, BigInteger> fsk;
    public static ArrayList<BigInteger> w;
    public static HashMap<Integer,Pair<ECPoint, ECPoint>> utEcPoint;
    public static ECPoint g;
    public static Triplet<ArrayList<BigInteger>,ECPoint,ECPoint> fpkTriplet;
    public static Integer label;

    public static HashMap<Integer,ArrayList<ECPoint>>ciphertexts;
    public static HashMap<Integer,ArrayList<BigInteger>> plaintexts;

    public static void Setup() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        initiateLabel();
        msk=new ArrayList<>();
        w=new ArrayList<>();
        plaintexts=new HashMap<>();
        ciphertexts=new HashMap<>();
        utEcPoint=new HashMap<>();

        for(int i=0;i<n;i++) {
            BigInteger s_1=nextRandomBigInteger(securityParameter);
            BigInteger s_2=nextRandomBigInteger(securityParameter);
            Pair<BigInteger, BigInteger> s=new Pair<>(s_1,s_2);
            msk.add(s);
            w.add(nextRandomBigInteger(securityParameter));

        }
        ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256r1");
        g=spec.getG();

    }


    public static void testDKeyGen() {
        DKeyGen dKeyGen=new DKeyGen(msk, w);
        fpkTriplet=dKeyGen.getfpk(g, g.getCurve());
        fsk=dKeyGen.getfsk();
    }

    public static void testEncrypt(Integer t) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {

        ArrayList<BigInteger> singleciphertext=new ArrayList<>();
        for(int i=0;i<n;i++){
            singleciphertext.add(nextRandomBigInteger(securityParameter));
        }

        ArrayList<ECPoint> x=new ArrayList<>();
        ECCurve curve =g.getCurve();
        System.out.println("---------------------------------------");
        for(int i=0;i<w.size();i++) {
            Encryption tEncryption=new Encryption(singleciphertext.get(i),label,msk.get(i).getKey(),msk.get(i).getValue(),g, curve);
            ECPoint point=tEncryption.getCipherText();
            System.out.println("The x coordinate of cipherText "+(i+1)+" is: "+point.getAffineXCoord());
            System.out.println("The y coordinate of cipherText "+(i+1)+" is: "+point.getAffineYCoord());
            System.out.println("");
            utEcPoint.put(t,tEncryption.getut());
            x.add(point);

        }
        ciphertexts.put(t,x);
        plaintexts.put(t,singleciphertext);

    }

    public static ECPoint getCombinedCiphertext(Integer t){
        ECPoint cipherPoint=ciphertexts.get(t).get(0).multiply(w.get(0)).normalize();
        for(int i=1;i<w.size();i++) {
            ECPoint temPoint=ciphertexts.get(t).get(i).multiply(w.get(i)).normalize();
            cipherPoint=cipherPoint.add(temPoint).normalize();
        }

        System.out.println("The x coordinate of C("+t+") is: "+cipherPoint.getAffineXCoord());
        System.out.println("The y coordinate of C("+t+") is: "+cipherPoint.getAffineYCoord());
        System.out.println("");
        return cipherPoint;
    }

    public static BigInteger nextRandomBigInteger(Integer securityParameter) {
        BigInteger n=TWO.pow(securityParameter);
        Random rand = new Random();
        BigInteger result = new BigInteger(n.bitLength(), rand);
        while( result.compareTo(n) >= 0 ) {
            result = new BigInteger(n.bitLength(), rand);
        }
        return result;
    }
    public static void updateLabel(){
        label=label+1;
    }
    public static void createNewGenerator(){
        ArrayList<BigInteger> x=new ArrayList<>();
        for(int i=0;i<n;i++){
            x.add(nextRandomBigInteger(securityParameter));
        }
        plaintexts.put(label,x);
    }

    public static void initiateLabel(){
        label=0;
    }



}