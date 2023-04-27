package com.example.todoapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todoapplication.Utels.DataBaseHelper;
import com.example.todoapplication.adapter.ToDoAdapter;
import com.example.todoapplication.model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogClaseListne{

    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private DataBaseHelper myDB;
    private List<ToDoModel>mList;
    private ToDoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView=findViewById(R.id.recyclerview);
        fab=findViewById(R.id.fab);
        myDB=new DataBaseHelper(MainActivity.this);
        mList=new ArrayList<>();
        adapter=new ToDoAdapter(myDB,MainActivity.this);

        mList=myDB.getAllTask();
        Collections.reverse(mList);
        adapter.setTask(mList);

        fab.setOnClickListener(view -> {
            AddNowTask.newInstance().show(getSupportFragmentManager(),AddNowTask.TAG);
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDialogClass(DialogInterface dialogInterface) {
        mList=myDB.getAllTask();
        Collections.reverse(mList);
        adapter.setTask(mList);
        adapter.notifyDataSetChanged();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }
}