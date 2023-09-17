package com.example.lab2_20191822;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import com.example.lab2_20191822.databinding.ActivityMenuBinding;
import com.example.lab2_20191822.dto.Result;
import com.example.lab2_20191822.dto.Profile;
import com.example.lab2_20191822.services.TypicodeServices;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {
    private ActivityMenuBinding binding;
    TypicodeServices typicodeService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toast.makeText(this, "Current Activity: Menu", Toast.LENGTH_SHORT).show();

        typicodeService = new Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TypicodeServices.class);
        //  Obtener los extras del Intent
        Intent intent = getIntent();
        String nombreValue = intent.getStringExtra("nombre");
        String apellidoValue = intent.getStringExtra("apellido");
        String usernameValue = intent.getStringExtra("username");
        String imagen = intent.getStringExtra("imagen");
        // Asignar los valores a las vistas en MenuActivity
        TextView username = findViewById(R.id.username);
        TextView nombrea = findViewById(R.id.nombreApellido);
        ImageView imageView = findViewById(R.id.imagen);

        // Asignar los valores
        username.setText(usernameValue);
        nombrea.setText(nombreValue + " " + apellidoValue);
        Glide.with(this).load(imagen).into(imageView);
        fetchWebServiceData();

    }

    public void fetchWebServiceData(){
        //  Obtener los extras del Intent
        Intent intent = getIntent();
        String nombreValue = intent.getStringExtra("nombre");
        String apellidoValue = intent.getStringExtra("apellido");
        String usernameValue = intent.getStringExtra("username");
        String imagen = intent.getStringExtra("imagen");
        // Asignar los valores a las vistas en MenuActivity
        TextView username = findViewById(R.id.username);
        TextView nombrea = findViewById(R.id.nombreApellido);
        ImageView imageView = findViewById(R.id.imagen);
        // Asignar los valores
        username.setText(usernameValue);
        nombrea.setText(nombreValue + " " + apellidoValue);
        ImageView imageview = findViewById(R.id.imagen);
        Glide.with(this).load(imagen).into(imageView);


    }

    public void cronometro(View view){
        Intent intent = new Intent(this, Cronometro.class);
        startActivity(intent);
    }
    public void contador(View view){
        Intent intent = new Intent(this, Contador.class);
        startActivity(intent);
    }
}
