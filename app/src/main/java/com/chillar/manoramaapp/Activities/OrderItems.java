package com.chillar.manoramaapp.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chillar.manoramaapp.Adapter.OrderItemsAdapter;
import com.chillar.manoramaapp.R;
import com.chillar.manoramaapp.Retrofit.OrderItemsList.OrderItemsCode;
import com.chillar.manoramaapp.Retrofit.OrderItemsList.OrderItemsDetails;
import com.chillar.manoramaapp.Retrofit.OrderItemsList.OrderItemsStatus;
import com.chillar.manoramaapp.WrapContentLinearLayoutManager;
import com.chillar.manoramaapp.app.ApiClient;
import com.chillar.manoramaapp.app.ApiInterface;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
/**
 * Order Items listing page
 *
 */
public class OrderItems extends AppCompatActivity implements ConnectivityChangeListener {

    Toolbar toolbar;
    Call<OrderItemsDetails> call = null;
    RecyclerView mRecyclerView;
    ProgressBar progbar;
    String UserPhone, UserID;
    Activity activity;
    Dialog NoInternet;
    int InternetFlag = 0;
    String transId, editable, StatusOrrder, itemsaletransactionId;
    LinearLayout Detailslay;
    Button Reload;
    TextView Txt_Content, txtOrderStatus,txtOrderStatus2;
    ImageView ErrorImage;
    private OrderItemsAdapter mAdapter;
    private List<String> itemName = new ArrayList<String>();
    private List<String> itemID = new ArrayList<String>();
    private List<String> itemPrice = new ArrayList<String>();
    private List<String> quantity = new ArrayList<String>();
    private List<String> statuss = new ArrayList<String>();
    private List<String> itemEditable = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_items);

        activity = this;
        initializeElements();
        PopupInternet();
    }

    /*
    * function for initialize all elements
    * */

    private void initializeElements() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order Items");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new OrderItemsAdapter(itemID, itemName, itemPrice, quantity, statuss, itemEditable, editable,
                itemsaletransactionId, transId, StatusOrrder, activity, OrderItems.this);
        mRecyclerView.setAdapter(mAdapter);
        findViewById(R.id.ErrorLayoutnew).setVisibility(View.GONE);
        Reload = (Button) findViewById(R.id.reloadbutton);
        Txt_Content = (TextView) findViewById(R.id.txt_content);
        ErrorImage = (ImageView) findViewById(R.id.img_no_image);
        Detailslay = (LinearLayout) findViewById(R.id.details);

        progbar = findViewById(R.id.progbar);
        txtOrderStatus = findViewById(R.id.txtOrderStatus1);
        txtOrderStatus2 = findViewById(R.id.txtOrderStatus2);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("Manorama", MODE_PRIVATE);
        UserPhone = prefs.getString("EmployeeCode", "");
        UserID = prefs.getString("UserID", "");
        progbar.setVisibility(View.VISIBLE);
        String IPADDRESS = prefs.getString("IPAddress", "");
        Bundle b = getIntent().getExtras();
        transId = b.getString("transid");
        editable = b.getString("editable");
        StatusOrrder = b.getString("status");
        itemsaletransactionId = b.getString("itemsaletransactionId");
        txtOrderStatus2.setText( StatusOrrder);

        if (StatusOrrder.equals("Order Cancelled")){

            txtOrderStatus2.setTextColor(Color.RED);

        }else if (StatusOrrder.equals("Order Purchased")){

            txtOrderStatus2.setTextColor(getResources().getColor(R.color.green));

        }else if (StatusOrrder.equals("Waiting for Delivery")){

            txtOrderStatus2.setTextColor(getResources().getColor(R.color.blue));

        }else {

            txtOrderStatus2.setTextColor(Color.WHITE);

        }

        OrderItems(transId, editable,IPADDRESS);

    }

    /**
     *  @param transId
     * @param editable
     * @param IPADDRESS
     */
    private void OrderItems(final String transId, final String editable, String IPADDRESS) {

        ApiInterface apiService =
                ApiClient.getClient(getApplicationContext(),IPADDRESS).create(ApiInterface.class);

        call = apiService.getorderitems("r_view_preorder", UserPhone, UserID, transId);
        call.enqueue(new Callback<OrderItemsDetails>() {
            @Override
            public void onResponse(Call<OrderItemsDetails> call, retrofit2.Response<OrderItemsDetails> response) {


                String URL = call.request().url().toString();
                //System.out.println("Retrofit URL : " + URL);

                progbar.setVisibility(View.GONE);
                final OrderItemsStatus status = response.body().getStatus();
                String code = status.getCode();
                String Messages = status.getMessage();

                if (code.equals("200")) {


                    itemName.clear();
                    itemID.clear();
                    itemPrice.clear();
                    quantity.clear();
                    statuss.clear();
                    itemEditable.clear();
                    List<OrderItemsCode> transactionCode = response.body().getCode();
                    if (transactionCode.size() > 0) {

                        for (int i = 0; i < transactionCode.size(); i++) {

                            OrderItemsCode codeonline = transactionCode.get(i);
                            itemName.add(codeonline.getItemName());
                            itemID.add(codeonline.getItemID());
                            itemPrice.add(codeonline.getItemPrice());
                            quantity.add(codeonline.getQuantity());
                            statuss.add(codeonline.getStatus());
                            itemEditable.add(codeonline.getItemEditable());
                            //System.out.println("PAYMENT : " + itemName);

                        }
                    } else {
                        mRecyclerView.setVisibility(View.GONE);
                        Detailslay.setVisibility(View.GONE);
                        Txt_Content.setText("No Details Found");
                        Reload.setText("GO BACK");
                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                        Reload.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                onBackPressed();

                            }
                        });


                    }
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setNestedScrollingEnabled(false);
                    mAdapter = new OrderItemsAdapter(itemID, itemName, itemPrice, quantity, statuss, itemEditable, editable,
                            itemsaletransactionId, transId, StatusOrrder, activity,OrderItems.this);
                    mRecyclerView.setAdapter(mAdapter);

                } else if (code.equals("400")) {

                    mRecyclerView.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);
                    Detailslay.setVisibility(View.GONE);
                    Txt_Content.setText("Some error occured please try again");
                    Reload.setText("TRY AGAIN");
                    ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(activity, OrderItems.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Bundle b = new Bundle();
                            b.putString("transid", transId);
                            b.putString("editable", editable);
                            b.putString("status", StatusOrrder);
                            b.putString("itemsaletransactionId", itemsaletransactionId);
                            intent.putExtras(b);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else if (code.equals("204")) {

                    mRecyclerView.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);
                    Detailslay.setVisibility(View.GONE);
                    Txt_Content.setText("No Details Found");
                    Reload.setText("GO BACK");
                    ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            onBackPressed();

                        }
                    });


                } else if (code.equals("401")) {
                    mRecyclerView.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);
                    Detailslay.setVisibility(View.GONE);
                    Txt_Content.setText("Some error occured please logout and try again");
                    Reload.setText("TRY AGAIN");
                    ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences sharedPreferences = getSharedPreferences("Manorama", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("UserName", "");
                            editor.putString("UserTypeKey", "");
                            editor.putString("EmployeeID", "");
                            editor.putString("EmployeeCode", "");
                            editor.putString("BranchOfficeID", "");
                            editor.putString("BranchName", "");
                            editor.putString("DesignationName", "");
                            editor.putString("DepartmentName", "");
                            editor.putString("UserID", "");
                            editor.putString("UserPhone", "");
                            editor.putString("Login", "False");
                            editor.apply();
                            Intent intent = new Intent(OrderItems.this, LogIn.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finishAffinity();
                        }
                    });

                } else if (code.equals("403")) {
                    mRecyclerView.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);
                    Detailslay.setVisibility(View.GONE);
                    Txt_Content.setText(Messages);
                    Reload.setText("GO BACK");
                    ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            onBackPressed();
                        }
                    });

                } else if (code.equals("500")) {

                    mRecyclerView.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);
                    Detailslay.setVisibility(View.GONE);
                    Txt_Content.setText("Some error occured please try again");
                    Reload.setText("TRY AGAIN");
                    ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(activity, OrderItems.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Bundle b = new Bundle();
                            b.putString("transid", transId);
                            b.putString("editable", editable);
                            b.putString("status", StatusOrrder);
                            b.putString("itemsaletransactionId", itemsaletransactionId);
                            intent.putExtras(b);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<OrderItemsDetails> call, Throwable t) {

                Log.i("SHANIL ", "Call Failed" + call + "  ");
                t.printStackTrace();
                //System.out.println("SHANIL 3 : ");
                mRecyclerView.setVisibility(View.GONE);
                progbar.setVisibility(View.GONE);
                Detailslay.setVisibility(View.GONE);
                Txt_Content.setText("Some error occured please try again");
                Reload.setText("TRY AGAIN");
                ErrorImage.setBackgroundResource(R.drawable.nodata);
                findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                Reload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(activity, OrderItems.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Bundle b = new Bundle();
                        b.putString("transid", transId);
                        b.putString("editable", editable);
                        b.putString("status", StatusOrrder);
                        b.putString("itemsaletransactionId", itemsaletransactionId);
                        intent.putExtras(b);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });

    }
/*
* Popup function for connection lost / internet lost
*
* */
    private void PopupInternet() {
        NoInternet = new Dialog(this);
        NoInternet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        NoInternet.setContentView(R.layout.popup_no_internet);
        NoInternet.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        NoInternet.setCancelable(false);
        NoInternet.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onConnectionChange(ConnectivityEvent event) {
        if (event.getState().getValue() == ConnectivityState.CONNECTED) {
            // device has active internet connection
            NoInternet.dismiss();
            if (InternetFlag == 1) {
                Intent intent = new Intent(OrderItems.this, OrderItems.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle b = new Bundle();
                b.putString("transid", transId);
                b.putString("editable", editable);
                b.putString("status", StatusOrrder);
                b.putString("itemsaletransactionId", itemsaletransactionId);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        } else {
            // there is no active internet connection on this device
            NoInternet.show();
            InternetFlag = 1;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionBuddy.getInstance().registerForConnectivityEvents(this, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ConnectionBuddy.getInstance().unregisterFromConnectivityEvents(this);
    }
}
