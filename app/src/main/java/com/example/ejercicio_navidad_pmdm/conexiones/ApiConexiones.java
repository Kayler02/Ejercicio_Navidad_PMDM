package com.example.ejercicio_navidad_pmdm.conexiones;

import com.example.ejercicio_navidad_pmdm.modelos.Personaje;
import com.example.ejercicio_navidad_pmdm.modelos.Respuesta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiConexiones {
    //Conexion para la descarga de datos inicial
    @GET("/characters")
    Call<Respuesta> getPersonajes();

    //Conexion para la descarga de una pagina en concreto
    @GET("/characters?")
    Call<Respuesta> getPage(@Query("page") String page);

    //Conexion para la descarga de un personaje
    @GET("/characters/{idPersonaje}")
    Call<Personaje> getPersonaje(@Path("idPersonaje") String id);
}
