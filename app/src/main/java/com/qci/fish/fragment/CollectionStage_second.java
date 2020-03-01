package com.qci.fish.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
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
import com.qci.fish.RoomDataBase.sample.SampleFishTypeList;
import com.qci.fish.RoomDataBase.sampleImage.SampleImageEntity;
import com.qci.fish.activity.LoginActivity;
import com.qci.fish.activity.SampleListActivity;
import com.qci.fish.adapter.FishTypeAdapter;
import com.qci.fish.adapter.ImageCaptureAdapter;
import com.qci.fish.adapter.OnItemImageClickListner;
import com.qci.fish.api.APIService;
import com.qci.fish.api.ApiUtils;
import com.qci.fish.pojo.ImageCapturePojo;
import com.qci.fish.pojo.ImageUploadResponse;
import com.qci.fish.util.AppConstants;
import com.qci.fish.util.AppDialog;
import com.qci.fish.util.ConnectionDetector;
import com.qci.fish.viewModel.SampleImageViewModel;
import com.qci.fish.viewModel.SampleListViewModel;
import com.qci.fish.viewModel.SampleModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionStage_second extends BaseFragment implements OnItemImageClickListner {

    TextView tv_title, tv_count;

    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;

    private String imagepath_1, imagepath_2, imagepath_3;

    @BindView(R.id.btn_Image_submit)
    Button btn_Image_submit;

    @BindView(R.id.recycler_image_capture)
    RecyclerView recycler_image_capture;

    @BindView(R.id.iView_back)
    ImageView iView_back;

    private SampleImageViewModel sampleImageViewModel;

    private int local_id;

    private String local;

    private SampleModel SampleModel;

    private String click_type;


    private RecyclerView.LayoutManager mLayoutManager;

    private ImageCaptureAdapter adapter;

    private SampleListViewModel sampleListViewModel;

    private int list_pos;

    private ProgressDialog pd;

    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;

    private APIService mAPIService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collectionstage_second, container, false);

        ButterKnife.bind(this, view);

        cd = new ConnectionDetector(getActivity());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        iView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        mAPIService = ApiUtils.getAPIService();

        sampleListViewModel = ViewModelProviders.of(this).get(SampleListViewModel.class);

        // local_id
        local_id = getArguments().getInt("local_id");

        click_type = getArguments().getString("click_type");

        pd = AppDialog.showLoading(getActivity());
        pd.setCanceledOnTouchOutside(false);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getMainList();
            }
        }, 2000);


      /*  if (click_type.equalsIgnoreCase("first")) {
            pd = AppDialog.showLoading(getActivity());
            pd.setCanceledOnTouchOutside(false);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getMainList();
                }
            }, 2000);
        } else {
            SampleGetData(local_id);
        }
*/

        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_count = (TextView) view.findViewById(R.id.tv_count);

        tv_title.setText("Capture Photos");
        tv_count.setText("2/4 >");

        recycler_image_capture.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_image_capture.setLayoutManager(mLayoutManager);

        return view;

    }

    @OnClick({R.id.btn_Image_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_Image_submit:

                CollectionStage_third stage_third = new CollectionStage_third();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("local_id", local_id);
                bundle.putString("click_type", click_type);
                stage_third.setArguments(bundle);
                ft.replace(R.id.frame_layout, stage_third, "newFragment");
                ft.addToBackStack("my_fragment");
                ft.commit();

                break;
        }
    }


    private void captureImage(int CAMERA_REQUEST) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "bmh" + timeStamp + "_";
            File albumF = getAlbumDir();
            imageF = File.createTempFile(imageFileName, "bmh", albumF);
            picUri = Uri.fromFile(imageF);

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageF));
            } else {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", imageF));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivityForResult(takePictureIntent, CAMERA_REQUEST);

    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraPicture");
            } else {
                storageDir = new File(Environment.getExternalStorageDirectory() + CAMERA_DIR + "CameraPicture");
            }

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        //		Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            //		Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                if (picUri != null) {
                    Uri uri = picUri;
                    imagepath_1 = compressImage(uri.toString());

                    UploadImage(imagepath_1,1);

                }
            } else if (requestCode == 2) {
                if (picUri != null) {
                    Uri uri = picUri;
                    imagepath_2 = compressImage(uri.toString());

                    UploadImage(imagepath_2,2);
                }

            } else if (requestCode == 3) {
                if (picUri != null) {
                    Uri uri = picUri;
                    imagepath_3 = compressImage(uri.toString());

                    UploadImage(imagepath_3,3);

                }
            }
        }
    }

    private void UploadImage(String image_path,int request_code){
        File file = new File(image_path);

        //pass it like this
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        final ProgressDialog d = AppDialog.showLoading(getActivity());
        d.setCanceledOnTouchOutside(false);


        mAPIService.ImageUploadRequest("Bearer " + getFromPrefs(AppConstants.ACCESS_Token),body).enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                d.dismiss();
                if (response.body() != null){
                    if (response.body().getSuccess()){

                        Toast.makeText(getActivity(),"Image uploaded successfully",Toast.LENGTH_LONG).show();

                        if (request_code == 1){
                            ImageCapturePojo image_pojo = imageCapture_list.get(list_pos);
                            image_pojo.setLocal_image_path1(imagepath_1);
                            image_pojo.setPic1(response.body().getMessage());

                            imageCapture_list.set(list_pos, image_pojo);

                            adapter.notifyDataSetChanged();
                        }else if (request_code == 2){
                            ImageCapturePojo image_pojo2 = imageCapture_list.get(list_pos);
                            image_pojo2.setLocal_image_path2(imagepath_2);
                            image_pojo2.setPic2(response.body().getMessage());

                            imageCapture_list.set(list_pos, image_pojo2);

                            adapter.notifyDataSetChanged();
                        }else if (request_code == 3){
                            ImageCapturePojo image_pojo3 = imageCapture_list.get(list_pos);
                            image_pojo3.setLocal_image_path3(imagepath_3);
                            image_pojo3.setPic3(response.body().getMessage());

                            imageCapture_list.set(list_pos, image_pojo3);

                            adapter.notifyDataSetChanged();
                        }
                    }
                }else {
                    Toast.makeText(getActivity(),"Image upload failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                d.dismiss();
                Toast.makeText(getActivity(),"Image upload failed",Toast.LENGTH_LONG).show();
            }
        });

    }

    public String getFromPrefs(String key) {
        SharedPreferences prefs = getActivity().getSharedPreferences(AppConstants.PREF_NAME, getActivity().MODE_PRIVATE);
        return prefs.getString(key, AppConstants.DEFAULT_VALUE);
    }



    private void SampleGetData(int localSampleId) {

        com.qci.fish.viewModel.SampleModel.Factory factory = new SampleModel.Factory(getActivity().getApplication(), localSampleId);


        SampleModel = new ViewModelProvider(this, factory).get(com.qci.fish.viewModel.SampleModel.class);

        SampleModel.getObservableSample().observe(this, new Observer<SampleEntity>() {
            @Override
            public void onChanged(SampleEntity sampleEntity) {
                if (sampleEntity != null) {
                    sampleEntityView = sampleEntity;

                    if (sampleEntity.getFishtype_pics() != null) {
                        imageCapture_list = sampleEntityView.getFishtype_pics();
                    } else {
                        for (int i = 0; i < sampleEntityView.getFishtypes().size(); i++) {

                            ImageCapturePojo image_pojo = new ImageCapturePojo();
                            image_pojo.setFishtype(sampleEntityView.getFishtypes().get(i).getFishtype());

                            imageCapture_list.add(image_pojo);

                        }
                    }
                    adapter = new ImageCaptureAdapter(getActivity(), imageCapture_list);
                    adapter.setOnItemClickListener(CollectionStage_second.this::onItemImageClicked);
                    recycler_image_capture.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onItemImageClicked(int from, int pos) {
        list_pos = pos;

        isInternetPresent = cd.isConnectingToInternet();

        if (isInternetPresent){
            if (from == 1) {
                captureImage(1);
            } else if (from == 2) {
                if (imageCapture_list.get(list_pos).getLocal_image_path1() != null) {
                    captureImage(2);
                } else {
                    Toast.makeText(getActivity(), "Please Capture first image before capture second image ", Toast.LENGTH_LONG).show();
                }
            } else if (from == 3) {
                if (imageCapture_list.get(list_pos).getLocal_image_path1() != null && imageCapture_list.get(list_pos).getLocal_image_path2() != null) {
                    captureImage(3);
                } else {
                    Toast.makeText(getActivity(), "Please Capture first and second image before capture third image ", Toast.LENGTH_LONG).show();
                }
            }
        }else {
            Toast.makeText(getActivity(), AppConstants.NO_INTERNET_CONNECTED,Toast.LENGTH_LONG).show();
        }
    }

    private void getMainList() {

        Observer<List<SampleEntity>> sampleObserver = new Observer<List<SampleEntity>>() {
            @Override
            public void onChanged(List<SampleEntity> sampleEntities) {
                sample_list.clear();
                sample_list.addAll(sampleEntities);

                System.out.println("xxx_size" + sample_list.size());

                sampleEntityView = sample_list.get(0);
                local_sample_id = sampleEntityView.getLocalSampleId();

                imageCapture_list = sampleEntityView.getFishtype_pics();

             /*   for (int i = 0; i < sampleEntityView.getFishtypes().size(); i++) {

                    ImageCapturePojo image_pojo = new ImageCapturePojo();
                    image_pojo.setFishtype(sampleEntityView.getFishtypes().get(i).getFishtype());

                    imageCapture_list.add(image_pojo);

                }*/

                pd.cancel();

                adapter = new ImageCaptureAdapter(getActivity(), imageCapture_list);
                adapter.setOnItemClickListener(CollectionStage_second.this::onItemImageClicked);
                recycler_image_capture.setAdapter(adapter);

            }
        };

        sampleListViewModel = ViewModelProviders.of(this).get(SampleListViewModel.class);
        sampleListViewModel.samplelist.observe(getActivity(), sampleObserver);

    }
    @Override
    public void onStop() {
        super.onStop();

        sampleEntityView.setFishtype_pics(imageCapture_list);

        sampleListViewModel.UpdateSample(sampleEntityView);
    }
}
