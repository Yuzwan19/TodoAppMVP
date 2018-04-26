package com.izx.firstapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.izx.firstapp.adapter.TodoAdapter;
import com.izx.firstapp.pojo.TodoModel;
import com.izx.firstapp.rest.ApiClient;
import com.izx.firstapp.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Izcax on 3/25/18.
 */

class TodoPresenter {
    private TodoView todoView;

    public TodoPresenter(TodoView todoView) {
        this.todoView = todoView;
    }

    public boolean validateInput(String name){
        if (name.isEmpty()){
            todoView.showErrorInput();
            return true;
        }
        return false;
    }

    public void getTodoList() {
        todoView.showLoading();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TodoModel>> call = apiInterface.getTodoList();
        call.enqueue(new Callback<List<TodoModel>>() {
            @Override
            public void onResponse(Call<List<TodoModel>> call, Response<List<TodoModel>> response) {
                List<TodoModel> list = response.body();
                todoView.initAdapter(list);
                todoView.dismissLoading();
            }

            @Override
            public void onFailure(Call<List<TodoModel>> call, Throwable t) {
                todoView.onDataFailed("An error occured");
                Log.e("TAG", "onFailure: ", t);
            }
        });
    }

    public void postTodo(String data) {
        todoView.showLoading();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TodoModel> call = apiInterface.postData(data);
        call.enqueue(new Callback<TodoModel>() {
            @Override
            public void onResponse(Call<TodoModel> call, Response<TodoModel> response) {
                todoView.dismissLoading();
            }

            @Override
            public void onFailure(Call<TodoModel> call, Throwable t) {
                todoView.dismissLoading();
            }
        });
    }
}
