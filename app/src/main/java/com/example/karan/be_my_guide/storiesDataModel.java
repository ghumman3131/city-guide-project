package com.example.karan.be_my_guide;

/**
 * Created by Karan on 05-05-2018.
 */

public class storiesDataModel {

    public String date , title , description , image_url ;


    public storiesDataModel()
    {

    }

    public storiesDataModel(String date , String title , String description , String image_url)
    {
        this.date = date ;

        this.title = title;

        this.description = description ;

        this.image_url = image_url;
    }
}
