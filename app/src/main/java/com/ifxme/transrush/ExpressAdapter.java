package com.ifxme.transrush;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Slbw on 2016/3/22.
 */
public class ExpressAdapter extends BaseAdapter {

    private Context context;
    private List<ExpressModel> datas;

    public ExpressAdapter(Context context, List<ExpressModel> datas) {
        this.context = context;
        this.datas = datas;
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = null;
        if (convertView == null || convertView.getTag(R.id.list_tag_key) == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_express, null);

            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(R.id.list_tag_key, holder);
        } else {
            holder = (Holder) convertView.getTag(R.id.list_tag_key);
        }
        ExpressModel express = datas.get(position);
        holder.tvTitle.setText(express.getTitle());
        holder.tvTime.setText(express.getTime());
        if(position==0)
        {
            holder.tvTitle.setTextColor(0xffff2222);
        }
        else
        {
            holder.tvTitle.setTextColor(0xff666666);
        }
        return convertView;
    }

    class Holder {
        TextView tvTitle;
        TextView tvTime;
    }

}
