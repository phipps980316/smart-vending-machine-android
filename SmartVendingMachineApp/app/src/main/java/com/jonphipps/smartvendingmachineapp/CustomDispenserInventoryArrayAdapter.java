package com.jonphipps.smartvendingmachineapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CustomDispenserInventoryArrayAdapter extends ArrayAdapter<InventoryModel> { //Class to change a ArrayList into an ArrayAdapter for a list view

    private class ViewHolder { //Private class for the structure of a view holder
        protected TextView lblName;
        protected TextView lblCost;
        protected CheckBox chkSelected;
    }

    public CustomDispenserInventoryArrayAdapter(Context context, int resource, ArrayList<InventoryModel> inventoryModelArrayList) { //Constructor for the class that calls the superclass constructor
        super(context, resource, inventoryModelArrayList);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) { //function to generate a row for a list view for an element in the array list
                                                                                    //function generates a new view if one does not exist or the function fetches the existing view from the cache
        InventoryModel inventoryModel = getItem(position);
        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.inventory_item, parent, false);

            viewHolder.lblName = (TextView) convertView.findViewById(R.id.lblInventoryName);
            viewHolder.lblCost = (TextView) convertView.findViewById(R.id.lblInventoryCost);
            viewHolder.chkSelected = (CheckBox) convertView.findViewById(R.id.chkInventorySelected);
            viewHolder.chkSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    InventoryModel item = getItem(position);
                    item.setSelected(isChecked);
                }
            });
            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();

        DecimalFormat currency = new DecimalFormat("0.00");
        viewHolder.lblName.setText("Name: " + inventoryModel.getName());
        viewHolder.lblCost.setText("Cost: £" + currency.format(inventoryModel.getCost()));
        viewHolder.chkSelected.setChecked(inventoryModel.getSelected());
        return convertView;
    }
}
