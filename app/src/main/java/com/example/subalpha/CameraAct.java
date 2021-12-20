package com.example.subalpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CameraAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        String st=item.getTitle().toString();
        if(st.equals("Map")) {
            Intent si = new Intent(this, MainActivity.class);
            startActivity(si);
        }
        if(st.equals("Auth")){
            Intent in = new Intent(this, LoginScreen.class);
            startActivity(in);
        }
        if(st.equals("Camera")){
            return false;
        }
        if(st.equals("Notification")){
            Intent in = new Intent(this, Notification.class);
            startActivity(in);
        }
        if(st.equals("Gallery")){
            Intent in = new Intent(this, Gallery.class);
            startActivity(in);
        }

        return true;
    }

    public void openCamera(View view) {
    }

    public void upload(View view) {
    }
}