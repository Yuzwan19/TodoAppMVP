package com.izx.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.izx.firstapp.adapter.TodoAdapter;
import com.izx.firstapp.pojo.TodoModel;
import com.izx.firstapp.rest.CRUDtodo;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity implements View.OnClickListener, TodoView {

    private String TAG = TodoActivity.class.getSimpleName();
    EditText edtTodo;
    RecyclerView rvTodo;
    ImageButton btnTodo;
    TodoPresenter todoPresenter;
    LinearLayoutManager mLayoutManager;
    DividerItemDecoration mDividerItemDecoration;
    TodoAdapter mAdapter;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        setupView();
    }

    private void setupView() {
        edtTodo = findViewById(R.id.edt_add);
        btnTodo = findViewById(R.id.btn_add);
        rvTodo = findViewById(R.id.rv_todo);
        progressBar = findViewById(R.id.progress);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rvTodo.setLayoutManager(mLayoutManager);
        todoPresenter = new TodoPresenter(this);
        todoPresenter.getTodoList();
        btnTodo.setOnClickListener(this);
    }

    @Override
    public void initAdapter(List<TodoModel> list) {
        mAdapter = new TodoAdapter();
        for (int i = 0; i < list.size(); i++) {
            mAdapter.addNewTodo(list.get(i).getTitle());
        }
        rvTodo.setAdapter(mAdapter);
    }

    @Override
    public void setDividerDecoration() {
        mDividerItemDecoration = new DividerItemDecoration(rvTodo.getContext(),
                mLayoutManager.getOrientation());
        rvTodo.addItemDecoration(mDividerItemDecoration);
    }

    @Override
    public void setAnimation() {
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        rvTodo.setLayoutAnimation(animation);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDataFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorInput() {
        Toast.makeText(this, "Please do not empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(todoPresenter.validateInput(edtTodo.getText().toString())) return;
        mAdapter.clear();
        todoPresenter.postTodo(edtTodo.getText().toString());
        todoPresenter.getTodoList();
    }



}
