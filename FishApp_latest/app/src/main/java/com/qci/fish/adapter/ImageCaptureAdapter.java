package com.qci.fish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qci.fish.R;
import com.qci.fish.RoomDataBase.sample.SampleFishTypeList;
import com.qci.fish.pojo.ImageCapturePojo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageCaptureAdapter extends RecyclerView.Adapter<ImageCaptureAdapter.ViewHolder > {

    private Context mContext;
    private ArrayList<ImageCapturePojo> imageCapture_list;

    OnItemImageClickListner onItemImageClickListner;

    public void setOnItemClickListener(OnItemImageClickListner listener) {
        onItemImageClickListner = listener;
    }

    public ImageCaptureAdapter(Context context, ArrayList<ImageCapturePojo>list){
        this.mContext = context;
        this.imageCapture_list = list;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_image_capture, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.llImageCollection1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemImageClickListner.onItemImageClicked(1,position);
            }
        });

        holder.llImageCollection2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemImageClickListner.onItemImageClicked(2,position);
            }
        });

        holder.llImageCollection3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemImageClickListner.onItemImageClicked(3,position);
            }
        });

        holder.tv_row_fish_name.setText("Fish Name :- "+imageCapture_list.get(position).getFishtype());

        try {
            if (imageCapture_list.get(position).getLocal_image_path1().length() > 0){
                Glide.with(mContext).load(imageCapture_list.get(position).getLocal_image_path1())
                        //           .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.ivImageCollection1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (imageCapture_list.get(position).getLocal_image_path2().length() > 0){
                Glide.with(mContext).load(imageCapture_list.get(position).getLocal_image_path2())
                        //           .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.ivImageCollection2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (imageCapture_list.get(position).getLocal_image_path3().length() > 0){
                Glide.with(mContext).load(imageCapture_list.get(position).getLocal_image_path3())
                        //           .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.ivImageCollection3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return imageCapture_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.llImageCollection1)
        LinearLayout llImageCollection1;

        @BindView(R.id.llImageCollection2)
        LinearLayout llImageCollection2;

        @BindView(R.id.llImageCollection3)
        LinearLayout llImageCollection3;



        @BindView(R.id.ivImageCollection1)
        ImageView ivImageCollection1;

        @BindView(R.id.ivImageCollection2)
        ImageView ivImageCollection2;

        @BindView( R.id.ivImageCollection3)
        ImageView ivImageCollection3;

        @BindView(R.id.tv_row_fish_name)
        TextView tv_row_fish_name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
