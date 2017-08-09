package com.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.database.SqlDB;
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
    public void onBindViewHolder(StudentAdapter.StudentViews holder, final int position) {

        holder.name.setText("Name: "+mList.get(position).getName());
        holder.age.setText("Age: "+mList.get(position).getAge());
        holder.roll.setText("Roll: "+mList.get(position).getRoll());
        holder.row.setText(mList.get(position).getRow());

        holder.items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogShow(mList, position);
            }
        });

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
        private View items;

        public StudentViews(View itemView) {
            super(itemView);
            items = itemView;
            name = (TextView) itemView.findViewById(R.id.name);
            age = (TextView) itemView.findViewById(R.id.age);
            roll= (TextView) itemView.findViewById(R.id.roll);
            row= (TextView) itemView.findViewById(R.id.id);
        }
    }

    public void setAllList(List<Stud> list) {
        mList.clear();
        mList = list;
    }

    public void removeItem(int position) {
        SqlDB.getInstanse(mContext).deleteRow(mList.get(position).getRow());
        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mList.size());
    }


    private void alertDialogShow(final List<Stud> mList, final int position) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.alertNameEdt);
        edt.setText(mList.get(position).getName());

        builder.setTitle("Custom dialog");
        builder.setMessage("Enter text below");

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();


//                setUpdateAlert(mAlertBtnUpdate);


                boolean b =SqlDB.getInstanse(mContext).updateRow(edt.getText().toString(),mList.get(position).getRoll());

                if (b) {
                  mAlertBtnUpdate.alertUpdateBtn();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        AlertDialog b = builder.create();
        b.show();
    }


    AlertBtnUpdate mAlertBtnUpdate;

    public void setUpdateAlert(AlertBtnUpdate ab) {
        mAlertBtnUpdate = ab;
    }

    public interface AlertBtnUpdate {
        void alertUpdateBtn();
    }

}