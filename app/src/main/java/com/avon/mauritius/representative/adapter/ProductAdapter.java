package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.avon.mauritius.representative.R;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> items;

    public ProductAdapter(Context context, List<String> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_product, parent, false);
        }

        TextView product_reference = convertView.findViewById(R.id.product_reference);

        String item = items.get(position);
        product_reference.setText(item);


        return convertView;
    }

}