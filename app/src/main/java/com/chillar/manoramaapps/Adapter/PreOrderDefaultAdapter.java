package com.chillar.manoramaapps.Adapter;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chillar.manoramaapps.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Adapter for displaying default items
 */
public class PreOrderDefaultAdapter extends RecyclerView.Adapter<PreOrderDefaultAdapter.ViewHolder> {


    private List<String> Def_Name = new ArrayList<>();
    private List<String> Def_Item_Price = new ArrayList<>();
    private int rowLayout;
    private Context mContext;
    private Activity activity;

    public PreOrderDefaultAdapter(List<String> Def_Name,List<String> Def_Item_Price, Activity activity, Context context) {
        this.Def_Name = Def_Name;
        this.Def_Item_Price = Def_Item_Price;
        this.activity = activity;
        this.rowLayout = R.layout.default_item;
        this.mContext = context;
    }

    @Override
    public PreOrderDefaultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PreOrderDefaultAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PreOrderDefaultAdapter.ViewHolder holder, final int position) {

        //System.out.println("MANORAMA  Adapter  1:  "+Def_Name.get(position));
        //System.out.println("MANORAMA  Adapter  2:  "+Def_Item_Price.get(position));

        holder.DefaultName.setText(""+Def_Name.get(position));
        holder.Def_Item_Price_Txt.setText("Rs/- "+Def_Item_Price.get(position));


    }

    @Override
    public int getItemCount() {

        return Def_Name.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView DefaultName;
        TextView Def_Item_Price_Txt;

        public ViewHolder(View itemView) {
            super(itemView);

            DefaultName=itemView.findViewById(R.id.def_name);
            Def_Item_Price_Txt=itemView.findViewById(R.id.def_price);

        }
    }

}
