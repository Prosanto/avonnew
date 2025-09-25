package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.FilterItemCallback;
import com.avon.mauritius.representative.model.BlogItemList;
import com.avon.mauritius.representative.utils.AllUrls;
import com.avon.mauritius.representative.utils.ConstantFunctions;
import com.avon.mauritius.representative.utils.Logger;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.ArrayList;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.viewHolder> {

    Context context;
    ArrayList<BlogItemList> arrayList;
    private FilterItemCallback lFilterItemCallback;

    public BlogAdapter(Context context, ArrayList<BlogItemList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        String storeLang = PersistentUser.getLanguage(context);
        Locale locale = new Locale(storeLang);
        Configuration config = new Configuration();
        config.locale = locale;
        this.context.getResources().updateConfiguration(config, this.context.getResources().getDisplayMetrics());
    }

    public void addClickListiner(FilterItemCallback lFilterItemCallback) {
        this.lFilterItemCallback = lFilterItemCallback;
    }

    public void addAllItem(ArrayList<BlogItemList> blogItemLists) {
        this.arrayList.clear();
        this.arrayList.addAll(blogItemLists);
        notifyDataSetChanged();
    }

    public void addItem(BlogItemList blogItemLists) {
        this.arrayList.add(blogItemLists);
        notifyDataSetChanged();
    }

    public void deleteItem() {
        this.arrayList.clear();
        notifyDataSetChanged();
    }

    public BlogItemList getSuggestItemList(int postion) {
        return arrayList.get(postion);
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_blog, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {

        String blog_pic = Logger.EmptyString(arrayList.get(position).getImage());
        if (!blog_pic.equalsIgnoreCase("")) {
            blog_pic = AllUrls.IMAGEURL_BLOG + blog_pic;
            ConstantFunctions.loadImageNomaForProfilel(blog_pic, viewHolder.blog_image);
        }
        viewHolder.txt_readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lFilterItemCallback.ClickFilterItemCallback(0, position);
            }
        });
        if (PersistentUser.getLanguage(context).equalsIgnoreCase("en")) {
            viewHolder.txt_blogtitle.setText(arrayList.get(position).getTitle());
            viewHolder.txt_blogdesc.setText(arrayList.get(position).getShort_descrition());
        } else {
            viewHolder.txt_blogtitle.setText(arrayList.get(position).getTitle_french());
            viewHolder.txt_blogdesc.setText(arrayList.get(position).getShort_descrition_french());
        }
//txt_open_file
        viewHolder.txt_readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lFilterItemCallback.ClickFilterItemCallback(0, position);
            }
        });
        viewHolder.txt_open_file.setVisibility(View.GONE);
        if(!Logger.EmptyString(arrayList.get(position).getDocument()).equalsIgnoreCase("")){
            viewHolder.txt_open_file.setVisibility(View.VISIBLE);
            viewHolder.txt_open_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lFilterItemCallback.ClickFilterItemCallback(3, position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_blogtitle)
        TextView txt_blogtitle;
        @BindView(R.id.txt_blogdesc)
        TextView txt_blogdesc;
        @BindView(R.id.txt_readmore)
        TextView txt_readmore;
        @BindView(R.id.blog_image)
        ImageView blog_image;

        @BindView(R.id.txt_open_file)
        TextView txt_open_file;




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