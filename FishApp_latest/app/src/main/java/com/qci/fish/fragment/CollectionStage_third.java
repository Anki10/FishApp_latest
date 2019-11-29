package com.qci.fish.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qci.fish.R;
import com.qci.fish.RoomDataBase.sample.SampleEntity;
import com.qci.fish.activity.SampleListActivity;
import com.qci.fish.adapter.ImageCaptureAdapter;
import com.qci.fish.adapter.OnItemResultClickListner;
import com.qci.fish.adapter.ResultCaptureAdapter;
import com.qci.fish.pojo.ImageCapturePojo;
import com.qci.fish.pojo.ResultCapturePojo;
import com.qci.fish.util.AppConstants;
import com.qci.fish.util.AppDialog;
import com.qci.fish.viewModel.SampleListViewModel;
import com.qci.fish.viewModel.SampleModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionStage_third extends BaseFragment implements OnItemResultClickListner {

    TextView tv_title,tv_count;


    @BindView(R.id.submit_third_stage)
    Button submit_third_stage;

    @BindView(R.id.recycler_result_capture)
    RecyclerView recycler_result_capture;

    private String staus_result = "";

    private int local_id;

    private String click_type;

    private SampleModel SampleModel;

    private SampleListViewModel sampleListViewModel;

    private ArrayList<ResultCapturePojo>result_list;

    private RecyclerView.LayoutManager mLayoutManager;

    private ResultCaptureAdapter adapter;

    private int list_pos;

    private ProgressDialog pd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collectionstage_third, container, false);

        ButterKnife.bind(this, view);

        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_count = (TextView) view.findViewById(R.id.tv_count);

        recycler_result_capture.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_result_capture.setLayoutManager(mLayoutManager);

        sampleListViewModel = ViewModelProviders.of(this).get(SampleListViewModel.class);

        result_list = new ArrayList<>();

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

        tv_title.setText("Capture Result");
        tv_count.setText("3/4 >");

        submit_third_stage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sampleEntityView.setFishtype_results(result_list);
                sampleListViewModel.UpdateSample(sampleEntityView);

                CollectionStage_fourth stage_fouth = new CollectionStage_fourth();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("local_id",local_id);
                bundle.putString("click_type",click_type);
                stage_fouth.setArguments(bundle);
                ft.replace(R.id.frame_layout,stage_fouth,"newFragment");
                ft.addToBackStack("my_fragment");
                ft.commit();
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

                    if (sampleEntityView.getFishtype_results() !=  null){
                        result_list = sampleEntity.getFishtype_results();
                    }else {
                        for (int i=0;i<sampleEntityView.getFishtypes().size();i++){

                            ResultCapturePojo result_pojo = new ResultCapturePojo();
                            result_pojo.setFishtype(sampleEntityView.getFishtypes().get(i).getFishtype());

                            result_list.add(result_pojo);
                        }
                    }

                    adapter = new ResultCaptureAdapter(getActivity(),result_list);
                    adapter.setOnItemResultClickListner(CollectionStage_third.this::onItemResultClicked);
                    recycler_result_capture.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onItemResultClicked(int from, int pos, View view) {
        list_pos = pos;
        if (from == 1){
            boolean checked = ((RadioButton) view).isChecked();

            if (checked){
                staus_result = "negative";

                ResultCapturePojo capturePojo = result_list.get(pos);
                capturePojo.setResult(staus_result);

                result_list.set(pos,capturePojo);
            }
        }else if (from == 2){
            boolean checked = ((RadioButton) view).isChecked();

            if (checked){
                staus_result = "Positive";

                ResultCapturePojo capturePojo = result_list.get(pos);
                capturePojo.setResult(staus_result);

                result_list.set(pos,capturePojo);
            }
        }else if (from == 3){
            boolean checked = ((RadioButton) view).isChecked();

            if (checked){
                staus_result = "Send_back";

                ResultCapturePojo capturePojo = result_list.get(pos);
                capturePojo.setResult(staus_result);

                result_list.set(pos,capturePojo);
            }
        }else if (from == 4){
            boolean checked = ((RadioButton) view).isChecked();

            if (checked){
                staus_result = "Exempted_Trucks";

                ResultCapturePojo capturePojo = result_list.get(pos);
                capturePojo.setResult(staus_result);

                result_list.set(pos,capturePojo);
            }
        }
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

                    ResultCapturePojo result_pojo = new ResultCapturePojo();
                    result_pojo.setFishtype(sampleEntityView.getFishtypes().get(i).getFishtype());

                    result_list.add(result_pojo);

                }

                pd.cancel();
                adapter = new ResultCaptureAdapter(getActivity(),result_list);
                adapter.setOnItemResultClickListner(CollectionStage_third.this::onItemResultClicked);
                recycler_result_capture.setAdapter(adapter);

            }
        };

        sampleListViewModel = ViewModelProviders.of(this).get(SampleListViewModel.class);
        sampleListViewModel.samplelist.observe(getActivity(),sampleObserver);

    }
}
