package Fragments;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nuance.speechkitsample.R;

import java.util.ArrayList;

import Database.DatabaseHelper;


public class AddContact extends Fragment {

    private EditText contactName, contactNumber;
    private View root;
    DatabaseHelper myDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.add_contact, container, false);
        myDB = new DatabaseHelper(root.getContext());
        contactName = (EditText) root.findViewById(R.id.editText);
        contactNumber = (EditText) root.findViewById(R.id.editText2);
        Button submitButton, sendMsgButton;

        submitButton = (Button) root.findViewById(R.id.submitContactButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = contactName.getText().toString();
                String number = contactNumber.getText().toString();

                if (!name.isEmpty() && !number.isEmpty()) {
                    contactName.setText("");
                    contactNumber.setText("");
                    boolean isInserted = myDB.insertData(name, number);

                    if (isInserted)
                        Toast.makeText(root.getContext(), "Successfully added " + name + " to contact list!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(root.getContext(), "Contact could not be added", Toast.LENGTH_SHORT).show();
                }
            }
        });


        sendMsgButton = (Button) root.findViewById(R.id.button);
        sendMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getAllData();

                if (res.getCount() == 0) {
                    return;
                }

                while (res.moveToNext()) {
                    sendSMSMessage(res.getString(1), res.getString(2));
                }
            }
        });

        return root;
    }

    public void sendSMSMessage(String name, String number) {

        String message = "Your assistance is required!";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
            Toast.makeText(root.getContext(), "SMS sent to " + name, Toast.LENGTH_SHORT).show();
        }

        catch (Exception e) {
            Toast.makeText(root.getContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


}
