package com.example.subalpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
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
            Intent in = new Intent(this, CameraAct.class);
            startActivity(in);
        }
        if(st.equals("Notification")){
            return false;
        }
        if(st.equals("Gallery")){
            Intent in = new Intent(this, Gallery.class);
            startActivity(in);
        }

        return true;
    }
}