package com.upc.group.parkingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.upc.group.parkingapp.R;
import com.upc.group.parkingapp.modelos.Estacionamiento;

import java.util.ArrayList;

public class EstacionamientosAdapter extends ArrayAdapter<Estacionamiento> {

    public EstacionamientosAdapter(@NonNull Context context, ArrayList<Estacionamiento> estacionamientos) {
        super(context, 0, estacionamientos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Estacionamiento estacionamiento = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_estacionamiento, parent, false);
        }
        // Lookup view for data population
        TextView tvCodigo = (TextView) convertView.findViewById(R.id.tvCodigo);
        TextView tvTipo = (TextView) convertView.findViewById(R.id.tvTipo);
        TextView tvNivel = (TextView) convertView.findViewById(R.id.tvNivel);
        TextView tvEstado = (TextView) convertView.findViewById(R.id.tvEstado);
        // Populate the data into the template view using the data object
        tvCodigo.setText(estacionamiento.getCodEst());
        tvTipo.setText(estacionamiento.getTipo());
        tvNivel.setText(estacionamiento.getNivel());
        tvEstado.setText(estacionamiento.getEstado());
        // Return the completed view to render on screen
        return convertView;
    }
}
