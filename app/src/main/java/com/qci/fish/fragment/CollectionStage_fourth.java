package com.qci.fish.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.qci.fish.R;
import com.qci.fish.RoomDataBase.sample.SampleEntity;
import com.qci.fish.RoomDataBase.sample.SampleFishTypeList;
import com.qci.fish.activity.QRCodeScanActivity;
import com.qci.fish.activity.SampleListActivity;
import com.qci.fish.adapter.OnItemQRClickListner;
import com.qci.fish.adapter.QRCodeCaptureAdapter;
import com.qci.fish.adapter.ResultCaptureAdapter;
import com.qci.fish.pojo.QRCodeCapturePojo;
import com.qci.fish.pojo.ResultCapturePojo;
import com.qci.fish.util.AppDialog;
import com.qci.fish.viewModel.SampleListViewModel;
import com.qci.fish.viewModel.SampleModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionStage_fourth extends BaseFragment implements OnItemQRClickListner {

    private View view;

    TextView tv_title,tv_count;

    @BindView(R.id.btn_Image_submit)
    Button btn_Image_submit;

    @BindView(R.id.recycler_qrCode)
    RecyclerView recycler_qrCode;

    private int local_id;

    private String click_type;

    private SampleModel SampleModel;

    private String qr_result;

    private SampleListViewModel sampleListViewModel;

    private ArrayList<QRCodeCapturePojo> QRCode_list;

    private RecyclerView.LayoutManager mLayoutManager;

    private QRCodeCaptureAdapter adapter;

    private ProgressDialog pd;

    @BindView(R.id.iView_back)
    ImageView iView_back;


    public CollectionStage_fourth() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_collection_stage_fourth, container, false);

        ButterKnife.bind(this, view);

        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_count = (TextView) view.findViewById(R.id.tv_count);

        tv_title.setText("Sample Qr scan");
        tv_count.setText("4/4 >");

        recycler_qrCode.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_qrCode.setLayoutManager(mLayoutManager);

        sampleListViewModel = ViewModelProviders.of(this).get(SampleListViewModel.class);

        QRCode_list = new ArrayList<>();

        iView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        // local_id
        local_id = getArguments().getInt("local_id");

        click_type = getArguments().getString("click_type");

        if (click_type.equalsIgnoreCase("first")){
            pd = AppDialog.showLoading(getActivity());
            pd.setCanceledOnTouchOutside(false);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getMainList();
                }
            }, 2000);
        }else {
            SampleGetData(local_id);
        }

        btn_Image_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sampleEntityView.setFishtype_qrcode(QRCode_list);

                sampleListViewModel.UpdateSample(sampleEntityView);

                getActivity().finish();
            }
        });
        return view;
    }



    private void SampleGetData(int localSampleId){

        com.qci.fish.viewModel.SampleModel.Factory factory = new SampleModel.Factory(getActivity().getApplication(),localSampleId);

        SampleModel = new ViewModelProvider(this,factory).get(com.qci.fish.viewModel.SampleModel.class);

        SampleModel.getObservableSample().observe(this, new Observer<SampleEntity>() {
            @Override
            public void onChanged(SampleEntity sampleEntity) {
                if (sampleEntity != null) {

                    sampleEntityView = sampleEntity;

                    if (sampleEntityView.getFishtype_qrcode()!=  null){
                        QRCode_list = sampleEntity.getFishtype_qrcode();
                    }else {
                        for (int i=0;i<sampleEntityView.getFishtypes().size();i++){

                            QRCodeCapturePojo result_pojo = new QRCodeCapturePojo();
                            result_pojo.setFishtype(sampleEntityView.getFishtypes().get(i).getFishtype());

                            QRCode_list.add(result_pojo);
                        }
                    }

                    adapter = new QRCodeCaptureAdapter(getActivity(),QRCode_list);
                    adapter.setItemQRClickListner(CollectionStage_fourth.this::onItemQRClicked);
                    recycler_qrCode.setAdapter(adapter);
                }
            }
        });
    }

    private void getMainList(){

        Observer<List<SampleEntity>> sampleObserver = new Observer<List<SampleEntity>>() {
            @Override
            public void onChanged(List<SampleEntity> sampleEntities) {
                sample_list.clear();
                sample_list.addAll(sampleEntities);

                System.out.println("xxx_size"+sample_list.size());

                sampleEntityView = sample_list.get(0);
                local_sample_id = sampleEntityView.getLocalSampleId();


                for (int i=0;i<sampleEntityView.getFishtypes().size();i++){

                    QRCodeCapturePojo result_pojo = new QRCodeCapturePojo();
                    result_pojo.setFishtype(sampleEntityView.getFishtypes().get(i).getFishtype());

                    QRCode_list.add(result_pojo);

                }

                pd.cancel();
                adapter = new QRCodeCaptureAdapter(getActivity(),QRCode_list);
                adapter.setItemQRClickListner(CollectionStage_fourth.this::onItemQRClicked);
                recycler_qrCode.setAdapter(adapter);

            }
        };

        sampleListViewModel = ViewModelProviders.of(this).get(SampleListViewModel.class);
        sampleListViewModel.samplelist.observe(getActivity(),sampleObserver);

    }

    @Override
    public void onItemQRClicked(int pos) {

        Intent intent = new Intent(getActivity(), QRCodeScanActivity.class);
        startActivityForResult(intent,pos);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){

            String QRText = data.getStringExtra("result");

            QRCodeCapturePojo qrCodeCapturePojo = QRCode_list.get(requestCode);
            qrCodeCapturePojo.setScanned_qrcode(QRText);

            QRCode_list.set(requestCode,qrCodeCapturePojo);

            adapter.notifyDataSetChanged();
        }
    }

}
