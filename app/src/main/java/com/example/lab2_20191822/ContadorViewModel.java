package com.example.lab2_20191822;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContadorViewModel extends ViewModel {

    private final MutableLiveData<Integer> contador = new MutableLiveData<>();

    public MutableLiveData<Integer> getContador() {
        return contador;
    }
}