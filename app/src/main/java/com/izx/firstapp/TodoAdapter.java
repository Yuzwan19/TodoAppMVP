package com.izx.firstapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.izx.firstapp.rest.CRUDtodo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<String> todoList;
    private int lastPosition = -1;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public ImageButton button;

        public ViewHolder(View v) {
            super(v);
            button = v.findViewById(R.id.btn_delete);
            mTextView = v.findViewById(R.id.text);
        }
    }

    public TodoAdapter(List<String> todolist) {
        todoList = todolist;
    }

    public void addNewTodo(String s) {
        todoList.add(s);
        notifyDataSetChanged();
    }

    public void clear() {
        todoList.clear();
        notifyDataSetChanged();
    }

    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTextView.setText(todoList.get(position));
        setAnimation(holder.itemView, position);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDtodo crud = new CRUDtodo();
                crud.deleteItem(position + 1 + "");
                notifyItemRemoved(position);
                todoList.remove(position);
                Toast.makeText(v.getContext(), "Delete success", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

}