package Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nuance.speechkitsample.MainActivityEP;
import com.nuance.speechkitsample.R;
import com.nuance.speechkitsample.TTSActivity;

import Database.DatabaseHelper;


public class MainMenu extends Fragment {

    private FragmentSettings mFragmentSettings;
    private DatabaseHelper myDB;
    private View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentSettings = (FragmentSettings) getActivity();
        root = inflater.inflate(R.layout.main_menu, container, false);
        myDB = new DatabaseHelper(root.getContext());
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
                Integer rows = myDB.deleteData(); // deletes last row

                if (rows > 0)
                    Toast.makeText(root.getContext(), "Contact successfully deleted!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(root.getContext(), "Can't delete contact!", Toast.LENGTH_SHORT).show();
            }
        });

        viewContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getAllData();

                if (res.getCount() == 0) {
                    return;
                }

                StringBuffer sb = new StringBuffer();

                while (res.moveToNext()) {
                    sb.append("ID: " + res.getString(0) + "\n");
                    sb.append("NAME: " + res.getString(1) + "\n");
                    sb.append("NUMBER: " + res.getString(2) + "\n\n");
                }

                showAll("Content in DB", sb.toString());
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

    public void showAll(String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(root.getContext());
        dialog.setCancelable(true);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.show();
    }
}