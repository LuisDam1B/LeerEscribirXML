package com.example.leerescribirxml;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // declaracion de controles necesarios que hincharemos para su uso en la logica del programa.

    EditText editTextNombre;
    EditText editTextPuntuacion;

    Button btNuevoJugador;
    Button btEscribirXML;
    Button btLeerXML;

    RadioButton rbtMujer;

    // array de personas
    ArrayList<Persona> datos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hinchar controles
        editTextNombre = findViewById(R.id.etNombre);
        editTextPuntuacion = findViewById(R.id.etPuntuacion);
        rbtMujer = findViewById(R.id.buttonMujer);
        btNuevoJugador = findViewById(R.id.btnNuevoJugador);

        btNuevoJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Persona persona = new Persona();
                persona.setNombre(editTextNombre.getText().toString());
                persona.setpuntuacion(Integer.parseInt(editTextPuntuacion.getText().toString()));

                if (rbtMujer.isChecked()) persona.setSexo("M");
                    persona.setSexo("H");

                datos.add(persona);

                Toast.makeText(MainActivity.this,persona.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        btEscribirXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escribirXML();
            }
        });

    }

    private  void escribirXML(){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput("datos.xml" ,MODE_PRIVATE);
        } catch (FileNotFoundException e){
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        XmlSerializer serializer = Xml.newSerializer();

        try {
            serializer.setOutput(fileOutputStream,"UTF-8");
            serializer.startDocument(null,true);
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output",true);
            // Empezamos a definir nuestro XML
            //ETIQUETA INICIO
            serializer.startTag(null,"Lista_personas");

            for (Persona persona : datos) {
                serializer.startTag(null,"persona");

                serializer.startTag(null,"nombre");
                serializer.text(persona.getNombre());
                serializer.endTag(null,"nombre");

                serializer.startTag(null,"puntuacion");
                serializer.text(String.valueOf(persona.getpuntuacion()));
                serializer.endTag(null,"puntuacion");

                serializer.startTag(null,"sexo");
                serializer.text(persona.getSexo());
                serializer.endTag(null,"sexo");

                serializer.endTag(null,"persona");
            }

            //CIERRE DE ETIQUETA PRINCIPAL
            serializer.endTag(null,"Lista_personas");
            serializer.endDocument();
            serializer.flush();
            fileOutputStream.close();



        }catch (Exception e){
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }


}


























