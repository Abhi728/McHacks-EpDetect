/*
 *  Copyright (c) 2011 by Twilio, Inc., all rights reserved.
 *
 *  Use of this software is subject to the terms and conditions of 
 *  the Twilio Terms of Service located at http://www.twilio.com/legal/tos
 */

package com.nuance.speechkitsample;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.twilio.client.Connection;
import com.twilio.client.Connection.State;
import com.twilio.client.ConnectionListener;
import com.twilio.client.Device;
import com.twilio.client.DeviceListener;
import com.twilio.client.PresenceEvent;
import com.twilio.client.Twilio;

public class MonkeyPhone implements Twilio.InitListener, DeviceListener, ConnectionListener {
    private static final String TAG = "MonkeyPhone";
    private Device device;
    private Connection connection;
    private final Context context;
    private BasicConnectionListener basicConnectionListener;
    private BasicDeviceListener basicDeviceListener;
    private Connection pendingIncomingConnection;
    InputMethodManager imm;
    private boolean speakerEnabled;
    private boolean muteEnabled;

    public interface BasicConnectionListener {
        public void onIncomingConnectionDisconnected();

        public void onConnectionConnecting();

        public void onConnectionConnected();

        public void onConnectionFailedConnecting(Exception error);

        public void onConnectionDisconnecting();

        public void onConnectionDisconnected();

        public void onConnectionFailed(Exception error);
    }

    public interface BasicDeviceListener {
        public void onDeviceStartedListening();

        public void onDeviceStoppedListening(Exception error);
    }

    public MonkeyPhone(Context context) {
        this.context = context;
        Twilio.initialize(context, this /* Twilio.InitListener */);
    }

    public void setListeners(BasicConnectionListener basicConnectionListener, BasicDeviceListener basicDeviceListener) {
        this.basicConnectionListener = basicConnectionListener;
        this.basicDeviceListener = basicDeviceListener;
    }

    /* Twilio.InitListener method */
    @Override
    public void onInitialized() {
        Log.d(TAG, "Twilio SDK is ready");
        try {
            // String capabilityToken =
            // HttpHelper.httpGet("http://------/mobile/auth.php");
            String capabilityToken = HttpHelper.httpGet("https://ep-detect.herokuapp.com/token");

            device = Twilio.createDevice(capabilityToken, null /* DeviceListener */);
        } catch (Exception e) {
            Log.e(TAG, "Failed to obtain capability token: " + e.getLocalizedMessage());
        }
    }

    /* Twilio.InitListener method */
    @Override
    public void onError(Exception e) {
        Log.e(TAG, "Twilio SDK couldn't start: " + e.getLocalizedMessage());
    }

    @Override
    protected void finalize() {
        if (device != null)
            device.release();
        if (connection != null)
            connection.disconnect();
    }

    // To Make Calls

    public void connect(String phoneNumber) {
        Toast toast = Toast.makeText(context, "Dialing...", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("PhoneNumber", phoneNumber);
        String capabilityToken;
        try {

            // capabilityToken =
            // HttpHelper.httpGet("http://------/mobile/auth.php");
            capabilityToken = HttpHelper.httpGet("https://ep-detect.herokuapp.com/token");
            device = Twilio.createDevice(capabilityToken, null /* DeviceListener */);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            device.disconnectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = device.connect(parameters, null /* ConnectionListener */);
        if (connection == null) {
            Log.w(TAG, "Failed to create new connection");
        }
    }

    // To Disconnect Phone
    public void disconnect() {
        Toast toast = Toast.makeText(context, "Call Disconnected...", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        if (connection != null) {
            connection.disconnect();
            connection = null; // will null out in onDisconnected()

            if (basicConnectionListener != null)
                basicConnectionListener.onConnectionDisconnecting();
        }
    }

    public void setSpeakerEnabled(boolean speakerEnabled) {
        if (speakerEnabled != this.speakerEnabled) {
            this.speakerEnabled = speakerEnabled;
            updateAudioRoute();
        }
    }

    private void updateAudioRoute() {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setSpeakerphoneOn(speakerEnabled);
    }

    public void setMuteEnabled(boolean muteEnabled) {
        if (muteEnabled != this.muteEnabled) {
            this.muteEnabled = muteEnabled;
            updateAudioRouteForMute();
        }
    }

    private void updateAudioRouteForMute() {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMicrophoneMute(muteEnabled);
    }

    public State status() {
        connection.getState();
        State statusHere = connection.getState();
        return statusHere;
    }

    @Override
    public void onConnected(Connection arg0) {
        updateAudioRoute();
        updateAudioRouteForMute();
        if (basicConnectionListener != null) {
            basicConnectionListener.onConnectionConnected();
        }
    }

    @Override
    public void onConnecting(Connection arg0) {
        if (basicConnectionListener != null) {
            basicConnectionListener.onConnectionConnecting();
        }
    }

    @Override
    public void onDisconnected(Connection inConnection) {
        if (inConnection == connection) {
            connection = null;
            if (basicConnectionListener != null)
                basicConnectionListener.onConnectionDisconnected();
        } else if (inConnection == pendingIncomingConnection) {
            pendingIncomingConnection = null;
            if (basicConnectionListener != null)
                basicConnectionListener.onIncomingConnectionDisconnected();
        }
    }

    @Override
    public void onDisconnected(Connection inConnection, int arg1, String inErrorMessage) {
        if (inConnection == connection) {
            connection = null;
            if (basicConnectionListener != null)
                basicConnectionListener.onConnectionFailedConnecting(new Exception(inErrorMessage));
        }

    }

    @Override
    public void onPresenceChanged(Device arg0, PresenceEvent arg1) {

    }

    @Override
    public void onStartListening(Device arg0) {
        if (basicDeviceListener != null) {
            basicDeviceListener.onDeviceStartedListening();
        }
    }

    @Override
    public void onStopListening(Device arg0) {
        if (basicDeviceListener != null) {
            basicDeviceListener.onDeviceStoppedListening(null);
        }
    }

    @Override
    public void onStopListening(Device arg0, int arg1, String arg2) {

    }

    @Override
    public boolean receivePresenceEvents(Device arg0) {
        return false;
    }

}