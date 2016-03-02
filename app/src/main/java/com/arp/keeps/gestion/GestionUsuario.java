package com.arp.keeps.gestion;


import com.arp.keeps.pojo.Usuario;

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

/**
 * Created by Alex on 10/02/2016.
 */
public class GestionUsuario {
    private String urldestino="http://192.168.208.68:8080/Keep/go";

    public boolean isValidUser(Usuario u){
        URL url = null;
        URLConnection conexion=null;
        String login="",pass="",res="";
        try {
            login= URLEncoder.encode(u.getEmail(), "UTF-8");
            pass= URLEncoder.encode(u.getPass(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        String destino=urldestino+"?tabla=usuario&op=login&accion=&" +
                "login="+login+"&pass="+pass+"&origen=android";
        try {
            url = new URL(destino);
            conexion = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            String linea;
            while ((linea = in.readLine()) != null) {
                res+=linea;
            }
            in.close();
            JSONObject obj=new JSONObject(res);
            return obj.getBoolean("r");
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (JSONException e) {
        }
        return false;
    }
}
