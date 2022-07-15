package com.example.uropproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.bouncycastle.math.ec.ECPoint;
import org.javatuples.Triplet;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsumerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsumerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView t_textView;
    private Button t_confirm_button;
    private Button refresh_button;
    private TextView consumer_textView;
    private Button consumer_back_button;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button verify_button;
    private String message;
    private static ECPoint g;
    public ConsumerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsumerFragment newInstance(String param1, String param2) {
        ConsumerFragment fragment = new ConsumerFragment();
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
        View view=inflater.inflate(R.layout.fragment_consumer, container, false);
        t_textView=view.findViewById(R.id.t_textView);
        t_confirm_button=view.findViewById(R.id.t_confirm_button);
        refresh_button=view.findViewById(R.id.refresh_button);
        consumer_textView=view.findViewById(R.id.consumer_textView);
        consumer_back_button=view.findViewById(R.id.consumer_back_button);
        verify_button=view.findViewById(R.id.verify_button);
        if(testUROP.label!=null){
            t_textView.setText("t is " +testUROP.label.toString());
        }
        else{
            t_textView.setText("Please enter the t");
        }
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.t_confirm_button).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                testUROP.label=testUROP.nextRandomBigInteger(testUROP.securityParameter);
                t_textView.setText( "t is " +testUROP.label.toString());

                try {
                    testUROP.Setup();
                    testUROP.testDKeyGen();
                    testUROP.testEncrypt();

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                }

            }

        });
        view.findViewById(R.id.refresh_button).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                ArrayList<ECPoint>ecPoints=BrokerFragment.getECPoints();
                ECPoint ga=ecPoints.get(0);
                ECPoint gafsk1=ecPoints.get(1);
                ECPoint gafsk2=ecPoints.get(2);
                ECPoint ut1afsk1=ecPoints.get(3);
                ECPoint ut2afsk2=ecPoints.get(4);
                message="Received from broker: "+"\n"
                        +"g*a= "+ga.getAffineXCoord()+"\n"
                        +"g*a*fsk(1)= "+gafsk1.getAffineXCoord()+"\n"
                        +"g*a*fsk(2)= "+gafsk2.getAffineXCoord()+"\n"
                        +"ut(1)*a*fsk(1)= "+ut1afsk1.getAffineXCoord()+"\n"
                        +"ut(2)*a*fsk(2)= "+ut2afsk2.getAffineXCoord()+"\n"
                        +"Ready to verify the above values..."+"\n";
                consumer_textView.setText(message);
            }

        });
        view.findViewById(R.id.verify_button).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                try {
                    if(verify()){
                        message=message+"The test passes"+"\n";
                        message=message+"Ready to take further steps"+"\n";
                        consumer_textView.setText(message);
                    }
                    else{
                        message=message+"The test fails to pass"+"\n";
                        consumer_textView.setText(message);
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            }

        });
        view.findViewById(R.id.consumer_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ConsumerFragment.this)
                        .navigate(R.id.action_ConsumerFragment_to_MenuFragment);

            }
        });


    }
    public boolean verify() throws NoSuchAlgorithmException {
        g=testUROP.g;
        ArrayList<ECPoint>ecPoints=BrokerFragment.getECPoints();
        ECPoint ga=ecPoints.get(0);
        ECPoint gafsk1=ecPoints.get(1);
        ECPoint gafsk2=ecPoints.get(2);
        ECPoint ut1afsk1=ecPoints.get(3);
        ECPoint ut2afsk2=ecPoints.get(4);
        Triplet<ECPoint,ECPoint,BigInteger> H0=BrokerFragment.proverTest().get(0);
        Triplet<ECPoint,ECPoint,BigInteger> H1=BrokerFragment.proverTest().get(1);
        Triplet<ECPoint,ECPoint,BigInteger> H2=BrokerFragment.proverTest().get(2);
        Triplet<ECPoint,ECPoint,BigInteger> H3=BrokerFragment.proverTest().get(3);
        Boolean boolean0=verifierTest(g,ga, g.multiply(testUROP.fsk.getKey()),gafsk1,H0);
        Boolean boolean1=verifierTest(g,ga,g.multiply(testUROP.fsk.getValue()),gafsk2,H1);
        Boolean boolean2=verifierTest(g,testUROP.utEcPoint.getKey(),ga.multiply(testUROP.fsk.getKey()),ut1afsk1,H2);
        Boolean boolean3=verifierTest(g,testUROP.utEcPoint.getValue(),ga.multiply(testUROP.fsk.getValue()),ut2afsk2,H3);

        return boolean0&&boolean1&&boolean2&&boolean3;
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