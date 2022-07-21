package com.example.uropproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.bouncycastle.math.ec.ECPoint;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeneratorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneratorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView generator_title_textView;
    private TextView  generator_content_textView;
    public GeneratorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GeneratorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GeneratorFragment newInstance(String param1, String param2) {
        GeneratorFragment fragment = new GeneratorFragment();
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


    private void updatetitle(){
        generator_title_textView.setText("Current round t is: "+testUROP.label);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_generator, container, false);
        generator_title_textView=view.findViewById(R.id.generator_title_textView);
        generator_title_textView.setText("Current round t is: "+testUROP.label);
        generator_content_textView =view.findViewById(R.id.generator_content_textView);
        return view;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.generator_generate_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                testUROP.updateLabel();
                try {
                    testUROP.testEncrypt(testUROP.label);
                    String message="";
                    for (int i=testUROP.label;i>0;i--){
                        ECPoint cipherPoint=testUROP.getCombinedCiphertext(i);
                        message=message+"Here is the C("+i+")'s information:\n";
                        message=message+"The x coordinate of C("+i+") is: "+cipherPoint.getAffineXCoord()+"\n";
                        message=message+"The y coordinate of C("+i+") is: "+cipherPoint.getAffineYCoord()+"\n"+"\n";
                    }

                    generator_content_textView.setText(message);
                    updatetitle();

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                }
            }
        });
        view.findViewById(R.id.generator_back_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NavHostFragment.findNavController(GeneratorFragment.this)
                        .navigate(R.id.action_GeneratorFragment_to_MenuFragment);
            }
        });
    }

}