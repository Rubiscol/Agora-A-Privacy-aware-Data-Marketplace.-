package com.example.uropproject;

import org.bouncycastle.math.ec.ECPoint;
import org.javatuples.Triplet;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Consumer {
    public static ECPoint g=Main.g;
    private static ECPoint ga;
    private static BigInteger a;
    private static BigInteger checkga;
    public static String getInfoFromBroker(){
        ArrayList<ECPoint>ecPoints=Broker.getECPoints();
        ga=ecPoints.get(0);
        ECPoint gafsk1=ecPoints.get(1);
        ECPoint gafsk2=ecPoints.get(2);
        ECPoint ut1afsk1=ecPoints.get(3);
        ECPoint ut2afsk2=ecPoints.get(4);
        String message="Received from broker: "+"\n"
                +"g*a= "+ga.getAffineXCoord()+"\n"
                +"g*a*fsk(1)= "+gafsk1.getAffineXCoord()+"\n"
                +"g*a*fsk(2)= "+gafsk2.getAffineXCoord()+"\n"
                +"ut(1)*a*fsk(1)= "+ut1afsk1.getAffineXCoord()+"\n"
                +"ut(2)*a*fsk(2)= "+ut2afsk2.getAffineXCoord()+"\n"
                +"Ready to verify the above values..."+"\n";
        return message;
    }
    public static String verifyXt(){
        String info="";
        info=info+("The X("+Main.currentlabel+")*g result's information are listed below"+"\n");
        BigInteger xt= Broker.getXt(Main.currentlabel);
        info=info+("====================\n"+xt+"\n"+"====================\n");
        ECPoint gx=g.multiply(xt).normalize();
        info=info+"Whether the given X(t) satisfy X(t)*g==result: \n";
        info=info+"The x coordinate of the X(t)*g is \n"+gx.getAffineXCoord()+"\n";
        info=info+"The y coordinate of the X(t)*g is \n"+gx.getAffineYCoord()+"\n";
        info=info+"Verification result of X(t): "+gx.equals(Broker.getresult())+"\n";


        return info;
    }
    public static boolean verifyGA() throws ExecutionException, InterruptedException {
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/e732435ba40d4d2c948ab4a9d3eace97"));
        Web3ClientVersion web3ClientVersion = (Web3ClientVersion)web3j.web3ClientVersion().sendAsync().get();
        Credentials credentials = Credentials.create("4bbe1fe43741f6143dcdd4af42ed6c9ab3e53a8bbb7ecfbb6aeb5c7aa8d7863f");
        new RawTransactionManager(web3j, credentials, 4L);
        String contractAddress =Main.contractAddress;
        ExperimentAgreegator contract = ExperimentAgreegator.load(contractAddress, web3j, credentials,  DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);

        List<BigInteger> x= Arrays.asList(new BigInteger[]{ga.getAffineXCoord().toBigInteger(),ga.getAffineYCoord().toBigInteger()});
        a=getAfrombroker();

        String message="The x coordinate of the g*a is \n"+g.multiply(a).normalize().getAffineXCoord()+"\n";
        message=message+"The y coordinate of the g*a is \n"+g.multiply(a).normalize().getAffineYCoord()+"\n";
        message=message+"=====Please wait for the verification of a =====\n";


        checkga=contract.verifyGA(x,BigInteger.ONE).sendAsync().get();
        return checkga.equals(BigInteger.ONE);


    }
    public static boolean verify() throws NoSuchAlgorithmException {

        ArrayList<ECPoint> ecPoints=Broker.getECPoints();
        ECPoint ga=ecPoints.get(0);
        ECPoint gafsk1=ecPoints.get(1);
        ECPoint gafsk2=ecPoints.get(2);
        ECPoint ut1afsk1=ecPoints.get(3);
        ECPoint ut2afsk2=ecPoints.get(4);
        Triplet<ECPoint,ECPoint, BigInteger> H0=Broker.proverTest().get(0);
        Triplet<ECPoint,ECPoint,BigInteger> H1=Broker.proverTest().get(1);
        Triplet<ECPoint,ECPoint,BigInteger> H2=Broker.proverTest().get(2);
        Triplet<ECPoint,ECPoint,BigInteger> H3=Broker.proverTest().get(3);
        Boolean boolean0=verifierTest(g,ga, g.multiply(Main.fsk.getValue0()),gafsk1,H0);
        Boolean boolean1=verifierTest(g,ga,g.multiply(Main.fsk.getValue1()),gafsk2,H1);
        Boolean boolean2=verifierTest(g, Main.getut(Main.currentlabel).getValue0(),ga.multiply(Main.fsk.getValue0()),ut1afsk1,H2);
        Boolean boolean3=verifierTest(g, Main.getut(Main.currentlabel).getValue1(),ga.multiply(Main.fsk.getValue1()),ut2afsk2,H3);

        return boolean0&&boolean1&&boolean2&&boolean3;
    }
    public static BigInteger getAfrombroker(){
        return Broker.a;
    }

    public static boolean verifierTest( ECPoint g, ECPoint h, ECPoint yg, ECPoint yh, Triplet<ECPoint,ECPoint,BigInteger> H) throws NoSuchAlgorithmException {

        BigInteger z=H.getValue2();
        ECPoint ug=H.getValue0();
        ECPoint uh=H.getValue1();
        ECPoint hashPoint=g.add(h);
        hashPoint=hashPoint.add(ug).normalize();
        hashPoint=hashPoint.add(uh).normalize();
        hashPoint=hashPoint.add(yg).normalize();
        hashPoint=hashPoint.add(yh).normalize();
        BigInteger c=SHA256Calculator.doSHA256(hashPoint.hashCode());
        ECPoint zg=g.multiply(z).normalize();
        ECPoint cgy=yg.multiply(c).normalize();
        ECPoint ugcgy=cgy.add(ug).normalize();
        ECPoint zh=h.multiply(z).normalize();
        ECPoint chy=yh.multiply(c).normalize();

        ECPoint uhchy=chy.add(uh).normalize();
        System.out.println(zg.equals(ugcgy));
        if(zg.equals(ugcgy)&& zh.equals(uhchy)) {
            return true;
        }
        else {
            return false;
        }

    }
}
