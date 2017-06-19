package com.everything22.bhanuprakashreddy.locashared.displayitemsActivies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.everything22.bhanuprakashreddy.locashared.PostActivity;
import com.everything22.bhanuprakashreddy.locashared.R;
import com.everything22.bhanuprakashreddy.locashared.SignupActivity;
import com.everything22.bhanuprakashreddy.locashared.modelclasses.EventEducationModel;
import com.everything22.bhanuprakashreddy.locashared.singlePostActivity.SinglePostViewActivity;

public class WorkShopActivity extends AppCompatActivity {
    private RecyclerView mEvent_List;

    private DatabaseReference mDatabaseRef;

    FirebaseAuth auth;
    private Toolbar mtoolbar;

    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_shop);
        mtoolbar = (Toolbar)findViewById(R.id.toolbar_design);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null) {
            try {
                getSupportActionBar().setTitle(" WorkShop ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        auth= FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user == null){
                    Intent i = new Intent(WorkShopActivity.this,SignupActivity.class);
                    startActivity(i);
                }else{
                    startActivity(new Intent(WorkShopActivity.this,PostActivity.class));
                }
            }
        };

        mEvent_List=(RecyclerView)findViewById(R.id.workshop_Recyclerview);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("WorkShops");

        mEvent_List.setHasFixedSize(true);
        mEvent_List.setLayoutManager(new LinearLayoutManager(this));
        mDatabaseRef.keepSynced(true);
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<EventEducationModel,WorkShopViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<EventEducationModel, WorkShopViewHolder>(
                EventEducationModel.class,
                R.layout.workshop_custom_cardview,
                WorkShopViewHolder.class,
                mDatabaseRef

        ) {
            @Override
            protected void populateViewHolder(WorkShopViewHolder viewHolder, EventEducationModel model, final int position) {
                final String Post_Key = getRef(position).getKey();

                viewHolder.setEvent_Name(model.getEvent_Name());
                viewHolder.setEvnt_desc(model.getEvnt_desc());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setEvent_location(model.getEvent_location());

                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent next = new Intent(WorkShopActivity.this, SinglePostViewActivity.class);
                        next.putExtra("items",Post_Key);
                        next.putExtra("Key1","WorkShops");
                        startActivity(next);
                    }
                });
            }
        };

        mEvent_List.setAdapter(firebaseRecyclerAdapter);
    }

    public static class  WorkShopViewHolder extends RecyclerView.ViewHolder {
        View mview;

        public WorkShopViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setEvent_Name(String event_Name) {
            TextView post_title = (TextView)mview.findViewById(R.id.ws_title);
            post_title.setText(event_Name);
        }

        public void setEvnt_desc(String evnt_desc) {
            TextView post_desc = (TextView)mview.findViewById(R.id.ws_desc);
            post_desc.setText(evnt_desc);
        }

        public void setEvent_location(String event_location){
            TextView post_location = (TextView)mview.findViewById(R.id.ws_location);
            post_location.setText(event_location);
        }

        public void setImage(final Context ctx, final String image) {
            final ImageView post_image = (ImageView)mview.findViewById(R.id.ws_image_recycle);
            //Picasso.with(ctx).load(image).into(post_image);

            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(post_image, new Callback() {
                @Override
                public void onSuccess() {
                    //Toast.makeText(ctx, "Image Upload Successfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError() {
                    Picasso.with(ctx).load(image).into(post_image);
                }
            });

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.education_item_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_item:
                startActivity(new Intent(WorkShopActivity.this,PostActivity.class));
                break;
            case R.id.Signout:
                signOut();
        }
        return super.onOptionsItemSelected(item);
    }
    public void signOut() {
        auth.signOut();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

}
