package com.izx.firstapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.izx.firstapp.pojo.TodoModel;
import com.izx.firstapp.rest.ApiClient;
import com.izx.firstapp.rest.ApiInterface;
import com.izx.firstapp.rest.CRUDtodo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = TodoActivity.class.getSimpleName();
    EditText edtTodo;
    RecyclerView rvTodo;
    ImageButton btnTodo;
    CRUDtodo cruDtodo;
    LinearLayoutManager mLayoutManager;
    DividerItemDecoration mDividerItemDecoration;
    TodoAdapter mAdapter;


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
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rvTodo.setLayoutManager(mLayoutManager);
        mDividerItemDecoration = new DividerItemDecoration(rvTodo.getContext(),
                mLayoutManager.getOrientation());
        rvTodo.addItemDecoration(mDividerItemDecoration);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        rvTodo.setLayoutAnimation(animation);

        List<String> toDo = new ArrayList<>();
        mAdapter = new TodoAdapter(toDo);
        rvTodo.setAdapter(mAdapter);

        btnTodo.setOnClickListener(this);
        cruDtodo = new CRUDtodo();
        cruDtodo.getTodoList(mAdapter);
    }

    private void getTodoList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TodoModel>> call = apiInterface.getTodoList();
        call.enqueue(new Callback<List<TodoModel>>() {
            @Override
            public void onResponse(Call<List<TodoModel>> call, Response<List<TodoModel>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    mAdapter.addNewTodo(response.body().get(i).getTitle());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<TodoModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    public void postTodo(String data) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TodoModel> call = apiInterface.postData(data);
        call.enqueue(new Callback<TodoModel>() {
            @Override
            public void onResponse(Call<TodoModel> call, Response<TodoModel> response) {
                Log.d(TAG, "onResponse: " + new Gson().toJson(response));
            }

            @Override
            public void onFailure(Call<TodoModel> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void onClick(View v) {
        mAdapter.clear();
        cruDtodo.postTodo(edtTodo.getText().toString());
        cruDtodo.getTodoList(mAdapter);
    }

}
