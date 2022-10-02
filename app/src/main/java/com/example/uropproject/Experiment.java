package com.example.uropproject;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class Experiment {
    public static String testsetup() throws NoSuchAlgorithmException, ExecutionException, InterruptedException {
        String result="";
        long startTime = System.currentTimeMillis();
        Setup.run();
        long setupTime = System.currentTimeMillis();
        result=result+("Time cost for setup()：" + (setupTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return result;
    }
    public static String testDKeyGen() throws NoSuchAlgorithmException, ExecutionException, InterruptedException{
        String result="";
        long startTime = System.currentTimeMillis();
        Setup.testDKeyGen();
        long endTime = System.currentTimeMillis();
        result=result+("Time cost for DkeyGen()：" + (endTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return result;
    }
    public static String testuploadciphertext() throws NoSuchAlgorithmException, ExecutionException, InterruptedException{
        String result="";
        long startTime = System.currentTimeMillis();
        for(int i=0;i<Main.n;i++) {
            Generator.uploadciphertext();
        }
        long endTime = System.currentTimeMillis();
        result=result+("Time cost for datagenerators()：" + (endTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return result;
    }
    public static String testgetciphertext() throws NoSuchAlgorithmException, ExecutionException, InterruptedException{
        String result="";
        long startTime = System.currentTimeMillis();
        Broker.getCiphertext();
        long endTime = System.currentTimeMillis();
        result=result+("Time cost for broker collecting ciphertext：" + (endTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return result;
    }
    public static String testZNP() throws NoSuchAlgorithmException, ExecutionException, InterruptedException{
        String result="";
        long startTime = System.currentTimeMillis();
        Broker.getRandomA(Main.securityParameter);
        Broker.sendWithZNP();
        Consumer.getInfoFromBroker();
        Consumer.verifyZNP();
        long endTime = System.currentTimeMillis();
        result=result+("Time cost for ZNP：" + (endTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return result;
    }
    public static String testGA() throws NoSuchAlgorithmException, ExecutionException, InterruptedException{
        String result="";
        long startTime = System.currentTimeMillis();
        Broker.sendGA();
        Consumer.getAfrombroker();
        Consumer.verifyGA();
        Consumer.verifyXt();
        long endTime = System.currentTimeMillis();
        result=result+("Time cost for verifying a：" + (endTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return result;
    }
}
