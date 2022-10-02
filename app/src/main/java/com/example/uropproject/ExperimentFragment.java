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

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExperimentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExperimentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
//    private TextView Experiment_TextView;
    private Button experiment_back_button;
    private Button start_experiment_button;
    private BarChart chart;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<Long> haha=new ArrayList<Long>();

    public ExperimentFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExperimentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExperimentFragment newInstance(String param1, String param2) {
        ExperimentFragment fragment = new ExperimentFragment();
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
        View view =inflater.inflate(R.layout.fragment_experiment, container, false);
        chart=view.findViewById(R.id.chart);

        experiment_back_button=view.findViewById(R.id.experiment_back_button);
        start_experiment_button =view.findViewById(R.id.start_experiment_button);
        return view;
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.start_experiment_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Main.n=5;
                Main.securityParameter=200;

                try {
                    haha.add(Experiment.testFE_setup());
                    haha.add(Experiment.testSC_setup());
                    haha.add(Experiment.testDKeyGen());
                    haha.add(Experiment.testuploadciphertext());
                    haha.add(Experiment.testprodciphertext());
                    haha.add(Experiment.testgetciphertext());
                    haha.add(Experiment.testBrokerZNP());
                    haha.add(Experiment.testConsumerZNP());
                    haha.add(Experiment.testUploadGA());
                    haha.add(Experiment.testVerifyA());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });
        view.findViewById(R.id.plot_experiment_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                ArrayList<BarEntry> barEntries = new ArrayList<>();

                for(Integer i=0 ;i<10;i++) {
                    barEntries.add(new BarEntry(Float.valueOf(i), Float.valueOf(haha.get(i))));
                }

                BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
                ArrayList<String> steps = new ArrayList<>();
                steps.add("FE_setup");
                steps.add("SC_setup");
                steps.add("DKeyGen");
                steps.add("UploadCiphertext");
                steps.add("ProdCiphertext");
                steps.add("GetCiphertext");
                steps.add("BrokerZNP");
                steps.add("ConsumerZNP");
                steps.add("UploadGA");
                steps.add("VerifyA");
                chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(steps));
                XAxis xAxis = chart.getXAxis();
                xAxis.setCenterAxisLabels(false);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1);
                xAxis.setGranularityEnabled(true);
                xAxis.setLabelRotationAngle(90);
                chart.setDragEnabled(true);
                chart.setVisibleXRangeMaximum(6);
                chart.getXAxis().setAxisMinimum(0);
                chart.getXAxis().setAxisMaximum(9f);
                BarData theData = new BarData(barDataSet);
                chart.setData(theData);
                chart.animateXY(1000, 1000);
                chart.setTouchEnabled(true);
                chart.setDragEnabled(true);
                chart.setScaleEnabled(true);
            }
        });
        view.findViewById(R.id.experiment_back_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                NavHostFragment.findNavController(ExperimentFragment.this)
                        .navigate(R.id.action_ExperimentFragment_to_MenuFragment);
            }
        });
    }
}