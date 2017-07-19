package com.sqliteapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.adapter.StudentAdapter;
import com.database.SqlDB;
import com.model.Stud;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context mContext = this;

    RecyclerView mRecyclerView;
    StudentAdapter mAdapter;
    List<Stud> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclearId);
        mList = SqlDB.getInstanse(mContext).getAllStudInfo();
        if (mList.size() == 0) {
            String dataStr = getAssetsFolder();
            try {
                JSONObject obj = new JSONObject(dataStr);
                JSONArray jsonArr = obj.getJSONArray("data");
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject o = jsonArr.getJSONObject(i);
                    SqlDB.getInstanse(mContext).insertStus(o.getString("name"), o.getString("roll"), o.getString("age"), o.getString("row"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        mList = SqlDB.getInstanse(mContext).getRangeIngo("10", "15");
//        mList = SqlDB.getInstanse(mContext).getSameName("Amit");



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new StudentAdapter(mContext,mList);
        mRecyclerView.setAdapter(mAdapter);


    }

    public String getAssetsFolder() {
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("map.json")));
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
