package com.qci.fish.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qci.fish.R;
import com.qci.fish.RoomDataBase.sample.SampleEntity;
import com.qci.fish.activity.HomeActivity;
import com.qci.fish.activity.SampleListActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SampleEntity> sample_list;

    private String sample_from;

    public SampleAdapter(Context context,ArrayList<SampleEntity>list,String sample){
        this.mContext = context;
        this.sample_list = list;
        this.sample_from = sample;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_row_view, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (sample_from.equalsIgnoreCase("view_sample")){
            holder.ivDeleteItem.setVisibility(View.GONE);
            holder.ivSyncItem.setVisibility(View.GONE);
        }

        SampleEntity pojo = sample_list.get(position);

        holder.tv_sampleId.setText(pojo.getSampleid());
        holder.tv_location_name.setText(pojo.getLocationname());
        holder.tv_collectionData.setText(pojo.getSamplecollectiondate_str());

        if (!sample_from.equalsIgnoreCase("view_sample")){
            holder.ll_view.setOnClickListener((SampleListActivity)mContext);
            holder.ll_view.setTag(R.string.key_list_click,position);
        }

        holder.ivDeleteItem.setOnClickListener((SampleListActivity)mContext);
        holder.ivDeleteItem.setTag(R.string.key_delete_click,position);

        holder.ivSyncItem.setOnClickListener((SampleListActivity)mContext);
        holder.ivSyncItem.setTag(R.string.key_sync_click,position);
    }

    @Override
    public int getItemCount() {
        return sample_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_sampleId)
        TextView tv_sampleId;

        @BindView(R.id.tv_location_name)
        TextView tv_location_name;

        @BindView(R.id.tv_collectionData)
        TextView tv_collectionData;

        @BindView(R.id.ll_view)
        LinearLayout ll_view;

        @BindView(R.id.ivDeleteItem)
        ImageView ivDeleteItem;

        @BindView(R.id.ivSyncItem)
        ImageView ivSyncItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
