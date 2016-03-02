package com.arp.keeps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.arp.keeps.gestion.GestionKeep;
import com.arp.keeps.pojo.Keep;
import com.arp.keeps.pojo.Usuario;
import com.arp.keeps.sql.GestorKeep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Keep> listanotas;
    private Adaptador ad;
    private ListView lv;
    private GestorKeep gk;
    private GestionKeep gsk;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView)findViewById(R.id.listView);
        init();
        ad=new Adaptador(this,R.layout.lista_detalle,listanotas);
        lv.setAdapter(ad);
        registerForContextMenu(lv);
    }

    private void init(){
        gk=new GestorKeep(this);
        gsk=new GestionKeep();
        gk.open();
        user=getIntent().getParcelableExtra("usuario");
        listanotas=new ArrayList<>();
        listanotas= gsk.getKeeps(user);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gk.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gk.close();
    }

    /**********************Menus***************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.añadir) {
            alertDialog();
            ad.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int posicion=info.position;
        Keep k=listanotas.get(posicion);
        switch (item.getItemId()) {
            case R.id.modificar:
                modificaAlert(k);
                ad.notifyDataSetChanged();
                return true;
            case R.id.eliminar:
                gsk.deleteKeep(user,k);
                gk.delete(k);
                listanotas.remove(posicion);
                ad.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /*******************Alert para añadir y modificar notas**************************/

    private void alertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.modifica, null);
        final EditText et1 = (EditText) vista.findViewById(R.id.editText);
        alert.setView(vista);
        alert.setPositiveButton("Añadir",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Keep k=new Keep();
                        k.setContenido(et1.getText().toString());
                        k.setEstado(true);
                        long id=gk.insert(k);
                        k.setId(id);
                        gsk.insertKeep(user,k);
                        listanotas.add(k);
                    }
                });
        alert.setNegativeButton("Cancelar", null);
        alert.show();

    }

    private void modificaAlert(final Keep k){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.modifica, null);
        final EditText et1 = (EditText) vista.findViewById(R.id.editText);
        et1.setText(k.getContenido());
        alert.setView(vista);
        alert.setPositiveButton("Modificar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        k.setContenido(et1.getText().toString());
                        k.setEstado(true);
                        gk.updateContenido(k);
                        gsk.updateKeep(user,k);
                    }
                });
        alert.setNegativeButton("Cancelar", null);
        alert.show();
    }

}
