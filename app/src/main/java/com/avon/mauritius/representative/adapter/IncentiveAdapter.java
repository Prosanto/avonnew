package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.avon.mauritius.representative.R;

import java.util.List;

public class IncentiveAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> items;

    public IncentiveAdapter(Context context, List<String> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_incentive, parent, false);
        }

        TextView itemText = convertView.findViewById(R.id.item_text);
        ImageView downloadBtn = convertView.findViewById(R.id.download_btn);

        String item = items.get(position);
        itemText.setText(item);

        // Handle download button click
        downloadBtn.setOnClickListener(v ->
                Toast.makeText(context, "Downloading " + item, Toast.LENGTH_SHORT).show()
        );

        return convertView;
    }

}