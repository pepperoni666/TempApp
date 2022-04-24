package com.example.wirtualnaszafa.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Suite {
    @SerializedName("id")
    public int id;
    @SerializedName("user_id")
    public String userId;
    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("image_path")
    public String imagePath;
    @SerializedName("target_image_path")
    public String targetImagePath;
    @SerializedName("token")
    public String token;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("tags")
    public List<SuiteTag> tags;
}

