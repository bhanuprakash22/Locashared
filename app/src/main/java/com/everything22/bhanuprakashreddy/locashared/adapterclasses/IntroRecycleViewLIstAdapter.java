package com.everything22.bhanuprakashreddy.locashared.adapterclasses;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.everything22.bhanuprakashreddy.locashared.DisplayEducationalEventsActivity;
import com.everything22.bhanuprakashreddy.locashared.R;
import com.everything22.bhanuprakashreddy.locashared.displayitemsActivies.BusinessActivity;
import com.everything22.bhanuprakashreddy.locashared.displayitemsActivies.ConcertActivity;
import com.everything22.bhanuprakashreddy.locashared.displayitemsActivies.ExhibitionActivity;
import com.everything22.bhanuprakashreddy.locashared.displayitemsActivies.FoodAndDrinkActivity;
import com.everything22.bhanuprakashreddy.locashared.displayitemsActivies.HealthActivity;
import com.everything22.bhanuprakashreddy.locashared.displayitemsActivies.MeetUpActivity;
import com.everything22.bhanuprakashreddy.locashared.displayitemsActivies.PartyActivity;
import com.everything22.bhanuprakashreddy.locashared.displayitemsActivies.SportActivity;
import com.everything22.bhanuprakashreddy.locashared.displayitemsActivies.WorkShopActivity;
import com.everything22.bhanuprakashreddy.locashared.modelclasses.Introitems;

import java.util.ArrayList;



public class IntroRecycleViewLIstAdapter extends RecyclerView.Adapter<IntroRecycleViewLIstAdapter.IntroViewAdapter> {

    Context context;
    public ArrayList<Introitems> list;

    public IntroRecycleViewLIstAdapter(ArrayList<Introitems> list,Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public IntroViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.introducation_row_list,parent,false);
        IntroViewAdapter imageAdapter = new IntroViewAdapter(view,list,context);
        return imageAdapter;
    }

    @Override
    public void onBindViewHolder(IntroViewAdapter holder, final int position) {

        Introitems items = list.get(position);
        holder.textView.setText(items.getImage_Text());
        holder.imageView.setImageResource(items.getImage_ids());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

        public static class IntroViewAdapter extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            private final Context context;
            ArrayList<Introitems> list = new ArrayList<Introitems>();


            public IntroViewAdapter(View view, final ArrayList<Introitems> list,  Context context) {
                super(view);
                this.context = context;
                this.list = list;
                imageView=(ImageView)view.findViewById(R.id.event_image_intro);
                textView=(TextView)view.findViewById(R.id.event_text_into);

                context = view.getContext();

                final Context finalContext = context;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Intent intent=null;
                        switch (getAdapterPosition()){
                            case 0:
                               intent =  new Intent(finalContext, BusinessActivity.class);
                                break;
                            case 1:
                                intent = new Intent(finalContext,DisplayEducationalEventsActivity.class);
                                break;
                            case 2:
                                intent = new Intent(finalContext,WorkShopActivity.class);
                                break;
                            case 3:
                                intent = new Intent(finalContext,ConcertActivity.class);
                                break;
                            case 4:
                                intent = new Intent(finalContext,ExhibitionActivity.class);
                                break;
                            case 5:
                                intent = new Intent(finalContext,PartyActivity.class);
                                break;
                            case 6:
                                intent = new Intent(finalContext,MeetUpActivity.class);
                                break;
                            case 7:
                                intent = new Intent(finalContext,SportActivity.class);
                                break;
                            case 8:
                                intent = new Intent(finalContext,FoodAndDrinkActivity.class);
                                break;
                            case 9:
                                intent = new Intent(finalContext,HealthActivity.class);
                                break;

                            default:
                                Toast.makeText(finalContext, "Select Any One Of Items", Toast.LENGTH_SHORT).show();
                                break;






                        }
                        finalContext.startActivity(intent);
                    }
                });



            }
        }
}
