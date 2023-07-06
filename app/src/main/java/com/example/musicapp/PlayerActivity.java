package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {
    Button btnplay,btnnext,btnprev,btnff,btnfr;
    TextView txtsname,textsstart,txtsstop;
    SeekBar seekmusic;

    String sname;
    public static final String EXTRA_NAME="song_name";
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> mySongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btnprev=findViewById(R.id.btnprev);
        btnnext=findViewById(R.id.btnnext);
        btnplay=findViewById(R.id.playbtn);
        btnff=findViewById(R.id.btnff);
        btnfr=findViewById(R.id.btnfr);
        txtsname=findViewById(R.id.txtsn);
        textsstart=findViewById(R.id.txtsstart);
        txtsstop=findViewById(R.id.txtsstop);
        seekmusic=findViewById(R.id.seekbar);

        if(mediaPlayer !=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Intent i=getIntent();
        Bundle bundle=i.getExtras();

        mySongs=(ArrayList)bundle.getParcelableArrayList("songs");
//        mySongs = (ArrayList<File>) getIntent().getSerializableExtra("songs");
        String songname=i.getStringExtra("songname");
        position=bundle.getInt("pos",0);
        txtsname.setSelected(true);
        Uri uri=Uri.parse(mySongs.get(position).toString());
        sname=mySongs.get(position).getName();
        txtsname.setText(sname);

        mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying())
                {
                    btnplay.setBackgroundResource(R.drawable.play);
                    mediaPlayer.pause();
                }
                else
                {
                    btnplay.setBackgroundResource(R.drawable.pause);
                    mediaPlayer.start();
                }
            }
        });

    }
}