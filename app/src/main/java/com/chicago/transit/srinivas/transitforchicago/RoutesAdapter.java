package com.chicago.transit.srinivas.transitforchicago;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chicago.transit.srinivas.transitforchicago.models.RoutesModel;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by srinivas on 5/25/17.
 */

public class RoutesAdapter extends ArrayAdapter<RoutesModel> {

    private List<RoutesModel> routesList;
    private int resource;
    private Context context;
    private LayoutInflater inflater;

    public RoutesAdapter(Context context, int resource, List<RoutesModel> objects) {
        super(context, resource, objects);

        this.routesList = objects;
        this.resource = resource;
        this.context = context;

        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_routes, null);
        }

        TextView tvRouteNumber;
        TextView tvRouteName;

        tvRouteName = (TextView) convertView.findViewById(R.id.tvRouteName);
        tvRouteNumber = (TextView) convertView.findViewById(R.id.tvRouteNumber);

        tvRouteName.setText(routesList.get(position).getRtnm());
        tvRouteNumber.setText(routesList.get(position).getRt());

        return convertView;
    }
}
