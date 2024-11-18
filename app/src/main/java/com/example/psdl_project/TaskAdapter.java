package com.example.psdl_project;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context context;
    private Cursor cursor;
    private DBHelper1 dbHelper;

    public TaskAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        this.dbHelper = new DBHelper1(context);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

        holder.descriptionTextView.setText(description);
        holder.dateTextView.setText(date);

        holder.deleteButton.setOnClickListener(v -> {
            dbHelper.deleteActivity(id);
            Toast.makeText(context, "Activity deleted", Toast.LENGTH_SHORT).show();
            updateTasks(dbHelper.getAllActivities()); // Refresh list after deletion
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void updateTasks(Cursor newCursor) {
        if (cursor != null) cursor.close();
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView descriptionTextView;
        public TextView dateTextView;
        public Button deleteButton;

        public TaskViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.tv_task_description);
            dateTextView = itemView.findViewById(R.id.tv_task_date);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }
}
