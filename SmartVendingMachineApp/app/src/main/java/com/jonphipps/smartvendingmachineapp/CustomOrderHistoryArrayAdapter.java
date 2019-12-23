package com.jonphipps.smartvendingmachineapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CustomOrderHistoryArrayAdapter extends ArrayAdapter<OrderRecordModel> { //Class to change a ArrayList into an ArrayAdapter for a list view

    private class ViewHolder { //Private class for the structure of a view holder
        protected TextView txtName;
        protected TextView txtCost;
        protected TextView txtDate;

    }

    public CustomOrderHistoryArrayAdapter(Context context, int resource, ArrayList<OrderRecordModel> orderRecordModelArrayList) { //Constructor for the class that calls the superclass constructor
        super(context, resource, orderRecordModelArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //function to generate a row for a list view for an element in the array list
                                                                            //function generates a new view if one does not exist or the function fetches the existing view from the cache
        OrderRecordModel orderRecordModel = getItem(position);
        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.order_history_item, parent, false);

            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtOrderName);
            viewHolder.txtCost = (TextView) convertView.findViewById(R.id.txtOrderCost);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txtOrderDate);
            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();

        DecimalFormat currency = new DecimalFormat("0.00");
        viewHolder.txtName.setText("Name: " + orderRecordModel.getProductName());
        viewHolder.txtCost.setText("Cost: £" + currency.format(orderRecordModel.getProductCost()));
        viewHolder.txtDate.setText("Date: " + orderRecordModel.getOrderDate());
        return convertView;
    }
}
