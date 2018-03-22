package com.example.henry.a3_1_consumo_de_servicion_web;

import android.content.AsyncTaskLoader;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Henry on 22/03/2018.
 */

class GetJson extends AsyncTask<Void,Void,Void> {

    String data = "";
    String dataParsed = "";
    String singleParsed = "";



    @Override
    protected Void doInBackground(Void... voids) {
        try{
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Tepic,mx&APPID=0906362826d2cfea265ed029381a7e31");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while (line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("weather"));
            JSONObject wea;
            String description = "";
            for (int i = 0; i<jsonArray.length();i++) {
                wea = (JSONObject) jsonArray.get(i);
                description = wea.getString("description");
            }
            JSONObject main = new JSONObject(jsonObject.getString("main"));
            JSONObject sys = new JSONObject(jsonObject.getString("sys"));
                singleParsed = "Pais: "+ sys.getString("country")+ "\n"+
                        "Lugar: "+ jsonObject.get("name")+ "\n"+
                        "Tiempo: "+ description+ "\n"+
                        "Temperatura: "+ main.getString("temp")+ "F° \n"+
                        "Humedad: "+ main.getString("temp")+ " % \n "+
                        "Nivel del mar: "+ main.getString("sea_level")+ " m.s.n.m\n"+
                        "Presion atm: "+ main.getString("pressure")+" milibares\n";
                dataParsed = dataParsed + singleParsed +"\n";
            //}

        } catch (MalformedURLException e ){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }


        return null;
    }

    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);

        MainActivity.data.setText(this.dataParsed);
    }
}
