package com.example.finalapplication.ui.veg;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalapplication.R;
import com.example.finalapplication.ui.MyAdapter;
import com.example.finalapplication.ui.stable.StableViewModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class VegFragment extends Fragment {

    private VegViewModel vegViewModel;
    ArrayList<HashMap<String, String>> listItems;
    ListView listview;
    ArrayList food_name=new ArrayList();
    ArrayList food_price=new ArrayList();
    ArrayList food_id=new ArrayList();
    int number=0;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        vegViewModel =
                ViewModelProviders.of(this).get(VegViewModel.class);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MyAdapter myAdapter = new MyAdapter(getActivity(), R.layout.list_item, listItems);
        View root = inflater.inflate(R.layout.fragment_veg, container, false);


        listview = (ListView)root.findViewById(R.id.list_veg);
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
            String dbName = "food_shop";
            String port = "3306";
            String url = "jdbc:mysql://" + ip + ":" + port
                    + "/" + dbName + "?useSSL=false" +
                    "&useUnicode=true" +
                    "&characterEncoding=UTF-8" +
                    "&serverTimezone=GMT%2B8" +
                    "&allowPublicKeyRetrieval=true";
            String user = "shopper";
            String password = "626695011";

            Connection conn = null;
            PreparedStatement PStmt = null;
            ResultSet rs = null;

            try {
                Log.e("sql", "开始数据库连接");

                conn = DriverManager.getConnection(url, user, password);
                Log.i("sql", "远程连接成功!");
                Statement stmt = conn.createStatement();
                //切换库。
                String sql = "SELECT * FROM food ";
                rs = stmt.executeQuery(sql);
                //Log.e("总行数", String.valueOf(number));
                //Log.e("当前返回行数", String.valueOf(rs.getRow()));

                listItems = new ArrayList<HashMap<String, String>>();
                rs.beforeFirst();

                while (rs.next()) {
                    if((rs.getString("classfiction")).equals("drink")) {
                        food_name.add(rs.getString("name"));
                        food_price.add(rs.getString("price"));
                        food_id.add(Integer.parseInt(rs.getString("id")));
                    }
                }

                number=food_id.size();
                for(int i=0;i<number;i++){
                    HashMap<String, String> map = new HashMap<String, String>();
                    Log.e("name",String.valueOf(food_name.get(i)));
                    map.put("ItemTitle", String.valueOf(food_name.get(i)));
                    map.put("ItemDetail",String.valueOf(food_price.get(i)));
                    map.put("id",String.valueOf(food_id.get(i)));
                    listItems.add(map);
                }

                Log.e("sql", "开始关闭远程连接");
                conn.close();
                stmt.close();
                rs.close();
                Log.e("sql", "关闭远程连接成功");

                return;
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
                Log.e("sql", String.valueOf(conn));
                Log.e("sql", "远程连接失败!");
            }
        }

    });
}