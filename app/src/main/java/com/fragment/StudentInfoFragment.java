package com.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adapter.StudentAdapter;
import com.database.SqlDB;
import com.model.Stud;
import com.sqliteapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by krishan on 19/7/17.
 */

public class StudentInfoFragment extends Fragment implements StudentAdapter.AlertBtnUpdate {

    private RecyclerView mRecyclerView;
    private StudentAdapter mAdapter;
    private List<Stud> mList;
    private Paint p = new Paint();


    public static StudentInfoFragment getStudentInfoFragment(Bundle bundle) {
        StudentInfoFragment fragment = new StudentInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_info_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclearId);

        mList = SqlDB.getInstanse(getContext()).getAllStudInfo(0);
        if (mList.size() == 0) {
            String dataStr = getAssetsFolder();
            try {
                JSONObject obj = new JSONObject(dataStr);
                JSONArray jsonArr = obj.getJSONArray("data");
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject o = jsonArr.getJSONObject(i);
                    SqlDB.getInstanse(getContext()).insertStus(o.getString("name"), o.getString("roll"), o.getString("age"), o.getString("row"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Bundle b = getArguments();
        int position = b.getInt("pos");
        switch (position) {
            case 0:
                int subPos = b.getInt("SubPos");
                mList = SqlDB.getInstanse(getContext()).getAllStudInfo(subPos);
                break;
            case 1:
                mList = SqlDB.getInstanse(getContext()).getSameName(b.getString("nameStr"));
                break;
            case 2:
                mList = SqlDB.getInstanse(getContext()).getRangeIngo(b.getString("startStr"), b.getString("endStr"));
                break;
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new StudentAdapter(getContext(),mList);
        mAdapter.setUpdateAlert(this);
        mRecyclerView.setAdapter(mAdapter);
        initSwipe();





    }

    public String getAssetsFolder() {
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("map.json")));
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }




    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    mAdapter.removeItem(position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.settings);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }


    @Override
    public void alertUpdateBtn() {
        mAdapter.notifyDataSetChanged();
    }
}
