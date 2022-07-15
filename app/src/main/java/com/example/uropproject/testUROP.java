package com.example.uropproject;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.ECPrivateKey;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECCurve;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import org.bouncycastle.util.encoders.Hex;
import org.javatuples.Triplet;

import javafx.util.Pair;

public class testUROP {
    public final static BigInteger TWO = BigInteger.valueOf(2);
    public final static BigInteger THREE = BigInteger.valueOf(3);
    public static Integer securityParameter=1000;
    public static ArrayList<Pair<BigInteger, BigInteger>> msk;
    public static Pair<BigInteger, BigInteger> fsk;
    public static ArrayList<BigInteger> w;
    public static Pair<ECPoint, ECPoint> utEcPoint;
    public static ECPoint g;
    public static Triplet<ArrayList<BigInteger>,ECPoint,ECPoint> fpkTriplet;
    public static BigInteger label;
    public static void Setup() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {

        msk=new ArrayList<>();
        w=new ArrayList<>();

        int n=20;
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
        DKeyGen dKeyGen=new DKeyGen(msk, w, TWO.pow(securityParameter));
        fpkTriplet=dKeyGen.getfpk(g, g.getCurve());
        fsk=dKeyGen.getfsk();
    }

    public static void testEncrypt() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {


        ECCurve curve =g.getCurve();
        BigInteger messageBigInteger=nextRandomBigInteger(1000);



        System.out.println("---------------------------------------");
        for(int i=0;i<w.size();i++) {
            Encryption tEncryption=new Encryption(messageBigInteger,label,msk.get(i).getKey(),msk.get(i).getValue(),g, curve);
            ECPoint point=tEncryption.getCipherText();
            System.out.println("The x coordinate of cipherText "+(i+1)+" is: "+point.getAffineXCoord());
            System.out.println("The y coordinate of cipherText "+(i+1)+" is: "+point.getAffineYCoord());
            System.out.println("");
            utEcPoint=tEncryption.getut();

        }

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

    private static String getPrivateKeyAsHex(PrivateKey privateKey) {

        ECPrivateKey ecPrivateKey = (ECPrivateKey) privateKey;
        byte[] privateKeyBytes = new byte[24];
        writeToStream(privateKeyBytes, 0, ecPrivateKey.getS(), 24);

        return Hex.toHexString(privateKeyBytes);
    }
    private static void writeToStream(byte[] stream, int start, BigInteger value, int size) {
        byte[] data = value.toByteArray();
        int length = Math.min(size, data.length);
        int writeStart = start + size - length;
        int readStart = data.length - length;
        System.arraycopy(data, readStart, stream, writeStart, length);
    }

}