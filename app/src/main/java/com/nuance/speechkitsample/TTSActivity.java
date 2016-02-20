package com.nuance.speechkitsample;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;
import com.nuance.speechkit.Audio;
import com.nuance.speechkit.AudioPlayer;
import com.nuance.speechkit.Language;
import com.nuance.speechkit.Session;
import com.nuance.speechkit.Transaction;
import com.nuance.speechkit.TransactionException;

import java.util.Calendar;
import java.util.UUID;

/**
 * This Activity is built to demonstrate how to perform TTS.
 *
 * TTS is the transformation of text into speech.
 *
 * When performing speech synthesis with SpeechKit, you have a variety of options. Here we demonstrate
 * Language. But you can also specify the Voice. If you do not, then the default voice will be used
 * for the given language.
 *
 * Supported languages and voices can be found here:
 * http://developer.nuance.com/public/index.php?task=supportedLanguages
 *
 * Copyright (c) 2015 Nuance Communications. All rights reserved.
 */
public class TTSActivity extends DetailActivity implements View.OnClickListener, AudioPlayer.Listener {

    private EditText ttsText;
    private EditText language;

    private TextView logs;
    private Button clearLogs;

    private Button toggleTTS;

    private Session speechSession;
    private Transaction ttsTransaction;

    private static final UUID EpDetect_ID = UUID.fromString("70e4db8a-9cf4-430c-93c3-2f7d71e54b15");

    private ImageView imgBlink;
    private Uri notification;
    private Ringtone r;

    private PebbleKit.PebbleDataReceiver ApDetectDataReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);

        ttsText = (EditText)findViewById(R.id.tts_text);
        language = (EditText)findViewById(R.id.language);

        logs = (TextView)findViewById(R.id.logs);
        clearLogs = (Button)findViewById(R.id.clear_logs);
        clearLogs.setOnClickListener(this);

        toggleTTS = (Button)findViewById(R.id.toggle_tts);
        toggleTTS.setOnClickListener(this);

        //Create a session
        speechSession = Session.Factory.session(this, Configuration.SERVER_URI, Configuration.APP_KEY);
        speechSession.getAudioPlayer().setListener(this);

        PebbleKit.startAppOnPebble(this, EpDetect_ID);

        blinkblinkImage();

        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
    }

    @Override
    public void onClick(View v) {
        if(v == clearLogs) {
            logs.setText("");
        } else if(v == toggleTTS) {
            toggleTTS();
        }
    }

    protected void blinkblinkImage() {

        Animation animation = new AlphaAnimation(1, 0); // Change alpha
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at

        imgBlink.startAnimation(animation);

    }


    /* TTS transactions */

    private void toggleTTS() {
        //If we are not loading TTS from the server, then we should do so.
        if(ttsTransaction == null) {
            toggleTTS.setText(getResources().getString(R.string.cancel));
            synthesize();
        }
        //Otherwise lets attempt to cancel that transaction
        else {
            toggleTTS.setText(getResources().getString(R.string.speak_string));
            stop();
        }
    }

    /**
     * Speak the text that is in the ttsText EditText, using the language in the language EditText.
     */
    private void synthesize() {
        //Setup our TTS transaction options.
        Transaction.Options options = new Transaction.Options();
        options.setLanguage(new Language(language.getText().toString()));
        //options.setVoice(new Voice(Voice.SAMANTHA)); //optionally change the Voice of the speaker, but will use the default if omitted.

        //Start a TTS transaction
        ttsTransaction = speechSession.speakString(ttsText.getText().toString(), options, new Transaction.Listener() {
            @Override
            public void onAudio(Transaction transaction, Audio audio) {
                logs.append("\nonAudio");

                //The TTS audio has returned from the server, and has begun auto-playing.
                ttsTransaction = null;
                toggleTTS.setText(getResources().getString(R.string.speak_string));
            }

            @Override
            public void onSuccess(Transaction transaction, String s) {
                logs.append("\nonSuccess");

                //Notification of a successful transaction. Nothing to do here.
            }

            @Override
            public void onError(Transaction transaction, String s, TransactionException e) {
                logs.append("\nonError: " + e.getMessage() + ". " + s);

                //Something went wrong. Check Configuration.java to ensure that your settings are correct.
                //The user could also be offline, so be sure to handle this case appropriately.

                ttsTransaction = null;
            }
        });
    }

    private void synthesize2(String x) {
        //Setup our TTS transaction options.
        Transaction.Options options = new Transaction.Options();
        options.setLanguage(new Language(language.getText().toString()));
        //options.setVoice(new Voice(Voice.SAMANTHA)); //optionally change the Voice of the speaker, but will use the default if omitted.

        //Start a TTS transaction
        ttsTransaction = speechSession.speakString(x, options, new Transaction.Listener() {
            @Override
            public void onAudio(Transaction transaction, Audio audio) {
                logs.append("\nonAudio");

                //The TTS audio has returned from the server, and has begun auto-playing.
                ttsTransaction = null;
                toggleTTS.setText(getResources().getString(R.string.speak_string));
            }

            @Override
            public void onSuccess(Transaction transaction, String s) {
                logs.append("\nonSuccess");

                //Notification of a successful transaction. Nothing to do here.
            }

            @Override
            public void onError(Transaction transaction, String s, TransactionException e) {
                logs.append("\nonError: " + e.getMessage() + ". " + s);

                //Something went wrong. Check Configuration.java to ensure that your settings are correct.
                //The user could also be offline, so be sure to handle this case appropriately.

                ttsTransaction = null;
            }
        });
    }

    /**
     * Cancel the TTS transaction.
     * This will only cancel if we have not received the audio from the server yet.
     */
    private void stop() {
        ttsTransaction.cancel();
    }

    @Override
    public void onBeginPlaying(AudioPlayer audioPlayer, Audio audio) {
        logs.append("\nonBeginPlaying");

        //The TTS Audio will begin playing.
    }

    @Override
    public void onFinishedPlaying(AudioPlayer audioPlayer, Audio audio) {
        logs.append("\nonFinishedPlaying");

        //The TTS Audio has finished playing
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean watchConnected = PebbleKit.isWatchConnected(this);
        Toast.makeText(this, "Pebble " + (watchConnected ? "is" : "is not") + " connected!", Toast.LENGTH_LONG).show();

        Log.i("testing2", " testing2");

        if(ApDetectDataReceiver == null) {
            ApDetectDataReceiver = new PebbleKit.PebbleDataReceiver(EpDetect_ID) {

                @Override
                public void receiveData(Context context, int transactionId, PebbleDictionary dict) {
                    // Always ACK
                    PebbleKit.sendAckToPebble(context, transactionId);

                    Log.i("testing", " testing");

                    Log.i("Returned by peb : ", " peb : " + dict.getString(5));

                    Calendar calen = Calendar.getInstance();

                    String hour = Integer.toString(calen.get(Calendar.HOUR_OF_DAY));
                    String minute = Integer.toString(calen.get(Calendar.MINUTE));
                    String seconds = Integer.toString(calen.get(Calendar.SECOND));

                    String timeNow = hour+":"+minute+":"+seconds;

                    Log.i("Calen Int : ", timeNow);

                    String timee = calen.getTime().toString();

                    Log.i("Calen Date Time : ", timee);

                    synthesize2("Shake");

                    /*String timee = calen.getTime().toString();

                    timee.format("HH:mm:ss");

                    Log.i("Calen : ", timee);

                   synthesize2(dict.getString(5));

                    synthesize2("Shake");*/





                    //Log.i("Returned by peb : ", " peb222 : " + (dict.getInteger(1).toString()));



                }

            };
            PebbleKit.registerReceivedDataHandler(getApplicationContext(), ApDetectDataReceiver);
        }
    }
}
