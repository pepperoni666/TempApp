package com.example.wirtualnaszafa.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("email_verified_at")
    public String emailVerifiedAt;
    @SerializedName("two_factor_confirmed_at")
    public String twoFactorConfirmedAt;
    @SerializedName("current_team_id")
    public String currentTeamId;
    @SerializedName("profile_photo_path")
    public String profilePhotoPath;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("profile_photo_url")
    public String profilePhotoUrl;
}
