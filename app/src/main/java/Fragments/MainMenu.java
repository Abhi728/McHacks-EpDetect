package Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nuance.speechkitsample.MainActivityEP;
import com.nuance.speechkitsample.R;
import com.nuance.speechkitsample.TTSActivity;


public class MainMenu extends Fragment {

    private FragmentSettings mFragmentSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentSettings = (FragmentSettings) getActivity();
        View root = inflater.inflate(R.layout.main_menu, container, false);
        Button addContactButton = (Button) root.findViewById(R.id.addContactButton);
        Button removeContactButton = (Button) root.findViewById(R.id.removeContactButton);
        Button viewContactButton = (Button) root.findViewById(R.id.viewContactButton);
        Button triggerButton = (Button) root.findViewById(R.id.triggerButton);

        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentSettings.createFragment(new AddContact());
            }
        });

        removeContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivityEP)getActivity()).createFragment(new AddContact());
            }
        });

        viewContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivityEP)getActivity()).createFragment(new AddContact());
            }
        });

        triggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), TTSActivity.class);
                getActivity().startActivity(myIntent);
            }
        });



        return root;
    }
}