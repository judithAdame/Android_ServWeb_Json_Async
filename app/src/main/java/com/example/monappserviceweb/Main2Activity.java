package com.example.monappserviceweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main2Activity extends AppCompatActivity {
    TextView outTextViewSId, outTextViewFName, outTextViewLName,
            outTextViewSomme, outTextViewMoyenne, outTextViewNombre,
            outTextViewLimite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        outTextViewSId = (TextView) findViewById(R.id.textViewSId);
        outTextViewFName = (TextView) findViewById(R.id.textViewFName);
        outTextViewLName = (TextView) findViewById(R.id.textViewLName);
        outTextViewSomme = (TextView) findViewById(R.id.textViewSomme);
        outTextViewMoyenne = (TextView) findViewById(R.id.textViewMoyenne);
        outTextViewNombre = (TextView) findViewById(R.id.textViewNombre);
        outTextViewLimite = (TextView) findViewById(R.id.textViewLimite);
        new MyTask().execute();
    }
    private class MyTask extends AsyncTask<Void, Void, Void> {
        double limite, somme, moyenne;
        String fName, lName;
        int sId, nombre;
        @Override
        protected Void doInBackground(Void... params) {
            try{
                Intent monIntent = getIntent();
                limite = ((Intent) monIntent).getDoubleExtra("limite",2.5);
                System.out.println("\n limite: " + limite);

                URL url = new URL("http://ziedzaier.com/wp-content/uploads/2018/09/student2.txt");
                System.out.println("\n Sending 'GET' request to URL: " + url);

                HttpURLConnection client;
                client = (HttpURLConnection) url.openConnection();
                client.setRequestMethod("GET");
                int responseCode = client.getResponseCode();
                System.out.println("\n Response code: " + responseCode);

                InputStreamReader myInput = new InputStreamReader (client.getInputStream());
                BufferedReader in = new BufferedReader(myInput);
                StringBuffer response = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());

                JSONObject obj = new JSONObject(response.toString());

                JSONArray jsonarray =obj.getJSONArray("results");

                sId = obj.getInt("student_id");
                fName= obj.getString("first_name");
                lName= obj.getString("last_name");
                nombre = 0;
                somme = 0;
                moyenne = 0;
                JSONObject jsonobject;

                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);
                    System.out.println("jsonobject.getCourse_id ("+i+") : "+jsonobject.getString("course_id"));
                    System.out.println("jsonobject.getMark ("+i+") : "+jsonobject.getString("mark"));
                    double mark =jsonobject.getDouble("mark");
                    if (mark>=limite) {
                        nombre++;
                        somme +=mark;
                        System.out.println("nombre : "+nombre+" somme : "+somme);
                    }
                }
                moyenne = somme/nombre;
                System.out.println("moyenne : "+moyenne);

            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }catch(JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            outTextViewSId.setText(Integer.toString(sId));
            outTextViewFName.setText(fName);
            outTextViewLName.setText(lName);
            outTextViewSomme.setText(Double.toString(somme));
            outTextViewMoyenne.setText(Double.toString(moyenne));
            outTextViewNombre.setText(Integer.toString(nombre));
            outTextViewLimite.setText("La limite choisit était de : "+Double.toString(limite));
            super.onPostExecute(result);
        }
    }
}
