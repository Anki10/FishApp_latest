package com.qci.fish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qci.fish.R;
import com.qci.fish.pojo.ResultCapturePojo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultCaptureAdapter extends RecyclerView.Adapter<ResultCaptureAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ResultCapturePojo>result_list;

    private OnItemResultClickListner onItemResultClickListner;

    public void setOnItemResultClickListner(OnItemResultClickListner listener) {
        onItemResultClickListner = listener;
    }

    public ResultCaptureAdapter(Context context, ArrayList<ResultCapturePojo>resultlist){
        this.mContext = context;
        this.result_list = resultlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_result_capture, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_capture_fish_name.setText("Capture Result of Fish : "+result_list.get(position).getFishtype());

        if (result_list.get(position).getResult() != null){
            if (result_list.get(position).getResult().equalsIgnoreCase("negative")){
                holder.radio_negative.setChecked(true);
            }else if (result_list.get(position).getResult().equalsIgnoreCase("Positive")){
                holder.radio_Positive.setChecked(true);
            }else if (result_list.get(position).getResult().equalsIgnoreCase("Send_back")){
                holder.radio_Send_back.setChecked(true);
            }else if (result_list.get(position).getResult().equalsIgnoreCase("Export")){
                holder.radio_Export.setChecked(true);
            }else if (result_list.get(position).getResult().equalsIgnoreCase("Other States")){
                holder.radio_Other_States.setChecked(true);
            }else if (result_list.get(position).getResult().equalsIgnoreCase("Exempted fish type")){
                holder.radio_Exempted_fish_type.setChecked(true);
            }
        }

        holder.radio_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemResultClickListner.onItemResultClicked(1,position,view);
            }
        });

        holder.radio_Positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemResultClickListner.onItemResultClicked(2,position,view);
            }
        });

        holder.radio_Send_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemResultClickListner.onItemResultClicked(3,position,view);
            }
        });

        holder.radio_Export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemResultClickListner.onItemResultClicked(4,position,view);
            }
        });

        holder.radio_Other_States.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemResultClickListner.onItemResultClicked(5,position,view);
            }
        });

        holder.radio_Exempted_fish_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemResultClickListner.onItemResultClicked(6,position,view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return result_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.radio_negative)
        RadioButton radio_negative;

        @BindView(R.id.radio_Positive)
        RadioButton radio_Positive;

        @BindView(R.id.radio_Send_back)
        RadioButton radio_Send_back;

        @BindView(R.id.radio_Export)
        RadioButton radio_Export;

        @BindView(R.id.radio_Other_States)
        RadioButton radio_Other_States;

        @BindView(R.id.radio_Exempted_fish_type)
        RadioButton radio_Exempted_fish_type;

        @BindView(R.id.tv_capture_fish_name)
        TextView tv_capture_fish_name;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
