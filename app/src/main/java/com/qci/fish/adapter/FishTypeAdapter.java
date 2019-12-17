package com.qci.fish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qci.fish.R;
import com.qci.fish.RoomDataBase.sample.SampleFishTypeList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FishTypeAdapter extends RecyclerView.Adapter<FishTypeAdapter.ViewHolder> {

    private Context mContext;
    private List<SampleFishTypeList>fish_list;

    onItemFishClickListner onItemFishClickListner;

    public void setOnItemClickListener(onItemFishClickListner listener) {
        onItemFishClickListner = listener;
    }

    public FishTypeAdapter(Context context, List<SampleFishTypeList>list){
        this.mContext = context;
        this.fish_list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fish_type_row_view, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SampleFishTypeList pojo = fish_list.get(position);

        holder.tv_fish_name.setText(pojo.getFishtype());
        holder.tv_fish_quantity.setText(Integer.toString(pojo.getQty()));

        holder.ivDelete_fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemFishClickListner.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fish_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_fish_name)
        TextView tv_fish_name;

        @BindView(R.id.tv_fish_quantity)
        TextView tv_fish_quantity;

        @BindView(R.id.ivDelete_fish)
        ImageView ivDelete_fish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
