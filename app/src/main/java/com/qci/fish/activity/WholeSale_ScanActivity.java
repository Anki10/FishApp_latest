package com.qci.fish.activity;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.qci.fish.R;

import butterknife.BindView;

public class WholeSale_ScanActivity extends BaseActivity implements QRCodeReaderView.OnQRCodeReadListener {

    private static QRCodeReaderView qrCodeReaderView;

    @BindView(R.id.iView_back)
    ImageView iView_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole_sale);

        iView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview_wholeSale);
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(1000);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set front camera preview
        qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();

    }

    @Override
    public void onResume() {
        super.onResume();

        qrCodeReaderView.startCamera();
    }


    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        qrCodeReaderView.stopCamera();

        Intent intent = new Intent(WholeSale_ScanActivity.this,WholeSaleSampleListActivity.class);
        intent.putExtra("qr_code_text",text);
        startActivity(intent);
        finish();



    }
}
