package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.avon.mauritius.representative.R;

import java.util.List;

public class SortAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> items;

    public SortAdapter(@NonNull Context context, List<String> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_sort_item_checkbox, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.item_text);
        CheckBox checkBox = convertView.findViewById(R.id.item_checkbox);

        textView.setText(items.get(position));
        checkBox.setChecked(false); // default unchecked

        // Optional: handle checkbox clicks
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // You can store checked state here
        });

        return convertView;
    }

}