package com.example.ejercicio_navidad_pmdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ejercicio_navidad_pmdm.conexiones.ApiConexiones;
import com.example.ejercicio_navidad_pmdm.conexiones.RetrofictObject;
import com.example.ejercicio_navidad_pmdm.modelos.Personaje;
import com.squareup.picasso.Picasso;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerPersonajeActivity extends AppCompatActivity {

    private ImageView imgPersonaje;
    private TextView lblNombre;
    private TextView lblFilms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_personaje);

        imgPersonaje = findViewById(R.id.imgPersonajeVer);
        lblNombre = findViewById(R.id.lblNombreVer);
        lblFilms = findViewById(R.id.lblFilmsVer);

        if (getIntent().getExtras() != null && getIntent().getExtras().getString("ID") != null){
            ApiConexiones api = RetrofictObject.getConnection().create(ApiConexiones.class);
            Call<Personaje> personajeCall = api.getPersonaje(getIntent().getExtras().getString("ID"));

            personajeCall.enqueue(new Callback<Personaje>() {
                @Override
                public void onResponse(Call<Personaje> call, Response<Personaje> response) {
                    if (response.code() == HttpsURLConnection.HTTP_OK){
                        Personaje personaje = response.body();
                        lblNombre.setText(personaje.getName());
                        lblFilms.setText("");
                        for (String film:personaje.getFilms()) {
                            lblFilms.setText(lblFilms.getText()+ "\n"+film);
                        }
                        Picasso.get()
                                .load(personaje.getImageUrl())
                                .into(imgPersonaje);
                    }
                }

                @Override
                public void onFailure(Call<Personaje> call, Throwable t) {

                }
            });
        }

    }
}