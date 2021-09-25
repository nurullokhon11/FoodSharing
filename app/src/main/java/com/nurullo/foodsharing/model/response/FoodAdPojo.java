package com.nurullo.foodsharing.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodAdPojo {
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("weight")
    @Expose
    private Long weight;

    @SerializedName("exp_date")
    @Expose
    private String expDate;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("type")
    @Expose
    private Long type;

    @SerializedName("typeName")
    @Expose
    private String typeName;

    @SerializedName("ownerId")
    @Expose
    private String ownerId;

    @SerializedName("reserved")
    @Expose
    private Boolean reserved;

    @SerializedName("__v")
    @Expose
    private Long v;

    public FoodAdPojo(String id, String name, String address, String description,
                      Long weight, String expDate, String image, Long type, String typeName,
                      String ownerId, Boolean reserved) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.weight = weight;
        this.expDate = expDate;
        this.image = image;
        this.type = type;
        this.typeName = typeName;
        this.ownerId = ownerId;
        this.reserved = reserved;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public Long getWeight() {
        return weight;
    }

    public String getImage() {
        return image;
    }

    public String getExpDate() {
        return expDate;
    }

    public Long getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public Long getV() {
        return v;
    }
}
