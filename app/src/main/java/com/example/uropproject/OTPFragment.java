package com.example.uropproject;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OTPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OTPFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Integer lambada;
    private int prime;
    private int key;
    private int message;
    private EditText editTextParameter;
    private EditText editTextKey;
    private EditText editTextMessage;
    private TextView viewTextRandom;

    private TextView prime_textview;
    private TextView key_textview;
    private TextView message_textview;
    private Button prime_random_button;
    private Button prime_confirm_button;
    private Button key_confirm_button;
    private Button message_confirm_button;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OTPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OTPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OTPFragment newInstance(String param1, String param2) {
        OTPFragment fragment = new OTPFragment();
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

        View fragmentOTPLayout=inflater.inflate(R.layout.fragment_otp,container,false);
        editTextParameter =fragmentOTPLayout.findViewById(R.id.editTextParameter);
        editTextKey =fragmentOTPLayout.findViewById(R.id.editTextKey);
        editTextMessage=fragmentOTPLayout.findViewById(R.id.editTextMessage);
        viewTextRandom=fragmentOTPLayout.findViewById(R.id.random_primenumber);
        prime_textview=fragmentOTPLayout.findViewById(R.id.prime_textview);
        key_textview=fragmentOTPLayout.findViewById(R.id.key_textview);
        message_textview=fragmentOTPLayout.findViewById(R.id.message_textview);
        prime_random_button=fragmentOTPLayout.findViewById(R.id.prime_random_button);
        prime_confirm_button=fragmentOTPLayout.findViewById(R.id.prime_confirm_button);
        key_confirm_button=fragmentOTPLayout.findViewById(R.id.key_confirm_button);
        message_confirm_button=fragmentOTPLayout.findViewById(R.id.message_confirm_button);



//        editTextParameter.setVisibility(View.INVISIBLE);
        editTextKey.setVisibility(View.INVISIBLE);
        editTextMessage.setVisibility(View.INVISIBLE);
        viewTextRandom.setVisibility(View.INVISIBLE);
        prime_textview.setVisibility(View.INVISIBLE);
        key_textview.setVisibility(View.INVISIBLE);
        message_textview.setVisibility(View.INVISIBLE);
        prime_random_button.setVisibility(View.INVISIBLE);
        prime_confirm_button.setVisibility(View.INVISIBLE);
        key_confirm_button.setVisibility(View.INVISIBLE);
        message_confirm_button.setVisibility(View.INVISIBLE);



        prime_random_button.setEnabled(false);
        prime_confirm_button.setEnabled(false);
        key_confirm_button.setEnabled(false);
        message_confirm_button.setEnabled(false);


        return fragmentOTPLayout;

    }
    boolean checkPrime(int n) {
        if (n == 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); ++i) {
            // checking the prime number
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.back_button_otp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(OTPFragment.this)
                        .navigate(R.id.action_OTPFragment_to_MenuFragment);

            }
        });
        view.findViewById(R.id.parameter_confirm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lambada= new Integer(editTextParameter.getText().toString());
                if (lambada>0) {
                    viewTextRandom.setVisibility(View.VISIBLE);
                    prime_textview.setVisibility(View.VISIBLE);
                    prime_random_button.setVisibility(View.VISIBLE);
                    prime_confirm_button.setVisibility(View.VISIBLE);
                    prime_random_button.setEnabled(true);
                    prime_confirm_button.setEnabled(true);
                    editTextKey.setHint("0 - "+(int) Math.pow(2, lambada));
                    editTextMessage.setHint("0 - "+(int) Math.pow(2, lambada));

                }
                else {

                }


            }
        });

        view.findViewById(R.id.prime_random_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Random random = new Random();
                if (lambada > 0) {
                        int randomNumber;
                       while (!checkPrime( randomNumber=random.nextInt((int) Math.pow(2, lambada)))){

                       }

                    viewTextRandom.setText(String.valueOf(randomNumber));

                }

            }



        });
        view.findViewById(R.id.prime_confirm_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                prime=new Integer(viewTextRandom.getText().toString());
                key_textview.setVisibility(View.VISIBLE);
                key_confirm_button.setVisibility(View.VISIBLE);
                editTextKey.setVisibility(View.VISIBLE);
                key_confirm_button.setEnabled(true);

            }

        });
        view.findViewById(R.id.key_confirm_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                key=new Integer(editTextKey.getText().toString());
                if(key>0 &&  key<(int) Math.pow(2, lambada)) {
                    editTextMessage.setVisibility(View.VISIBLE);
                    message_textview.setVisibility(View.VISIBLE);
                    message_confirm_button.setVisibility(View.VISIBLE);
                    message_confirm_button.setEnabled(true);

                }
            }

        });
        view.findViewById(R.id.message_confirm_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                message=new Integer(editTextMessage.getText().toString());
                if(message>0 &&  message<(int) Math.pow(2, lambada)) {
                    TextView ciphertext = view.getRootView().findViewById(R.id.ciphertext);
                    ciphertext.setText("Ciphertext: "+String.valueOf(EncryptWithOTP(message)));

                }
            }

            private int EncryptWithOTP(int message) {
                int x=(int) Math.pow(message, key);

                return x%prime;
            }

        });
     

    }
}