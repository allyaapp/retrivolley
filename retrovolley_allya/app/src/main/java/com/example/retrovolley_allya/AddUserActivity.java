package com.example.retrovolley_allya;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import model.User;
import retrofit.MethodHTTP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddUserActivity extends AppCompatActivity {

    private EditText edtFullname, edtEmail, edtPassword;
    private Button btnSubmit;
    private String typeConn = "retrofit";

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        edtEmail = findViewById(R.id.edt_email);
        edtFullname = findViewById(R.id.edt_fullname);
        edtPassword = findViewById(R.id.edt_password);
        btnSubmit = findViewById(R.id.btn_submit);

        setTitle(getString(R.string.tambah_user));

        Bundle extras = getIntent().getExtras();
        if (extras!= null) {
            typeConn=extras.getString("typeConnection");
        }
    }

    public void actionSubmit(View view) {
        boolean isInputValid = false;

        if (edtFullname.getText().toString().isEmpty()) {
            edtFullname.setError("Tidak Boleh Kosong");
            edtFullname.requestFocus();
            isInputValid = false;
        }else {
            isInputValid = true;
        }

        if (edtEmail.getText().toString().isEmpty()) {
            edtEmail.setError("Tidak Boleh Kosong");
            edtEmail.requestFocus();
            isInputValid = false;
        }else {
            isInputValid = true;
        }

        if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError("Tidak Boleh Kosong");
            edtPassword.requestFocus();
            isInputValid = false;
        }else {
            isInputValid = true;
        }

        if (isInputValid) {
            User user = new User();
            user.setUser_fullname(edtFullname.getText().toString());
            user.setUser_email(edtEmail.getText().toString());
            user.setUser_password(edtPassword.getText().toString());
            if (typeConn.equalsIgnoreCase("retrofit"))
                submitByRetrofit(user);
            else submitByVolley(user);
        }
    }

    private void submitByVolley(User user) {
        Gson gson = new Gson();
        String URL = "http://192.168.74.158/volley/User_Registration.php";
        
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("volley");
        progressDialog.setMessage("Sedang disubmit");
        progressDialog.show();
        
        String userRequest=gson.toJson(user);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request= new JsonObjectRequest(com.android.volley.Request.Method.POST, URL, null, new com.android.volley.Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                if (response!=null) {
                    Request requestFormat = gson.fromJson(response.toString(), Request.class);
                    if (requestFormat.getCode() ==201){
                        Toast.makeText(getApplicationContext(), "Response :"+ requestFormat.getStatus(),
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }else if (requestFormat.getCode() ==406) {
                        Toast.makeText(getApplicationContext(),
                                "Response :"+ requestFormat.getStatus(),
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Response:"+ requestFormat.getStatus(),
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
        
    }

    private void submitByRetrofit(User user) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.retrofit));
        progressDialog.setMessage("Sedang Disubmit");
        progressDialog.show();

        Retrofit.Builder builder= new Retrofit.Builder().baseUrl("http://192.168.74.158/volley").addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        MethodHTTP client= retrofit.create(MethodHTTP.class);
        Call<Request> call = client.sendUser(user);

        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
               progressDialog.dismiss();
               if (response.body()!=null){
                   if (response.body().getCode()==201){
                       Toast.makeText(getApplicationContext(), "response :"+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                       finish();
                   }else if (response.body().getCode()==406){
                       Toast.makeText(getApplicationContext(),"Response : "+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                       edtEmail.requestFocus();
                   }else {
                       Toast.makeText(getApplicationContext(),"Response : "+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                       finish();
                   }
               }
                Log.e(TAG, "Error :"+response.message());
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {

                progressDialog.dismiss();
                Log.e(TAG,"Error2 :"+t.getMessage());

            }
        });
    }

    private class JsonObjectRequest {
        public JsonObjectRequest(int post, String url, Object o, com.android.volley.Response.Listener<JSONObject> jsonObjectListener) {
        }
    }
}