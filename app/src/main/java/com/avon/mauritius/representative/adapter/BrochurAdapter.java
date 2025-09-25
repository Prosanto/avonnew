package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.FilterItemCallback;
import com.avon.mauritius.representative.model.BrochuresList;
import com.avon.mauritius.representative.utils.PersistentUser;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrochurAdapter extends RecyclerView.Adapter<BrochurAdapter.viewHolder> {

    Context context;
    ArrayList<BrochuresList> arrayList;
    private FilterItemCallback lFilterItemCallback;

    public BrochurAdapter(Context context, ArrayList<BrochuresList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void addClickListiner(FilterItemCallback lFilterItemCallback) {
        this.lFilterItemCallback = lFilterItemCallback;
    }

    public void addAllItem(ArrayList<BrochuresList> BrochuresLists) {
        this.arrayList.clear();
        this.arrayList.addAll(BrochuresLists);
        notifyDataSetChanged();
    }

    public void addItem(BrochuresList BrochuresLists) {
        this.arrayList.add(BrochuresLists);
        notifyDataSetChanged();
    }

    public void deleteItem() {
        this.arrayList.clear();
        notifyDataSetChanged();
    }

    public BrochuresList getSuggestItemList(int postion) {
        return arrayList.get(postion);
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_brochure, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        viewHolder.image_for_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lFilterItemCallback.ClickFilterItemCallback(0, position);
            }
        });
        if(PersistentUser.getLanguage(context).equalsIgnoreCase("en"))
            viewHolder.txt_ordernumber.setText(arrayList.get(position).getTitle());
        else
            viewHolder.txt_ordernumber.setText(arrayList.get(position).getTitle_french());

        Glide.with(context)
                .load(R.raw.download_icon)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(viewHolder.image_for_order);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_ordernumber)
        TextView txt_ordernumber;
        @BindView(R.id.image_for_order)
        ImageView image_for_order;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public void removeAt(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }

}