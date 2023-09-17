package com.example.lab2_20191822;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.lab2_20191822.databinding.ActivitySignupBinding;
import com.example.lab2_20191822.dto.Profile;
import com.example.lab2_20191822.dto.Result;
import com.example.lab2_20191822.services.TypicodeServices;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    TypicodeServices typicodeService;
    private String nombreValue;
    private String apellidoValue;
    private String usernameValue;
    private String imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        typicodeService = new Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TypicodeServices.class);

        fetchWebServiceData();

        CheckBox checkBoxValidacion = findViewById(R.id.checkBox);
        Button btnContinuar = findViewById(R.id.button3);
        Toast.makeText(this, "Current activity: SignUp", Toast.LENGTH_SHORT).show();

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxValidacion.isChecked()) {
                    obtenerValores(v);
                    // Obtener los valores de nombre y apellido
                    //String nombreValue = binding.nombre.getText().toString();
                    //String apellidoValue = binding.apellido.getText().toString();
                    //String usernameValue = binding.username1.getText().toString();
                    //String nombreApValue = binding.nombrea.getText().toString();
                    // Crear un Intent
                    //Intent intent = new Intent(SignupActivity.this, MenuActivity.class);

                    // Poner los valores como extras
                    //intent.putExtra("nombre", nombreValue);
                    //intent.putExtra("apellido", apellidoValue);
                    //intent.putExtra("username", usernameValue);
                    //intent.putExtra("nombreAp", nombreApValue);
                    // Iniciar la nueva actividad
                    //startActivity(intent);
                } else {
                    Toast.makeText(SignupActivity.this, "Accept the terms and conditions", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }

    private boolean conexionInternet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void obtenerValores(View view) {


        Intent intent = new Intent(SignupActivity.this, MenuActivity.class);
        intent.putExtra("nombre", nombreValue);
        intent.putExtra("apellido", apellidoValue);
        intent.putExtra("username", usernameValue);
        intent.putExtra("imagen", imagen);
        startActivity(intent);
    }
    public void fetchWebServiceData(){
        if(conexionInternet()){
            typicodeService.getResult().enqueue(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {
                    if(response.isSuccessful()){
                        Profile profile = response.body();
                        List<Result> results = profile.getResults();
                        if (!results.isEmpty()) {
                            Result firstResult = results.get(0);

                            nombreValue = firstResult.getName().getFirst();
                            apellidoValue = firstResult.getName().getLast();
                            usernameValue = firstResult.getLogin().getUsername();
                            imagen = firstResult.getPicture().getLarge();

                            binding.textInputNombre.getEditText().setText(nombreValue);
                            binding.textInputApellido.getEditText().setText(apellidoValue);
                            binding.textInputCorreo.getEditText().setText(firstResult.getEmail());
                            binding.textInputContra.getEditText().setText(firstResult.getLogin().getPassword());

                            binding.textInputNombre.getEditText().setEnabled(false);
                            binding.textInputApellido.getEditText().setEnabled(false);
                            binding.textInputCorreo.getEditText().setEnabled(false);
                            binding.textInputContra.getEditText().setEnabled(false);

                        }
                    } else {
                        Log.d("msg-test", "Error in webservice");
                    }
                }

                @Override
                public void onFailure(Call<Profile> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

}
