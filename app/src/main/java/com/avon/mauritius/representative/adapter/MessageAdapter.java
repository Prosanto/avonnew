package com.avon.mauritius.representative.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.avon.mauritius.representative.R;
import com.avon.mauritius.representative.callbackinterface.FilterItemCallback;
import com.avon.mauritius.representative.model.MessageList;
import com.avon.mauritius.representative.utils.DateUtility;
import com.avon.mauritius.representative.utils.PersistentUser;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.viewHolder> {

    Context context;
    ArrayList<MessageList> arrayList;
    private FilterItemCallback lFilterItemCallback;

    public MessageAdapter(Context context, ArrayList<MessageList> arrayList) {
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

    public void addAllItem(ArrayList<MessageList> messageLists) {
        this.arrayList.clear();
        this.arrayList.addAll(messageLists);
        notifyDataSetChanged();
    }

    public int getItemSize() {
        return this.arrayList.size();
    }

    public void addItem(MessageList messageList) {
        this.arrayList.add(messageList);
        notifyDataSetChanged();
    }

    public void deleteItem() {
        this.arrayList.clear();
        notifyDataSetChanged();
    }

    public MessageList getSuggestMessageList(int postion) {
        return arrayList.get(postion);
    }

    public void updateData(int postion) {
         arrayList.get(postion).setIs_read("1");
         notifyDataSetChanged();
    }
    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_message, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        String textMessage = DateUtility.getFormattedDate(arrayList.get(position).getDate());
        viewHolder.txt_msgdate.setText(textMessage);
        viewHolder.txt_msgdescription.setText(arrayList.get(position).getMessages());

        viewHolder.img_msgcros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lFilterItemCallback.ClickFilterItemCallback(0, position);
            }
        });
        viewHolder.due_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lFilterItemCallback.ClickFilterItemCallback(1, position);
            }
        });
        if (arrayList.get(position).getIs_read().equalsIgnoreCase("0")) {
            viewHolder.unread_message.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(R.raw.iconnotificationactif)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(viewHolder.unread_message);
        } else {
            viewHolder.unread_message.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txt_msgdate)
        TextView txt_msgdate;
        @BindView(R.id.img_msgcros)
        ImageView img_msgcros;
        @BindView(R.id.txt_msgdescription)
        TextView txt_msgdescription;
        @BindView(R.id.due_action)
        LinearLayout due_action;
        @BindView(R.id.unread_message)
        ImageView unread_message;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            txt_msgdescription.setMovementMethod(LinkMovementMethod.getInstance());

        }

        @Override
        public void onClick(View view) {
            lFilterItemCallback.ClickFilterItemCallback(1, getAdapterPosition());

        }
    }

    public void removeAt(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }

}