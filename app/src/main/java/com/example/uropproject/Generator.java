package com.example.uropproject;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Generator
{
    public static String uploadciphertext() throws NoSuchAlgorithmException, ExecutionException, InterruptedException {
        ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256k1");
        ECPoint g=spec.getG();
        ECCurve curve =g.getCurve();

//        System.out.println("Generator :"+Main.label+" "+Main.devicecounter);
        Agreegator contract = Main.contract;
        BigInteger originaltext=Main.nextRandomBigInteger(Main.securityParameter);
        GeneratorFragment.plaintexts.add(originaltext);
        Encryption tEncryption=
                new Encryption(originaltext,Main.label,Main.msk.get(Main.devicecounter).getValue0(),Main.msk.get(Main.devicecounter).getValue1(),g, curve);
        ECPoint cipherText=tEncryption.getCipherText();
        List<BigInteger> x= Arrays.asList(new BigInteger[]{cipherText.normalize().getAffineXCoord().toBigInteger(), cipherText.normalize().getAffineYCoord().toBigInteger()});
//        System.out.println("Ready to appendCiphers");
        contract.appendCiphers(x,BigInteger.ONE).sendAsync().get();
//        System.out.println("Sent to appendCiphers");
        String message="Append round "+Main.label+"\n";
        message=message+"Here is the c("+Main.devicecounter+")'s information:\n";
        message=message+"The x coordinate of c("+Main.devicecounter+") is: "+cipherText.getAffineXCoord()+"\n";
        message=message+"The y coordinate of c("+Main.devicecounter+") is: "+cipherText.getAffineYCoord()+"\n"+"\n";
        return message;
    }
}
