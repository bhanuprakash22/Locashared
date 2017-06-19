package com.everything22.bhanuprakashreddy.locashared.singlePostActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.everything22.bhanuprakashreddy.locashared.R;

public class SinglePostViewActivity extends AppCompatActivity {

    private String mPost_Key = null;
    private DatabaseReference mDatabase;
    private TextView mSingleTitle,mSingleDesc,mSingleLocation,mSingleAddress,mSinglePhone;
    private ImageView mSingleImage;
    String key1 = null;
    private TextView mToolbarTitle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post_view);

         mPost_Key = getIntent().getExtras().getString("items");

        key1 = getIntent().getExtras().getString("Key1");


        mDatabase= FirebaseDatabase.getInstance().getReference().child(key1);
        mToolbarTitle=(TextView)findViewById(R.id.toolbar_title);
        mToolbar=(Toolbar)findViewById(R.id.custom_toolbar_design);
        setSupportActionBar(mToolbar);

        //Toast.makeText(getApplicationContext(), Post_Key, Toast.LENGTH_SHORT).show();

        mSingleTitle=(TextView)findViewById(R.id.event_title_SingleView);
        mSingleDesc=(TextView)findViewById(R.id.event_desc_SingleView);
        mSingleLocation=(TextView)findViewById(R.id.event_location_SingleView);
        mSingleImage=(ImageView)findViewById(R.id.image_SingleView);
        mSingleAddress=(TextView)findViewById(R.id.event_address_SingleView);
        mSinglePhone=(TextView)findViewById(R.id.event_ph_SingleView);

        mDatabase.child(mPost_Key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String Post_Title = (String) dataSnapshot.child("event_Name").getValue();
                String Post_Desc= (String) dataSnapshot.child("evnt_desc").getValue();
                String Post_Image = (String) dataSnapshot.child("image").getValue();
                String Post_Location = (String)dataSnapshot.child("event_location").getValue();
                String Post_Address=(String)dataSnapshot.child("event_address").getValue();
                String Post_Phone = (String)dataSnapshot.child("phoneno").getValue();

                mSingleTitle.setText(Post_Title);
                mSingleDesc.setText(Post_Desc);
                mSingleLocation.setText(Post_Location);
                mSingleAddress.setText(Post_Address);
                mSinglePhone.setText(Post_Phone);
                Picasso.with(SinglePostViewActivity.this).load(Post_Image).into(mSingleImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SinglePostViewActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
