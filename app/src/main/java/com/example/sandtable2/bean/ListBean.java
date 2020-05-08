package com.example.sandtable2.bean;

public class ListBean {
    private String text;//用来放文字的
    private int ImageID;//用来放图片的
    private String info;



    public ListBean(int imageID, String text, String info){
        this.ImageID = imageID;
        this.text = text;
        this.info = info;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
