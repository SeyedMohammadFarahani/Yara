package com.ansarbank.room.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ansarbank.room.R;
import com.ansarbank.room.Util.Row;
import com.ansarbank.room.widget.Text;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Row> rows;

    public MyAdapter(Context context, ArrayList<Row> rows) {
        this.context = context;
        this.rows = rows;
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int position) {
        return rows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        Text text = view1.findViewById(R.id.rowTxt);
        ImageView imageView = view1.findViewById(R.id.rowImg);
        imageView.setBackgroundResource(rows.get(position).getImage());
        text.setText(rows.get(position).getName());

        return view1;
    }
}
