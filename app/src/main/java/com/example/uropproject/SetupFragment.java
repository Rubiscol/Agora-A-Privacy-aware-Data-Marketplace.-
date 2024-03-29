package com.example.uropproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText generator_number;
    private TextView setup_info_textView;
    private EditText parameter;


    public SetupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetupFragment newInstance(String param1, String param2) {
        SetupFragment fragment = new SetupFragment();
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
        View view=inflater.inflate(R.layout.fragment_setup, container, false);
        generator_number=view.findViewById(R.id.generator_number);
        parameter=view.findViewById(R.id.parameter);
        setup_info_textView=view.findViewById(R.id.setup_info_textView);
        return view;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.generator_number_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Integer input_number= Integer.parseInt(String.valueOf(generator_number.getText()));
                Main.n=input_number;
            }
        });
        view.findViewById(R.id.parameter_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Integer input_number= Integer.parseInt(String.valueOf(parameter.getText()));
                Main.securityParameter=input_number;
            }
        });
        view.findViewById(R.id.setup_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    Setup.FE_Setup();
                    Setup.SetupWithContract();
                    setup_info_textView.append("Finish setup");
                    Setup.testDKeyGen();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        view.findViewById(R.id.setup_back_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NavHostFragment.findNavController(SetupFragment.this)
                        .navigate(R.id.action_SetupFragment_to_MenuFragment);
            }
        });
    }
}