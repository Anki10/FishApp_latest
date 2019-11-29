package com.qci.fish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qci.fish.R;
import com.qci.fish.pojo.QRCodeCapturePojo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QRCodeCaptureAdapter extends RecyclerView.Adapter<QRCodeCaptureAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<QRCodeCapturePojo> QrCode_list;
    private OnItemQRClickListner itemQRClickListner;

    public void setItemQRClickListner(OnItemQRClickListner listener) {
        itemQRClickListner = listener;
    }


    public QRCodeCaptureAdapter(Context context,ArrayList<QRCodeCapturePojo>Qr_list){
        this.mContext = context;
        this.QrCode_list = Qr_list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_qr_code, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.text_fish_name.setText("Fish Name : - "+QrCode_list.get(position).getFishtype());
        holder.text_QrCode.setText(QrCode_list.get(position).getScanned_qrcode());

        holder.btn_QrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemQRClickListner.onItemQRClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return QrCode_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_fish_name)
        TextView text_fish_name;

        @BindView(R.id.text_QrCode)
        TextView text_QrCode;

        @BindView(R.id.btn_QrCode)
        Button btn_QrCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
