package com.nuance.speechkitsample;

import android.app.Fragment;
import android.os.Bundle;
import android.app.Activity;

import Database.DatabaseHelper;
import Fragments.FragmentSettings;
import Fragments.MainMenu;

public class MainActivityEP extends Activity implements FragmentSettings {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_ep);
        createFragment(new MainMenu());
    }

    public void createFragment(Fragment f) {
        getFragmentManager().beginTransaction().replace(R.id.contentFrame, f).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed ()
    {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.contentFrame);

        if (getFragmentManager().getBackStackEntryCount() > 0 && !(fragment instanceof MainMenu))
            getFragmentManager().popBackStack ();
        else {
            android.os.Process.killProcess(android.os.Process.myPid());
            //super.onBackPressed();
        }
    }
}
