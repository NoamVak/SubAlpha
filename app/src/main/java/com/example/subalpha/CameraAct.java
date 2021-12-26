package com.example.subalpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class CameraAct extends AppCompatActivity {
    ImageView cam;

    byte bb[];
    private StorageReference mStorageRef;

    private FirebaseAuth auth;

    Uri photoUri;
    final int CAMERA_REQUEST = 45;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cam = (ImageView) findViewById(R.id.imgView);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String st = item.getTitle().toString();
        if (st.equals("Map")) {
            Intent si = new Intent(this, MainActivity.class);
            startActivity(si);
        }
        if (st.equals("Auth")) {
            Intent in = new Intent(this, LoginScreen.class);
            startActivity(in);
        }
        if (st.equals("Camera")) {
            return false;
        }
        if (st.equals("Notification")) {
            Intent in = new Intent(this, Notification.class);
            startActivity(in);
        }
        if (st.equals("Gallery")) {
            Intent in = new Intent(this, Gallery.class);
            startActivity(in);
        }

        return true;
    }

    public void openCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, 101);

        //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        if (true) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        photoUri = Uri.fromFile(image);
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            cam.setImageURI(photoUri);
        }
    }

    /**
     * private void onCaptureImage(Intent data){
     * Bitmap thumbnail= (Bitmap) data.getExtras().get("data");
     * ByteArrayOutputStream bytes= new ByteArrayOutputStream();
     * thumbnail.compress(Bitmap.CompressFormat.JPEG,90, bytes);
     * bb=bytes.toByteArray();
     * cam.setImageBitmap(thumbnail);
     * }
     *
     * @param
     */

    public void upload(View view) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        StorageReference ref = mStorageRef.child("images/users/" + auth.getCurrentUser().getUid()+"-"+Gallery.count);
        Gallery.count++;
        ref.putBytes(bb).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(CameraAct.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(CameraAct.this, "Failed to upload.", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded " + (int)progress + "%");
            }
        });
    }
}