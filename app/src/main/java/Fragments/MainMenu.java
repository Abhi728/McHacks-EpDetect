package Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nuance.speechkitsample.R;


public class MainMenu extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.main_menu, container, false);
        Button b1 = (Button) root.findViewById(R.id.button);
        Button b2 = (Button) root.findViewById(R.id.button2);
        Button b3 = (Button) root.findViewById(R.id.button3);
        Button b4 = (Button) root.findViewById(R.id.button4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity)getActivity()).createFragment(new Notification());
            }
        });

        return root;
    }
}