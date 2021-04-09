package com.example.coattts;

import java.util.HashMap;
import java.util.Locale;

import android.content.Context;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

public class Speaker extends UtteranceProgressListener implements OnInitListener {

    private static final String TAG = "TTSSpeaker";
    private TextToSpeech tts;

    private boolean ready = false;

    private boolean allowed = false;

    public Speaker(Context context){
        tts = new TextToSpeech(context, this);
    }

    public boolean isAllowed(){
        return allowed;
    }

    public void allow(boolean allowed){
        this.allowed = allowed;
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            // Change this to match your
            // locale
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA)
            {
                Log.e("TextToSpeech", "onInit: failed lang no support, engine:"+ tts.getDefaultEngine());
            }
            ready = true;
            speak("I am ready.");

        }else{
            ready = false;
        }
    }

    public void speak(String text){

        // Speak only if the TTS is ready
        // and the user has allowed speech

        if(ready && allowed) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void pause(int duration){
        tts.playSilence(duration, TextToSpeech.QUEUE_ADD, null);
    }

    // Free up resources
    public void destroy(){
        tts.shutdown();
    }

    @Override
    public void onStart(String utteranceId) {
        Log.i(TAG, "onStart: "+utteranceId);
    }

    @Override
    public void onDone(String utteranceId) {
        Log.i(TAG, "onDone: "+utteranceId);
    }

    @Override
    public void onError(String utteranceId) {
        Log.i(TAG, "onError: "+utteranceId);
    }
}
