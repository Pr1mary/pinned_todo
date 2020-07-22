package com.se.pinned;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> taskList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    EditText inputTask;
    Context currCtx;
    TinyDB tinyDB;

//    Toast taskAddedToast;
//    Toast taskFinishedToast;
//    Toast taskRemovedToast;
//    Toast taskEmptyToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        taskAddedToast = Toast.makeText(currCtx, "Task Pinned!", Toast.LENGTH_SHORT);
//        taskFinishedToast = Toast.makeText(currCtx, "Task finished!", Toast.LENGTH_SHORT);
//        taskRemovedToast = Toast.makeText(this, "All task removed", Toast.LENGTH_SHORT);
//        taskEmptyToast = Toast.makeText(this, "New task is empty!", Toast.LENGTH_SHORT);

        currCtx = this;
        arrayAdapter = new ArrayAdapter<>(this, R.layout.task_list_layout, taskList);
        tinyDB = new TinyDB(currCtx);

        listView = findViewById(R.id.task_list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView taskView = (TextView) view;
                //remove string from taskList
                taskList.remove(taskView.getText().toString());
                //update the listview
                arrayAdapter.notifyDataSetChanged();
                //update data on sharedpreferences
                tinyDB.putListString("TaskData", taskList);

                Toast.makeText(currCtx, "Task finished!", Toast.LENGTH_SHORT).show();

//                taskAddedToast.cancel();
//                taskFinishedToast.show();
//                taskRemovedToast.cancel();
//                taskEmptyToast.cancel();

            }
        });
        //retrieve data from sharedpreferences
        loadItem();
        inputTask = findViewById(R.id.input_task);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteAll:
                taskList.clear();
                arrayAdapter.notifyDataSetChanged();
                tinyDB.putListString("TaskData", taskList);

                Toast.makeText(currCtx, "All task removed!", Toast.LENGTH_SHORT).show();

//                taskAddedToast.cancel();
//                taskFinishedToast.cancel();
//                taskRemovedToast.show();
//                taskEmptyToast.cancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //add item to the list
    public void addItem(View view){
        String task = inputTask.getText().toString();
        if(!task.isEmpty()){
            //add todo_task to taskList
            taskList.add(task);
            //update the listview
            arrayAdapter.notifyDataSetChanged();
            //save task list to shared preferences
            tinyDB.putListString("TaskData", taskList);
            //reset input task
            inputTask.setText("");

            Toast.makeText(currCtx, "Task Pinned!", Toast.LENGTH_SHORT).show();

//            taskAddedToast.show();
//            taskFinishedToast.cancel();
//            taskRemovedToast.cancel();
//            taskEmptyToast.cancel();

        }else{
//            taskAddedToast.cancel();
//            taskFinishedToast.cancel();
//            taskRemovedToast.cancel();
//            taskEmptyToast.show();

            Toast.makeText(currCtx, "New task is empty!", Toast.LENGTH_SHORT).show();
        }

    }

    //retrieve item list from sharedpreferences
    private void loadItem(){
        //use .addAll instead "=" to assign data list from sharedpreferences to arraylist
        //retrieve data from sharedpreferences to taskList
        taskList.addAll(tinyDB.getListString("TaskData"));
        //update the listview
        arrayAdapter.notifyDataSetChanged();

    }

}