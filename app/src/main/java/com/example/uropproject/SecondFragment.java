package com.example.uropproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.uropproject.databinding.FragmentSecondBinding;

import java.util.Random;
public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Integer count = SecondFragmentArgs.fromBundle(getArguments()).getMyarg()[0];
        Integer repeat_times =SecondFragmentArgs.fromBundle(getArguments()).getMyarg()[1];
        String countText = getString(R.string.hello_second_fragment,count);
        TextView headerView = view.getRootView().findViewById(R.id.textview_header);
        headerView.setText(countText);
        Random random = new java.util.Random();
        Integer randomNumber = 0;
        String randomList="";
        if (count > 0) {
            for (int i = 0; i< repeat_times; i++) {
                randomNumber = random.nextInt(count + 1);
                randomList=randomList+randomNumber+" ";
            }
        }
        TextView randomView = view.getRootView().findViewById(R.id.textview_random);

        randomView.setText(randomList);
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}