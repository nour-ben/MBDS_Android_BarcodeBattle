package com.mbds.barcode_battle.utils;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;


public class SoundService extends Service {
    private static final String TAG = null;

    MediaPlayer mediaPlayer;
    String sound;
    public SoundService() throws IOException {

    }

    public IBinder onBind(Intent arg0) {

        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd("Générique-Pokémon.mp3");
            mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true); // Set looping
            mediaPlayer.setVolume(100,100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("WrongConstant")
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return 1;
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
    }
    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {

    }
    public void onPause() {

    }
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void onLowMemory() {

    }
}
