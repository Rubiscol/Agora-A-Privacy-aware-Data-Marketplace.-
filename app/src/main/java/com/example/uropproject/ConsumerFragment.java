package com.example.uropproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

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
    private EditText t_textView;
    private Button t_confirm_button;
    private Button refresh_button;
    private TextView consumer_textView;
    private Button consumer_back_button;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button verify_button;
    private Button verify_button2;
    private String message;
    private static ECPoint g=Main.g;
    private static ECPoint ga;
    private static BigInteger  a;
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
        consumer_textView=view.findViewById(R.id.setup__info_textView);
        consumer_back_button=view.findViewById(R.id.consumer_back_button);
        verify_button=view.findViewById(R.id.verify_button);
        verify_button2=view.findViewById(R.id.verify_button2);
        return view;
    }

    public void receiveA(BigInteger a){
        this.a =a;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.t_confirm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Main.currentlabel=Integer.valueOf(String.valueOf(t_textView.getText()));
            }
        });
        view.findViewById(R.id.verify_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(Consumer.verifyGA()){
                        consumer_textView.setText("=====Please wait for the verification of a =====\n"+"The verification passes, ready to decrypt: \n");
                        String info=Consumer.verifyXt();
                        consumer_textView.append(info);
                    }
                    else{
                        consumer_textView.setText("The verification of g*a fails\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        view.findViewById(R.id.refresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message=Consumer.getInfoFromBroker();
                consumer_textView.setText(message);
            }

        });
        view.findViewById(R.id.verify_button).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                try {
                    if( Consumer.verifyZNP()){
                        message=message+"--------------"+"\n";
                        message=message+"The ZNP test passes"+"\n";
                        message=message+"--------------"+"\n";
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

}