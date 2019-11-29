package com.qci.fish.RoomDataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.qci.fish.RoomDataBase.sample.ImageTypeConverator;
import com.qci.fish.RoomDataBase.sample.QRCodeTypeConverator;
import com.qci.fish.RoomDataBase.sample.ResultConverator;
import com.qci.fish.RoomDataBase.sample.SampleDao;
import com.qci.fish.RoomDataBase.sample.SampleEntity;
import com.qci.fish.RoomDataBase.sample.TypeConverator;
import com.qci.fish.RoomDataBase.sampleImage.SampleImageDao;
import com.qci.fish.RoomDataBase.sampleImage.SampleImageEntity;

@Database(entities = {SampleEntity.class, SampleImageEntity.class}, version = 3)
@TypeConverters({TypeConverator.class, ImageTypeConverator.class, ResultConverator.class, QRCodeTypeConverator.class})
public abstract class AppDataBase extends RoomDatabase {

  public static final String DATABASE_NAME = "fishApp.db";

  private static AppDataBase instance;

  public abstract SampleDao SampleDao();

  public abstract SampleImageDao sampleImageDao();

  public static AppDataBase getAppDataBase(Context context){

      if (instance == null){
         instance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,DATABASE_NAME).fallbackToDestructiveMigration().build();
      }
      return instance;
  }
}
