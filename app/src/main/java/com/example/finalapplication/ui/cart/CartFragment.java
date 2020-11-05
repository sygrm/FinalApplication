package com.example.finalapplication.ui.cart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalapplication.R;
import com.example.finalapplication.ui.CartAdapter;
import com.example.finalapplication.ui.MyAdapter;
import com.example.finalapplication.ui.all.AllViewModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;
    ArrayList<HashMap<String, String>> listItems;
    ListView listview;
    public int number=0;
    ArrayList food_name=new ArrayList();
    ArrayList food_price=new ArrayList();
    ArrayList food_id=new ArrayList();
    ArrayList food_number=new ArrayList();
    int total_price=0;
    String all_s;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        cartViewModel =
                ViewModelProviders.of(this).get(CartViewModel.class);

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CartAdapter myAdapter = new CartAdapter(getActivity(), R.layout.cart_list_item, listItems);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        TextView money =(TextView)root.findViewById(R.id.textView4);
        money.setText(all_s);
        listview = (ListView)root.findViewById(R.id.list_cart);
        listview.setAdapter(myAdapter);
        return root;
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
                //rs.last();
                //number = rs.getRow();
                //rs.beforeFirst();

                int total=0;


                rs_cart.beforeFirst();
                int total_price=0;
                int all=0;
                while (rs_cart.next()) {
                    if(rs_cart.getInt("number")!=0) {
                        food_name.add(rs_cart.getString("name"));
                        food_price.add(rs_cart.getString("per_price"));
                        food_id.add(rs_cart.getString("id"));
                        food_number.add(rs_cart.getString("number"));
                        Log.e("当前行数", String.valueOf(rs_cart.getRow()));
                        Log.e("name", String.valueOf(rs_cart.getString("name")));
                        int per=Integer.parseInt(rs_cart.getString("per_price"));
                        total_price = per*rs_cart.getInt("number");
                        all += total_price;
                        Log.e("price",String.valueOf(all));
                        number++;
                    }
                }
                all_s=String.valueOf(all);
                rs_cart.last();
                Log.e("总行数", String.valueOf(number));
                listItems = new ArrayList<HashMap<String, String>>();
                for(int i=0;i<number;i++){
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ItemTitle", String.valueOf(food_name.get(i)));
                    map.put("ItemDetail",String.valueOf(food_price.get(i)));
                    map.put("id",String.valueOf(food_id.get(i)));
                    map.put("number",String.valueOf(food_number.get(i)));
                    listItems.add(map);
                }
                Log.e("sql", "开始关闭远程连接");
                conn_cart.close();
                stmt_cart.close();
                rs_cart.close();

                return;
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
                Log.e("sql", String.valueOf(conn_cart));
            }
        }



    });

}