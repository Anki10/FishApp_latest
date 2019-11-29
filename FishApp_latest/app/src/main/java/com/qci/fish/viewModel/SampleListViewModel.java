package com.qci.fish.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.qci.fish.RoomDataBase.sample.SampleEntity;
import com.qci.fish.RoomDataBase.sample.SampleRepository;

import java.util.ArrayList;
import java.util.List;

public class SampleListViewModel extends AndroidViewModel {

    public LiveData<List<SampleEntity>> samplelist;
    private SampleRepository sampleRepository;


    public SampleListViewModel(@NonNull Application application) {
        super(application);

        sampleRepository = SampleRepository.getInstance(application.getApplicationContext());
        samplelist = sampleRepository.sampleEntities_list;
    }


    public void addSample(SampleEntity sampleEntity, int req_id) {
        sampleRepository.addSampleData(sampleEntity, req_id);
    }

    public void UpdateSample(SampleEntity sampleEntity) {
        sampleRepository.UpdateSampleData(sampleEntity);
    }

    public void getSampleById(int sample_id) {
        sampleRepository.getSampleId(sample_id);
    }

    public void deleteSample(SampleEntity sampleEntity) {
        sampleRepository.DeleteSample(sampleEntity);
    }
}
