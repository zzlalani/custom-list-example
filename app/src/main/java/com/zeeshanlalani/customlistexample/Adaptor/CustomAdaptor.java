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
import com.zeeshanlalani.customlistexample.Models.Student;
import com.zeeshanlalani.customlistexample.R;

import java.util.List;

/**
 * Created by admin on 10/15/2017.
 */

public class CustomAdaptor extends BaseAdapter {

    List<Student> students;
    Context context;
    private static LayoutInflater inflater = null;

    public CustomAdaptor(MainActivity activity, List<Student> _students) {
        context = activity;
        students = _students;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return students.size() + 1;
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
        if ( position == 0) {
            rowView = inflater.inflate(R.layout.list_header, null);
        } else {
            rowView = inflater.inflate(R.layout.student_list, null);
            TextView studentId = (TextView) rowView.findViewById(R.id.studentId);
            TextView studentName = (TextView) rowView.findViewById(R.id.studentName);

            final Student student = students.get(position-1);
            studentId.setText(String.valueOf(student.id));
            studentName.setText(student.name);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(student.name);
                    builder.show();
                }
            });
        }

        return rowView;
    }
}
