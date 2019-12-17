package com.qci.fish.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.qci.fish.R;

public class QRCodeScanActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    private static QRCodeReaderView qrCodeReaderView;

    private String qr_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan);

        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
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
    protected void onResume() {
        super.onResume();

        qrCodeReaderView.startCamera();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        qr_code = text;
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", qr_code);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
