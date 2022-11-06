package com.example.todolist;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("/todo/add")
    Call<Void> executeAdd(@Body HashMap<String, String> map); //List<Todo> newTodo
}
