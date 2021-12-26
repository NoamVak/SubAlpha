package com.example.subalpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;


public class Gallery extends AppCompatActivity {
    ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;

    public static int count=0;

    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);


        imageView=(ImageView) findViewById(R.id.imgView);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        auth=FirebaseAuth.getInstance();

        // on pressing btnSelect SelectImage() is called


    }

    public void selectImage(View view) {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    public void uploadImage(View view) {
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/users/" +auth.getCurrentUser().getUid()+"-"+count);
            count++;
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(Gallery.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(Gallery.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
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
            Intent in = new Intent(this, Notification.class);
            startActivity(in);
        }
        if(st.equals("Gallery")){
            return false;
        }

        return true;
    }

}