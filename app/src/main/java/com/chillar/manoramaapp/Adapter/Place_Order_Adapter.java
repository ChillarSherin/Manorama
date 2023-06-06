package com.chillar.manoramaapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chillar.manoramaapp.R;
import com.chillar.manoramaapp.Activities.SelectItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class of Place_order_Activity
 *
 */
public class Place_Order_Adapter extends RecyclerView.Adapter<Place_Order_Adapter.ViewHolder> {

    Activity activity1;
    private List<Integer> hardwareModuleID = new ArrayList<Integer>();
    private List<String> hardwareModuleName = new ArrayList<String>();
    private List<Integer> preorderSessionTimingID = new ArrayList<Integer>();
    private List<String> preorderSessionName = new ArrayList<String>();
    private List<Integer> orderTime = new ArrayList<Integer>();
    private List<String> prerderEndTiming = new ArrayList<String>();
    Context mContext;
    String Date;


    public Place_Order_Adapter(List<Integer> hardwareModuleID, List<String> hardwareModuleName, List<Integer> preorderSessionTimingID,
                               List<String> preorderSessionName, List<Integer> orderTime, List<String> prerderEndTiming,
                                String Date,Activity activity, Context context) {
        this.hardwareModuleID = hardwareModuleID;
        this.hardwareModuleName = hardwareModuleName;
        this.preorderSessionTimingID = preorderSessionTimingID;
        this.preorderSessionName = preorderSessionName;
        this.orderTime = orderTime;
        this.Date = Date;
        this.prerderEndTiming = prerderEndTiming;
        activity1 = activity;
         mContext = context;
    }

    @Override
    public Place_Order_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_outlets_lists, parent, false);
        return new Place_Order_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Place_Order_Adapter.ViewHolder holder, final int position) {


        //System.out.println("MANORAMA  1::: "+preorderSessionTimingID.get(position));
        //System.out.println("MANORAMA  2::: "+hardwareModuleID.get(position));

        holder.Nametext.setText(""+preorderSessionName.get(position));
        holder.MainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //System.out.println("MANORAMA 3 ::: "+preorderSessionTimingID.get(position));
                //System.out.println("MANORAMA 4 ::: "+hardwareModuleID.get(position));
                //System.out.println("PAYMENT 111: " + Date);
                Intent in=new Intent(mContext, SelectItems.class);
                Bundle bn=new Bundle();
                bn.putInt("preorderSessionTimingID",preorderSessionTimingID.get(position));
                bn.putInt("hardwareModuleID",hardwareModuleID.get(position));
                bn.putString("Date",Date);
                in.putExtras(bn);
                mContext.startActivity(in);


            }
        });


    }


    @Override
    public int getItemCount() {
        return orderTime.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView Nametext;
        RelativeLayout MainLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            MainLayout =itemView.findViewById(R.id.cardlayout);
            Nametext =itemView.findViewById(R.id.ordernameid);

        }
    }

}
