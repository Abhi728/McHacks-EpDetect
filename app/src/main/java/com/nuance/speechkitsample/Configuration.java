package com.nuance.speechkitsample;

import android.net.Uri;

import com.nuance.speechkit.PcmFormat;

/**
 * All Nuance Developers configuration parameters can be set here.
 *
 * Copyright (c) 2015 Nuance Communications. All rights reserved.
 */
public class Configuration {

    //All fields are required.
    //Your credentials can be found in your Nuance Developers portal, under "Manage My Apps".
    public static final String APP_KEY = "1f9260733bdb77ca1986c5e1a13f877b9abc837a1964db8d2249156940de33607093d3ab6f4a7588d03a697179f0ec13ac6f8dc6d46953f6b2685ff3469253b6";
    public static final String APP_ID = "NMDPTRIAL_pirasathv_gmail_com20160219175800";
    public static final String SERVER_HOST = "sslsandbox.nmdp.nuancemobility.net";
    public static final String SERVER_PORT = "443";

    public static final Uri SERVER_URI = Uri.parse("nmsps://" + APP_ID + "@" + SERVER_HOST + ":" + SERVER_PORT);

    //Only needed if using NLU
    public static final String CONTEXT_TAG = "!NLU_CONTEXT_TAG!";

    public static final PcmFormat PCM_FORMAT = new PcmFormat(PcmFormat.SampleFormat.SignedLinear16, 16000, 1);
}

