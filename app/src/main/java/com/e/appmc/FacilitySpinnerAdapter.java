package com.e.appmc;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.e.bd.appmc.Facility;

public class FacilitySpinnerAdapter extends ArrayAdapter<Facility> {

    private Context context;
    private Facility[] values;

    public FacilitySpinnerAdapter(Context context, int textViewResourceId, Facility[] values)
    {
        super(context,textViewResourceId,values);
        this.context = context;
        this.values = values;
    }


    @Override
    public int getCount(){
        return values.length;
    }

    @Override
    public Facility getItem(int position){
        return values[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView label = (TextView) super.getView(position, convertView, parent);

        label.setText(values[position].getName());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setText(values[position].getName());

        return label;
    }
}
