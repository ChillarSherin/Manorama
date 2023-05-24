package com.chillar.manoramaapps.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chillar.manoramaapps.R;
import com.chillar.manoramaapps.app.Constants;

import java.util.ArrayList;

/**
 * Adapter class for Preorder_list
 */
public class PreOrderListAdapter extends ArrayAdapter<PreOrderDataModel> implements View.OnClickListener {

    Context mContext;
    private ArrayList<PreOrderDataModel> dataSet;

    public PreOrderListAdapter(ArrayList<PreOrderDataModel> data, Context context) {
        super(context, R.layout.layout_select_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        PreOrderDataModel dataModel = (PreOrderDataModel) object;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final PreOrderDataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_select_item, parent, false);
            viewHolder.txtItem = convertView.findViewById(R.id.txtItem);
            viewHolder.txtAvailability = convertView.findViewById(R.id.txtAvailability);
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.Qntity = convertView.findViewById(R.id.qty);
            viewHolder.spinnerQty = convertView.findViewById(R.id.spinnerQty);
            viewHolder.checked = convertView.findViewById(R.id.checked);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        viewHolder.txtItem.setText(dataModel.getItemName());
        viewHolder.txtAvailability.setText(dataModel.getAvailability());
        viewHolder.price.setText(dataModel.getItemPrice());
        viewHolder.checked.setChecked(dataModel.getIsItemChecked());
        if (dataModel.getAvailability().equals("Unlimited")) {
            viewHolder.spinnerQty.setVisibility(View.GONE);
            viewHolder.Qntity.setVisibility(View.VISIBLE);
            viewHolder.Qntity.setText(dataModel.getItemQty());
            viewHolder.Qntity.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    dataModel.setItemQty(viewHolder.Qntity.getText().toString().trim());
                    if ((viewHolder.Qntity.getText().toString().trim().equals("0")) || (viewHolder.Qntity.getText().toString().trim().equals(""))) {
                        Toast.makeText(mContext, "Enter quantity greater than zero", Toast.LENGTH_SHORT).show();
                    }
                    if (viewHolder.checked.isChecked()) {
                        //System.out.println("MANORAMA11 qty : " + Constants.SELECTED_ITEMS_QTY);
                        //System.out.println("MANORAMA11 TESTTTT : " + dataModel.getItemID());
                        //System.out.println("MANORAMA11 TESTTTT qty : " + Constants.SELECTED_ITEMS_QTY);
                        int index = Constants.SELECTED_ITEMS_ID.indexOf(dataModel.getItemID());
                        Constants.SELECTED_ITEMS_QTY.set(index, viewHolder.Qntity.getText().toString().trim());
                        //System.out.println("MANORAMA11 qty : " + Constants.SELECTED_ITEMS_QTY);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else {
            viewHolder.spinnerQty.setVisibility(View.VISIBLE);
            viewHolder.Qntity.setVisibility(View.GONE);
            final ArrayList<String> arrayQty = new ArrayList<>();
            int maxAvail = Integer.parseInt(dataModel.getAvailability());
            for (int i = 1; i <= maxAvail; i++) {
                arrayQty.add(i + "");
            }
            ArrayAdapter<String> spinnerArrayAdapterQty = new ArrayAdapter<>(mContext, R.layout.layout_spinner_qty, R.id.spinnertxt, arrayQty);
            spinnerArrayAdapterQty.setDropDownViewResource(R.layout.spinner_text);
            viewHolder.spinnerQty.setAdapter(spinnerArrayAdapterQty);
            viewHolder.spinnerQty.setSelection(dataModel.getItemPos());
            viewHolder.spinnerQty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    dataModel.setItemQty(arrayQty.get(position));
                    dataModel.setItemPos(position);
                    if (viewHolder.checked.isChecked()) {
                        //System.out.println("MANORAMA11 qty : " + Constants.SELECTED_ITEMS_QTY);
                        //System.out.println("MANORAMA11 TESTTTT : " + dataModel.getItemID());
                        //System.out.println("MANORAMA11 TESTTTT qty : " + Constants.SELECTED_ITEMS_QTY);
                        int index = Constants.SELECTED_ITEMS_ID.indexOf(dataModel.getItemID());
                        Constants.SELECTED_ITEMS_QTY.set(index, dataModel.getItemQty());
                        //System.out.println("MANORAMA11 qty : " + Constants.SELECTED_ITEMS_QTY);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        viewHolder.checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //System.out.println("MANORAMA11 isChecked: " + isChecked);
                dataModel.setItemChecked(isChecked);
                if (isChecked) {
                    if (dataModel.getAvailability().equals("Unlimited")) {
                        dataModel.setItemQty(viewHolder.Qntity.getText().toString().trim());
                        if ((viewHolder.Qntity.getText().toString().trim().equals("0")) || (viewHolder.Qntity.getText().toString().trim().equals(""))) {
                            Toast.makeText(mContext, "Enter quantity greater than zero", Toast.LENGTH_SHORT).show();
                        }
                        if (!Constants.SELECTED_ITEMS_ID.contains(dataModel.getItemID())) {
                            Constants.SELECTED_ITEMS_ID.add(dataModel.getItemID());
                            Constants.SELECTED_ITEMS_QTY.add(dataModel.getItemQty());
                        }
                    } else {
                        if (!Constants.SELECTED_ITEMS_ID.contains(dataModel.getItemID())) {
                            Constants.SELECTED_ITEMS_ID.add(dataModel.getItemID());
                            Constants.SELECTED_ITEMS_QTY.add(dataModel.getItemQty());
                        }
                    }
                } else {
                    if (Constants.SELECTED_ITEMS_ID.contains(dataModel.getItemID())) {
                        int index = Constants.SELECTED_ITEMS_ID.indexOf(dataModel.getItemID());
                        Constants.SELECTED_ITEMS_ID.remove(dataModel.getItemID());
                        Constants.SELECTED_ITEMS_QTY.remove(index);
                    }
                }
                //System.out.println("MANORAMA11 idd : " + Constants.SELECTED_ITEMS_ID);
                //System.out.println("MANORAMA11 qty : " + Constants.SELECTED_ITEMS_QTY);

            }
        });
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtItem, txtAvailability, price;
        CheckBox checked;
        EditText Qntity;
        Spinner spinnerQty;
    }
}

/*package com.chillar.manoramaapps.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chillar.manoramaapps.R;
import com.chillar.manoramaapps.app.Constants;

import java.util.ArrayList;

public class PreOrderListAdapter extends ArrayAdapter<PreOrderDataModel> implements View.OnClickListener{

    private ArrayList<PreOrderDataModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtItem, txtAvailability, price;
        CheckBox checked;
        EditText Qntity;
    }

    public PreOrderListAdapter(ArrayList<PreOrderDataModel> data, Context context) {
        super(context, R.layout.layout_select_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object = getItem(position);
        PreOrderDataModel dataModel=(PreOrderDataModel)object;

        *//*icon_switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }*//*
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final PreOrderDataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag


        CharSequence Sequence;


        final View result;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_select_item, parent, false);
            viewHolder.txtItem = convertView.findViewById(R.id.txtItem);
            viewHolder.txtAvailability = convertView.findViewById(R.id.txtAvailability);
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.Qntity = convertView.findViewById(R.id.qty);
            viewHolder.checked = convertView.findViewById(R.id.checked);
            result=convertView;
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.txtItem.setText(dataModel.getItemName());
        viewHolder.txtAvailability.setText(dataModel.getAvailability());
        viewHolder.price.setText(dataModel.getItemPrice());
        viewHolder.Qntity.setText(dataModel.getItemQty());
        viewHolder.checked.setChecked(dataModel.getIsItemChecked());
        viewHolder.Qntity.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                if (dataModel.getAvailability().equals("Unlimited"))
                {
                    dataModel.setItemQty(viewHolder.Qntity.getText().toString().trim());
                    if (viewHolder.checked.isChecked())
                    {
                        //System.out.println("MANORAMA11 qty : "+Constants.SELECTED_ITEMS_QTY);
                        //System.out.println("MANORAMA11 TESTTTT : "+dataModel.getItemID());
                        //System.out.println("MANORAMA11 TESTTTT qty : "+Constants.SELECTED_ITEMS_QTY);
                        int index = Constants.SELECTED_ITEMS_ID.indexOf(dataModel.getItemID());
                        Constants.SELECTED_ITEMS_QTY.set(index,viewHolder.Qntity.getText().toString().trim());
                        //System.out.println("MANORAMA11 qty : "+Constants.SELECTED_ITEMS_QTY);
                    }
                }
                else
                {
                    //System.out.println("MANORAMA6 : "+s);
                    int numbers=0;
                    try
                    {
                        final int counts= Integer.parseInt(dataModel.getAvailability());
                        numbers = Integer.parseInt(String.valueOf(s));
                        //System.out.println("MANORAMA7 : "+numbers);
                        if (numbers>counts)
                        {
                            Constants.Check="1";
                            Toast.makeText(mContext, "Enter quantity less than availability", Toast.LENGTH_SHORT).show();
                            viewHolder.checked.setChecked(false);

                        }
                        else if (numbers==0||(viewHolder.Qntity.getText().toString().equals(""))||s.toString().equals(""))
                        {
                            Constants.Check="0";
                            Toast.makeText(mContext, "Enter quantity greater than zero", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Constants.Check="2";
                            dataModel.setItemQty(viewHolder.Qntity.getText().toString().trim());
                            if (viewHolder.checked.isChecked())
                            {
                                int index = Constants.SELECTED_ITEMS_ID.indexOf(dataModel.getItemID());
                                Constants.SELECTED_ITEMS_QTY.set(index,String.valueOf(numbers));
                                //System.out.println("MANORAMA11 qty : "+Constants.SELECTED_ITEMS_QTY);
                            }
                        }
                    }
                    catch (Exception c)
                    {
                        if (numbers==0||(viewHolder.Qntity.getText().toString().equals(""))||s.toString().equals(""))
                        {
                            Toast.makeText(mContext, "Enter quantity greater than zero", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        viewHolder.checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //System.out.println("MANORAMA11 isChecked: "+isChecked);
                //System.out.println("MANORAMA11 isChecked2 "+Constants.Check);
                dataModel.setItemChecked(isChecked);
                if (isChecked)
                {
                   if (Constants.Check.equals("1")){

                       viewHolder.checked.setChecked(true);

                   }else if (Constants.Check.equals("0")){
                       viewHolder.checked.setChecked(false);
                   }else if (!Constants.Check.equals(""))
                   {
                       viewHolder.checked.setChecked(true);
                   }
                       else {
                       if (!Constants.SELECTED_ITEMS_ID.contains(dataModel.getItemID())) {
                           Constants.SELECTED_ITEMS_ID.add(dataModel.getItemID());
                           Constants.SELECTED_ITEMS_QTY.add(viewHolder.Qntity.getText().toString().trim());
                           dataModel.setItemQty(viewHolder.Qntity.getText().toString().trim());
                       }
                   }
                }
                else
                {

                    if (Constants.SELECTED_ITEMS_ID.contains(dataModel.getItemID()))
                    {
                        int index = Constants.SELECTED_ITEMS_ID.indexOf(dataModel.getItemID());
                        Constants.SELECTED_ITEMS_ID.remove(dataModel.getItemID());
                        Constants.SELECTED_ITEMS_QTY.remove(index);
                    }
                }

                //System.out.println("MANORAMA11 idd : "+Constants.SELECTED_ITEMS_ID);
                //System.out.println("MANORAMA11 qty : "+Constants.SELECTED_ITEMS_QTY);

            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }
}*/
