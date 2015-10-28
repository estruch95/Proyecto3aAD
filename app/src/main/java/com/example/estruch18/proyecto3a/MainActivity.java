package com.example.estruch18.proyecto3a;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity {
    //Atributos de la clase
    private EditText texto;
    private TextView textoImpreso;
    private Button btnAddFichero;
    private Button btnMostrar;
    //Nombre del fichero
    String FILE_NAME = "Fichero.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaración de atributos de la clase
        texto = (EditText)findViewById(R.id.texto);
        textoImpreso = (TextView)findViewById(R.id.t_mostradoInformacion);
        btnAddFichero = (Button)findViewById(R.id.btn_addFichero);
        btnMostrar = (Button)findViewById(R.id.btn_mostrar);

        //Ejecución de métodos
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Listener del botón addFichero
    /* La finalidad de este método es abrir un fichero y realizar una escritura en este.
    * Se debe escribir el texto capturado en el EditText superior, de forma que se irá concatenando dicho texto a
    * medida que lo añadimos. */
    public void accionBtnAddFichero(View v){
        btnAddFichero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Realiza la escritura capurando el texto del EditText en un archivo de salida.
                FileOutputStream fos = null;

                //Encapsulado try/catch para captura de errores
                try {
                    //Declaración del Stream de escritura PD: MODE_APPEND (Concatenado de texto)
                    fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);
                    //Guardamos el texto capturado del EditText
                    String cadena = texto.getText().toString();
                    DataOutputStream dos = new DataOutputStream(fos);
                    //Escribimos mediante el Stream el texto capturado en el fichero
                    dos.writeBytes(cadena);
                    //Cerrado del Stream de escritura
                    fos.close();
                    //Refrescado de campo (EN BLANCO)
                    texto.setText("");
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //Mensaje de error informativo
                    System.err.println("No se encuentra el fichero especificado.");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    //Impreso informativo
                    Toast.makeText(getApplicationContext(), "El fichero se ha guardado con éxito.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Listener del botón mostrar
    /* Este método se encarga de abrir un fichero y realizar una lectura de este.
    * Una vez obtenido el texto que lo forma, lo muestra a través de un TextView */
    public void accionBtnMostrar(View v){
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creación de un Stream de lectura
                FileInputStream fis = null;
                //""
                try {
                    //Declaración del Stream de lectura (recibe por parámetro el nombre del fichero)
                    fis = openFileInput(FILE_NAME);
                    DataInputStream dis = new DataInputStream(fis);
                    //Buffer
                    byte[] bufer = new byte[1000];
                    //Lectura del buffer
                    dis.read(bufer);
                    //Impreso en el TextView mencionado anteriormente
                    textoImpreso.setText(new String(bufer));
                    //Cerrado del Stream
                    fis.close();
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //Mensaje de error informativo
                    System.err.println("No se encuentra el fichero especificado.");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                //Impreso informativo
                Toast.makeText(getApplicationContext(), "Archivo mostrado correctamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
