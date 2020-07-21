package com.se.pinned;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
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

    ArrayList<Object> taskList;
    ArrayAdapter<Object> arrayAdapter;
    ListView listView;
    EditText inputTask;
    Context currCtx;

//    SharedPreferences sharedPref;

    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currCtx = this;
        taskList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.task_list_layout, taskList);
        tinyDB = new TinyDB(currCtx);
//        sharedPref = getPreferences(MODE_PRIVATE);

        listView = findViewById(R.id.task_list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView taskView = (TextView) view;

                taskList.remove(taskView.getText().toString());
                arrayAdapter.notifyDataSetChanged();

                Toast.makeText(currCtx, "Task finished!", Toast.LENGTH_SHORT).show();

            }
        });

        if(!taskList.isEmpty()) loadItem();

        inputTask = findViewById(R.id.input_task);

        //load item list

    }

    //add item to the list
    public void addItem(View view){
        String task = inputTask.getText().toString();
        if(!task.isEmpty()){
            taskList.add(task);
            arrayAdapter.notifyDataSetChanged();

            inputTask.setText("");
            Toast.makeText(currCtx, "Task Pinned!", Toast.LENGTH_SHORT).show();

            //save task list to shared preferences
            tinyDB.putListObject("TaskData", taskList);

        }else{
            Toast.makeText(currCtx, "New task is empty!", Toast.LENGTH_SHORT).show();
        }



//        Editor prefsEditor = sharedPref.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(taskList);
//        prefsEditor.putString("TaskData", json);
//        prefsEditor.commit();

    }

    //load item list from sharedpreferences
    private void loadItem(){
        taskList = tinyDB.getListObject("TaskData", taskList.getClass());

//        Gson gson = new Gson();
//        String json = sharedPref.getString("TaskData", "");
//        taskList = gson.fromJson(json, taskList.getClass());

    }

}