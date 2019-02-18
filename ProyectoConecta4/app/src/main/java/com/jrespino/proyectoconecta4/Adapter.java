package com.jrespino.proyectoconecta4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    private ArrayList<Partida> myArrayList;
    private int layout;
    private Context context;

    public Adapter(ArrayList<Partida> myArrayList, int layout, Context context) {
        this.myArrayList = myArrayList;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return myArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return myArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout,null);
            holder = new ViewHolder();
            holder.textViewDescripcion = convertView.findViewById(R.id.textViewDescripcion);
            holder.textViewUsuarioCreador = convertView.findViewById(R.id.textViewUsuarioCreador);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Partida partida = (Partida) myArrayList.get(i);

        holder.textViewDescripcion.setText(partida.getDescripcion());
        holder.textViewUsuarioCreador.setText(partida.getNombreCreador());
        return convertView;
    }
    static class ViewHolder {
        public TextView textViewDescripcion;
        public TextView textViewUsuarioCreador;

    }
}
