package com.arp.keeps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arp.keeps.pojo.Keep;

import java.util.List;

/**
 * Created by Alex on 10/02/2016.
 */
public class Adaptador  extends ArrayAdapter<Keep> {

    private int recurso;
    private List<Keep> lista;
    private LayoutInflater i;
    private Context contexto;

    public Adaptador(Context context, int resource, List <Keep> objects) {
        super(context, resource, objects);
        this.contexto=context;
        this.recurso=resource;
        this.lista=objects;
        i= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if(vh==null){
            vh=new ViewHolder();
            convertView=i.inflate(recurso,null);
            vh.tvTexto=(TextView)convertView.findViewById(R.id.tvContenido);
            vh.tv2=(TextView)convertView.findViewById(R.id.tvEstado);
            convertView.setTag(vh);
        }else {
            vh=(ViewHolder)convertView.getTag();
        }
        vh.tvTexto.setText(lista.get(position).getContenido());
        vh.tv2.setText(lista.get(position).isEstado()+"");
        return convertView;
    }

    static class ViewHolder{
        private TextView tvTexto,tv2;
    }
}