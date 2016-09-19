package com.charles.todoapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements EditItemFragment.DataUpdate{

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private ListView TaskList;
    private DBHelper db;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DBHelper (this);

        ListView();

        fragmentManager = getFragmentManager();
    }

    public void ListView(){
        TaskList = (ListView)findViewById(R.id.tasklistView);
        Cursor cursor = db.getAll();
        taskAdapter = new TaskAdapter(this, cursor);
        TaskList.setAdapter(taskAdapter);

        TaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EditItemFragment EditItem = new EditItemFragment(l);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.additemfragment, EditItem,"update");
                fragmentTransaction.commit();
            }
        });

        TaskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                db.delete(String.valueOf(l));
                updateList();
                Toast.makeText(getApplicationContext(), "The item has been deleted", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void updateList(){
        taskAdapter = new TaskAdapter(this, db.getAll());
        TaskList.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        EditItemFragment EditItem = new EditItemFragment(item.getItemId());


        switch (item.getItemId()) {
            case R.id.additem:
                if(!EditItem.isAdded()) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.additemfragment, EditItem, "add");
                    fragmentTransaction.commit();
                    return true;
                }
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("onStart", "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("onResume", "onResume");
    }
}