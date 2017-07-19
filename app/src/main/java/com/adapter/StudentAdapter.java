package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.model.Stud;
import com.sqliteapplication.R;

import java.util.List;

/**
 * Created by krishan on 17/7/17.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViews> {

    private Context mContext;
    private List<Stud> mList;

    public StudentAdapter(Context context, List<Stud> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public StudentAdapter.StudentViews onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new StudentViews(view);
    }

    @Override
    public void onBindViewHolder(StudentAdapter.StudentViews holder, int position) {

        holder.name.setText("Name: "+mList.get(position).getName());
        holder.age.setText("Age: "+mList.get(position).getAge());
        holder.roll.setText("Roll: "+mList.get(position).getRoll());
        holder.row.setText(mList.get(position).getRow());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class StudentViews extends RecyclerView.ViewHolder {
        TextView name;
        TextView age;
        TextView roll;
        TextView row;

        public StudentViews(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            age = (TextView) itemView.findViewById(R.id.age);
            roll= (TextView) itemView.findViewById(R.id.roll);
            row= (TextView) itemView.findViewById(R.id.id);
        }
    }
}