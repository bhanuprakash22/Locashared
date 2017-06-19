package com.everything22.bhanuprakashreddy.locashared.fragmentationActivities;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;
import com.everything22.bhanuprakashreddy.locashared.R;
import com.everything22.bhanuprakashreddy.locashared.singlePostActivity.SinglePostViewActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class ProfileDialog extends DialogFragment implements View.OnClickListener {
    private static final int GELLERY_REQ = 100;
    private CircleImageView mProfileImage;
    private Uri mImageuri = null;
    private TextView mProfileStatus;
    DatabaseReference mDatabaseref;
    StorageReference mStorageref;
    private Button btn_save;
    public ProfileDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_dialog_fragment,null);
        mProfileImage=(CircleImageView)view.findViewById(R.id.profile_circleview);
        mProfileStatus=(TextView)view.findViewById(R.id.tv_profile_name);
        btn_save=(Button)view.findViewById(R.id.btn_pro_save);
        mProfileImage.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        mStorageref= FirebaseStorage.getInstance().getReference();
        mDatabaseref= FirebaseDatabase.getInstance().getReference().child("ProfilePictures");

        setCancelable(true);
        return view;
    }

    private void startPostingData() {

        if (mImageuri != null){
            StorageReference filepath = mStorageref.child("Profile_Images").child(mImageuri.getLastPathSegment());
            filepath.putFile(mImageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getDownloadUrl();
                    DatabaseReference newpost = mDatabaseref.push();
                    newpost.child("profilepic").setValue(downloadUri.toString());

                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile_circleview:
                Intent profile = new Intent(Intent.ACTION_GET_CONTENT);
                profile.setType("image/*");
                startActivityForResult(profile,GELLERY_REQ);
                break;
            case R.id.btn_pro_save:
                startPostingData();
                setProfilePicture();
                dismiss();
                break;
        }
    }

    private void setProfilePicture() {

        mDatabaseref.child("ProfilePictures").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String Post_image = (String)dataSnapshot.child("profilepic").getValue();
                Picasso.with(getActivity()).load(Post_image).into(mProfileImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getActivity(), databaseError.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GELLERY_REQ && resultCode == RESULT_OK){
            mImageuri = data.getData();
            mProfileImage.setImageURI(mImageuri);
        }

    }
}
