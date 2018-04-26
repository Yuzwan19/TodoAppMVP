package com.izx.firstapp;

import com.izx.firstapp.pojo.TodoModel;

import java.util.List;

/**
 * Created by Izcax on 3/25/18.
 */

public interface TodoView {

    void initAdapter(List<TodoModel> list);

    void setDividerDecoration();

    void setAnimation();

    void showLoading();

    void dismissLoading();

    void onDataFailed(String message);

    void showErrorInput();
}
