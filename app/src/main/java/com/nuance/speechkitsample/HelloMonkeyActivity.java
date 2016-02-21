/*
 *  Copyright (c) 2011 by Twilio, Inc., all rights reserved.
 *
 *  Use of this software is subject to the terms and conditions of 
 *  the Twilio Terms of Service located at http://www.twilio.com/legal/tos
 */

package com.nuance.speechkitsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.nuance.speechkitsample.R;

public class HelloMonkeyActivity extends Activity implements View.OnClickListener {
    private MonkeyPhone phone;
    private EditText numberField;
    private String newContact;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);

        phone = new MonkeyPhone(getApplicationContext());

        ImageButton dialButton = (ImageButton) findViewById(R.id.btnCallHere);
        dialButton.setOnClickListener(this);

        ImageButton hangupButton = (ImageButton) findViewById(R.id.btnDisconnectHere);
        hangupButton.setOnClickListener(this);

        numberField = (EditText) findViewById(R.id.numberField);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCallHere) {

            newContact = numberField.getText().toString();
            phone.connect(newContact);

        } else if (view.getId() == R.id.btnDisconnectHere) {

            phone.disconnect();

        }
    }
}