package com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sqliteapplication.R;
import com.utils.Constant_Util;

/**
 * Created by krishan on 19/7/17.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {

    private EditText mSortNameEdt;
    private EditText mRangeStartEdt;
    private EditText mRangeEndEdt;

    public BtnClickCallBack mBtnClickCallBack;

    public void SetBtnClickCallBack (BtnClickCallBack btnClickCallBack){
        this.mBtnClickCallBack = btnClickCallBack;
    }

    public interface BtnClickCallBack{
        void ButtonCallBack(int position, int pos);
        void ButtonCallBack(int position, String name);
        void ButtonCallBack(int position, String Start,String end);
    }


    public static SettingFragment getSettingFragment(){
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.sortNameBtn).setOnClickListener(this);
        view.findViewById(R.id.rangeBtn).setOnClickListener(this);
        view.findViewById(R.id.ascending).setOnClickListener(this);
        view.findViewById(R.id.descending).setOnClickListener(this);
        mSortNameEdt =(EditText) view.findViewById(R.id.sortNameEdt);
        mRangeStartEdt =(EditText) view.findViewById(R.id.rangeStartEdt);
        mRangeEndEdt =(EditText) view.findViewById(R.id.rangeEndEdt);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.sortNameBtn:
                mBtnClickCallBack.ButtonCallBack(Constant_Util.SORT_NAME,mSortNameEdt.getText().toString());
                break;
            case R.id.rangeBtn:
                mBtnClickCallBack.ButtonCallBack(Constant_Util.RANGE,mRangeStartEdt.getText().toString(),mRangeEndEdt.getText().toString());
                break;
            case R.id.ascending:
                mBtnClickCallBack.ButtonCallBack(0,Constant_Util.SORT_ASCENDING);
                break;
            case R.id.descending:
                mBtnClickCallBack.ButtonCallBack(0,Constant_Util.SORT_DESCENDING);
                break;
        }
    }
}
