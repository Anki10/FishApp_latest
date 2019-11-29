package com.qci.fish.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.qci.fish.RoomDataBase.sample.SampleEntity;
import com.qci.fish.RoomDataBase.sample.SampleRepository;

public class SampleModel extends AndroidViewModel {

    public LiveData<SampleEntity> sampleEntityLiveData;


    public SampleModel(@NonNull Application application, SampleRepository sampleRepository, int sampleId) {
        super(application);

        sampleEntityLiveData = sampleRepository.getSampleId(sampleId);
    }

    public LiveData<SampleEntity> getObservableSample() {
        return sampleEntityLiveData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int mProductId;

        private final SampleRepository mRepository;

        public Factory(@NonNull Application application, int productId) {
            mApplication = application;
            mProductId = productId;
            mRepository = SampleRepository.getInstance(application.getApplicationContext());
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new SampleModel(mApplication, mRepository, mProductId);
        }
    }

}


