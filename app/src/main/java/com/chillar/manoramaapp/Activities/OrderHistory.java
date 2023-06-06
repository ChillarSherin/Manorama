package com.chillar.manoramaapp.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chillar.manoramaapp.Adapter.OrderHistoryAdapter;
import com.chillar.manoramaapp.R;
import com.chillar.manoramaapp.Retrofit.OrderHistory.OrderHistoryCode;
import com.chillar.manoramaapp.Retrofit.OrderHistory.OrderHistoryDetails;
import com.chillar.manoramaapp.Retrofit.OrderHistory.OrderHistoryStatus;
import com.chillar.manoramaapp.WrapContentLinearLayoutManager;
import com.chillar.manoramaapp.app.ApiClient;
import com.chillar.manoramaapp.app.ApiInterface;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Employee order history
 *
 */
public class OrderHistory extends AppCompatActivity implements ConnectivityChangeListener {

    Toolbar toolbar;
    RecyclerView mRecyclerView;
    EditText DateEdt;
    EditText DateEdtTo;
    TextView FromDateTxtview,TodateTcxtvNew;
    Call<OrderHistoryDetails> call = null;
    Activity activity;
    String UserPhone,IPADDRESS, UserID;
    ProgressBar progbar;
    RelativeLayout LaytDate;
    RelativeLayout LaytDateTo;
    int mYear = 0, mMonth = 0, mDay = 0;
    int mYear2 = 0, mMonth2 = 0, mDay2 = 0;
    String DATE = "";
    String DATE2 = "";
    Dialog NoInternet;
    int InternetFlag = 0;
    Button Reload;
    TextView Txt_Content;
    ImageView ErrorImage;
    private OrderHistoryAdapter mAdapter;
    private List<String> orderDate = new ArrayList<String>();
    private List<String> orderSession = new ArrayList<String>();
    private List<String> outletName = new ArrayList<String>();
    private List<String> totalAmount = new ArrayList<String>();
    private List<String> statusvalue = new ArrayList<String>();
    private List<String> itemSaleTransactionID = new ArrayList<String>();
    private List<String> totalEditable = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_history);
        activity = this;
        initializeElements();
        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new OrderHistoryAdapter(orderDate, orderSession, outletName, totalAmount, statusvalue, itemSaleTransactionID,
                totalEditable, activity, OrderHistory.this);
        mRecyclerView.setAdapter(mAdapter);
        PopupInternet();

        LaytDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mYear == 0) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mYear = year;
                                mMonth = monthOfYear;
                                mDay = dayOfMonth;
                                DateEdt.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                FromDateTxtview.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                DATE = DateEdt.getText().toString();
                                progbar.setVisibility(View.VISIBLE);
                                mRecyclerView.setAdapter(null);
                                mRecyclerView.setVisibility(View.GONE);
                                progbar.setVisibility(View.GONE);
                                OrderHistory(DATE,DATE2, IPADDRESS);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        LaytDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mYear2 == 0) {
                    final Calendar c = Calendar.getInstance();
                    mYear2 = c.get(Calendar.YEAR);
                    mMonth2 = c.get(Calendar.MONTH);
                    mDay2 = c.get(Calendar.DAY_OF_MONTH);
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mYear2 = year;
                                mMonth2 = monthOfYear;
                                mDay2 = dayOfMonth;
                                DateEdtTo.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                TodateTcxtvNew.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                DATE2 = DateEdtTo.getText().toString();
                                progbar.setVisibility(View.VISIBLE);
                                mRecyclerView.setAdapter(null);
                                mRecyclerView.setVisibility(View.GONE);
                                progbar.setVisibility(View.GONE);
                                OrderHistory(DATE,DATE2, IPADDRESS);
                            }
                        }, mYear2, mMonth2, mDay2);
                datePickerDialog.show();
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
/*
*
* Function for initialize all elements
* */
    private void initializeElements() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order History");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mRecyclerView = findViewById(R.id.list);
        progbar = findViewById(R.id.progbar);
        DateEdt = findViewById(R.id.txtDate);
        DateEdtTo = findViewById(R.id.todate);
        LaytDate = findViewById(R.id.LaytDate);
        LaytDateTo = findViewById(R.id.LaytDateto);
        FromDateTxtview = findViewById(R.id.txtDatenew);
        TodateTcxtvNew = findViewById(R.id.todatenew);
        findViewById(R.id.ErrorLayoutnew).setVisibility(View.GONE);
        Reload = (Button) findViewById(R.id.reloadbutton);
        Reload.setVisibility(View.GONE);
        Txt_Content = (TextView) findViewById(R.id.txt_content);
        ErrorImage = (ImageView) findViewById(R.id.img_no_image);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mYear2 = c.get(Calendar.YEAR);
        mMonth2 = c.get(Calendar.MONTH);
        mDay2 = c.get(Calendar.DAY_OF_MONTH);
        DateEdt.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
        DateEdtTo.setText(mYear2 + "-" + (mMonth2 + 1) + "-" + mDay2);
        FromDateTxtview.setText(mDay + "-" + (mMonth + 1) + "-" + mYear);
        TodateTcxtvNew.setText(mDay2 + "-" + (mMonth2 + 1) + "-" + mYear2);

        SharedPreferences prefs = getSharedPreferences("Manorama", MODE_PRIVATE);
        UserPhone = prefs.getString("EmployeeCode", "");
        UserID = prefs.getString("UserID", "");
        IPADDRESS = prefs.getString("IPAddress", "");
        DATE = DateEdt.getText().toString();
        DATE2 = DateEdtTo.getText().toString();
        progbar.setVisibility(View.VISIBLE);
        OrderHistory(DATE,DATE2,IPADDRESS);

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

        progbar.setVisibility(View.VISIBLE);


    }

    /**
     *
     * Api call for getting all order history by selecting from date and to date
     * @param Dates - From date
     * @param Dates2 - to date
     * @param IPADDRESS
     */
    private void OrderHistory(String Dates, String Dates2, String IPADDRESS) {

        ApiInterface apiService =
                ApiClient.getClient(getApplicationContext(),IPADDRESS).create(ApiInterface.class);

        call = apiService.getorderhistory("r_order_history", UserPhone, UserID, Dates,Dates2);
        call.enqueue(new Callback<OrderHistoryDetails>() {
            @Override
            public void onResponse(Call<OrderHistoryDetails> call, retrofit2.Response<OrderHistoryDetails> response) {


                String URL = call.request().url().toString();
                //System.out.println("Retrofit URL : " + URL);

                progbar.setVisibility(View.GONE);
                OrderHistoryStatus status = response.body().getStatus();
                String code = status.getCode();
                String MESSAGES = status.getMessage();

                if (code.equals("200")) {

                    orderDate.clear();
                    orderSession.clear();
                    outletName.clear();
                    totalAmount.clear();
                    statusvalue.clear();
                    totalEditable.clear();
                    itemSaleTransactionID.clear();

                    List<OrderHistoryCode> transactionCode = response.body().getCode();
                    if (transactionCode.size() > 0) {

                        for (int i = 0; i < transactionCode.size(); i++) {

                            OrderHistoryCode codeonline = transactionCode.get(i);
                            orderDate.add(codeonline.getOrderDate());
                            orderSession.add(codeonline.getOrderSession());
                            outletName.add(codeonline.getOutletName());
                            totalAmount.add(codeonline.getTotalAmount());
                            statusvalue.add(codeonline.getStatus());
                            itemSaleTransactionID.add(codeonline.getItemSaleTransactionID());
                            totalEditable.add(codeonline.getTotalEditable());
                            //System.out.println("PAYMENT : " + orderDate);
                        }
                    } else {
                        mRecyclerView.setVisibility(View.GONE);
                        Txt_Content.setText("No Order Found");
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
                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setNestedScrollingEnabled(false);
                    mAdapter = new OrderHistoryAdapter(orderDate, orderSession, outletName, totalAmount, statusvalue, itemSaleTransactionID,
                            totalEditable, activity, OrderHistory.this);
                    mRecyclerView.setAdapter(mAdapter);

                } else if (code.equals("400")) {

                    mRecyclerView.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);

                    Txt_Content.setText("Some error occured please try again");
                    Reload.setText("TRY AGAIN");
                    ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(OrderHistory.this, OrderHistory.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }
                    });
                } else if (code.equals("204")) {

                    mRecyclerView.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);

                    Txt_Content.setText("No Order Found");
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

                    Txt_Content.setText("Some error occured please logout and try again");
                    Reload.setText("LOGOUT");
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
                            Intent intent = new Intent(OrderHistory.this, LogIn.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finishAffinity();

                        }
                    });

                } else if (code.equals("403")) {
                    mRecyclerView.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);

                    Txt_Content.setText(MESSAGES);
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

                    Txt_Content.setText("Some error occured please try again");
                    Reload.setText("TRY AGAIN");
                    ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(OrderHistory.this, OrderHistory.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<OrderHistoryDetails> call, Throwable t) {

                Log.i("SHANIL ", "Call Failed" + call + "  ");
                t.printStackTrace();
                //System.out.println("SHANIL 3 : ");
                mRecyclerView.setVisibility(View.GONE);
                progbar.setVisibility(View.GONE);

                Txt_Content.setText("Some error occured please try again");
                Reload.setText("TRY AGAIN");
                ErrorImage.setBackgroundResource(R.drawable.nodata);
                findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                Reload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(OrderHistory.this, OrderHistory.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });
            }
        });


    }

    /*
    * Popup function for connection lost/ internet lost
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
                Intent intent = new Intent(OrderHistory.this, OrderHistory.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
