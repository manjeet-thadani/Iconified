package com.genius.iconified;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.genius.iconified.utils.Constants;
import com.genius.iconified.utils.OnItemClickListener;
import com.genius.iconified.utils.SearchRecordHolder;

import java.util.List;

/**
 * Created by manjeet on 2/3/18.
 */

public class RecyclerViewMainActivityRecentSearches extends RecyclerView.Adapter<RecyclerViewMainActivityRecentSearches.RecyclerViewHolder> {

    private List<SearchRecordHolder> mTitles;
    private Context mContext;

    private SharedPreferences sharedPreferences;

    public RecyclerViewMainActivityRecentSearches(List<SearchRecordHolder> titles, Context context) {
        mTitles = titles;
        mContext = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_row_main_activity_recent_searches, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final SearchRecordHolder record = mTitles.get(position);

        holder.iconImageView.setImageResource(R.mipmap.ic_launcher);

        holder.iconNameTextView.setText(record.getIconName());
        holder.dateTextView.setText(record.getDate());

        holder.setClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("query", record.getIconName());
                mContext.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, boolean isLongClick) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout containerLayout;
        ImageView iconImageView;
        TextView iconNameTextView;
        TextView dateTextView;
        OnItemClickListener itemClickListener;

        public RecyclerViewHolder(final View view) {
            super(view);

            containerLayout = (LinearLayout) view.findViewById(R.id.new_row_main_activity_recent_searches_container_layout);
            iconImageView = (ImageView) view.findViewById(R.id.new_row_main_activity_recent_searches_icon_imageview);
            iconNameTextView = (TextView) view.findViewById(R.id.new_row_main_activity_recent_searches_icon_name_textview);
            dateTextView = (TextView) view.findViewById(R.id.new_row_main_activity_recent_searches_date_textview);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition(), false);
        }


        public void setClickListener(OnItemClickListener listener) {
            this.itemClickListener = listener;
        }
    }
}




