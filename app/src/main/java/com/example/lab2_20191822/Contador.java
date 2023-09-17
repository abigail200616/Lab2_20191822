package com.example.lab2_20191822;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lab2_20191822.databinding.ActivityContadorBinding;
import com.example.lab2_20191822.ContadorViewModel;
import com.example.lab2_20191822.ApplicationThreads;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Contador extends AppCompatActivity {

    private ActivityContadorBinding binding;
    private boolean Click = true;

    private int increment = 10;
    int i = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContadorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toast.makeText(Contador.this, "Current Activity: Contador", Toast.LENGTH_SHORT).show();


        ApplicationThreads application = (ApplicationThreads) getApplication();
        ExecutorService executorService = application.executorService;

        ContadorViewModel contadorViewModel = new ViewModelProvider(Contador.this).get(ContadorViewModel.class);
        contadorViewModel.getContador() .observe(this, contador -> {
            binding.textViewContador.setText(String.valueOf(contador));
        });

        binding.buttonContador.setOnClickListener(view -> {
            if ( Click ) {
                executorService.execute(() -> {
                    for (i = 104; i <= 226; i = i+10) {
                        contadorViewModel.getContador().postValue(i);
                        Log.d("msg-test", "i: " + i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if ((226-(i+10) <= 0)) {
                            contadorViewModel.getContador().postValue(226);
                            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                v.vibrate(500);
                            }
                        }
                    }
                });
                Click = false;
            } else {
                increment = (int) (increment + Math.round(0.5*increment));
            }

        });
    }
}
