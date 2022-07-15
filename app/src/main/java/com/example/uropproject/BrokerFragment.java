package com.example.uropproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrokerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrokerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static ECPoint g;
    private static BigInteger a;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView broker_t;
    private TextView a_textView;
    private Button broker_generate_button;
    private TextView broker_textView;
    private Button broker_send_button;
    private Button broker_back_button;

    private static  ECPoint ga;
    private static ECPoint gafsk1;
    private  static ECPoint gafsk2;
    private static ECPoint ut1afsk1;
    private  static ECPoint ut2afsk2;


    public BrokerFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BrokerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrokerFragment newInstance(String param1, String param2) {
        BrokerFragment fragment = new BrokerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentBrokerLayout =inflater.inflate(R.layout.fragment_broker, container, false);
        broker_t=fragmentBrokerLayout.findViewById(R.id.broker_t);
        broker_t.setText("The round t you are given is: "+testUROP.label);
        a_textView=fragmentBrokerLayout.findViewById(R.id.a_textView);
        broker_generate_button=fragmentBrokerLayout.findViewById(R.id.broker_generate_button);
        broker_textView=fragmentBrokerLayout.findViewById(R.id.broker_textView);
        broker_send_button=fragmentBrokerLayout.findViewById(R.id.broker_send_button);
        broker_back_button =fragmentBrokerLayout.findViewById(R.id.broker_back_button);;
        return fragmentBrokerLayout;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.broker_generate_button).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                a=testUROP.nextRandomBigInteger(testUROP.securityParameter);
                a_textView.setText(a.toString());

            }

        });
        view.findViewById(R.id.broker_send_button).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                 g=testUROP.g;
                 ga=g.multiply(a).normalize();
                 gafsk1=ga.multiply(testUROP.fsk.getKey()).normalize();
                 gafsk2=ga.multiply(testUROP.fsk.getValue()).normalize();
                 ECPoint ut1=testUROP.utEcPoint.getKey().normalize();
                 ECPoint ut2=testUROP.utEcPoint.getValue().normalize();
                 ut1afsk1=ut1.multiply(a).multiply(testUROP.fsk.getKey()).normalize();
                 ut2afsk2=ut2.multiply(a).multiply(testUROP.fsk.getValue()).normalize();
                 broker_textView.setText("Send to consumer\n"+"g*a= "+ga.getAffineXCoord()+"\n"
                         +"g*a*fsk(1)= "+gafsk1.getAffineXCoord()+"\n"
                         +"g*a*fsk(2)= "+gafsk2.getAffineXCoord()+"\n"
                         +"ut(1)*a*fsk(1)= "+ut1afsk1.getAffineXCoord()+"\n"
                         +"ut(2)*a*fsk(2)= "+ut2afsk2.getAffineXCoord()+"\n");

            }
        });
        view.findViewById(R.id.broker_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(BrokerFragment.this)
                        .navigate(R.id.action_BrokerFragment_to_MenuFragment);

            }
        });
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
        Triplet<ECPoint,ECPoint,BigInteger> test0=singletest(g,ga, g.multiply(testUROP.fsk.getKey()).normalize(),gafsk1,testUROP.fsk.getKey());
        Triplet<ECPoint,ECPoint,BigInteger> test1=singletest(g,ga,g.multiply(testUROP.fsk.getValue()).normalize(),gafsk2,testUROP.fsk.getValue());
        Triplet<ECPoint,ECPoint,BigInteger> test2=singletest(g,testUROP.utEcPoint.getKey(),ga.multiply(testUROP.fsk.getKey()).normalize(),ut1afsk1,testUROP.fsk.getKey().multiply(a));
        Triplet<ECPoint,ECPoint,BigInteger> test3=singletest(g,testUROP.utEcPoint.getValue(),ga.multiply(testUROP.fsk.getValue()).normalize(),ut2afsk2,testUROP.fsk.getValue().multiply(a));
        tests.add(test0);
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        return tests;
    }


    private static Triplet<ECPoint,ECPoint,BigInteger> singletest(ECPoint g, ECPoint h, ECPoint gy, ECPoint hy, BigInteger y) throws NoSuchAlgorithmException {


        BigInteger u = testUROP.nextRandomBigInteger(testUROP.securityParameter);
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


}