package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Button> users;
    private final Retrofit retrofit = new RetrofitService().getRetrofit();
    private final Controller controller = retrofit.create(Controller.class);
    private LinearLayout usersList;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usersList = findViewById(R.id.listUsers);
        tv = findViewById(R.id.textView);
        Call<List<User>> call = controller.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> userList = response.body();
                    if (userList != null) {
                        for (User user : userList) {
                            int id = user.getId();
                            String name = user.getUsername();
                            Button button = new Button(MainActivity.this);
                            button.setText(name);

                            button.setOnClickListener(view -> {
                                Call<List<Todo>> callTodo = controller.getTodos();
                                callTodo.enqueue(new Callback<List<Todo>>() {
                                    @Override
                                    public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                                        List<Todo> toList = response.body();
                                        if (toList != null){
                                            String toView = "";
                                            for (Todo todo : toList){
                                                if (todo.getUserId() == id && !todo.isCompleted())
                                                    toView = toView + "- " + todo.getTitle() + "\n";
                                            }
                                            tv.setText(toView);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Todo>> call, Throwable t) {

                                    }
                                });
                            });
                            button.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View view) {
                                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                    intent.putExtra("user", user.getId());
                                    startActivity(intent);
                                    return true;
                                }
                            });
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(0, 0, 0, 0 );
                            float textSizeInSp = 10; // Example: 16 sp
                            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeInSp);
                            button.setLayoutParams(layoutParams);
                            usersList.addView(button);
                        }
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });

//        Button button = new Button(this);
//        button.setText("hello");
//        button.setOnClickListener(view -> {
//            Intent intent = new Intent(this, MainActivity2.class);
//            startActivity(intent);
//        });
//        Button button1 = new Button(this);
//        button1.setText("zim");
//        button1.setOnClickListener(view -> {
//            try {
//                controller.getError(6)
//                        .enqueue(new Callback<Integer>() {
//                            @Override
//                            public void onResponse(Call<Integer> call, Response<Integer> response) {
//                                if(response.isSuccessful()){
//                                    Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
//                                }
//                                else {
//
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<Integer> call, Throwable t) {
//                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//                                System.out.println(t.getMessage());
//                            }
//                        });
//            }
//            catch (Exception e){
//                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_LONG).show();
//            }
//
//        });
//        bookList = findViewById(R.id.listBooks);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        button.setLayoutParams(layoutParams);
//        button1.setLayoutParams(layoutParams);
//
//        bookList.addView(button);
//        bookList.addView(button1);
    }
}
//Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();

//controller.
//        .*function*
//        .enqueue(new Callback<*object*>() {
//@Override
//public void onResponse(Call<*object*> call, Response<*object*> response) {
//        if(!response.isSuccessful()){
//            response.body();
//        }
//        else {
//
//        }
//}
//
//@Override
//public void onFailure(Call<User> call, Throwable t) {
//        }
//        });

