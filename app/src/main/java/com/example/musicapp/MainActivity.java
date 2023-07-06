package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import com.karumi.dexter.Dexter;
import android.os.Environment;

public class MainActivity extends AppCompatActivity {
    ArrayList<File> mySongs;
    //    ListView listView;
//    String[] items;
    private ListView listView;
    private ArrayList<String> songList;
    private ArrayAdapter<String> adapter;

    private static final int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewSong);

        // Check if the READ_EXTERNAL_STORAGE permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_PERMISSION_REQUEST);
        } else {
            // Permission is already granted, fetch the songs
            fetchSongs();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, fetch the songs
                fetchSongs();
            } else {
                // Permission denied, handle accordingly (e.g., show an error message)
            }
        }
    }

    private void fetchSongs() {
        // Create an ArrayList to store the list of songs
        songList = new ArrayList<>();

        // Retrieve the songs using the MediaStore
        Cursor cursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);

            int columnIndex;

            do {
                // Get the title of the song and add it to the list
                String title = cursor.getString(titleColumn);
                songList.add(title);

            } while (cursor.moveToNext());
            System.out.println(songList);

            cursor.close();




//            System.out.println("------------------------------------------------------------");
////            mySongs= fetSongs(Environment.getExternalStorageDirectory());
////            String[] items= new String[mySongs.size()];
//            System.out.println("------------------------------------------------------------"+mySongs.size());
        }

        // Create the ArrayAdapter and set it on the ListView
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList);
//        listView.setAdapter(adapter);

        customAdapter customAdapter=new customAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songName=(String) listView.getItemAtPosition(position);
//                startActivity(new Intent(getApplicationContext(),PlayerActivity.class).putExtra("songs",songList).putExtra("songname",songName).putExtra("pos",position));

                Intent intent= new Intent(MainActivity.this, PlayerActivity.class);
                String currentSong= listView.getItemAtPosition(position).toString();
                intent.putExtra("songs", songList);
                intent.putExtra("songname", currentSong);
                intent.putExtra("pos", position);
                startActivity(intent);

            }
        });




    }

    class customAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return songList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView=getLayoutInflater().inflate(R.layout.list_item,null);
            TextView textsong=myView.findViewById(R.id.txtsongname);
            textsong.setSelected(true);
            textsong.setText(songList.get(position));
            return myView;
        }
    }








}