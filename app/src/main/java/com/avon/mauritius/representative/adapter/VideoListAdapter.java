package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.FilterItemCallback;
import com.avon.mauritius.representative.model.VideoItemList;
import com.avon.mauritius.representative.utils.ConstantFunctions;
import com.avon.mauritius.representative.utils.PersistentUser;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.viewHolder> {

    Context context;
    ArrayList<VideoItemList> arrayList;
    private FilterItemCallback lFilterItemCallback;

    public VideoListAdapter(Context context, ArrayList<VideoItemList> arrayList) {
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

    public void addAllItem(ArrayList<VideoItemList> blogItemLists) {
        this.arrayList.clear();
        this.arrayList.addAll(blogItemLists);
        notifyDataSetChanged();
    }

    public void addItem(VideoItemList blogItemLists) {
        this.arrayList.add(blogItemLists);
        notifyDataSetChanged();
    }

    public void deleteItem() {
        this.arrayList.clear();
        notifyDataSetChanged();
    }

    public VideoItemList getSuggestItemList(int postion) {
        return arrayList.get(postion);
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_video, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {

        if(PersistentUser.getLanguage(context).equalsIgnoreCase("en"))
            viewHolder.txt_blogtitle.setText(arrayList.get(position).getTitle_en());
        else
            viewHolder.txt_blogtitle.setText(arrayList.get(position).getTitle_fr());

        String youtube_link = arrayList.get(position).getYoutube_link();
        Log.e("youtube_link","are"+youtube_link);
        String[] dividedList =youtube_link.split("=");
        if(dividedList.length>1){
           String blog_pic="https://img.youtube.com/vi/"+dividedList[1]+"/mqdefault.jpg";
            ConstantFunctions.loadImageNomaForProfilel(blog_pic, viewHolder.blog_image);
        }
        viewHolder.blog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lFilterItemCallback.ClickFilterItemCallback(1, position);

            }
        });
        viewHolder.view_blog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lFilterItemCallback.ClickFilterItemCallback(1, position);

            }
        });





    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        @BindView(R.id.txt_blogtitle)
        TextView txt_blogtitle;
        @BindView(R.id.blog_image)
        ImageView blog_image;
        @BindView(R.id.view_blog_image)
        ImageView view_blog_image;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            lFilterItemCallback.ClickFilterItemCallback(1, getAdapterPosition());

        }
    }



}