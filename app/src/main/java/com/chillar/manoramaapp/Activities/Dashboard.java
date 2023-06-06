package com.chillar.manoramaapp.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.chillar.manoramaapp.R;
import com.chillar.manoramaapp.Retrofit.DashBoard.DashBoardCode;
import com.chillar.manoramaapp.Retrofit.DashBoard.DashBoardDetails;
import com.chillar.manoramaapp.Retrofit.DashBoard.DashBoardStatus;
import com.chillar.manoramaapp.app.ApiClient;
import com.chillar.manoramaapp.app.ApiInterface;
import com.google.android.material.navigation.NavigationView;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * User DashBoard page
 *
 */
public class Dashboard extends AppCompatActivity implements ConnectivityChangeListener {

    NavigationView navigationView;
    DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    Call<DashBoardDetails> call1 = null;
    Dialog NoInternet;
    int InternetFlag = 0;
    String UserPhone, UserName, UserID;
    String TotalTransaction, FirstDate,TotalAmount,TotalTrans, LastDate;
    TextView TxtTotal;
    TextView TxtTotalTrans;
    TextView VersionId;
    TextView Username;
    TextView statusid;
    Button LaytPlaceOrder, LaytOrderHistory;
    Dialog DialogSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        PopupInternet();
        initializeElements();
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        // This method will trigger on item Click of navigation menu
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            // Set item in checked state
                            menuItem.setChecked(true);
                            if (menuItem.getItemId() == R.id.nav_logout) {

                                //System.out.println("Manorama 111   ");

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
                                editor.putString("Login", "false");
                                editor.putString("UserPhone", "");
                                editor.putString("Get_Total_Transaction", "False");
                                editor.apply();
                                Intent intent = new Intent(Dashboard.this, LogIn.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }/*else if (menuItem.getItemId() == R.id.nav_switch){
                                //System.out.println("Manorama 1111   ");
                                DialogSwitch = new Dialog(Dashboard.this, android.R.style.Theme_Dialog);
                                DialogSwitch.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                DialogSwitch.setContentView(R.layout.add_ipaddress);
                                DialogSwitch.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                DialogSwitch.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                LinearLayout EnteButton=DialogSwitch.findViewById(R.id.buttonadd);
                                final EditText IpEdt=DialogSwitch.findViewById(R.id.nameid);
                                final ImageView Close=DialogSwitch.findViewById(R.id.close);
                                DialogSwitch.show();
                                EnteButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        if (IpEdt.getText().toString().equals("")){

                                            Toast.makeText(Dashboard.this, "Enter ip address to continue...", Toast.LENGTH_SHORT).show();

                                        }else {

                                            DialogSwitch.dismiss();
                                            SharedPreferences prefs = getSharedPreferences("Manorama", MODE_PRIVATE);
                                            String Switchvalue = prefs.getString("Switchvalue", "");
                                            SharedPreferences sharedPreferences = getSharedPreferences("Manorama", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            if (Switchvalue.equals("1")) {
                                                editor.putString("Switchvalue", "2");
                                                editor.putString("IPADDRESS", IpEdt.getText().toString());
                                            }else
                                            {
                                                editor.putString("Switchvalue", "1");
                                                editor.putString("IPADDRESS", IpEdt.getText().toString());
                                            }
                                            editor.apply();
                                            Intent intent = new Intent(Dashboard.this, Dashboard.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();

                                        }



                                    }
                                });
                                Close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        DialogSwitch.dismiss();

                                    }
                                });



                            }*/
                            // Closing drawer on item click
                            mDrawerLayout.closeDrawers();
                            return true;
                        }
                    });
        }
        LaytPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Place_Order_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        LaytOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, OrderHistory.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
/*
* Function for Initializing all elemnets of this class
* */
    private void initializeElements() {
        navigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer);
        View header = navigationView.getHeaderView(0);
        toolbar = findViewById(R.id.toolbar);
        TxtTotal = findViewById(R.id.txtTotal);
        TxtTotalTrans = findViewById(R.id.txttoatltrans);
        VersionId = header.findViewById(R.id.nameid);
        Username = findViewById(R.id.username);
        statusid = findViewById(R.id.statusid);
        toolbar.setTitle("MANORAMA");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        LaytOrderHistory = findViewById(R.id.LaytOrderHistory);
        LaytPlaceOrder = findViewById(R.id.LaytPlaceOrder);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.nav) {
            if (mDrawerLayout.isDrawerVisible(GravityCompat.END)) {
                mDrawerLayout.closeDrawer(GravityCompat.END);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.END);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * popup function for connection lost condition/ internet lost
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

    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("Manorama", MODE_PRIVATE);
        UserPhone = prefs.getString("EmployeeCode", "");
        UserID = prefs.getString("UserID", "");
        UserName = prefs.getString("UserName", "");
        PackageManager manager = getApplicationContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
       String versionNew = info.versionName;
        VersionId.setText("Version : " + versionNew);
        Username.setText(" " + UserName);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Quicksand-Medium.ttf");
        Username.setTypeface(custom_font);
        getTimeFromAndroid();
        String IPADDRESS = prefs.getString("IPAddress", "");
        Get_Total_Transaction(IPADDRESS);
    }

    private void getTimeFromAndroid() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            statusid.setText("Good Morning");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            statusid.setText("Good Afternoon");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            statusid.setText("Good Evening");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            statusid.setText("Good Night");
        }

    }

    /*
     * Api call for getting current month total transaction and total amount of this user
     *
     * */
    private void Get_Total_Transaction(String Api) {

        ApiInterface apiService =
                ApiClient.getClient(getApplicationContext(),Api).create(ApiInterface.class);

        //System.out.println("SHANIL 1 : ");
        call1 = apiService.getcurrntmnthtransaction("r_current_month_total_transaction", UserPhone, UserID);
        call1.enqueue(new Callback<DashBoardDetails>() {
            @Override
            public void onResponse(Call<DashBoardDetails> call, retrofit2.Response<DashBoardDetails> response) {

                try {


                String URL = call.request().url().toString();
                System.out.println("Retrofit URL : " + URL);
                System.out.println("SHANIL 2 : ");
                DashBoardStatus otpstatus = response.body().getStatus();
                String status = otpstatus.getCode();
                if (status.equals("200")) {

                    DashBoardCode otpcode = response.body().getCode();
                    TotalTransaction = String.valueOf(otpcode.getTotalTransaction());
                    FirstDate = otpcode.getFirstDate();
                    TotalAmount = otpcode.getTotalAmount();
                    TotalTrans = String.valueOf(otpcode.getTotalTransaction());
                    LastDate = otpcode.getLastDate();
                    if (TotalAmount.equals("null")){
                        TotalAmount="0";
                    }
                    TxtTotal.setText(TotalAmount);
                    if (TotalTrans.equals("null")){
                        TotalTrans="0";
                    }
                    TxtTotalTrans.setText(TotalTrans);
                }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<DashBoardDetails> call, Throwable t) {
                Log.i("SHANIL ", "Call Failed" + call + "  ");
                t.printStackTrace();
                //System.out.println("SHANIL 3 : ");
            }

        });

    }

    /**
     * This override function is works if any change in internet connection
     * @param event
     */
    @Override
    public void onConnectionChange(ConnectivityEvent event) {
        if (event.getState().getValue() == ConnectivityState.CONNECTED) {
            // device has active internet connection
            NoInternet.dismiss();
            if (InternetFlag == 1) {
                Intent intent = new Intent(Dashboard.this, Dashboard.class);
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
