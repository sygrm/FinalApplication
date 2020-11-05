package com.example.finalapplication.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.finalapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.recyclerview.widget.RecyclerView.OnClickListener;
import static androidx.recyclerview.widget.RecyclerView.ViewHolder;


public class CartAdapter extends ArrayAdapter {

    public CartAdapter(@NonNull Context context, int resource, ArrayList<HashMap<String, String>> list) {
        super(context, resource, list);
    }

    private ArrayList<HashMap<String, String>> listItems;
    private ListView listview;
    //private Callback mCallback;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        ViewHolder viewHolder = null;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.cart_list_item, parent,
                    false);
            //为convertView设置tag标志
            //convertView.setTag(viewHolder);
        }
        Map<String, String> map = (Map<String, String>) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
        TextView detail = (TextView) itemView.findViewById(R.id.itemDetail);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.imageView2);
        TextView number=(TextView)itemView.findViewById(R.id.textView2);

        int resID = getContext().getResources().getIdentifier("food_"+ map.get("id"), "drawable","com.example.finalapplication");
        Bitmap gameStatusBitmap = BitmapFactory.decodeResource(imageView.getResources(), resID);
        imageView.setImageBitmap(gameStatusBitmap);

        title.setText(map.get("ItemTitle"));
        detail.setText(map.get("ItemDetail"));
        number.setText(map.get("number"));

        return itemView;
    }
}
