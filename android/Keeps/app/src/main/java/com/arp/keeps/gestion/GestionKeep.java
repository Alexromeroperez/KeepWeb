package com.arp.keeps.gestion;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.arp.keeps.pojo.Keep;
import com.arp.keeps.pojo.Usuario;
import com.arp.keeps.sql.GestorKeep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 10/02/2016.
 */
public class GestionKeep {
    private String urldestino = "http://192.168.208.68:8080/Keep/go";
    private List<Keep> keeps;
    private GestorKeep gk;
    private HiloGestion hg;

    public List<Keep> getKeeps(Usuario u) {
        keeps = new ArrayList<>();
        String login = "";
        try {
            login = URLEncoder.encode(u.getEmail(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        String destino = urldestino + "?tabla=keep&op=read&accion=&" +
                "login=" + login + "&origen=android";
        HiloConexion h = new HiloConexion();
        h.execute(destino);
        return keeps;
    }

    public void insertKeep(Usuario u, Keep k) {
        String login = "";
        try {
            login = URLEncoder.encode(u.getEmail(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        String destino = urldestino + "?tabla=keep&op=create&accion=&" +
                "login=" + login + "&origen=android&idAndroid=" + k.getId()+"&contenido="+k.getContenido();
        hg = new HiloGestion(destino);
        hg.execute();
    }

    public void updateKeep(Usuario u, Keep k) {
        String login = "";
        try {
            login = URLEncoder.encode(u.getEmail(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        String destino = urldestino + "?tabla=keep&op=update&accion=&" +
                "login=" + login + "&origen=android&idAndroid=" + k.getId()+"&contenido="+k.getContenido();
        hg = new HiloGestion(destino);
        hg.execute();
    }

    public void deleteKeep(Usuario u, Keep k) {
        String login = "";
        try {
            login = URLEncoder.encode(u.getEmail(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        String destino = urldestino + "?tabla=keep&op=delete&accion=&" +
                "login=" + login + "&origen=android&idAndroid=" + k.getId();
        hg = new HiloGestion(destino);
        hg.execute();

    }

    class HiloConexion extends AsyncTask<String, Void, List<Keep>> {
        URL url = null;
        URLConnection conexion = null;
        String res = "";

        @Override
        protected List<Keep> doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                conexion = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    res += linea;
                }
                in.close();
                JSONObject obj = new JSONObject(res);
                JSONArray array = (JSONArray) obj.get("r");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = (JSONObject) array.get(i);
                    Keep keep = new Keep(o.getInt("id"), o.getString("contenido"), true);
                    keeps.add(keep);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class HiloGestion extends AsyncTask<String, Void, List<Keep>> {

        private URL url = null;
        private URLConnection conexion = null;
        private String ruta;
        public HiloGestion(String ruta) {
            this.ruta = ruta;
        }

        @Override
        protected List<Keep> doInBackground(String... params) {

            try {
                url = new URL(ruta);
                conexion = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
