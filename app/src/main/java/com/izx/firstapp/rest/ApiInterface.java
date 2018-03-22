package com.izx.firstapp.rest;

import com.izx.firstapp.pojo.TodoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Izcax on 3/20/18.
 */

public interface ApiInterface {
    @GET("todo")
    Call<List<TodoModel>> getTodoList();

    @POST("todo")
    @FormUrlEncoded
    Call<TodoModel> postData(@Field("title") String title);

    @DELETE("todo/{id}")
    Call<TodoModel> deleteData(@Path("id") String id);
}
