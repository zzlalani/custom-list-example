package com.zeeshanlalani.customlistexample.Adaptor;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeeshanlalani.customlistexample.MainActivity;
import com.zeeshanlalani.customlistexample.R;

/**
 * Created by admin on 10/15/2017.
 */

public class CustomAdaptor extends BaseAdapter {

    String[] students;
    String[] ids;
    Context context;
    private static LayoutInflater inflater = null;

    public CustomAdaptor(MainActivity activity, String[] _students, String[] _ids) {
        context = activity;
        students = _students;
        ids = _ids;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return students.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View rowView;
        rowView = inflater.inflate(R.layout.student_list, null);
        TextView studentId = (TextView) rowView.findViewById(R.id.studentId);
        TextView studentName =(TextView) rowView.findViewById(R.id.studentName);

        studentId.setText( ids[position]);
        studentName.setText( students[position]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage( students[position] );
                builder.show();
            }
        });

        return rowView;
    }
}
