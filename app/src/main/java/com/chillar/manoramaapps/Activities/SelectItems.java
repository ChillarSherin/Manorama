package com.chillar.manoramaapps.Activities;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chillar.manoramaapps.Adapter.PreOrderDataModel;
import com.chillar.manoramaapps.Adapter.PreOrderDefaultAdapter;
import com.chillar.manoramaapps.Adapter.PreOrderListAdapter;
import com.chillar.manoramaapps.R;
import com.chillar.manoramaapps.Retrofit.PreorderItems.Default;
import com.chillar.manoramaapps.Retrofit.PreorderItems.PreOrderDetails;
import com.chillar.manoramaapps.Retrofit.PreorderItems.PreOrderDetailsCode;
import com.chillar.manoramaapps.Retrofit.PreorderItems.PreOrderDetails_Status;
import com.chillar.manoramaapps.Retrofit.placeOrder.PlaceCode;
import com.chillar.manoramaapps.Retrofit.placeOrder.PlaceOrderDetails;
import com.chillar.manoramaapps.Retrofit.placeOrder.PlaceStatus;
import com.chillar.manoramaapps.WrapContentLinearLayoutManager;
import com.chillar.manoramaapps.app.ApiClient;
import com.chillar.manoramaapps.app.ApiInterface;
import com.chillar.manoramaapps.app.Constants;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Displaying all items selected by employee and Displaying default items
 */
public class SelectItems extends AppCompatActivity implements ConnectivityChangeListener {

    private  PreOrderListAdapter adapter;
    private  PreOrderDefaultAdapter adapter2;
    Toolbar toolbar;
    Call<PreOrderDetails> call = null;
    String UserPhone, UserID,  IPADDRESS,  Date;
    int preorderSessionTimingID, hardwareModuleID;
    ProgressBar progbar;
    Button PlaceOrder;
    Dialog NoInternet;
    int InternetFlag = 0;
    Call<PlaceOrderDetails> call1 = null;
    ArrayList<PreOrderDataModel> dataModels;
    ListView Preorderlistview;
    RelativeLayout recyclerlay;
    RecyclerView listview;
    Button Reload;
    TextView Txt_Content;
    ImageView ErrorImage;
    Activity activity;
    private List<String> itemID = new ArrayList<String>();
    private List<String> itemName = new ArrayList<String>();
    private List<String> Defaultname = new ArrayList<String>();
    private List<String> Def_Item_Price = new ArrayList<String>();
    private List<String> itemPrice = new ArrayList<String>();
    private List<String> availability = new ArrayList<String>();
    private List<Boolean> isItemChecked = new ArrayList<>();
    private List<String> itemQty = new ArrayList<String>();
    private Boolean exit = false;
    LinearLayout defaultitems_lay;
    LinearLayout headLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_items);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Select Items");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        PopupInternet();
        activity = this;
        PlaceOrder = findViewById(R.id.placeorder);
        progbar = findViewById(R.id.prog);
        defaultitems_lay = findViewById(R.id.defaultitems_lay);
        headLayout = findViewById(R.id.headLayout);
        Preorderlistview = findViewById(R.id.list1);
        dataModels = new ArrayList<>();

        findViewById(R.id.ErrorLayoutnew).setVisibility(View.GONE);
        Reload = (Button) findViewById(R.id.reloadbutton);
        Txt_Content = (TextView) findViewById(R.id.txt_content);
        ErrorImage = (ImageView) findViewById(R.id.img_no_image);
        recyclerlay = (RelativeLayout) findViewById(R.id.layoutnew);
        listview = (RecyclerView) findViewById(R.id.listview);
        listview.setVisibility(View.GONE);


        PlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String qty = String.valueOf(Constants.SELECTED_ITEMS_QTY);
                String ID = String.valueOf(Constants.SELECTED_ITEMS_ID);
                qty = qty.replace("[", "");
                qty = qty.replace("]", "");
                qty = qty.replace(" ", "");
                ID = ID.replace("[", "");
                ID = ID.replace("]", "");
                ID = ID.replace(" ", "");
                //System.out.println("MANORAMA11 ITEM_QTYsss : " + qty);

                if ((qty.length() > 0) || (ID.length() > 0)) {
                    if (Constants.SELECTED_ITEMS_QTY.contains("0") || Constants.SELECTED_ITEMS_QTY.contains("")) {
                        Toast.makeText(SelectItems.this, "Selected item's quantity can't be empty/zero", Toast.LENGTH_SHORT).show();
                    } else {
                        progbar.setVisibility(View.VISIBLE);
                        PlaceOrder.setEnabled(false);
                        Place_Order(ID, qty,IPADDRESS);
                    }
                } else {
                    Toast.makeText(SelectItems.this, "Select any item", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    /**
     * Api call function for getting all ordered items and default items
     * @param preorderSessionTimingID
     * @param hardwareModuleID
     * @param Date
     * @param IPADDRESS
     */
    private void Get_Preorder_Items(final int preorderSessionTimingID, final int hardwareModuleID, final String Date, String IPADDRESS) {

        ApiInterface apiService =
                ApiClient.getClient(getApplicationContext(),IPADDRESS).create(ApiInterface.class);
        //System.out.println("MANORAMA1 : " + Date);
        call = apiService.getpreorder("r_preorder_items", UserPhone, UserID, preorderSessionTimingID, hardwareModuleID, Date);
        call.enqueue(new Callback<PreOrderDetails>() {
            @Override
            public void onResponse(Call<PreOrderDetails> call, retrofit2.Response<PreOrderDetails> response) {

                String URL = call.request().url().toString();
                //System.out.println("Retrofit URL111 : " + URL);
                //System.out.println("Retrofit URL1111 : " + URL);

                progbar.setVisibility(View.GONE);
                PreOrderDetails_Status status = response.body().getStatus();
                String code = status.getCode();
                String Message = status.getMessage();

                if (code.equals("200")) {

                    itemID.clear();
                    Defaultname.clear();
                    Def_Item_Price.clear();
                    itemName.clear();
                    itemPrice.clear();
                    availability.clear();
                    isItemChecked.clear();
                    itemQty.clear();
                    dataModels.clear();

                    List<PreOrderDetailsCode> transactionCode = response.body().getCode();
                    List<Default> defaultdetails = response.body().getDefault();
                    //System.out.println("MANORAMA 2  : " + defaultdetails.size());

                    if (defaultdetails.size()>0){

                        for (int j=0;j<defaultdetails.size();j++){

                            Default def=defaultdetails.get(j);
                            Defaultname.add(def.getItemName());
                            Def_Item_Price.add(def.getDefault_itemPrice());

                        }
                        listview.setVisibility(View.VISIBLE);
                        listview.setLayoutManager(new WrapContentLinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        listview.setItemAnimator(new DefaultItemAnimator());
                        listview.setNestedScrollingEnabled(false);
                        adapter2 = new PreOrderDefaultAdapter(Defaultname, Def_Item_Price, activity, SelectItems.this);
                        listview.setAdapter(adapter2);
                    }else {

                        Toast.makeText(SelectItems.this, "No Default Items Found Today...", Toast.LENGTH_SHORT).show();

                    }

                    if (transactionCode.size() > 0) {

                        for (int i = 0; i < transactionCode.size(); i++) {

                            PreOrderDetailsCode codeonline = transactionCode.get(i);
                            itemID.add(codeonline.getItemID());
                            itemName.add(codeonline.getItemName());
                            itemPrice.add(codeonline.getItemPrice());
                            availability.add((codeonline.getAvailability()));
                            isItemChecked.add(false);
                            itemQty.add("1");
                            //System.out.println("MANORAMA 1  : " + itemID);
                            dataModels.add(new PreOrderDataModel(itemID.get(i), itemName.get(i), itemPrice.get(i), availability.get(i), isItemChecked.get(i), "1", 0));
                        }
                        adapter = new PreOrderListAdapter(dataModels, SelectItems.this);
                        Preorderlistview.setAdapter(adapter);

                    } else {
                        headLayout.setVisibility(View.GONE);
//                        recyclerlay.setVisibility(View.GONE);

//                        progbar.setVisibility(View.GONE);
                        /*
                         Preorderlistview.setVisibility(View.GONE);
                         Txt_Content.setText("No Details Found");
                        Reload.setText("GO BACK");
                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                        Reload.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                onBackPressed();

                            }
                        });*/
                    }


                } else if (code.equals("400")) {

                    Preorderlistview.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);
                    recyclerlay.setVisibility(View.GONE);
                    Txt_Content.setText("Some error occured please try again");
                    Reload.setText("TRY AGAIN");
                    ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(SelectItems.this, SelectItems.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Bundle b = new Bundle();
                            b.putInt("preorderSessionTimingID", preorderSessionTimingID);
                            b.putInt("hardwareModuleID", hardwareModuleID);
                            b.putString("Date", Date);
                            intent.putExtras(b);
                            startActivity(intent);

                        }
                    });

                } else if (code.equals("204")) {


                    Preorderlistview.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);
                    recyclerlay.setVisibility(View.GONE);
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

                    Preorderlistview.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);
                    recyclerlay.setVisibility(View.GONE);
                    Txt_Content.setText("Some error occured please try again");
                    Reload.setText("TRY AGAIN");
                    ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(SelectItems.this, SelectItems.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Bundle b = new Bundle();
                            b.putInt("preorderSessionTimingID", preorderSessionTimingID);
                            b.putInt("hardwareModuleID", hardwareModuleID);
                            b.putString("Date", Date);
                            intent.putExtras(b);
                            startActivity(intent);

                        }
                    });

                } else if (code.equals("403")) {

                    Preorderlistview.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);
                    recyclerlay.setVisibility(View.GONE);
                    Txt_Content.setText(Message);
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
                    Preorderlistview.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);
                    recyclerlay.setVisibility(View.GONE);
                    Txt_Content.setText("Some error occured please try again");
                    Reload.setText("TRY AGAIN");
                    ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(SelectItems.this, SelectItems.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Bundle b = new Bundle();
                            b.putInt("preorderSessionTimingID", preorderSessionTimingID);
                            b.putInt("hardwareModuleID", hardwareModuleID);
                            b.putString("Date", Date);
                            intent.putExtras(b);
                            startActivity(intent);

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<PreOrderDetails> call, Throwable t) {

                String URL = call.request().url().toString();
                //System.out.println("Retrofit URL222 : " + URL);
                t.printStackTrace();
                //System.out.println("SHANIL 3 : ");

                Preorderlistview.setVisibility(View.GONE);
                progbar.setVisibility(View.GONE);
                recyclerlay.setVisibility(View.GONE);
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
        });

    }
    /*
    * Popup for order successfully completed
    * */
    private void SuccessPopup() {

        final Dialog SerDialogStudent;
        SerDialogStudent = new Dialog(SelectItems.this);
        SerDialogStudent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SerDialogStudent.setContentView(R.layout.activity_success);
        SerDialogStudent.setCancelable(false);
        SerDialogStudent.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button OK = (Button) SerDialogStudent.findViewById(R.id.cancel);
        try {
            SerDialogStudent.show();
        } catch (Exception e) {

        }


        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SerDialogStudent.dismiss();
                Constants.SELECTED_ITEMS_ID.clear();
                Constants.SELECTED_ITEMS_QTY.clear();
                Constants.Check = "1";
                Intent intent = new Intent(SelectItems.this, Dashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });
    }

    /**
     * Api function for place order with employeecode,userid,hardwaremodule id,preorder session id,date ,id and quantity
     * @param ID Ordered item id
     * @param QTY ordered item quantity
     * @param IPADDRESS
     */
    private void Place_Order(String ID, String QTY, String IPADDRESS) {

        ApiInterface apiService =
                ApiClient.getClient(getApplicationContext(),IPADDRESS).create(ApiInterface.class);


        call1 = apiService.getplaceorder("c_place_order", UserPhone, UserID, hardwareModuleID, preorderSessionTimingID, Date, ID, QTY);
        call1.enqueue(new Callback<PlaceOrderDetails>() {
            @Override
            public void onResponse(Call<PlaceOrderDetails> call, retrofit2.Response<PlaceOrderDetails> response) {
                progbar.setVisibility(View.GONE);
                String URL = call.request().url().toString();
                System.out.println("Retrofit URL : " + URL);

                PlaceStatus otpstatus = response.body().getStatus();
                String code = otpstatus.getCode();
                String Message = otpstatus.getMessage();
                if (code.equals("200")) {
                    PlaceCode otpcode = response.body().getCode();
                    String count = otpcode.getCount();

                    SuccessPopup();

                } else if (code.equals("400")) {

                    Preorderlistview.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);

                    Txt_Content.setText("Some error occured please try again");
                    Reload.setText("TRY AGAIN");
                    ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(SelectItems.this, SelectItems.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Bundle b = new Bundle();
                            b.putInt("preorderSessionTimingID", preorderSessionTimingID);
                            b.putInt("hardwareModuleID", hardwareModuleID);
                            b.putString("Date", Date);
                            intent.putExtras(b);
                            startActivity(intent);

                        }
                    });

                } else if (code.equals("401")) {

                    Preorderlistview.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);

                    Txt_Content.setText("Some error occured please try again");
                    Reload.setText("TRY AGAIN");
                    ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(SelectItems.this, SelectItems.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Bundle b = new Bundle();
                            b.putInt("preorderSessionTimingID", preorderSessionTimingID);
                            b.putInt("hardwareModuleID", hardwareModuleID);
                            b.putString("Date", Date);
                            intent.putExtras(b);
                            startActivity(intent);

                        }
                    });

                } else if (code.equals("403")) {

                    Preorderlistview.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);

                    Txt_Content.setText(Message);
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
                    Preorderlistview.setVisibility(View.GONE);
                    progbar.setVisibility(View.GONE);

                    Txt_Content.setText("Some error occured please try again");
                    Reload.setText("TRY AGAIN");
                    ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(SelectItems.this, SelectItems.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Bundle b = new Bundle();
                            b.putInt("preorderSessionTimingID", preorderSessionTimingID);
                            b.putInt("hardwareModuleID", hardwareModuleID);
                            b.putString("Date", Date);
                            intent.putExtras(b);
                            startActivity(intent);

                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<PlaceOrderDetails> call, Throwable t) {
                Log.i("SHANIL ", "Call Failed" + call + "  ");
                String URL = call.request().url().toString();
                System.out.println("Retrofit URL : " + URL);
                t.printStackTrace();
                progbar.setVisibility(View.GONE);
            }

        });

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

    @Override
    protected void onResume() {
        super.onResume();

        progbar.setVisibility(View.VISIBLE);
        SharedPreferences prefs = getSharedPreferences("Manorama", MODE_PRIVATE);
        UserPhone = prefs.getString("EmployeeCode", "");
        UserID = prefs.getString("UserID", "");
        IPADDRESS = prefs.getString("IPAddress", "");

        Bundle b = getIntent().getExtras();
        preorderSessionTimingID = b.getInt("preorderSessionTimingID");
        hardwareModuleID = b.getInt("hardwareModuleID");
        Date = b.getString("Date");
        //System.out.println("MANORAMA2 : " + Date + "preorderSessionTimingID   :   " + preorderSessionTimingID);
        Get_Preorder_Items(preorderSessionTimingID, hardwareModuleID, Date,IPADDRESS);
    }

    /**
     * Popup for connection lost
     */
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
                Intent intent = new Intent(SelectItems.this, SelectItems.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle b = new Bundle();
                b.putInt("preorderSessionTimingID", preorderSessionTimingID);
                b.putInt("hardwareModuleID", hardwareModuleID);
                b.putString("Date", Date);
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

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Constants.SELECTED_ITEMS_ID.clear();
        Constants.SELECTED_ITEMS_QTY.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constants.SELECTED_ITEMS_ID.clear();
        Constants.SELECTED_ITEMS_QTY.clear();
    }
}
