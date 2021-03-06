package com.globallogic.rss_reader.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.globallogic.rss_reader.R;
import com.globallogic.rss_reader.RSSApplication;
import com.globallogic.rss_reader.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego.rotondale on 10/05/2015.
 */
public class RssAdapter extends RecyclerView.Adapter<RssAdapter.RSSHolder> {
    private List<Item> mDataset = new ArrayList<Item>();
    private OnItemClickListener onItemClickListener;

    @Override
    public RSSHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss, parent, false);
        RSSHolder vh = new RSSHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RSSHolder holder, int position) {
        Item item = mDataset.get(position);
        holder.mTitle.setText(item.title);
        holder.mDescription.setText(Html.fromHtml(item.getDescription()));
        holder.pubDate.setText(item.getPubDate());
        if (item.content != null && item.content.url != null) {
            holder.image.setImageUrl(item.content.url, RSSApplication.getInstance().getImageLoader());
            holder.image.setContentDescription(item.content.title);
            holder.image.setVisibility(View.VISIBLE);
        } else {
            holder.image.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addAll(List<Item> items) {
        mDataset.clear();
        mDataset.addAll(items);
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(Item item);
    }

    public class RSSHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitle;
        public TextView mDescription;
        public TextView pubDate;
        public NetworkImageView image;

        public RSSHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mTitle = (TextView) view.findViewById(R.id.item_rss_title);
            mDescription = (TextView) view.findViewById(R.id.item_rss_description);
            pubDate = (TextView) view.findViewById(R.id.item_rss_pub_date);
            image = (NetworkImageView) view.findViewById(R.id.item_rss_image);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClickListener(mDataset.get(getPosition()));
        }
    }
}
