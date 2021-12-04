package it.unibo.rubrica.persona;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import it.unibo.rubrica.R;
import it.unibo.rubrica.utils.ImageUtils;

/**
 * Created by Andrea on 24/03/2015.
 */
public class PersonaAdapter extends ArrayAdapter<Persona> {

    private class ViewHolder {

        ImageView imageView;
        TextView nomeCognome;
        TextView email;
    }

    public PersonaAdapter(Context context, List<Persona> persone) {
        super(context, 0, persone);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_persona, parent, false);

            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.persona_image);
            holder.email = (TextView) convertView.findViewById(R.id.persona_email);
            holder.nomeCognome = (TextView) convertView.findViewById(R.id.persona_nome_cognome);

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

        holder.email.setText(persona.getEmail() != null ? persona.getEmail() : "");

        String path = persona.getImage();
        if (path != null && !path.isEmpty()) {
            float width = ImageUtils.convertDpToPixel(getContext().getResources().getDimension(R.dimen.img_size), getContext());
            float height = ImageUtils.convertDpToPixel(getContext().getResources().getDimension(R.dimen.img_size), getContext());
            holder.imageView.setImageBitmap(ImageUtils.decodeSampledBitmapFromMemory(path, (int) width, (int) height));
        }

        return convertView;
    }
}