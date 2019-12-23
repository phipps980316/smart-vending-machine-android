package com.jonphipps.smartvendingmachineapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomControlPanelUserArrayAdapter extends ArrayAdapter<UserModel> { //Class to change a ArrayList into an ArrayAdapter for a list view

    private class ViewHolder { //Private class for the structure of a view holder
        protected TextView lblName;
    }

    public CustomControlPanelUserArrayAdapter(Context context, int resource, ArrayList<UserModel> userModelArrayList) { //Constructor for the class that calls the superclass constructor
        super(context, resource, userModelArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //function to generate a row for a list view for an element in the array list
                                                                            //function generates a new view if one does not exist or the function fetches the existing view from the cache
        UserModel userModel = getItem(position);
        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.control_panel_list_item, parent, false);

            viewHolder.lblName = (TextView) convertView.findViewById(R.id.lblListItemName);
            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.lblName.setText(userModel.getName());
        return convertView;
    }
}
