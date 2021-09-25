package com.nurullo.foodsharing.model.requestParams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateFoodAdPojo {
    @Expose
    @SerializedName("ownerId")
    private String ownerId;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("address")
    private String address;

    @Expose
    @SerializedName("type")
    private Long type;

    @Expose
    @SerializedName("typeName")
    private String typeName;

    @Expose
    @SerializedName("weight")
    private Long weight;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("image")
    private String image;

    @Expose
    @SerializedName("exp_date")
    private String exp_date;

    @Expose
    @SerializedName("latitude")
    private Double latitude;

    @Expose
    @SerializedName("longitude")
    private Double longitude;

    @Expose
    @SerializedName("reserved")
    private Boolean reserved;

    public CreateFoodAdPojo(String ownerId, String name, String address, Long type, String typeName,
                            Long weight, String description, String image, String exp_date,
                            Double latitude, Double longitude, Boolean reserved) {
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.type = type;
        this.typeName = typeName;
        this.weight = weight;
        this.description = description;
        this.image = image;
        this.exp_date = exp_date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reserved = reserved;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Long getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    public Long getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getExp_date() {
        return exp_date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Boolean getReserved() {
        return reserved;
    }
}
