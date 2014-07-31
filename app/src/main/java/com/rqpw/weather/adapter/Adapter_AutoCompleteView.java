package com.rqpw.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rqpw.weather.R;
import com.rqpw.weather.db.DBHelper;
import com.rqpw.weather.entity.CityEntity;

import java.util.ArrayList;

/**
 * Created by Pan Jiafang on 2014/7/30.
 */
public class Adapter_AutoCompleteView extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<CityEntity> citys;
    private CityFilter cityFilter;

    public Adapter_AutoCompleteView(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return citys == null ? 0 : citys.size();
    }

    @Override
    public Object getItem(int position) {
        return citys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static TextView tv;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_autocompleteview, null);
            tv = (TextView) convertView.findViewById(R.id.item_autocompleteview_tv);
            convertView.setTag(tv);
        }
        else
            tv = (TextView) convertView.getTag();

        CityEntity entity = citys.get(position);

        tv.setText(entity.province+","+entity.city+","+entity.town);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(cityFilter == null)
            cityFilter = new CityFilter();
        return cityFilter;
    }

    private class CityFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<CityEntity> citys = DBHelper.getInstance(context).getCityByPY(constraint.toString());
            filterResults.values = citys;
            filterResults.count = citys.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            citys = (ArrayList<CityEntity>) results.values;

            if(results.count > 0)
                notifyDataSetChanged();
            else
                notifyDataSetInvalidated();
        }
    }
}
