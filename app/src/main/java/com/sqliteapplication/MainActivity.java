package com.sqliteapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fragment.SettingFragment;
import com.fragment.StudentInfoFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.setting).setOnClickListener(this);
        mBundle = new Bundle();
        mBundle.putInt("pos",0);
        mBundle.putInt("SubPos",0);
        SowStudntInfo(mBundle);
    }

    private void SowStudntInfo(Bundle mBundle) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        StudentInfoFragment fragment = StudentInfoFragment.getStudentInfoFragment(mBundle);
        ft.add(R.id.content, fragment, "StudentInfoFragment");
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.setting:
                settingAlert();
                break;
        }
    }

    private void settingAlert() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SettingFragment fragment = SettingFragment.getSettingFragment();
        ft.replace(R.id.content, fragment, "Setting");
        ft.addToBackStack("");
        ft.commit();

        fragment.SetBtnClickCallBack(new SettingFragment.BtnClickCallBack() {

            @Override
            public void ButtonCallBack(int position, int subpos) {
                mBundle = new Bundle();
                mBundle.putInt("pos",position);
                mBundle.putInt("SubPos",subpos);
                SowStudntInfo(mBundle);

            }

            @Override
            public void ButtonCallBack(int position, String name) {
                mBundle = new Bundle();
                mBundle.putInt("pos",position);
                mBundle.putString("nameStr",name);
                SowStudntInfo(mBundle);

            }

            @Override
            public void ButtonCallBack(int position, String Start, String end) {
                mBundle = new Bundle();
                mBundle.putInt("pos",position);
                mBundle.putString("startStr",Start);
                mBundle.putString("endStr",end);
                SowStudntInfo(mBundle);
            }
        });
    }
}
