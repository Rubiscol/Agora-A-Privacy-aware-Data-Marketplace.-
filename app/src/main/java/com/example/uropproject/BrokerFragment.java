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

import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.javatuples.Triplet;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;


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
    public static BigInteger a=BigInteger.ZERO;
    private static ECPoint g=Main.g;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView broker_t;
    private TextView a_textView;
    private Button broker_generate_button;
    private TextView broker_textView;
    private Button broker_send_button;
    private Button broker_back_button;
    private Button broker_getcipher_button;

    private Button  broker_sendga_button;


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
        broker_t.setText("The round t you are given is: "+Main.currentlabel);
        a_textView=fragmentBrokerLayout.findViewById(R.id.a_textView);
        broker_generate_button=fragmentBrokerLayout.findViewById(R.id.broker_generate_button);
        broker_textView=fragmentBrokerLayout.findViewById(R.id.broker_textView);
        broker_send_button=fragmentBrokerLayout.findViewById(R.id.broker_send_button);
        broker_back_button =fragmentBrokerLayout.findViewById(R.id.broker_back_button);;
        broker_getcipher_button=fragmentBrokerLayout.findViewById(R.id.broker_getcipher_button);
        broker_sendga_button=fragmentBrokerLayout.findViewById(R.id.broker_sendga_button);
        a_textView.setText(a.toString());
        return fragmentBrokerLayout;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.broker_generate_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                a=Broker.getRandomA(Main.securityParameter);
                a_textView.setText(a.toString());

            }
        });
        view.findViewById(R.id.broker_sendga_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                try {
                if(Broker.sendGA()){
                    broker_textView.setText("Successfully upload a to the blockchain");
                }
                else{
                    broker_textView.setText("Error");
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        view.findViewById(R.id.broker_getcipher_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                try {
                    String information=Broker.getCiphertext();
                    broker_textView.setText(information);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        view.findViewById(R.id.broker_send_button).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                try {
                    String information=Broker.sendWithZNP(a);
                    broker_textView.setText(information);
                }catch (Exception e) {
                    e.printStackTrace();
                }

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





}