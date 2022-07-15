package com.example.uropproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.uropproject.databinding.FragmentAesBinding;
import com.example.uropproject.databinding.FragmentMenuBinding;
import com.example.uropproject.databinding.FragmentSecondBinding;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AESFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AESFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText input;
    private TextView output;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AESFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AESFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AESFragment newInstance(String param1, String param2) {
        AESFragment fragment = new AESFragment();
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
        View fragmentAESLayout=inflater.inflate(R.layout.fragment_aes,container,false);
        input =fragmentAESLayout.findViewById(R.id.message_plaintext);
        output =fragmentAESLayout.findViewById(R.id.AES_message);
        return fragmentAESLayout;

    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.confirm_button_aes).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view){
                String secretMessage=input.getText().toString();
                String ciphertext="";
                try {
                    ciphertext=EncryptWithAES(secretMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                output.setText(ciphertext);
            }

            private String EncryptWithAES(String secretMessage) throws Exception{
                byte[] plaintext = secretMessage.getBytes();
                KeyGenerator keygen = KeyGenerator.getInstance("AES");
                keygen.init(256);
                SecretKey key = keygen.generateKey();
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] ciphertextinitial = cipher.doFinal(plaintext);
                byte[] iv = cipher.getIV();

                return ciphertextinitial.toString();

            }
        });
        view.findViewById(R.id.back_button_aes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AESFragment.this)
                        .navigate(R.id.action_AESFragment_to_MenuFragment);

            }
        });
    }
}