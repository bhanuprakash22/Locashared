package com.everything22.bhanuprakashreddy.locashared.modelclasses;



public class Introitems {

    private int Image_ids;
    private String Image_Text;


    public Introitems() {
    }

    public Introitems(int image_ids, String image_Text) {
        this.Image_ids = image_ids;
        this.Image_Text = image_Text;
    }

    public int getImage_ids() {
        return Image_ids;
    }

    public void setImage_ids(int image_ids) {
        this.Image_ids = image_ids;
    }

    public String getImage_Text() {
        return Image_Text;
    }

    public void setImage_Text(String image_Text) {
        this.Image_Text = image_Text;
    }
}
