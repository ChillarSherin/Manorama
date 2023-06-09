package com.chillar.manoramaapps.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.chillar.manoramaapps.R;
import com.chillar.manoramaapps.Retrofit.Login.LoginCode;
import com.chillar.manoramaapps.Retrofit.Login.LoginDetails;
import com.chillar.manoramaapps.Retrofit.Login.LoginStatus;
import com.chillar.manoramaapps.app.ApiClient;
import com.chillar.manoramaapps.app.ApiInterface;
import com.chillar.manoramaapps.app.ManoramaApp;
import com.google.gson.Gson;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * User login page
 *
 */
public class LogIn extends AppCompatActivity implements ConnectivityChangeListener {

    Button btnLogin;
    EditText etUser, etPassword;
//    TextView EditApi;
    String USER = "", PASS = "";
    String ipAddress = "";
    ProgressBar ProBar;
    Call<LoginDetails> call1 = null;

    String UserName;
    String UserTypeKey;
    String EmployeeID;
    String EmployeeCode;
    String BranchOfficeID;
    String BranchName;
    String DesignationName;
    String DepartmentName;
    String UserID;
    Dialog NoInternet;
    int InternetFlag = 0;
    Dialog DialogSwitch;
    EditText IpEdt;
    LinearLayout iptxt;
    Spinner placeSpinner;
    int swich=0;
    List < Places > categories = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences sharedPreferences = getSharedPreferences("Manorama", Context.MODE_PRIVATE);
        try {
            if (sharedPreferences.getString("Login", "").equalsIgnoreCase("True")) {
                Intent intent = new Intent(LogIn.this, Dashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            //System.out.println("CHECK---> [Login onCreate] Exception : " + e.getMessage());
        }
        setContentView(R.layout.activity_log_in);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        initializeElements();
        PopupInternet();
        btnLogin.setOnClickListener(view -> {
            USER = etUser.getText().toString().trim();
            PASS = etPassword.getText().toString().trim();
            if (!USER.equals("") && !PASS.equals("")) {
                Login();
            } else {
                Toast.makeText(LogIn.this, "Please enter username & password", Toast.LENGTH_SHORT).show();
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
/*
* function for initialize all elements
* */
    private void initializeElements() {
        btnLogin = findViewById(R.id.btLogin);
        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        IpEdt = findViewById(R.id.etip);
        ProBar = findViewById(R.id.progress);
        iptxt = findViewById(R.id.linear);
        placeSpinner = findViewById(R.id.placeSpinner);
        placeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ipAddress = categories.get(i).getIpAddress();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ipAddress ="";
            }
        });


        categories.add(new Places("KOTTAYAM","http://10.1.2.220/app_api/api_1_0/"));
        categories.add(new Places("KODIMATHA","http://10.1.6.20/app_api/api_1_0/"));
        categories.add(new Places("PUBLICATIONS","http://10.1.8.22/app_api/api_1_0/"));

        categories.add(new Places("TEST","http://dev.chillarcards.com/manorama/app_api/api_1_0/"));


        ArrayAdapter<Places> dataAdapter = new ArrayAdapter<Places>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(dataAdapter);


//        if (IpEdt.getText().toString().length()>0){
//
//            SharedPreferences prefs = getSharedPreferences("Manorama", MODE_PRIVATE);
//            String IPADDRESS = prefs.getString("IPAddress", "");
//            IpEdt.setText("" + IPADDRESS);
//        }else {
//
//            IpEdt.setText("" + ApiClient.BASE_URL1);
//
//        }

    }

    private void Login() {
        ProBar.setVisibility(View.VISIBLE);
        final String tag_json_object = "r_user_login";
        String URL;
        URL = ApiClient.BASE_URL1 + "r_user_login?"+"employeeCode="+USER+"&userPassword="+PASS;
        System.out.println("CHECK---> URL " + URL);
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                System.out.println("CHECK---> Response " + jsonObject);

                ManoramaApp.getInstance().cancelPendingRequests(tag_json_object);
                ProBar.setVisibility(View.GONE);
                Gson gson = new Gson();
                LoginDetails noticeboard = gson.fromJson(jsonObject, LoginDetails.class);
                LoginStatus loginStatus = noticeboard.getStatus();
                String status = loginStatus.getCode();
                String Messages = loginStatus.getMessage();

                switch (status) {
                    case "200":

                        LoginCode otpcode = noticeboard.getCode();

                        UserName = otpcode.getUserName();
                        UserTypeKey = otpcode.getUserTypeKey();
                        EmployeeID = otpcode.getEmployeeID();
                        EmployeeCode = otpcode.getEmployeeCode();
                        BranchOfficeID = otpcode.getBranchOfficeID();
                        BranchName = otpcode.getBranchName();
                        DesignationName = otpcode.getDesignationName();
                        DepartmentName = otpcode.getDepartmentName();
                        UserID = otpcode.getUserID();

                        SharedPreferences.Editor editor = getSharedPreferences("Manorama", MODE_PRIVATE).edit();
                        editor.putString("UserName", UserName);
                        editor.putString("UserTypeKey", UserTypeKey);
                        editor.putString("EmployeeID", EmployeeID);
                        editor.putString("EmployeeCode", EmployeeCode);
                        editor.putString("BranchOfficeID", BranchOfficeID);
                        editor.putString("BranchName", BranchName);
                        editor.putString("DesignationName", DesignationName);
                        editor.putString("DepartmentName", DepartmentName);
                        editor.putString("UserID", UserID);
                        editor.putString("UserPhone", USER);
                        editor.putString("IPAddress", ipAddress);
                        editor.putString("Login", "True");
                        editor.commit();

                        Intent intent = new Intent(LogIn.this, Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;
                    case "401":
                        Toast.makeText(LogIn.this, "Wrong username/password", Toast.LENGTH_SHORT).show();
                        break;
                    case "403":
                        Toast.makeText(LogIn.this, " " + Messages, Toast.LENGTH_SHORT).show();
                        break;
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ProBar.setVisibility(View.GONE);
                ManoramaApp.getInstance().cancelPendingRequests(tag_json_object);
                VolleyLog.d("Object Error : ", volleyError.getMessage());

                Toast.makeText(LogIn.this, volleyError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(CartListActivity.this));
        ManoramaApp.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

    }

    /**
     * Api call for user login with employeecode and password
     */
//    private void Login() {
//
//
//        Retrofit retrofit= ApiClient.getClient(getApplicationContext(),ipAddress);
//        if (retrofit==null){
//
//            Toast.makeText(this, "You entered a wrong IP address", Toast.LENGTH_SHORT).show();
//
//        }else {
//
//            ProBar.setVisibility(View.VISIBLE);
//            ApiInterface apiService =
//                    retrofit.create(ApiInterface.class);
//
//            //System.out.println("SHANIL 1 : ");
//            call1 = apiService.getlogin("r_user_login", USER, PASS);
//            call1.enqueue(new Callback<LoginDetails>() {
//                @Override
//                public void onResponse(Call<LoginDetails> call, retrofit2.Response<LoginDetails> response) {
//
//                    String URL = call.request().url().toString();
//                    //System.out.println("Retrofit URL : " + URL);
//                    //System.out.println("SHANIL 2 : ");
//                    ProBar.setVisibility(View.GONE);
//                    LoginStatus otpstatus = response.body().getStatus();
//                    String status = otpstatus.getCode();
//                    String Messages = otpstatus.getMessage();
//                    if (status.equals("200")) {
//
//                        LoginCode otpcode = response.body().getCode();
//
//                        UserName = otpcode.getUserName();
//                        UserTypeKey = otpcode.getUserTypeKey();
//                        EmployeeID = otpcode.getEmployeeID();
//                        EmployeeCode = otpcode.getEmployeeCode();
//                        BranchOfficeID = otpcode.getBranchOfficeID();
//                        BranchName = otpcode.getBranchName();
//                        DesignationName = otpcode.getDesignationName();
//                        DepartmentName = otpcode.getDepartmentName();
//                        UserID = otpcode.getUserID();
//
//                        SharedPreferences.Editor editor = getSharedPreferences("Manorama", MODE_PRIVATE).edit();
//                        editor.putString("UserName", UserName);
//                        editor.putString("UserTypeKey", UserTypeKey);
//                        editor.putString("EmployeeID", EmployeeID);
//                        editor.putString("EmployeeCode", EmployeeCode);
//                        editor.putString("BranchOfficeID", BranchOfficeID);
//                        editor.putString("BranchName", BranchName);
//                        editor.putString("DesignationName", DesignationName);
//                        editor.putString("DepartmentName", DepartmentName);
//                        editor.putString("UserID", UserID);
//                        editor.putString("UserPhone", USER);
//                        editor.putString("IPAddress", ipAddress);
//                        editor.putString("Login", "True");
//                        editor.commit();
//
//                        Intent intent = new Intent(LogIn.this, Dashboard.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();
//                    } else if (status.equals("401")) {
//                        Toast.makeText(LogIn.this, "Wrong username/password", Toast.LENGTH_SHORT).show();
//                    } else if (status.equals("403")) {
//                        Toast.makeText(LogIn.this, " " + Messages, Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<LoginDetails> call, Throwable t) {
//                    Log.i("SHANIL ", "Call Failed" + call.request().url() + "  ");
//                    t.printStackTrace();
//                    Toast.makeText(LogIn.this, t.getMessage()+"\n"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                    //System.out.println("SHANIL 3 : ");
//                    ProBar.setVisibility(View.GONE);
//                }
//
//            });
//        }
//    }
/*
* Popup function for connection lost
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
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
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
