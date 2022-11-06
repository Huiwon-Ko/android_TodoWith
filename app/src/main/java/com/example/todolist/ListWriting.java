package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Console;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListWriting extends AppCompatActivity {
    private Button submitbtn;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_writing);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        submitbtn = findViewById(R.id.saveButton);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleNewTodo();
            }
        });
    }
    private void handleNewTodo() {
        View view = getLayoutInflater().inflate(R.layout.activity_list_writing, null);
        EditText inputTodoEdit = view.findViewById(R.id.inputToDo);
        EditText placeEdit = view.findViewById(R.id.place);
        EditText dateEdit = view.findViewById(R.id.date);
        EditText timeEdit = view.findViewById(R.id.time);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                map.put("Title", inputTodoEdit.getText().toString());
                map.put("Place", placeEdit.getText().toString());
                map.put("Date", dateEdit.getText().toString());
                map.put("Time", timeEdit.getText().toString());

               Call<Void> call = retrofitInterface.executeAdd(map);
               call.enqueue(new Callback<Void>() {
                   @Override
                   public void onResponse(Call<Void> call, Response<Void> response) {
                       if (response.code() == 200) {
                           Toast.makeText(ListWriting.this,"성공",Toast.LENGTH_LONG).show();
                       } else if (response.code() == 400) {
                           Toast.makeText(ListWriting.this, "이미 등록되었습니다", Toast.LENGTH_LONG).show();
                       }

                   }

                   @Override
                   public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ListWriting.this,t.getMessage(),Toast.LENGTH_LONG).show();
                   }
               });
            }
        });
    }
}