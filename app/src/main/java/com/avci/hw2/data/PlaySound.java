package com.avci.hw2.data;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.avci.hw2.R;

public class PlaySound {

    public Context context;


    public PlaySound(Context context) {
        this.context = context;
    }

    public SoundPool SoundImplementation() {
        SoundPool soundpool;
        int add_btn_sound;
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundpool = new SoundPool.Builder().setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        add_btn_sound = soundpool.load(context, R.raw.add_btn_sound, 1);

        soundpool.play(add_btn_sound, 1.0f, 1.0f, 0, 0, 1.5f);
        Log.d("MUSIC", "SoundImplementation: " + soundpool.toString());

        return soundpool;

    }
}
