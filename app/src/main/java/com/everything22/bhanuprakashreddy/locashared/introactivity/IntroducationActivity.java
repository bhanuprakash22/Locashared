package com.everything22.bhanuprakashreddy.locashared.introactivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.everything22.bhanuprakashreddy.locashared.LoginActivity;
import com.everything22.bhanuprakashreddy.locashared.R;
import com.everything22.bhanuprakashreddy.locashared.ResetPasswordActivity;
import com.everything22.bhanuprakashreddy.locashared.accountactivies.ChangeEMailActivity;
import com.everything22.bhanuprakashreddy.locashared.accountactivies.ChangePasswordActivity;
import com.everything22.bhanuprakashreddy.locashared.accountactivies.DeleteAccountActivity;
import com.everything22.bhanuprakashreddy.locashared.adapterclasses.IntroRecycleViewLIstAdapter;
import com.everything22.bhanuprakashreddy.locashared.adapterclasses.ViewPageAdapter;
import com.everything22.bhanuprakashreddy.locashared.fragmentationActivities.ProfileDialog;
import com.everything22.bhanuprakashreddy.locashared.modelclasses.Introitems;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class IntroducationActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private Toolbar toolbar;

    private ViewPager viewPager;
    LinearLayout sliderDotpanel;
    private int dotcount;
    private ImageView[] dots;

    private NestedScrollView nestedScrollView;


    //RecycleView Fileds

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    int[] img_id={R.drawable.ic_bussiness,R.drawable.ic_education,R.drawable.ic_workshops,R.drawable.ic_concets,
            R.drawable.ic_exhibitions,R.drawable.ic_parties,R.drawable.ic_meetings,R.drawable.ic_sports,R.drawable.ic_food,R.drawable.ic_health};
    String[] name;
    ArrayList<Introitems> arrayList = new ArrayList<Introitems>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducation);

        nestedScrollView=(NestedScrollView)findViewById(R.id.intro_nestedscrollview);


        name=getResources().getStringArray(R.array.Image_Names);
        int count=0;
        for(String Name : name){
            Introitems Item =new Introitems(img_id[count],Name);
            count++;
            arrayList.add(Item);
        }
        recyclerView=(RecyclerView)findViewById(R.id.intro_recycle);
        layoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new IntroRecycleViewLIstAdapter(arrayList,getApplicationContext());
        recyclerView.setAdapter(adapter);

        int spanCount = 2; // 3 columns
        int spacing = 25; // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

     //   recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));


        viewPager=(ViewPager)findViewById(R.id.viewPager);
        sliderDotpanel=(LinearLayout)findViewById(R.id.sliderDots);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(this);
        viewPager.setAdapter(viewPageAdapter);

        dotcount=viewPageAdapter.getCount();
        dots=new ImageView[dotcount];

        for (int i=0;i<dotcount;i++){
                dots[i]= new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8,0,8,0);
            sliderDotpanel.addView(dots[i],params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i =0;i<dotcount;i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));

                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
            Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(IntroducationActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            IntroducationActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    }else if (viewPager.getCurrentItem()==1){
                        viewPager.setCurrentItem(2);
                    }else if (viewPager.getCurrentItem()==2){
                        viewPager.setCurrentItem(3);
                    }else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account_menu_details,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.change_Email:
                Intent email = new Intent(IntroducationActivity.this, ChangeEMailActivity.class);
                startActivity(email);
                break;
            case R.id.change_Password:
                Intent changepassword = new Intent(IntroducationActivity.this, ChangePasswordActivity.class);
                startActivity(changepassword);
                break;
            case R.id.forget_password:
                Intent forgetpassword = new Intent(IntroducationActivity.this, ResetPasswordActivity.class);
                startActivity(forgetpassword);
                break;
            case R.id.delete_account:
                Intent delte = new Intent(IntroducationActivity.this, DeleteAccountActivity.class);
                startActivity(delte);
                break;
            case R.id.signout:
                signOut();
                break;
            case R.id.profile:
                android.app.FragmentManager manager = getFragmentManager();
                ProfileDialog profileDialog = new ProfileDialog();
                profileDialog.show(manager,"ProfileDialog");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void signOut() {
        auth.signOut();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public void onBackPressed() {
        Log.i("Back Pressed", "Successfully");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}
