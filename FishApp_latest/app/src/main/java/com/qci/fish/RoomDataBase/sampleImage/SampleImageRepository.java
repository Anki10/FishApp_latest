package com.qci.fish.RoomDataBase.sampleImage;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.qci.fish.RoomDataBase.AppDataBase;
import com.qci.fish.RoomDataBase.sample.SampleRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SampleImageRepository {

    private AppDataBase mDataBase;

    private static SampleImageRepository ourInstance;

    private Executor mExecutor = Executors.newSingleThreadExecutor();


    public static SampleImageRepository getInstance(Context context){
        return ourInstance = new SampleImageRepository(context);
    }

    private SampleImageRepository(Context context){
        this.mDataBase = AppDataBase.getAppDataBase(context);
    }

    public void addSampleImageEntity(List<SampleImageEntity>sampleImageEntityList){

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDataBase.sampleImageDao().insertAll(sampleImageEntityList);
            }
        });

    }

    public void insertSampleImage(SampleImageEntity sampleImageEntity){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDataBase.sampleImageDao().insert(sampleImageEntity);
            }
        });
    }

    public void updateSampleImageEntity(SampleImageEntity sampleImageEntity){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
               mDataBase.sampleImageDao().update(sampleImageEntity);
            }
        });
    }

    public LiveData<SampleImageEntity>getSampleImageById(int sampleId){
        return mDataBase.sampleImageDao().getSampleBYID(sampleId);
    }

    public LiveData<List<SampleImageEntity>> getAllImageSample(int sampleId){
        return mDataBase.sampleImageDao().getSampleList(sampleId);
    }
}
