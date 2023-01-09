package com.example.ejercicio_navidad_pmdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ejercicio_navidad_pmdm.adapters.PersonajesAdapter;
import com.example.ejercicio_navidad_pmdm.conexiones.ApiConexiones;
import com.example.ejercicio_navidad_pmdm.conexiones.RetrofictObject;
import com.example.ejercicio_navidad_pmdm.modelos.Personaje;
import com.example.ejercicio_navidad_pmdm.modelos.Respuesta;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //Recycler
    private RecyclerView recyclerView;
    private PersonajesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Personaje> personajes;
    private Respuesta respuesta;

    private ApiConexiones api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.contenedor);
        personajes = new ArrayList<>();
        adapter = new PersonajesAdapter(personajes, R.layout.personaje_view_holder, this);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        api = RetrofictObject.getConnection().create(ApiConexiones.class);

        cargaInicialDatos();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)){
                    if (respuesta != null && respuesta.getNextPage() != null){
                        String url = respuesta.getNextPage();
                        String page = url.split("=")[1];
                        cargaMasPaginas(page);
                    }
                }
            }
        });
    }

    private void cargaMasPaginas(String page) {
        Call<Respuesta> newPage = api.getPage(page);

        newPage.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK){
                    int tam = personajes.size();
                    respuesta = response.body();
                    personajes.addAll(respuesta.getData());
                    adapter.notifyItemRangeInserted(tam, respuesta.getCount());
                    Toast.makeText(MainActivity.this, "Página cargada: "+page, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {

            }
        });
    }

    private void cargaInicialDatos() {
        Call<Respuesta> doGetCarga = api.getPersonajes();

        doGetCarga.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK){
                    respuesta = response.body();
                    personajes.addAll(respuesta.getData());
                    adapter.notifyItemRangeInserted(0, respuesta.getCount());
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {

            }
        });
    }
}