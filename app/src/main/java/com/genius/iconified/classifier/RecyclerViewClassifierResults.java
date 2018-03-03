package com.genius.iconified.classifier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.genius.iconified.R;
import com.genius.iconified.WebViewActivity;
import com.genius.iconified.utils.OnItemClickListener;

import java.util.List;

/**
 * Created by manjeet on 3/3/18.
 */

public class RecyclerViewClassifierResults extends RecyclerView.Adapter<RecyclerViewClassifierResults.RecyclerViewHolder> {

    private List<ClassifierResult> mTitles;
    private Context mContext;

    public RecyclerViewClassifierResults(List<ClassifierResult> titles, Context context) {
        mTitles = titles;
        mContext = context;
    }

    @Override
    public RecyclerViewClassifierResults.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_row_acivity_classifier_results, parent, false);
        RecyclerViewClassifierResults.RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewClassifierResults.RecyclerViewHolder holder, int position) {
        final ClassifierResult record = mTitles.get(position);

        if (position == 0)
            holder.greenSideView.setBackgroundColor(mContext.getResources().getColor(R.color.dark_green));
        else
            holder.greenSideView.setBackgroundColor(mContext.getResources().getColor(R.color.gray));

        holder.iconNameTextView.setText(record.getIconName());
        holder.confidenceLevelTextView.setText(String.format("%.2f", record.getIconConfidenceLevel() * 100.0f) + "% Confidence Level");

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
        View greenSideView;
        TextView iconNameTextView;
        TextView confidenceLevelTextView;
        OnItemClickListener itemClickListener;

        public RecyclerViewHolder(final View view) {
            super(view);

            containerLayout = (LinearLayout) view.findViewById(R.id.new_row_acivity_classifier_results_container_layout);
            greenSideView = view.findViewById(R.id.new_row_acivity_classifier_results_green_side_view);
            iconNameTextView = (TextView) view.findViewById(R.id.new_row_acivity_classifier_results_icon_name_textview);
            confidenceLevelTextView = (TextView) view.findViewById(R.id.new_row_acivity_classifier_results_confidence_level_textview);

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




