package com.chillar.manoramaapp.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chillar.manoramaapp.Activities.OrderItems;
import com.chillar.manoramaapp.R;
import com.chillar.manoramaapp.Retrofit.DeleteOrder.DeleteDetails;
import com.chillar.manoramaapp.Retrofit.DeleteOrder.DeleteStatus;
import com.chillar.manoramaapp.app.ApiClient;
import com.chillar.manoramaapp.app.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.MODE_PRIVATE;

/**
 * Adapter class of OrederItems class
 *
 */
public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.ViewHolder> {

    Activity activity1;
    String UserPhone, UserID, IPADDRESS, Itemsid, itemsaletransactionId, transid, editable, statuOrdeer;
    Call<DeleteDetails> call1 = null;
    private List<String> itemName = new ArrayList<String>();
    private List<String> itemPrice = new ArrayList<String>();
    private List<String> quantity = new ArrayList<String>();
    private List<String> itemID = new ArrayList<String>();
    private List<String> statuss = new ArrayList<String>();
    private List<String> itemEditable = new ArrayList<String>();

    public OrderItemsAdapter(List<String> itemID, List<String> itemName, List<String> itemPrice, List<String> quantity, List<String> statuss, List<String> itemEditable, String editable, String itemsaletransactionId, String transid, String statuOrdeer, Activity activity, Context context) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.statuOrdeer = statuOrdeer;

        this.itemID = itemID;
        this.itemsaletransactionId = itemsaletransactionId;
        this.statuss = statuss;
        this.itemEditable = itemEditable;
        this.editable = editable;
        this.transid = transid;
        activity1 = activity;
        Context mContext = context;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        SharedPreferences prefs = activity1.getSharedPreferences("Manorama", MODE_PRIVATE);
        UserPhone = prefs.getString("EmployeeCode", "");
        UserID = prefs.getString("UserID", "");
         IPADDRESS = prefs.getString("IPAddress", "");
        if (statuss.get(position).equals("Order Cancelled")) {

            holder.LayoutMain.setBackgroundResource(R.drawable.bg_curve_redbg);
            holder.ItemName.setTextColor(Color.WHITE);
            holder.txtAvailability.setTextColor(Color.WHITE);
            holder.itemqty.setTextColor(Color.WHITE);
            holder.itemprice.setTextColor(Color.WHITE);
            holder.Price.setTextColor(Color.WHITE);
            holder.Qty.setTextColor(Color.WHITE);
            holder.viewid.setBackgroundColor(Color.WHITE);
            holder.txtAvailability.setText("CANCELLED");
            holder.txtAvailability.setEnabled(false);

        } else if (statuss.get(position).equals("Order Purchased")) {
            holder.LayoutMain.setBackgroundResource(R.drawable.bg_curve_green);
            holder.ItemName.setTextColor(Color.WHITE);
            holder.txtAvailability.setTextColor(Color.WHITE);
            holder.itemqty.setTextColor(Color.WHITE);
            holder.itemprice.setTextColor(Color.WHITE);
            holder.Price.setTextColor(Color.WHITE);
            holder.Qty.setTextColor(Color.WHITE);
            holder.viewid.setBackgroundColor(Color.WHITE);
            holder.txtAvailability.setText("PURCHASED");
        }else {
            holder.LayoutMain.setBackgroundResource(R.drawable.bg_curve_white_main);
            holder.mainlayout.setBackgroundResource(R.drawable.bg_curve_grey);
        }

        try {
            holder.ItemName.setText(itemName.get(position));
        } catch (Exception ignored) {

        }
        try {
            holder.itemprice.setText(itemPrice.get(position));
        } catch (Exception ignored) {
        }

        try {
            holder.itemqty.setText(quantity.get(position));
        } catch (Exception ignored) {
        }

        holder.txtAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupstudentStatus(itemID.get(position),IPADDRESS);


            }
        });

    }

    private void popupstudentStatus(final String Itemid, final String IPADDRESS) {

        final Dialog SerDialogStudent;
        SerDialogStudent = new Dialog(activity1);
        SerDialogStudent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SerDialogStudent.setContentView(R.layout.activity_popupconfirmation);
        SerDialogStudent.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button OK = (Button) SerDialogStudent.findViewById(R.id.ok);
        Button CANCEL = (Button) SerDialogStudent.findViewById(R.id.cancel);
        try {
            SerDialogStudent.show();
        } catch (Exception e) {

        }


        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SerDialogStudent.dismiss();
                DeleteOrder(Itemid,IPADDRESS);

            }
        });
        CANCEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SerDialogStudent.dismiss();

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemName.size();
    }

    /*
    * Function for Deleting Order
    * */
    private void DeleteOrder(String Itemid, String IPADDRESS) {

        ApiInterface apiService =
                ApiClient.getClient(activity1,IPADDRESS).create(ApiInterface.class);


        call1 = apiService.getorderdelete("u_delete_order_item", UserPhone, UserID, itemsaletransactionId, Itemid);
        call1.enqueue(new Callback<DeleteDetails>() {
            @Override
            public void onResponse(Call<DeleteDetails> call, retrofit2.Response<DeleteDetails> response) {
                String URL = call.request().url().toString();
                //System.out.println("Retrofit URL : " + URL);

                DeleteStatus otpstatus = response.body().getStatus();
                String status = otpstatus.getCode();
                if (status.equals("200")) {

                    Intent intent = new Intent(activity1, OrderItems.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bundle b = new Bundle();
                    b.putString("editable", editable);
                    b.putString("transid", transid);
                    b.putString("status", statuOrdeer);
                    b.putString("itemsaletransactionId", itemsaletransactionId);
                    intent.putExtras(b);
                    activity1.startActivity(intent);
                } else {
                    Toast.makeText(activity1, "TRY AGAIN", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DeleteDetails> call, Throwable t) {
                Log.i("SHANIL ", "Call Failed" + call + "  ");
                t.printStackTrace();
            }


        });


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView ItemName, txtAvailability, itemprice, itemqty,Price,Qty;
        LinearLayout LayoutMain;
        LinearLayout mainlayout;
        View viewid;

        public ViewHolder(View itemView) {
            super(itemView);

            ItemName = itemView.findViewById(R.id.txtItem);
            txtAvailability = itemView.findViewById(R.id.txtAvailability);
            itemprice = itemView.findViewById(R.id.itemprice);
            itemqty = itemView.findViewById(R.id.itemqty);
            Price = itemView.findViewById(R.id.price);
            Qty = itemView.findViewById(R.id.qty);
            LayoutMain = itemView.findViewById(R.id.mainlayout);
            mainlayout = itemView.findViewById(R.id.statusbg);
            viewid = itemView.findViewById(R.id.viewid);
        }
    }
}
