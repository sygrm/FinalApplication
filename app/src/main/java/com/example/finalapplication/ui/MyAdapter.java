package com.example.finalapplication.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapplication.R;
import com.example.finalapplication.ui.all.AllFragment;
import com.example.finalapplication.ui.all.AllViewModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.recyclerview.widget.RecyclerView.*;


public class MyAdapter extends ArrayAdapter {

    public MyAdapter(@NonNull Context context, int resource, ArrayList<HashMap<String, String>> list) {
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
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,
                    false);

            //为convertView设置tag标志
            //convertView.setTag(viewHolder);
        }
        Map<String, String> map = (Map<String, String>) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
        TextView detail = (TextView) itemView.findViewById(R.id.itemDetail);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.imageView2);

        int resID = getContext().getResources().getIdentifier("food_"+ map.get("id"), "drawable","com.example.finalapplication");
        Bitmap gameStatusBitmap = BitmapFactory.decodeResource(imageView.getResources(), resID);
        imageView.setImageBitmap(gameStatusBitmap);

        title.setText(map.get("ItemTitle"));
        detail.setText(map.get("ItemDetail"));
        //TypedArray ar = getContext().getResources().obtainTypedArray(R.array.image_array);
        //TextView number= (TextView) itemView.findViewById(R.id.number);

        Button btn2 =
                (Button)itemView.findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener()
        {
            int total_price=0;
            @Override
            public void onClick(View v)
            {
                //Log.e("num","开始取得数字");
                //String id=map.get("id");
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String driver = "com.mysql.jdbc.Driver";
                    try {
                        Class.forName(driver);
                        Log.e("sql", "数据库驱动加载成功");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        Log.e("sql", "数据库驱动加载失败");
                    }
                    String ip = "rm-2vc3b6wr831l399mkwo.mysql.cn-chengdu.rds.aliyuncs.com";
                    String dbName_cart = "cart";
                    String port = "3306";
                    String url_cart = "jdbc:mysql://" + ip + ":" + port
                            + "/" + dbName_cart + "?useSSL=false" +
                            "&useUnicode=true" +
                            "&characterEncoding=UTF-8" +
                            "&serverTimezone=GMT%2B8" +
                            "&allowPublicKeyRetrieval=true";
                    String user_cart = "cart_adder";
                    String password_cart = "626695011";
                    Connection conn_cart = null;
                    PreparedStatement PStmt = null;
                    ResultSet rs_cart = null;

                    try {
                        Log.e("sql", "开始数据库连接");
                        conn_cart = DriverManager.getConnection(url_cart, user_cart, password_cart);
                        //conn = DriverManager.getConnection(url, user, password);
                        Log.i("sql", "远程连接成功!");

                        Statement stmt_cart = conn_cart.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                        //切换库。
                        String sql_cart = "select * from cart";
                        rs_cart = stmt_cart.executeQuery(sql_cart);
                        rs_cart.beforeFirst();
                        int id =Integer.parseInt(map.get("id"));
                        Log.e("click",String.valueOf(id));
                        while(rs_cart.next()){
                            if(rs_cart.getInt("id")==id) {
                                int number=rs_cart.getInt("number");
                                number++;
                                String in =String.valueOf(number);
                                //s_cart.updateInt("number",number);
                                String sql= "UPDATE cart SET number =' "+in+"' WHERE id = '"+map.get("id")+"'";
                                stmt_cart.executeUpdate(sql);
                                Log.e("sql","写入成功");
                            }
                        }
                        Log.e("sql", "开始关闭远程连接");
                        rs_cart.close();stmt_cart.close();conn_cart.close();
                        return;
                    } catch (java.sql.SQLException e) {
                        e.printStackTrace();
                        Log.e("sql", String.valueOf(conn_cart));
                    }
                }

            });
        });
        return itemView;
    }
}
