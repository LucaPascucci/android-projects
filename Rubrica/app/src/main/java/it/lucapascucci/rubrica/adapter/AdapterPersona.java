package it.lucapascucci.rubrica.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import it.lucapascucci.rubrica.R;
import it.lucapascucci.rubrica.model.Persona;
import it.lucapascucci.rubrica.utils.ImageUtils;

/**
 * Created by Luca on 26/03/15.
 */
public class AdapterPersona extends ArrayAdapter<Persona>{



    public AdapterPersona(Context context, List<Persona> persone) {
        super(context, 0, persone);
    }

    //convertView è una View che può essere riciclata all'interno dell'Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_persona, parent, false);

            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.profileImage);
            holder.email = (TextView) convertView.findViewById(R.id.mail_persona);
            holder.nomeCognome = (TextView) convertView.findViewById(R.id.nominativo_persona);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Persona persona = getItem(position);

        StringBuilder sb = new StringBuilder();
        if (persona.getNome() != null)
            sb.append(persona.getNome());
        sb.append(" ");
        if (persona.getCognome() != null)
            sb.append(persona.getCognome());
        holder.nomeCognome.setText(sb.toString());

        holder.email.setText(persona.getMail() != null ? persona.getMail() : "");

        String path = persona.getImage();
        if (path != null && !path.isEmpty()) {
            float width = ImageUtils.convertDpToPixel(getContext().getResources().getDimension(R.dimen.img_size), getContext());
            float height = ImageUtils.convertDpToPixel(getContext().getResources().getDimension(R.dimen.img_size), getContext());
            holder.imageView.setImageBitmap(ImageUtils.decodeSampledBitmapFromMemory(path, (int) width, (int) height));
        }

        return convertView;

    }

    private class ViewHolder {

        ImageView imageView;
        TextView nomeCognome;
        TextView email;
    }
}
