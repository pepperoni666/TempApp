package com.example.wirtualnaszafa.ui.randomize;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wirtualnaszafa.ClientDB;
import com.example.wirtualnaszafa.MainActivityViewModel;
import com.example.wirtualnaszafa.R;
import com.example.wirtualnaszafa.WardrobeDB;
import com.example.wirtualnaszafa.databinding.FragmentRandomizeBinding;
import com.example.wirtualnaszafa.model.Suite;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RandomizeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RandomizeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RandomizeViewModel viewModel;

    private FragmentRandomizeBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RandomizeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment collections.
     */
    // TODO: Rename and change types and number of parameters
    public static RandomizeFragment newInstance(String param1, String param2) {
        RandomizeFragment fragment = new RandomizeFragment();
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
        binding = FragmentRandomizeBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(RandomizeViewModel.class);
        viewModel.setDb(
                ClientDB.getInstance(getContext())
                        .getAppDatabase()
                        .wardrobeDAO()
        ).observe(getViewLifecycleOwner(), suites -> {
            refresh(suites);
        });
        ;

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            refresh(viewModel.suitesLiveData.getValue());
        });
        return binding.getRoot();
    }

    private void refresh(List<WardrobeDB> suites) {
        if(suites == null) return;
        Collections.shuffle(suites);

        if (suites.size() > 0) {
            WardrobeDB first = suites.get(0);

            Picasso.get().load(first.getPath()).placeholder(R.drawable.ic_hanger).into(binding.firstSuiteImage);
            binding.firstSuiteName.setText(first.getTag());
            binding.firstSuiteDescription.setText(first.getColor());
        } else {
            binding.firstSuite.setVisibility(View.GONE);
        }

        if (suites.size() > 1) {
            WardrobeDB second = suites.get(1);

            Picasso.get().load(second.getPath()).placeholder(R.drawable.ic_hanger).into(binding.secondSuiteImage);
            binding.secondSuiteName.setText(second.getTag());
            binding.secondSuiteDescription.setText(second.getColor());
        } else {
            binding.secondSuite.setVisibility(View.GONE);
        }

        if (suites.size() > 2) {
            WardrobeDB third = suites.get(2);

            Picasso.get().load(third.getPath()).placeholder(R.drawable.ic_hanger).into(binding.thirdSuiteImage);
            binding.thirdSuiteName.setText(third.getTag());
            binding.thirdSuiteDescription.setText(third.getColor());
        } else {
            binding.thirdSuite.setVisibility(View.GONE);
        }

        binding.swipeRefreshLayout.setRefreshing(false);
    }
}