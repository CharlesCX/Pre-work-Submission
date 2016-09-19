package com.charles.todoapp;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;


/**
 * Created by charles on 16/6/25.
 */
public class EditItemFragment extends DialogFragment {

    private EditText title;
    private EditText note;
    private DatePicker date;
    private Spinner priority;
    private Button save;
    private Button cancel;
    private DBHelper db;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ListView TaskList;
    private TaskAdapter taskAdapter;
    private long id;

    public interface DataUpdate {
        public void updateList();
    }

    public EditItemFragment(long l){
        id = l;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_info, container, false);

        title = (EditText) view.findViewById(R.id.edit_title);
        note = (EditText) view.findViewById(R.id.edit_note);
        date = (DatePicker) view.findViewById(R.id.DatePicker);
        priority = (Spinner) view.findViewById(R.id.spinner_priority_level);
        save = (Button) view.findViewById(R.id.bSave);
        cancel = (Button) view.findViewById(R.id.bCancel);
        db = new DBHelper (getActivity());
        fragmentManager = getFragmentManager();


        return view;
    }

    public interface DateBridge {
        public void updateView();
    }


    @Override
    public void onResume() {
        super.onResume();

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(getTag().equals("add")) {
                    String TaskTitle = title.getText().toString();
                    String TaskNote = note.getText().toString();
                    Integer TYear = date.getYear();
                    String TaskYear = TYear.toString();
                    Integer TMonth = date.getMonth();
                    String TaskMonth = TMonth.toString();
                    Integer TDay = date.getDayOfMonth();
                    String TaskDay = TDay.toString();
                    Integer TaskPriority = priority.getSelectedItemPosition();
                    db.insert(TaskTitle, TaskNote, TaskYear, TaskMonth, TaskDay, TaskPriority);

                }

                if(getTag().equals("update")) {
                    Cursor cursor = db.getData(String.valueOf(id));
                    cursor.moveToFirst();
                    String TaskId =cursor.getString(0);
                    String TaskTitle = title.getText().toString();
                    String TaskNote = note.getText().toString();
                    Integer TYear = date.getYear();
                    String TaskYear = TYear.toString();
                    Integer TMonth = date.getMonth();
                    String TaskMonth = TMonth.toString();
                    Integer TDay = date.getDayOfMonth();
                    String TaskDay = TDay.toString();
                    Integer TaskPriority = priority.getSelectedItemPosition();
                    db.update(TaskId, TaskTitle, TaskNote, TaskYear, TaskMonth, TaskDay, TaskPriority);

                }

                DataUpdate dataUpdate = (DataUpdate) getActivity();
                dataUpdate.updateList();
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}