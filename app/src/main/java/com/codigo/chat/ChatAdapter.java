package com.codigo.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MensajeHolder> {
    Context context;
    int layout;
    List<String> datos;
    LayoutInflater layoutInflater;

    public ChatAdapter(Context context, int layout, List<String> datos) {
        this.context = context;
        this.layout = layout;
        this.datos = datos;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MensajeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(layout, parent,false);
        return new MensajeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeHolder holder, int position) {
        holder.mensaje.setText(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    class MensajeHolder extends RecyclerView.ViewHolder{
        TextView usuario;
        TextView mensaje;

        public MensajeHolder(@NonNull View itemView) {
            super(itemView);
            usuario = itemView.findViewById(R.id.txtUsuario);
            mensaje = itemView.findViewById(R.id.txtItemMensaje);
        }
    }
}
