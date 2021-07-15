package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN";

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;

    private RetrofitJSON retrofitJSON;

    private static boolean success;
    private static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://testandroid.macropay.com.mx")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitJSON = retrofit.create(RetrofitJSON.class);
        buttonLogin.setOnClickListener(view -> execute());
    }

    private void init(){
        Log.d(TAG, "init");
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonLogin = findViewById(R.id.buttonLogin);
    }

    private void execute(){
        Log.d(TAG, "executeCall");
        if(validateInput()){
            //getPost();
            createPost();
        }
    }

    private boolean validateInput(){
        Log.d(TAG, "validateInput");
        //TODO: Create more detailed checks
        if(editTextEmail.getText().toString().equals("")) {
            Toast.makeText(this, "Datos incompletos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(editTextEmail.getText().toString().equals("")) {
            Toast.makeText(this, "Datos incompletos", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void verifyLogin(){
        Log.d(TAG, "verifyLogin: success: " + success);
        if(success){
            Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show();
            launchSessionActivity();
        }
        else{
            Toast.makeText(this, "Login no exitoso", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchSessionActivity() {
        Log.d(TAG, "launchSessionActivity");
        Intent i = new Intent(MainActivity.this, CurrentSession.class);
        i.putExtra("TOKEN", token);
        MainActivity.this.startActivity(i);
    }

    private void getPost() {
        Log.d(TAG, "getPost");
        Call<PostResponse> call = retrofitJSON.getPostResponse();

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "getPost: Successful\nCode: " + response.code());
                    return;
                }

                PostResponse postResponse = response.body();
                assert postResponse != null;
                success = postResponse.getSuccess();
                token = postResponse.getToken();

                String content = "Success: " + success + "\nToken: " + token;
                Log.d(TAG, "getPost: Successful\nContent:\n" + content + "\nCode: " + response.code());
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.d(TAG, "getPost: Unsuccessful\nCode: " + t.getMessage());
            }
        });
    }

    private void createPost() {
        Log.d(TAG, "createPost");

        String email = editTextEmail.getText().toString(); //expected: admin@macropay.mx
        String password = editTextPassword.getText().toString(); //expected: Admin1

        Log.d(TAG, "createPost: dataSent \nemail: " + email + "\npassword: " + password);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", email)
                .addFormDataPart("password", password)
                .build();

        Call<PostResponse> call = retrofitJSON.createPost(requestBody);

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "createPost: Successful\nCode: " + response.code());
                    return;
                }

                PostResponse postResponse = response.body();
                assert postResponse != null;
                success = postResponse.getSuccess();
                token = postResponse.getToken();

                String content = "Success: " + success + "\nToken: " + token;
                Log.d(TAG, "createPost: Successful.\nContent:\n" + content + "\nCode: " + response.code());

                verifyLogin();
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.d(TAG, "createPost: Unsuccessful.\nCode: " + t.getMessage());
            }
        });
    }
}