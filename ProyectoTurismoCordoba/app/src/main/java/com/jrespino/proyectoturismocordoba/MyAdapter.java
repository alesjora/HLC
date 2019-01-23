package com.jrespino.proyectoturismocordoba;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Lugar> myArrayList;
    private Context context;
    private int layout;
    private OnItemClickListener onItemClickListener;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewLugar;
        public TextView textViewNombre;
        public CardView cardViewLugar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewLugar = itemView.findViewById(R.id.imageViewLugar);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            cardViewLugar = itemView.findViewById(R.id.cardViewLugar);
        }

        public void bind(final Lugar lugar, final OnItemClickListener listener ){
            Picasso.get().load(lugar.getImagen()).fit().into(imageViewLugar);
            textViewNombre.setText(lugar.getNombre());
            cardViewLugar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(lugar,getAdapterPosition());
                }
            });
        }
    }

    public MyAdapter(ArrayList<Lugar> myArrayList, Context context, int layout, OnItemClickListener listener) {
        this.myArrayList = myArrayList;
        this.context = context;
        this.layout = layout;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(layout,viewGroup,false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(myArrayList.get(i),onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return myArrayList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Lugar lugar, int position);
    }
}
