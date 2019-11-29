package com.qci.fish.RoomDataBase.sampleImage;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sample_image")
public class SampleImageEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "localSampleId")
    private int localSampleId;
    @ColumnInfo(name = "localFilePath")
    private String localFilePath;
    @ColumnInfo(name = "remoteFileName")
    private String remoteFileName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocalSampleId() {
        return localSampleId;
    }

    public void setLocalSampleId(int localSampleId) {
        this.localSampleId = localSampleId;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public void setRemoteFileName(String remoteFileName) {
        this.remoteFileName = remoteFileName;
    }
}
