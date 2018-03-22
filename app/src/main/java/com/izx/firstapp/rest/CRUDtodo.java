package com.izx.firstapp.rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.izx.firstapp.TodoAdapter;
import com.izx.firstapp.pojo.TodoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Izcax on 3/20/18.
 */

public class CRUDtodo {
    String TAG = "CRUD";

    public void getTodoList(final TodoAdapter mAdapter) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TodoModel>> call = apiInterface.getTodoList();
        call.enqueue(new Callback<List<TodoModel>>() {
            @Override
            public void onResponse(Call<List<TodoModel>> call, Response<List<TodoModel>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    mAdapter.addNewTodo(response.body().get(i).getTitle());
                }
                Log.d(TAG, "onResponse: GET" + new Gson().toJson(response));

            }

            @Override
            public void onFailure(Call<List<TodoModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: GET", t);
            }
        });
    }

    public void postTodo(String data) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TodoModel> call = apiInterface.postData(data);
        call.enqueue(new Callback<TodoModel>() {
            @Override
            public void onResponse(Call<TodoModel> call, Response<TodoModel> response) {
                Log.d(TAG, "onResponse: POST" + new Gson().toJson(response));
            }

            @Override
            public void onFailure(Call<TodoModel> call, Throwable t) {
                Log.e(TAG, "onFailure: POST", t);
            }
        });
    }

    public void deleteItem(String id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TodoModel> call = apiInterface.deleteData(id);
        call.enqueue(new Callback<TodoModel>() {
            @Override
            public void onResponse(Call<TodoModel> call, Response<TodoModel> response) {
                Log.d(TAG, "onResponse: Delete" + new Gson().toJson(response));
            }

            @Override
            public void onFailure(Call<TodoModel> call, Throwable t) {
                Log.e(TAG, "onFailure: Delete", t);

            }
        });
    }

}
