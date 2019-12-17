package com.qci.fish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.qci.fish.activity.HomeActivity;
import com.qci.fish.activity.SampleListActivity;
import com.qci.fish.activity.WholeSale_ScanActivity;
import com.qci.fish.util.PermissionResultCallback;
import com.qci.fish.util.PermissionUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback {

    @BindView(R.id.ll_capture_sample_details)
    LinearLayout ll_capture_sample_details;

    @BindView(R.id.ll_sample_lists)
    LinearLayout ll_sample_lists;

    @BindView(R.id.ll_scan_sample_wholeSale)
    LinearLayout ll_scan_sample_wholeSale;

    @BindView(R.id.ll_scan_sample_lab)
    LinearLayout ll_scan_sample_lab;

    PermissionUtils permissionUtils;

    ArrayList<String> permissions=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        permissionUtils=new PermissionUtils(MainActivity.this);

        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);

        permissionUtils.check_permission(permissions,"All the permissions are required to access the app functionality",1);

        ll_capture_sample_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SampleListActivity.class);
                intent.putExtra("from_main","add_sample");
                startActivity(intent);
            }
        });

        ll_sample_lists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SampleListActivity.class);
                intent.putExtra("from_main","view_sample");
                startActivity(intent);

            }
        });

        ll_scan_sample_wholeSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WholeSale_ScanActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void PermissionGranted(int request_code) {

    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

    }

    @Override
    public void PermissionDenied(int request_code) {

    }

    @Override
    public void NeverAskAgain(int request_code) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
