# SpeechKit 2 Sample App

This is intended to be a demonstration of some of the most common tasks you will perform with SpeechKit 2.

If you have problems or questions, feel free to [contact us](mailto:developerrelations@nuance.com)

You can also find Documentation, SDK Downloads, Account Settings, and more [here](http://developers.nuance.com)

# Setup

Note: This project was created for use in Android Studio.

## Using SpeechKit as an AAR

We are compiling against API level 23, so make sure you have it installed.

* Open this directory in Android Studio.
* Copy SpeechKit.aar into `./app/aars`

## Using SpeechKit as a library project

If you are developing both this app and SpeechKit, you may want SpeechKit to be a library project.

Add the following to the dependencies block in the apps build.gradle file:

``` java
    compile (project(':speechkit')) {
        transitive false
    }
```

Add the following to your settings.gradle file:
``` java
    include ':speechkit'
    project(':speechkit').projectDir = new File(settingsDir, '<relative path to speechkit module>')
```
