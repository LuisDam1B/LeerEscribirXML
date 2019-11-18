package com.example.leerescribirxml;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // declaracion de controles necesarios que hincharemos para su uso en la logica del programa.

    TextView textViewDatos;
    EditText editTextNombre;
    EditText editTextPuntuacion;

    Button btNuevoJugador;
    Button btEscribirXML;
    Button btLeerXML;

    RadioButton rbtMujer;
    LinearLayout linearLayoutContenedorLectura;
    LinearLayout linearLayoutContEditText;

    // array de personas para añadirlas
    ArrayList<Persona> datos = new ArrayList<>();

    // array personas Leidas
    ArrayList<Persona> datosLeidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hinchar controles
        editTextNombre = findViewById(R.id.etNombre);
        editTextPuntuacion = findViewById(R.id.etPuntuacion);
        rbtMujer = findViewById(R.id.buttonMujer);
        btNuevoJugador = findViewById(R.id.btnNuevoJugador);
        btEscribirXML = findViewById(R.id.btnEscribirXML);
        btLeerXML = findViewById(R.id.btnLeerXML);
        textViewDatos = findViewById(R.id.tvDatos);
        linearLayoutContenedorLectura = findViewById(R.id.contenedorTexto);
        linearLayoutContEditText = findViewById(R.id.contendorEditText);

        btNuevoJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Persona persona = new Persona();
                persona.setNombre(editTextNombre.getText().toString());
                persona.setpuntuacion(Integer.parseInt(editTextPuntuacion.getText().toString()));

                if (rbtMujer.isChecked()) persona.setSexo("M");
                persona.setSexo("H");

                datos.add(persona);

                Toast.makeText(MainActivity.this, persona.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btEscribirXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escribirXML();
            }
        });

        btLeerXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lectura = "";
                leerXML();

                for (Persona persona : datosLeidos) {
                    lectura = persona.toString();
                    Toast.makeText(MainActivity.this, persona.toString(), Toast.LENGTH_LONG).show();

                }
                linearLayoutContenedorLectura.setVisibility(View.VISIBLE);
                linearLayoutContEditText.setVisibility(View.INVISIBLE);
                textViewDatos.setText(lectura);
                Toast.makeText(MainActivity.this, lectura, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void escribirXML() {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput("datos.xml", MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        XmlSerializer serializer = Xml.newSerializer();

        try {
            serializer.setOutput(fileOutputStream, "UTF-8");
            serializer.startDocument(null, true);
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            // Empezamos a definir nuestro XML
            //ETIQUETA INICIO
            serializer.startTag(null, "Lista_personas");

            for (Persona persona : datos) {
                serializer.startTag(null, "persona");

                serializer.startTag(null, "nombre");
                serializer.text(persona.getNombre());
                serializer.endTag(null, "nombre");

                serializer.startTag(null, "puntuacion");
                serializer.text(String.valueOf(persona.getpuntuacion()));
                serializer.endTag(null, "puntuacion");

                serializer.startTag(null, "sexo");
                serializer.text(persona.getSexo());
                serializer.endTag(null, "sexo");

                serializer.endTag(null, "persona");
            }

            //CIERRE DE ETIQUETA PRINCIPAL
            serializer.endTag(null, "Lista_personas");
            serializer.endDocument();
            serializer.flush();
            fileOutputStream.close();


        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void leerXML() {
        XmlPullParser parser = Xml.newPullParser();
        FileInputStream fileInputStream = null;


        //abrimos el fichero
        try {
            fileInputStream = openFileInput("datos.xml");

            parser.setInput(fileInputStream, null);

            // variable que usamos para recorrer el documento.
            int evento = parser.getEventType();

            Persona persona = null;
            datosLeidos = null;

            while (evento != XmlPullParser.END_DOCUMENT) {
                switch (evento) {
                    case XmlPullParser.START_DOCUMENT:
                        datosLeidos = new ArrayList<>();
                        break;

                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("persona")) {
                            persona = new Persona();
                        } else if (parser.getName().equals("nombre")) {
                            persona.setNombre(parser.nextText());
                        } else if (parser.getName().equals("puntuacion")) {
                            String punt = parser.nextText();
                            persona.setpuntuacion(Integer.parseInt(punt));
                        } else if (parser.getName().equals("sexo")) {
                            persona.setSexo(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        if (parser.getName().equals("persona")) {
                            datosLeidos.add(persona);
                        }
                        break;

                }
                // avanzamos en la lectura
                evento = parser.next();

            }

            fileInputStream.close();

            Toast.makeText(MainActivity.this, "Lectura Correcta", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();


            Toast.makeText(MainActivity.this, e.getMessage() + "Fichero no encontrado", Toast.LENGTH_LONG).show();

        } catch (XmlPullParserException e) {
            Toast.makeText(MainActivity.this, e.getMessage() + "Fallo lectura", Toast.LENGTH_LONG).show();

            e.printStackTrace();

        } catch (IOException e) {
            Toast.makeText(MainActivity.this, e.getMessage() + "Ffallo fichero", Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
    }
}




























