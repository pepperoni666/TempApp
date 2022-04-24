package com.example.wirtualnaszafa.model;

import com.google.gson.annotations.SerializedName;

public class SuiteTag {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("token")
    public String token;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("pivot")
    public Pivot pivot;
}

