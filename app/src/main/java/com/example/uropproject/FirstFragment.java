package com.example.uropproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.uropproject.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {
    public int repeat_times =-1;
    public int count =0;
    private FragmentFirstBinding binding;
    private EditText max_number;
    private EditText numberOfRandom;
    private Button randomButton;
    private Button confirmButton2;
    private TextView input_hint;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        View fragmentFirstLayout=inflater.inflate(R.layout.fragment_first,container,false);
        max_number =fragmentFirstLayout.findViewById(R.id.textview_first);
        numberOfRandom =fragmentFirstLayout.findViewById(R.id.permutation_input);
        randomButton=fragmentFirstLayout.findViewById(R.id.prime_random_button);
        confirmButton2=fragmentFirstLayout.findViewById(R.id.confirm_button2);
        input_hint=fragmentFirstLayout.findViewById(R.id.inputHint2_text);
        input_hint.setVisibility(View.INVISIBLE);
        max_number.setVisibility(View.INVISIBLE);
        confirmButton2.setEnabled(false);
        randomButton.setEnabled(false);
        confirmButton2.setVisibility(View.INVISIBLE);
        randomButton.setVisibility(View.INVISIBLE);
        return fragmentFirstLayout;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.prime_random_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (repeat_times >=0) {
                    int[] input = {count, repeat_times};
                    FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(input);
                    NavHostFragment.findNavController(FirstFragment.this).navigate(action);
                }

            }
        });
        view.findViewById(R.id.confirm_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeat_times =Integer.parseInt(numberOfRandom.getText().toString());
                input_hint.setVisibility(View.VISIBLE);
                confirmButton2.setVisibility(View.VISIBLE);
                confirmButton2.setEnabled(true);
                max_number.setVisibility(View.VISIBLE);

            }
        });
        view.findViewById(R.id.confirm_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count =Integer.parseInt(max_number.getText().toString());
                randomButton.setEnabled(true);
                randomButton.setVisibility(View.VISIBLE);

            }
        });
        view.findViewById(R.id.back_button_aes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_MenuFragment);

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}