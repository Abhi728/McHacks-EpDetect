package com.nuance.speechkitsample;

import android.app.Fragment;
import android.os.Bundle;
import android.app.Activity;

import Fragments.MainMenu;

public class MainActivityEP extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_ep);

        createFragment(new MainMenu());
    }

    public void createFragment(Fragment f) {
        getFragmentManager().beginTransaction().replace(R.id.contentFrame, f).addToBackStack(null).commit();
    }
}
