package com.everything22.bhanuprakashreddy.locashared;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.everything22.bhanuprakashreddy.locashared.introactivity.IntroducationActivity;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    private AppCompatImageButton Image_event_button;
    private static final int GALLERY_REQUEST_CODE = 1;
    private Uri mImageUri=null;

    private ProgressDialog mprogressbar;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;

    private AppCompatSpinner cat_spinner;
    private AppCompatButton event_button_Post;

    private TextInputLayout event_textinputlayout;
    private TextInputLayout desc_textinputlayout;
    private TextInputLayout Address_textinputlayout;
    private TextInputLayout location_textinputlayout;
    private  TextInputLayout phone_textinputlayout;

    private TextInputEditText event_edittext;
    private TextInputEditText desc_edittext;
    private TextInputEditText address_edittext ;
    private TextInputEditText location_edittext;
    private TextInputEditText phone_edittext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        event_button_Post =(AppCompatButton)findViewById(R.id.event_button_Post);
        mprogressbar = new ProgressDialog(this);
        mStorage = FirebaseStorage.getInstance().getReference();
      //  mDatabase = FirebaseDatabase.getInstance().getReference().child("Education");
        galleryFromImages();
        initialViews();
        event_button_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPostingData();
            }
        });

        ArrayList<String> list = new ArrayList<String>();
        list.add("Education");
        list.add("Business");
        list.add("Party");
        list.add("WorkShops");
        list.add("Concerts");
        list.add("Exhibitions");
        list.add("MeetUps");
        list.add("Sports");
        list.add("FoodAndDrinks");
        list.add("Health");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat_spinner.setAdapter(arrayAdapter);

        cat_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Log.i("Items"," "+items);

                switch (items){

                    case "Party":
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Party");
                        break;
                    case "Business":
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Business");
                        break;
                    case "Education":
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Education");
                        break;
                    case "WorkShops":
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("WorkShops");
                        break;
                    case "Concerts":
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Concerts");
                        break;
                    case "Exhibitions":
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Exhibitions");
                        break;
                    case "MeetUps":
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("MeetUps");
                        break;
                    case "Sports":
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Sports");
                        break;
                    case "FoodAndDrinks":
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("FoodAndDrinks");
                        break;
                    case "Health":
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Health");
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }










    private void initialViews() {
        event_textinputlayout = (TextInputLayout)findViewById(R.id.event_textinputlayout);
        desc_textinputlayout = (TextInputLayout)findViewById(R.id.desc_textinputlayout);
        Address_textinputlayout = (TextInputLayout)findViewById(R.id.Address_textinputlayout);
        location_textinputlayout = (TextInputLayout)findViewById(R.id.location_textinputlayout);
        phone_textinputlayout=(TextInputLayout)findViewById(R.id.Phone_textinputlayout);

        event_edittext =(TextInputEditText)findViewById(R.id.event_edittext);
        desc_edittext =(TextInputEditText)findViewById(R.id.desc_edittext);
        address_edittext=(TextInputEditText)findViewById(R.id.address_edittext);
        location_edittext =(TextInputEditText)findViewById(R.id.location_edittext);
        phone_edittext=(TextInputEditText)findViewById(R.id.phone_edittext);

        cat_spinner =(AppCompatSpinner)findViewById(R.id.cat_spinner);

    }

    private void galleryFromImages() {

        Image_event_button =(AppCompatImageButton)findViewById(R.id.event_images);

        Image_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){
             mImageUri = data.getData();

            CropImage.activity(mImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Image_event_button.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

            }
        }
    }


    private void startPostingData() {

        mprogressbar.setMessage("Posting Data....");


        final String event_title = event_edittext.getText().toString().trim();
        final String event_desc = desc_edittext.getText().toString().trim();
        final String event_location = location_edittext.getText().toString().trim();
        final String event_address = address_edittext.getText().toString().trim();
        final String event_phone =phone_edittext.getText().toString().trim();

        if (TextUtils.isEmpty(event_title)){
            Toast.makeText(getApplicationContext(), "Enter Event Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(event_desc)){
            Toast.makeText(getApplicationContext(), "Enter Description of an Event", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(event_location)){
            Toast.makeText(getApplicationContext(), "Enter Location Name", Toast.LENGTH_SHORT).show();
            return;
        }
        /*if (TextUtils.isEmpty(event_ImageUrl)){
            Toast.makeText(getApplicationContext(), "Enter ImageUrl ", Toast.LENGTH_SHORT).show();
            return;
        }*/
        if (TextUtils.isEmpty(event_phone)){
            Toast.makeText(getBaseContext(), "Enter Phone Number Here", Toast.LENGTH_SHORT).show();
        }

        if (!TextUtils.isEmpty(event_title) && !TextUtils.isEmpty(event_desc) && !TextUtils.isEmpty(event_location) && !TextUtils.isEmpty(event_phone) && mImageUri != null ){
            mprogressbar.show();
            StorageReference filepath = mStorage.child("Educaion_Images").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost = mDatabase.push();
                    newPost.child("event_Name").setValue(event_title);
                    newPost.child("evnt_desc").setValue(event_desc);
                    newPost.child("event_address").setValue(event_address);
                    newPost.child("event_location").setValue(event_location);
                    newPost.child("image").setValue(downloadUri.toString());
                    newPost.child("phoneno").setValue(event_phone);
                    mprogressbar.dismiss();


                    Intent i = new Intent(PostActivity.this,IntroducationActivity.class);
                    emptyInputEditText();
                    startActivity(i);

                }
            });
        }
    }

    private void emptyInputEditText() {
        event_edittext.setText(null);
        desc_edittext.setText(null);
        location_edittext.setText(null);
        address_edittext.setText(null);
        phone_edittext.setText(null);
        Image_event_button.setImageResource(R.drawable.event_small);
    }


}
