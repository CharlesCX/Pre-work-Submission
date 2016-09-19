package com.charles.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by charles on 16/7/14.
 */
public class TaskAdapter extends CursorAdapter{


    public TaskAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.task_list, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView listTitle = (TextView) view.findViewById(R.id.list_task_title);
        TextView listPriority = (TextView) view.findViewById(R.id.list_task_priority);
        // Extract properties from cursor
        String title = cursor.getString(cursor.getColumnIndex("title"));
        int prioritylvl = cursor.getInt(cursor.getColumnIndex("priority"));

        String priority;

        if (prioritylvl == 0) {
            priority = "HIGH";
        } else if (prioritylvl == 1) {
            priority = "MEDIUM";
        } else {
            priority = "LOW";
        }


        // Populate fields with extracted properties
        listTitle.setText(title);
        listPriority.setText(String.valueOf(priority));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
