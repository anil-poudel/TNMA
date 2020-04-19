package com.csce4901.tnma;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.BlogDao;
import com.csce4901.tnma.DAO.Impl.BlogDaoImpl;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;



public class CreatePost extends AppCompatActivity {

    //TO upload images from gallery
    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int  IMAGE_PICK_GALLERY_CODE = 200;
    String[] storagePermission;
    Uri image_uri;
    String uri;

    //Declare buttons and texts
    Button btn_selectImage, btn_create, btn_cancel;
    EditText blogTitle;
    EditText blogContent;
    String titleText, contentText;
    ImageView imageContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        //Initialize storage permission
        storagePermission = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //Define Buttons
        btn_create = findViewById(R.id.btn_post);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_selectImage = findViewById(R.id.uploadImageButton);
        blogTitle = findViewById(R.id.blogTitle);
        blogContent = findViewById(R.id.blogContent);
        imageContent = findViewById(R.id.imageBG);


        //upload image from gallery
        btn_selectImage.setOnClickListener(v -> {
            Toast.makeText(this, "Choose Image", Toast.LENGTH_SHORT).show();
            showimagePicker();
        });


        //Submit post
        BlogDao blogDao = new BlogDaoImpl();
        btn_create.setOnClickListener(v -> {
            //TODO: Check user role before posting a blog
            if(FirebaseAuth.getInstance().getCurrentUser() != null){
                //Define texts
                titleText = blogTitle.getText().toString();
                contentText = blogContent.getText().toString();
                String uName = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

                //Current Time
                String timeStamp = String.valueOf(System.currentTimeMillis());
                //Storage location in database
                String filePathName = "Blogs/" + "blog_" + titleText;

                if(image_uri==null)
                {
                    uri = "noImage";
                }
                else
                {
                    uri = String.valueOf(image_uri);
                }

                //Make post with image
                if(!uri.equals("noImage"))
                {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference ref = storage.getReference().child(filePathName);
                    ref.putFile(Uri.parse(String.valueOf(image_uri)))
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //Get image URL from the database once uploaded
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isSuccessful());

                                    String downloadURI = uriTask.getResult().toString();

                                    if(uriTask.isSuccessful())
                                    {
                                        blogDao.createNewBlog(titleText, contentText, downloadURI, uName);
                                        Toast.makeText(v.getContext(), "Blog successfully posted.", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(v.getContext(), "Cannot post", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(v.getContext(), "Could not upload an image", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else
                {
                    //default image for blogposts
                    uri = "https://firebasestorage.googleapis.com/v0/b/tnma-375e2.appspot.com/o/Blogs%2Fblog_default.jpg?alt=media&token=985c469f-0127-46d2-9925-7cb1446e25be";
                    blogDao.createNewBlog(titleText, contentText, uri, uName);
                    Toast.makeText(v.getContext(), "Blog successfully posted.", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "There was an unexpected error. Please try again later.", Toast.LENGTH_SHORT).show();
            }
            finish();
        });

        //Cancel Button closes activity
        btn_cancel.setOnClickListener(v -> {finish();});

    }

    //Storage Permissions for choosing image from gallery
    private boolean checkStoragePermission(){
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    //Request Permissions
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, storagePermission, GALLERY_REQUEST_CODE);
    }

    //
    private void showimagePicker() {
        if(!checkStoragePermission())
        {
            requestPermission();
        }
            //Pick image from Gallery
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, IMAGE_PICK_GALLERY_CODE);
    }

    //Permissions Handler
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == GALLERY_REQUEST_CODE)
        {
            if (grantResults.length>0)
            {
                boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if(storageAccepted)
                {
                    showimagePicker();
                    imageContent.setImageURI(image_uri);
                }
            }
            else
            {
                Toast.makeText(this, "This app needs Storage Access to upload images.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Get image URI from local storage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == IMAGE_PICK_GALLERY_CODE)
            {
                assert data != null;
                image_uri = data.getData();
                imageContent.setImageURI(image_uri);
            }
        }
    }

}

