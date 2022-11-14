package com.example.app4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);
        Button rename = view.findViewById(R.id.btn_profile_rename);
        rename.setOnClickListener(new ProfileRenameListener());
        getParentFragmentManager().setFragmentResultListener("name", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                EditText titleText = requireActivity().findViewById(R.id.rename);
                titleText.setText(bundle.getString("data"));
            }
        });
        return view;
    }

    public class ProfileRenameListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            EditText editText = requireActivity().findViewById(R.id.rename);
            if (editText.getText() != null && !editText.getText().toString().trim().equals("")) {

                Bundle result = new Bundle();
                result.putString("data", editText.getText().toString());
                getParentFragmentManager().setFragmentResult("rename", result);
                Navigation.findNavController(view).navigate(R.id.action_profileFragment2_to_mainFragment3);
            }
        }
    }
}