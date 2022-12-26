package com.example.lpamvil.sprint0laura;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorSensor extends RecyclerView.Adapter<AdaptadorSensor.AdaptadorSensorHolder> implements View.OnClickListener{

    private ArrayList<Sensor> listasensores;
    private View.OnClickListener listener;
    public AdaptadorSensor(ArrayList<Sensor> listasensores){
        this.listasensores = listasensores;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        if (listener!=null)
        {
            listener.onClick(view);
        }
    }

    public class AdaptadorSensorHolder extends RecyclerView.ViewHolder{
        private TextView tipotxt;
        private TextView nombretxt;

        public AdaptadorSensorHolder(final View view){
            super(view);
            tipotxt = view.findViewById(R.id.idtiposensor);
            nombretxt = view.findViewById(R.id.idnombresensor);

        }
    }

    @NonNull
    @Override
    public AdaptadorSensor.AdaptadorSensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sensorView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsensor,parent,false);

        sensorView.setOnClickListener(this);

        return new AdaptadorSensorHolder(sensorView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorSensor.AdaptadorSensorHolder holder, int position) {
        String tipo = listasensores.get(position).getTipo();
        holder.tipotxt.setText(tipo);
        String nombre = listasensores.get(position).getNombre();
        holder.nombretxt.setText(nombre);


    }

    @Override
    public int getItemCount() {
        return listasensores.size();
    }
}
