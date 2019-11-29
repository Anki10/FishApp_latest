package com.qci.fish.RoomDataBase.sampleImage;

import android.icu.text.Replaceable;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SampleImageDao {

    @Insert
    void insert(SampleImageEntity sampleImageEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SampleImageEntity>sampleImageEntityList);

    @Update
    void update(SampleImageEntity sampleImageEntity);

    @Query("SELECT * FROM sample_image where localSampleId = :localSampleId")
    LiveData<List<SampleImageEntity>> getSampleList(int localSampleId);


    @Query("SELECT * FROM sample_image where localSampleId = :localSampleId")
    LiveData<SampleImageEntity> getSampleBYID(int localSampleId);

     @Delete
     void delete(SampleImageEntity sampleImageEntity);


}
