package com.qci.fish.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.qci.fish.RoomDataBase.sample.SampleEntity;
import com.qci.fish.RoomDataBase.sample.SampleRepository;
import com.qci.fish.RoomDataBase.sampleImage.SampleImageEntity;
import com.qci.fish.RoomDataBase.sampleImage.SampleImageRepository;

import java.util.List;

public class SampleImageViewModel extends AndroidViewModel {

    public LiveData<List<SampleImageEntity>> sampleimagelist;
    private static SampleImageRepository mRepository;


    public SampleImageViewModel(@NonNull Application application,SampleImageRepository sampleImageRepository, int sampleId) {
        super(application);

        sampleimagelist = sampleImageRepository.getAllImageSample(sampleId);
    }

    public LiveData<List<SampleImageEntity>> getObservableImageSample(){
        return sampleimagelist;
    }


    public void addSampleImage(List<SampleImageEntity> sampleImageEntityList){
        mRepository.addSampleImageEntity(sampleImageEntityList);
    }

    public void insertSample(SampleImageEntity sampleImageEntity){
        mRepository.insertSampleImage(sampleImageEntity);
    }

    public void updateSample(SampleImageEntity sampleImageEntity){
        mRepository.updateSampleImageEntity(sampleImageEntity);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int mProductId;

        public Factory(@NonNull Application application, int productId) {
            mApplication = application;
            mProductId = productId;
            mRepository = SampleImageRepository.getInstance(application.getApplicationContext());
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new SampleImageViewModel(mApplication, mRepository, mProductId);
        }
    }

}
