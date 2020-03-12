package com.qci.fish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qci.fish.MainActivity;
import com.qci.fish.R;
import com.qci.fish.RoomDataBase.sample.SampleEntity;
import com.qci.fish.adapter.SampleAdapter;
import com.qci.fish.api.APIService;
import com.qci.fish.api.ApiUtils;
import com.qci.fish.pojo.SampleSyncResponse;
import com.qci.fish.util.AppConstants;
import com.qci.fish.util.AppDialog;
import com.qci.fish.util.ConnectionDetector;
import com.qci.fish.viewModel.SampleListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SampleListActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.recyclerView_sampleList)
    RecyclerView recyclerView_sampleList;

    @BindView(R.id.ll_NoData)
    LinearLayout ll_NoData;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ArrayList<SampleEntity> sample_list;

    private SampleListViewModel sampleListViewModel;

    private RecyclerView.LayoutManager mLayoutManager;

    private SampleAdapter adapter;

    private TextView tv_title;

    private String sample_from;

    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;

    private APIService mAPIService;

    @BindView(R.id.iView_back)
    ImageView iView_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_list);

        ButterKnife.bind(this);

        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        iView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAPIService = ApiUtils.getAPIService();

        tv_title = (TextView) findViewById(R.id.tv_title);

        tv_title.setText("Saved Collections");

        sample_list = new ArrayList<>();

        recyclerView_sampleList.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(SampleListActivity.this);
        recyclerView_sampleList.setLayoutManager(mLayoutManager);

        if (getIntent().getStringExtra("from_main")!= null){
            sample_from = getIntent().getStringExtra("from_main");
            if (getIntent().getStringExtra("from_main").equalsIgnoreCase("view_sample")){
                fab.hide();
            }
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SampleListActivity.this, HomeActivity.class);
                intent.putExtra("local_sample_id","00");
                intent.putExtra("click_type","first");
                startActivity(intent);
                //           finish();
            }
        });

        getList();

    }

    private void getList(){

        Observer<List<SampleEntity>> sampleObserver = new Observer<List<SampleEntity>>() {
            @Override
            public void onChanged(List<SampleEntity> sampleEntities) {
                sample_list.clear();
                sample_list.addAll(sampleEntities);

                if (sample_list.size() > 0){
                    ll_NoData.setVisibility(View.GONE);
                }

                if (adapter == null){
                    adapter = new SampleAdapter(SampleListActivity.this,sample_list,sample_from);
                    recyclerView_sampleList.setAdapter(adapter);
                }else {
                    adapter.notifyDataSetChanged();
                }

                System.out.println("xxx list"+sample_list);

            }
        };

        sampleListViewModel = ViewModelProviders.of(this).get(SampleListViewModel.class);
        sampleListViewModel.samplelist.observe(SampleListActivity.this,sampleObserver);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_view:

                int position = (int) view.getTag(R.string.key_list_click);

                Intent intent = new Intent(SampleListActivity.this, HomeActivity.class);
                intent.putExtra("local_sample_id",sample_list.get(position).getLocalSampleId());
                intent.putExtra("click_type","already");
                startActivity(intent);

                break;

            case R.id.ivDeleteItem:

                int pos = (int) view.getTag(R.string.key_delete_click);

                SampleEntity sampleEntity = new SampleEntity();

                sampleEntity = sample_list.get(pos);

                sampleListViewModel.deleteSample(sampleEntity);

                getList();

                break;

            case R.id.ivSyncItem:

                int pos_sync = (int) view.getTag(R.string.key_sync_click);

                syncSampleData(pos_sync);

                break;
        }
    }

    private void syncSampleData(int pos) {

        SampleEntity sampleEntity = sample_list.get(pos);

        final ProgressDialog d = AppDialog.showLoading(SampleListActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.DataSync("application/json", "Bearer " + getFromPrefs(AppConstants.ACCESS_Token), sampleEntity).enqueue(new Callback<SampleSyncResponse>() {
            @Override
            public void onResponse(Call<SampleSyncResponse> call, Response<SampleSyncResponse> response) {

                d.dismiss();
                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        Toast.makeText(SampleListActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                        sampleListViewModel.deleteSample(sampleEntity);

                        getList();
                    } else {
                        Toast.makeText(SampleListActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SampleSyncResponse> call, Throwable t) {
                d.dismiss();
                Toast.makeText(SampleListActivity.this, "Sync failed", Toast.LENGTH_LONG).show();
            }
        });
     }
    }