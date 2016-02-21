package Fragments;

import android.os.Bundle;
import android.app.Fragment;
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
                    Toast.makeText(root.getContext(), "Contact successfully add!", Toast.LENGTH_SHORT);
                }
            }
        });

        return root;
    }
}
