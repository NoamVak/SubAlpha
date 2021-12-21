package com.example.subalpha;

import static com.example.subalpha.help.CHANNEL_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Notification extends AppCompatActivity {
    EditText ed;
    public NotificationManagerCompat nManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        ed= (EditText) findViewById(R.id.ed);
        nManager = NotificationManagerCompat.from(this);

    }


    public void send(View view) {
        String title = "NOTIFICATION";
        String mess = ed.getText().toString();
        ed.setText("");
//create notification
        android.app.Notification n = new NotificationCompat.Builder(this, CHANNEL_ID)

                .setContentTitle(title)
                .setContentText(mess)
                .setSmallIcon(R.drawable.phone)
                .setPriority(NotificationCompat.PRIORITY_LOW) //important
                .build();

        nManager.notify(1, n);

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