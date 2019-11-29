package com.qci.fish.pojo;

import androidx.room.ColumnInfo;

public class ImageCapturePojo {

    private String fishtype;
    private String local_image_path1;
    private String local_image_path2;
    private String local_image_path3;
    private String pic1;
    private String pic2;
    private String pic3;

    public String getFishtype() {
        return fishtype;
    }

    public void setFishtype(String fishtype) {
        this.fishtype = fishtype;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }





    public String getLocal_image_path1() {
        return local_image_path1;
    }

    public void setLocal_image_path1(String local_image_path1) {
        this.local_image_path1 = local_image_path1;
    }

    public String getLocal_image_path2() {
        return local_image_path2;
    }

    public void setLocal_image_path2(String local_image_path2) {
        this.local_image_path2 = local_image_path2;
    }

    public String getLocal_image_path3() {
        return local_image_path3;
    }

    public void setLocal_image_path3(String local_image_path3) {
        this.local_image_path3 = local_image_path3;
    }


}
