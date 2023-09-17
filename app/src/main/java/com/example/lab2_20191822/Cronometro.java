package com.example.lab2_20191822;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.lab2_20191822.databinding.ActivityCronometroBinding;

public class Cronometro extends AppCompatActivity {
    private ActivityCronometroBinding binding;
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCronometroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toast.makeText(Cronometro.this, "Current Activity: Cronometro", Toast.LENGTH_SHORT).show();

        chronometer = binding.cronometro;
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());

    }

    public void iniciar(View v) {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pausar(View v) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }
    public void detener(View v) {
        chronometer.stop();
        pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
        running = false;
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
    public void limpiar(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
}
