package com.example.aalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private static MediaPlayer mediaPlayer;
    Intent gameIntent;
    int alarmRequestCode = -1;

    @Override
    public void onReceive(Context context, Intent intent) {
        mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();

        alarmRequestCode = intent.getIntExtra("requestCode", -1);

        String game = intent.getStringExtra("game");
        switch (game) {
            case "shake phone":
                break;
            case "code":
                break;
            case "calculation":
                gameIntent = new Intent(context, GameMath.class);
                gameIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                gameIntent.putExtra("requestCode", alarmRequestCode);
                context.startActivity(gameIntent);
                break;
            case "question":
                gameIntent = new Intent(context, GameChoice.class);
                gameIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                gameIntent.putExtra("requestCode", alarmRequestCode);
                context.startActivity(gameIntent);
                break;
            case "rewrite":
                gameIntent = new Intent(context, GameRewrite.class);
                gameIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                gameIntent.putExtra("requestCode", alarmRequestCode);
                context.startActivity(gameIntent);
                break;
            case "no game":
                break;
        }
        Toast.makeText(context, "ALARM!!! " + game, Toast.LENGTH_LONG).show();

    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

}
