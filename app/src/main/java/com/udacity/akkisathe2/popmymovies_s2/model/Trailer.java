package com.udacity.akkisathe2.popmymovies_s2.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 836158 on 23-05-2016.
 */
public class Trailer {

    @SerializedName("source")
    private String source;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("size")
    private String size;


    public String getSource ()
    {
        return source;
    }

    public void setSource (String source)
    {
        this.source = source;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }

}
