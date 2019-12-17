package com.qci.fish.RoomDataBase.sample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SampleDao {

      @Insert
       void insert(SampleEntity sampleEntity);

        @Insert
        void insertAll(List<SampleEntity>sampleEntities);

        @Query("SELECT * FROM samples ORDER BY localSampleId DESC")
        LiveData<List<SampleEntity>> getData();

        @Query("SELECT * FROM samples Where localSampleId=:localSampleId")
        LiveData<SampleEntity> getSampleEntity(int localSampleId);

         @Update
         void updateData(SampleEntity sampleEntity);


/*        @Query("UPDATE samples set Location_Name = :location_name, Sample_Id = :sample_Id, Sample_collection_Time = :sample_collection_time,Sample_available = :Sample_available,Truck_number = :truck_number, Truck_driver_name = :truck_driver_name , Truck_driver_mobile_number = :truck_driver_mobile_number, Consignee_name = :consignee_name , Consignee_number = :consignee_number , fda_licence_number = :fda_licence_number, Fish_type = :fish_type, Fish_quantity = :fish_quantity WHERE localSampleId = :localSampleId")
         void UpdateData(int localSampleId, String location_name, String sample_Id, String sample_collection_time,String Sample_available, String truck_number,String truck_driver_name,String truck_driver_mobile_number,String consignee_name,String consignee_number,String fda_licence_number,String fish_type,String fish_quantity);*/

        @Delete
        void SampleDelete(SampleEntity sampleEntity);

        @Query("DELETE FROM samples")
         void DeleteAll();
}
