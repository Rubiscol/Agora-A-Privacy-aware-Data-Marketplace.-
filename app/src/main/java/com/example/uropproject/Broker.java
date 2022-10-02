package com.example.uropproject;

import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.javatuples.Triplet;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Broker {
    private static ECPoint g=Main.g;
    private static ECPoint result;
    private static  ECPoint ga;
    private static ECPoint gafsk1;
    private  static ECPoint gafsk2;
    private static ECPoint ut1afsk1;
    private  static ECPoint ut2afsk2;
    public static BigInteger a;
    public static BigInteger getRandomA(Integer securityparameter){
        a=Main.nextRandomBigInteger(Main.securityParameter);
        return a;
    }
    public static String sendWithZNP() throws NoSuchAlgorithmException {
        g = Main.g;
        ga = g.multiply(a).normalize();
        gafsk1 = ga.multiply(Main.fsk.getValue0()).normalize();
        gafsk2 = ga.multiply(Main.fsk.getValue1()).normalize();
        ECPoint ut1 = Main.getut(Main.currentlabel).getValue0().normalize();
        ECPoint ut2 = Main.getut(Main.currentlabel).getValue1().normalize();
        ut1afsk1 = ut1.multiply(a).multiply(Main.fsk.getValue0()).normalize();
        ut2afsk2 = ut2.multiply(a).multiply(Main.fsk.getValue1()).normalize();
        String information="Send to consumer\n" + "g*a= " + ga.getAffineXCoord() + "\n"
                + "g*a*fsk(1)= " + gafsk1.getAffineXCoord() + "\n"
                + "g*a*fsk(2)= " + gafsk2.getAffineXCoord() + "\n"
                + "ut(1)*a*fsk(1)= " + ut1afsk1.getAffineXCoord() + "\n"
                + "ut(2)*a*fsk(2)= " + ut2afsk2.getAffineXCoord() + "\n";
        return information;
    }
    public static void prodCiphertext() throws NoSuchAlgorithmException, ExecutionException, InterruptedException {
        Agreegator contract = Main.contract;
        contract.goToCipherSumation().sendAsync().get();
        contract.prodCipher(BigInteger.ONE).sendAsync().get();
    }
    public static String getCiphertext() throws NoSuchAlgorithmException, ExecutionException, InterruptedException {
        Agreegator contract = Main.contract;
        Object[] point=contract.getfCiphertext().sendAsync().get().toArray();
        ECCurve curve=g.getCurve();
        BigInteger x= (BigInteger) point[0];
        BigInteger y=(BigInteger) point[1];
        ECPoint cipherpoint=curve.createPoint(x,y);
        result= cipherpoint.subtract(Main.getut(Main.currentlabel).getValue0().multiply(Main.fsk.getValue0()).normalize()).normalize();
        result= result.subtract(Main.getut(Main.currentlabel).getValue1().multiply(Main.fsk.getValue1()).normalize()).normalize();
        String information="The ciphertext from the blockchain is \n"+"The x coordinate of the result is \n"+ cipherpoint.getAffineXCoord()+"\n"
                +"The y coordinate of the result is \n"+cipherpoint.getAffineYCoord()+"\n"
                +"=======Decryption======\n"
                +"The g*X(t) from the ciphertext is \n"+"The x coordinate of the g*X(t) is \n"+ result.getAffineXCoord()+"\n" +"The y coordinate of the g*X(t) is \n"+result.getAffineYCoord()+"\n\n"
                ;
        return information;
    }
    public static ArrayList<ECPoint> getECPoints(){
        ArrayList<ECPoint> ecPoints=new ArrayList<>();
        ecPoints.add(ga);
        ecPoints.add(gafsk1);
        ecPoints.add(gafsk2);
        ecPoints.add(ut1afsk1);
        ecPoints.add(ut2afsk2);
        return ecPoints;
    }
    public static ArrayList<Triplet<ECPoint,ECPoint,BigInteger>> proverTest() throws NoSuchAlgorithmException {
        ArrayList<Triplet<ECPoint,ECPoint,BigInteger>> tests=new ArrayList<Triplet<ECPoint,ECPoint,BigInteger>>();
        Triplet<ECPoint,ECPoint,BigInteger> test0=singletest(g,ga, g.multiply(Main.fsk.getValue0()).normalize(),gafsk1, Main.fsk.getValue0());
        Triplet<ECPoint,ECPoint,BigInteger> test1=singletest(g,ga,g.multiply(Main.fsk.getValue1()).normalize(),gafsk2, Main.fsk.getValue1());
        Triplet<ECPoint,ECPoint,BigInteger> test2=singletest(g, Main.getut(Main.currentlabel).getValue0(),ga.multiply(Main.fsk.getValue0()).normalize(),ut1afsk1, Main.fsk.getValue0().multiply(a));
        Triplet<ECPoint,ECPoint,BigInteger> test3=singletest(g, Main.getut(Main.currentlabel).getValue1(),ga.multiply(Main.fsk.getValue1()).normalize(),ut2afsk2, Main.fsk.getValue1().multiply(a));
        tests.add(test0);
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        return tests;
    }
    public static boolean sendGA(){

        try {
            Agreegator contract = Main.contract;
            contract.uploada(a,BigInteger.valueOf(1)).sendAsync().get();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ECPoint getresult(){
        return result;
    }
    private static Triplet<ECPoint,ECPoint,BigInteger> singletest(ECPoint g, ECPoint h, ECPoint gy, ECPoint hy, BigInteger y) throws NoSuchAlgorithmException {


        BigInteger u = Main.nextRandomBigInteger(Main.securityParameter);
        ECPoint gu = g.multiply(u).normalize();
        ECPoint hu = h.multiply(u).normalize();
        ECPoint hashPoint = g.add(h);
        hashPoint = hashPoint.add(gu);
        hashPoint = hashPoint.add(hu);
        hashPoint = hashPoint.add(gy);
        hashPoint = hashPoint.add(hy);
        BigInteger c = SHA256Calculator.doSHA256(hashPoint.hashCode());
        BigInteger z = u.add(c.multiply(y));
        Triplet<ECPoint, ECPoint, BigInteger> H = new Triplet<>(gu, hu, z);
        return H;
    }
    public static BigInteger getXt(Integer t){
        BigInteger x=BigInteger.ZERO;
        for(int i = 0; i< Setup.w.size(); i++) {
            x=x.add(Setup.w.get(i).multiply(GeneratorFragment.plaintexts.get(i)));
        }
        return x;
    }
}
