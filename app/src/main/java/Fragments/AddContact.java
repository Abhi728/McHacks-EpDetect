package Fragments;

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


public class AddContact extends Fragment {

    private EditText contactName, contactNumber;
    private Button submitButton;
    private ArrayList<Contact> contactList = new ArrayList<Contact>();
    private View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         root = inflater.inflate(R.layout.add_contact, container, false);

        contactName = (EditText) root.findViewById(R.id.editText);
        contactNumber = (EditText) root.findViewById(R.id.editText2);
        submitButton = (Button) root.findViewById(R.id.submitContactButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = contactName.getText().toString();
                String number = contactNumber.getText().toString();

                if (!name.isEmpty() && !number.isEmpty()) {
                    Contact c = new Contact(name, number);
                    contactList.add(c);
                    Toast.makeText(root.getContext(), "Successfully added " + c.getName() + " to contact list!", Toast.LENGTH_SHORT).show();
                    contactName.setText("");
                    contactNumber.setText("");
                    sendSMSMessage(number);
                }
            }
        });

        return root;
    }

    public void sendSMSMessage(String number) {

        String message = "This is a test!";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
            Toast.makeText(root.getContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(root.getContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
