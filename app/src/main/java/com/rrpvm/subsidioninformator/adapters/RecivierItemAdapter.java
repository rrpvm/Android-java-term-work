package com.rrpvm.subsidioninformator.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rrpvm.subsidioninformator.R;
import com.rrpvm.subsidioninformator.objects.SubsidingRecivier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RecivierItemAdapter extends ArrayAdapter<SubsidingRecivier> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<SubsidingRecivier> data;

    public RecivierItemAdapter(Context ctx, int resource, ArrayList<SubsidingRecivier> reciviers) {
        super(ctx, resource, reciviers);
        this.data = reciviers;
        this.layout = resource;
        this.inflater = LayoutInflater.from(ctx);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        ImageView recivierIconView = (ImageView) convertView.findViewById(R.id.recivier_logotype);
        TextView nameView = (TextView) convertView.findViewById(R.id.receiving_pib);
        TextView regionView = (TextView) convertView.findViewById(R.id.receiving_region);
        TextView positionView = (TextView) convertView.findViewById(R.id.recivier_position);
        TextView birthdateView = (TextView) convertView.findViewById(R.id.recivier_birthdate);
        SubsidingRecivier currentReciever = data.get(position);
        String tmpName = currentReciever.getSurname() + " " + currentReciever.getName() + " " + currentReciever.getPatronymic();
        nameView.setText(tmpName);
        regionView.setText(new String("region: ").concat(currentReciever.getRegion()));
        positionView.setText(new String("city: ").concat(currentReciever.getCity()));
        birthdateView.setText(new String("birthdate: ").concat( dateFormat.format(currentReciever.getBirthdate()).toString()));
        int imgId = recivierIconView.getContext().getResources().getIdentifier(currentReciever.getImage(), "drawable", recivierIconView.getContext().getPackageName());
        recivierIconView.setImageResource(imgId);
        return convertView;
    }
}
