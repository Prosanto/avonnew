package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.avon.mauritius.representative.R;

import java.util.List;

public class MysellAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> items;

    public MysellAdapter(Context context, List<String> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_mysell, parent, false);
        }

        TextView itemText = convertView.findViewById(R.id.item_text);
        ImageView imageView=convertView.findViewById(R.id.circle_image);

        String item = items.get(position);
        itemText.setText(item);
        if(item.equalsIgnoreCase("The Campaign")){
            imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.solid_circle_purple));

        }else if (item.equalsIgnoreCase("Quarter")){
            imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.solid_circle_yellow));

        }else if (item.equalsIgnoreCase("Current Year")){
            imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.solid_circle_gray_yellow));

        }else {
            imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.solid_circle_red));

        }


        return convertView;
    }

}