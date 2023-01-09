package com.example.ejercicio_navidad_pmdm.conexiones;

import com.example.ejercicio_navidad_pmdm.configuraciones.Constantes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofictObject {

    public static Retrofit getConnection(){
        return new Retrofit.Builder()
                .baseUrl(Constantes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
