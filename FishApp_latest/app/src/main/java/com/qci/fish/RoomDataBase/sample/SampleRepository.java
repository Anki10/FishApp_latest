package com.qci.fish.RoomDataBase.sample;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.qci.fish.RoomDataBase.AppDataBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SampleRepository {

    private long value;

    private AppDataBase mDataBase;

    private static SampleRepository ourInstance;

    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public LiveData<List<SampleEntity>> sampleEntities_list;


    public SampleRepository(AppDataBase appDataBase) {
        this.mDataBase = appDataBase;
    }

    public static SampleRepository getInstance(Context context) {
        return ourInstance = new SampleRepository(context);
    }

    private SampleRepository(Context context) {
        this.mDataBase = AppDataBase.getAppDataBase(context);
        sampleEntities_list = getAppSampleData();

    }

    public void addSampleData(SampleEntity sampleEntity, int req_id) {

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDataBase.SampleDao().insert(sampleEntity);
            }
        });
    }


    public void UpdateSampleData(SampleEntity sampleEntity) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
    /*          mDataBase.SampleDao().UpdateData(sampleEntity.getLocalSampleId(),sampleEntity.getLocation_name(),sampleEntity.getSample_Id(),sampleEntity.getSample_collection_time(),
                      sampleEntity.getSample_available(),sampleEntity.getTruck_number(),sampleEntity.getTruck_driver_name(),sampleEntity.getTruck_driver_mobile_number(),sampleEntity.getConsignee_name(),sampleEntity.getConsignee_number(),sampleEntity.getFda_licence_number(),sampleEntity.getFish_type(),sampleEntity.getFish_quantity());*/


                mDataBase.SampleDao().updateData(sampleEntity);

            }
        });
    }

    public void DeleteSample(SampleEntity sampleEntity) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDataBase.SampleDao().SampleDelete(sampleEntity);
            }
        });
    }

    private LiveData<List<SampleEntity>> getAppSampleData() {
        return mDataBase.SampleDao().getData();
    }

    public LiveData<SampleEntity> getSampleId(int sampleId) {
        return mDataBase.SampleDao().getSampleEntity(sampleId);
    }

}
