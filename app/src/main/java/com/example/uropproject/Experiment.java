package com.example.uropproject;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class Experiment {
    public static long testFE_setup() throws NoSuchAlgorithmException, ExecutionException, InterruptedException {
        String result="";
        long startTime = System.currentTimeMillis();
        Setup.FE_Setup();
        long setupTime = System.currentTimeMillis();
        result=result+("Time cost for FE_setup()：" + (setupTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return (setupTime - startTime);
    }
    public static long testSC_setup() throws Exception {
        String result="";
        long startTime = System.currentTimeMillis();
        Setup.SetupWithContract();
        long setupTime = System.currentTimeMillis();
        result=result+("Time cost for SetupWithContract()：" + (setupTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return (setupTime - startTime);
    }

    public static long testDKeyGen() throws NoSuchAlgorithmException, ExecutionException, InterruptedException{
        String result="";
        long startTime = System.currentTimeMillis();
        Setup.testDKeyGen();
        long endTime = System.currentTimeMillis();
        result=result+("Time cost for DkeyGen()：" + (endTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return (endTime - startTime);
    }
    public static long testuploadciphertext() throws NoSuchAlgorithmException, ExecutionException, InterruptedException{
        String result="";
        long startTime = System.currentTimeMillis();
        for(int i=0;i<Main.n;i++) {
            Generator.uploadciphertext();
        }
        long endTime = System.currentTimeMillis();
        result=result+("Time cost for data generator：" + (endTime - startTime)/Main.n + "ms"+"\n");
        System.out.println(result);
        return (endTime - startTime);
    }

    public static long testprodciphertext() throws NoSuchAlgorithmException, ExecutionException, InterruptedException{
        String result="";
        long startTime = System.currentTimeMillis();
        Broker.prodCiphertext();
        long endTime = System.currentTimeMillis();
        result=result+("Time cost for combining ciphertext：" + (endTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return (endTime - startTime);
    }
    public static long testgetciphertext() throws NoSuchAlgorithmException, ExecutionException, InterruptedException{
        String result="";
        long startTime = System.currentTimeMillis();
        Broker.getCiphertext();
        long endTime = System.currentTimeMillis();
        result=result+("Time cost for broker getting ciphertext：" + (endTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return (endTime - startTime);
    }
    public static long testBrokerZNP() throws NoSuchAlgorithmException, ExecutionException, InterruptedException{
        String result="";
        long startTime = System.currentTimeMillis();
        Broker.getRandomA(Main.securityParameter);
        Broker.sendWithZNP();
        long endTime = System.currentTimeMillis();
        result=result+("Time cost for ZNP：" + (endTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return (endTime - startTime);
    }
    public static long testConsumerZNP() throws NoSuchAlgorithmException, ExecutionException, InterruptedException{
        String result="";
        long startTime = System.currentTimeMillis();
        Consumer.getInfoFromBroker();
        Consumer.verifyZNP();
        long endTime = System.currentTimeMillis();
        result=result+("Time cost for ZNP：" + (endTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return (endTime - startTime);
    }
    public static long testUploadGA() throws NoSuchAlgorithmException, ExecutionException, InterruptedException{
        String result="";
        long startTime = System.currentTimeMillis();
        Broker.sendGA();
        long endTime = System.currentTimeMillis();
        result=result+("Time cost for uploading ga：" + (endTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return (endTime - startTime);
    }
    public static long testVerifyA() throws NoSuchAlgorithmException, ExecutionException, InterruptedException{
        String result="";
        long startTime = System.currentTimeMillis();
        Consumer.getAfrombroker();
        Consumer.verifyGA();
        Consumer.verifyXt();
        long endTime = System.currentTimeMillis();
        result=result+("Time cost for verifying a：" + (endTime - startTime) + "ms"+"\n");
        System.out.println(result);
        return (endTime - startTime);
    }
}
