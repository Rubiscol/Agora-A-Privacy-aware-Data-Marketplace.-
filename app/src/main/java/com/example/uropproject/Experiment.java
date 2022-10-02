package com.example.uropproject;

import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.concurrent.ExecutionException;

public class Experiment {
    public String test() throws NoSuchAlgorithmException, ExecutionException, InterruptedException {
        String result="";
        long startTime = System.currentTimeMillis();
        SetupActivity.run();
        long setupTime = System.currentTimeMillis();
        result=result+("Time cost for setup()：" + (setupTime - startTime) + "ms"+"\n");
        SetupActivity.testDKeyGen();
        long dkeyGenTime = System.currentTimeMillis();
        result=result+("Time cost for DkeyGen()：" + (dkeyGenTime - setupTime) + "ms"+"\n");
        for(int i=0;i<Main.n;i++) {
            Generator.uploadciphertext();
        }
        long generatorTime = System.currentTimeMillis();
        result=result+("Time cost for datagenerators：" + (generatorTime - dkeyGenTime) + "ms"+"\n");


        return result;
    }
}
