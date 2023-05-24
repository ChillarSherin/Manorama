package com.chillar.manoramaapps.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chillar.manoramaapps.Activities.OrderItems;
import com.chillar.manoramaapps.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for Order history class
 */
public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    Activity activity1;
    private List<String> orderDate = new ArrayList<String>();
    private List<String> orderSession = new ArrayList<String>();
    private List<String> outletName = new ArrayList<String>();
    private List<String> totalAmount = new ArrayList<String>();
    private List<String> statusvalue = new ArrayList<String>();
    private List<String> itemSaleTransactionID = new ArrayList<String>();
    private List<String> totalEditable = new ArrayList<String>();

    public OrderHistoryAdapter(List<String> orderDate, List<String> orderSession, List<String> outletName, List<String> totalAmount, List<String> statusvalue, List<String> itemSaleTransactionID, List<String> totalEditable, Activity activity, Context context) {
        this.orderDate = orderDate;
        this.orderSession = orderSession;
        this.outletName = outletName;
        this.totalAmount = totalAmount;
        this.statusvalue = statusvalue;
        this.totalEditable = totalEditable;
        this.itemSaleTransactionID = itemSaleTransactionID;
        activity1 = activity;
        Context mContext = context;
    }

    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_view, parent, false);
        return new OrderHistoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            holder.txtDate.setText(orderDate.get(position));
        } catch (Exception ignored) {

        }
        try {
            holder.txtSession.setText(orderSession.get(position));
        } catch (Exception ignored) {
        }

        try {
            holder.txtOutlet.setText(outletName.get(position));
        } catch (Exception ignored) {
        }

        try {

            if (statusvalue.get(position).equals("Order Cancelled")){

                holder.txtStatus.setTextColor(Color.RED);

            }

            holder.txtStatus.setText(statusvalue.get(position));
        } catch (Exception ignored) {
        }

        try {
            holder.txtTotal.setText(totalAmount.get(position));
        } catch (Exception ignored) {
        }

        holder.LaytMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity1, OrderItems.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle b = new Bundle();
                b.putString("transid", itemSaleTransactionID.get(position));
                b.putString("editable", totalEditable.get(position));
                b.putString("status", statusvalue.get(position));
                b.putString("itemsaletransactionId", itemSaleTransactionID.get(position));
                intent.putExtras(b);
                activity1.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return orderDate.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout LaytMore;
        TextView txtDate, txtSession, txtOutlet, txtStatus, txtTotal;

        public ViewHolder(View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txtDate);
            txtSession = itemView.findViewById(R.id.txtSession);
            txtOutlet = itemView.findViewById(R.id.txtOutlet);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            LaytMore = itemView.findViewById(R.id.LaytMore);
        }
    }

}
