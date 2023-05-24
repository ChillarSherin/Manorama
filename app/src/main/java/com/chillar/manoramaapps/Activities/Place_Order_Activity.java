package com.chillar.manoramaapps.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chillar.manoramaapps.Adapter.Place_Order_Adapter;
import com.chillar.manoramaapps.R;
import com.chillar.manoramaapps.Retrofit.PlaceOrdeList.PlaceOrdersCode;
import com.chillar.manoramaapps.Retrofit.PlaceOrdeList.PlaceOrdersList_Class;
import com.chillar.manoramaapps.Retrofit.PlaceOrdeList.PlaceOrdersStatus;
import com.chillar.manoramaapps.WrapContentLinearLayoutManager;
import com.chillar.manoramaapps.app.ApiClient;
import com.chillar.manoramaapps.app.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

/**
 *
 * Displaying all outlet sessions
 */
public class Place_Order_Activity extends AppCompatActivity {

    Activity activity;

    RecyclerView OrdersList;
    TextView DateEdittext;
    TextView DateEdittextNew;
    Toolbar toolbar;
    Place_Order_Adapter orderAdapter;

    String UserEmployeecode, UserID, BranchOfficeID,SelectedDate;
    Calendar myCalendar;
    Calendar myCalendar2;

    DrawerLayout recyclerlay;

    private final List<Integer> hardwareModuleID = new ArrayList<>();
    private final List<String> hardwareModuleName = new ArrayList<>();
    private final List<Integer> preorderSessionTimingID = new ArrayList<>();
    private final List<String> preorderSessionName = new ArrayList<>();
    private final List<Integer> orderTime = new ArrayList<>();
    private final List<String> prerderEndTiming = new ArrayList<>();

    Call<PlaceOrdersList_Class> call = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_place__order_);

        InitializeValues();


    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("Manorama", MODE_PRIVATE);
        UserEmployeecode = prefs.getString("EmployeeCode", "");
        UserID = prefs.getString("UserID", "");
        BranchOfficeID = prefs.getString("BranchOfficeID", "");
        final String IPADDRESS = prefs.getString("IPAddress", "");
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        final SimpleDateFormat mdformat2 = new SimpleDateFormat("dd-MM-yyyy");
        final String strDate = mdformat.format(calendar.getTime());
        String strDate2 = mdformat2.format(calendar.getTime());

        DateEdittext.setText("" + strDate);
        DateEdittextNew.setText("" + strDate2);

        findViewById(R.id.progressbar).setVisibility(View.VISIBLE);

        myCalendar = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Date now = new Date(System.currentTimeMillis());
                //System.out.println("MANORAMA  ::now  "+now);

                if (mdformat2.format(now).equals(mdformat2.format(myCalendar.getTime()))){

                    updateLabel(IPADDRESS);

                }else if (myCalendar.getTime().before(now)){

                    Toast.makeText(activity, "Please select a valid date", Toast.LENGTH_SHORT).show();

                }else if (myCalendar.getTime().after(now)){

                    updateLabel(IPADDRESS);

                }

            }

        };

        DateEdittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Place_Order_Activity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ListOrders(IPADDRESS);

    }

    /*
    * Api call function for listing all outlet sessions
    * */
    private void ListOrders(String IPADDRESS) {

        ApiInterface apiService =
                ApiClient.getClient(getApplicationContext(),IPADDRESS).create(ApiInterface.class);

        call = apiService.placeorderlists("r_outlet_sessions", UserEmployeecode, UserID, BranchOfficeID);
        call.enqueue(new Callback<PlaceOrdersList_Class>() {
            @Override
            public void onResponse(Call<PlaceOrdersList_Class> call, retrofit2.Response<PlaceOrdersList_Class> response) {

                findViewById(R.id.progressbar).setVisibility(View.GONE);

                String URL = call.request().url().toString();
                //System.out.println("Retrofit URL : " + URL);


                PlaceOrdersStatus status = response.body().getStatus();
                String code = status.getCode();

                if (code.equals("200")) {

                    hardwareModuleID.clear();
                    hardwareModuleName.clear();
                    preorderSessionTimingID.clear();
                    preorderSessionName.clear();
                    orderTime.clear();
                    prerderEndTiming.clear();

                    List<PlaceOrdersCode> transactionCode = response.body().getCode();
                    if (transactionCode.size() > 0) {

                        for (int i = 0; i < transactionCode.size(); i++) {

                            PlaceOrdersCode codeonline = transactionCode.get(i);
                            hardwareModuleID.add(codeonline.getHardwareModuleID());
                            hardwareModuleName.add(codeonline.getHardwareModuleName());
                            preorderSessionTimingID.add(codeonline.getPreorderSessionTimingID());
                            preorderSessionName.add(codeonline.getPreorderSessionName());
                            orderTime.add(codeonline.getOrderTime());
                            prerderEndTiming.add(codeonline.getPrerderEndTiming());

                            //System.out.println("PAYMENT : " + hardwareModuleID);
                            //System.out.println("PAYMENT 11: " + DateEdittext.getText().toString());
                        }
                    } else {
                        OrdersList.setVisibility(View.GONE);


                    }

                    OrdersList.setVisibility(View.VISIBLE);
                    OrdersList.setLayoutManager(new WrapContentLinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    OrdersList.setItemAnimator(new DefaultItemAnimator());
                    OrdersList.setNestedScrollingEnabled(false);
                    //System.out.println("MANORAMA  1111 : "+DateEdittext.getText().toString());
                    //System.out.println("MANORAMA  11111 : "+DateEdittextNew.getText().toString());
                    orderAdapter = new Place_Order_Adapter(hardwareModuleID, hardwareModuleName, preorderSessionTimingID,
                            preorderSessionName,
                            orderTime, prerderEndTiming, DateEdittextNew.getText().toString(), activity, Place_Order_Activity.this);
                    OrdersList.setAdapter(orderAdapter);

                } else if (code.equals("400")) {

                    OrdersList.setVisibility(View.GONE);
                    findViewById(R.id.progressbar).setVisibility(View.GONE);


                } else if (code.equals("204")) {

                    OrdersList.setVisibility(View.GONE);
                    findViewById(R.id.progressbar).setVisibility(View.GONE);


                } else if (code.equals("401")) {
                    OrdersList.setVisibility(View.GONE);
                    findViewById(R.id.progressbar).setVisibility(View.GONE);


                } else if (code.equals("403")) {
                    OrdersList.setVisibility(View.GONE);
                    findViewById(R.id.progressbar).setVisibility(View.GONE);


                } else if (code.equals("500")) {

                    OrdersList.setVisibility(View.GONE);
                    findViewById(R.id.progressbar).setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<PlaceOrdersList_Class> call, Throwable t) {

                Log.i("SHANIL ", "Call Failed" + call + "  ");
                t.printStackTrace();
                //System.out.println("SHANIL 3 : ");
                findViewById(R.id.progressbar).setVisibility(View.GONE);


            }
        });


    }
/*
* Function for initialize all elements in oncreate function
* */
    private void InitializeValues() {

        activity = this;
        toolbar = findViewById(R.id.toolbar);
        OrdersList = findViewById(R.id.orderlistview);
        DateEdittext = findViewById(R.id.dateedittext);
        DateEdittextNew = findViewById(R.id.dateedittextnew);
        recyclerlay = findViewById(R.id.drawer);
        toolbar.setTitle("Place Order");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
/*
* Updating dates to the textview on datepicker click
* */
    private void updateLabel(String IPADDRESS) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        String myFormat2 = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);

        DateEdittext.setText(sdf.format(myCalendar.getTime()));
        DateEdittextNew.setText(sdf2.format(myCalendar.getTime()));
        //System.out.println("MANORAMA   ::  "+DateEdittext.getText().toString());
        ListOrders(IPADDRESS);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {


            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            //System.out.println("TRESA KEYBOARD imm.isAcceptingText() " + imm.isAcceptingText());
            boolean keyback = imm.hideSoftInputFromWindow(recyclerlay.getWindowToken(), 0);
            //System.out.println("TRESA KEYBOARD keyback " + keyback);
            if (keyback) {

            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
