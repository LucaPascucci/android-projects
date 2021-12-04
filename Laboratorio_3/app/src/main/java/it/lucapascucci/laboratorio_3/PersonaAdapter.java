package it.lucapascucci.laboratorio_3;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PersonaAdapter extends BaseAdapter {

    private Context context;
    private List<Persona> persone;

    public PersonaAdapter(Context context, List<Persona> persone){
        this.context = context;
        this.persone = persone;
    }

    @Override
    public int getCount() {
        return persone != null? persone.size() : 0;
    }

    @Override
    public Persona getItem(int i) {
        return persone != null && i < persone.size()? persone.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //convertView è una View che può essere riciclata all'interno dell'Adapter
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_persona, parent, false);
        }

        TextView nome = (TextView) convertView.findViewById(R.id.nome_persona);
        nome.setText(getItem(i).getNome());

        return convertView;
    }
}
