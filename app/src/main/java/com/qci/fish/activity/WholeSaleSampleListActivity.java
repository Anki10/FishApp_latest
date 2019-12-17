package com.qci.fish.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.qci.fish.R;
import com.qci.fish.RoomDataBase.sample.SampleEntity;
import com.qci.fish.api.APIService;
import com.qci.fish.api.ApiUtils;
import com.qci.fish.util.AppConstants;
import com.qci.fish.util.ConnectionDetector;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WholeSaleSampleListActivity extends BaseActivity {

    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;

    private APIService mAPIService;

    private String qr_code_text;

    @BindView(R.id.tv_sampleResult)
    TextView tv_sampleResult;

    @BindView(R.id.tv_sampleIdData)
    TextView tv_sampleIdData;

    @BindView(R.id.tv_location_nameData)
    TextView tv_location_nameData;

    @BindView(R.id.tv_collectionDataSet)
    TextView tv_collectionDataSet;

    @BindView(R.id.tv_vehicle_number)
    TextView tv_vehicle_number;

    @BindView(R.id.tv_vehicle_driver_name)
    TextView tv_vehicle_driver_name;

    @BindView(R.id.tv_vehicle_driver_mobile)
    TextView tv_vehicle_driver_mobile;

    @BindView(R.id.tv_Consignee_Name)
    TextView tv_Consignee_Name;

    @BindView(R.id.tv_Consignee_number)
    TextView tv_Consignee_number;

    @BindView(R.id.tv_licence_number)
    TextView tv_licence_number;

    @BindView(R.id.ok_result)
    Button ok_result;

    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samplelist_wholesale);

        ButterKnife.bind(this);

        tv_title = (TextView) findViewById(R.id.tv_title);

        tv_title.setText("View Sample Details");


        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        mAPIService = ApiUtils.getAPIService();

        qr_code_text = getIntent().getStringExtra("qr_code_text");

        getSampleData();

        ok_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void getSampleData(){
        mAPIService.getSampleInfo("application/json","Bearer " + getFromPrefs(AppConstants.ACCESS_Token),"http://52.66.213.181:5004/api/sampleinfo_app/getsample_qrcode?qrcode="+qr_code_text).enqueue(new Callback<SampleEntity>() {
            @Override
            public void onResponse(Call<SampleEntity> call, Response<SampleEntity> response) {
                System.out.println("xxx sucess");

                if (response.body() != null){
                    SampleView(response.body());
                }
            }

            @Override
            public void onFailure(Call<SampleEntity> call, Throwable t) {
                System.out.println("xxx failed");
            }
        });
    }

    private void SampleView(SampleEntity sampleEntity){
        if (sampleEntity.getLocationname() != null){
            tv_location_nameData.setText(sampleEntity.getLocationname());
        }
        if (sampleEntity.getSampleid() != null){
            tv_sampleIdData.setText(sampleEntity.getSampleid());
        }
        if (sampleEntity.getSamplecollectiondate_str() != null){
            tv_collectionDataSet.setText(sampleEntity.getSamplecollectiondate_str());
        }
        if (sampleEntity.getTruckno() != null){
            tv_vehicle_number.setText(sampleEntity.getTruckno());
        }
        if (sampleEntity.getTruckdrivername() != null){
            tv_vehicle_driver_name.setText(sampleEntity.getTruckdrivername());
        }
        if (sampleEntity.getTruckdrivermobile() != null){
            tv_vehicle_driver_mobile.setText(sampleEntity.getTruckdrivermobile());
        }
        if (sampleEntity.getConsigneename() != null){
            tv_Consignee_Name.setText(sampleEntity.getConsigneename());
        }
        if (sampleEntity.getConsignmentno() != null){
            tv_Consignee_number.setText(sampleEntity.getConsignmentno());
        }
        if (sampleEntity.getFssai_fda_licenceno() != null){
            tv_licence_number.setText(sampleEntity.getFssai_fda_licenceno());
        }
    }
}

