package com.izx.firstapp;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Izcax on 3/25/18.
 */
public class TodoPresenterTest {

    @Test
    public void testGetDataTodoFromServer() throws Exception {
        TodoView addCharacterView = mock(TodoView.class);
        TodoPresenter todoPresenter = new TodoPresenter(addCharacterView);
        todoPresenter.getTodoList();
        verify(addCharacterView).showLoading();
    }

    @Test
    public void testPostData() throws Exception {
        TodoView addCharacterView = mock(TodoView.class);
        TodoPresenter todoPresenter = new TodoPresenter(addCharacterView);
        todoPresenter.postTodo("Lunch");
        verify(addCharacterView).showLoading();
    }
}