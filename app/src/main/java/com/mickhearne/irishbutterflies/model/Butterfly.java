package com.mickhearne.irishbutterflies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Butterfly implements Parcelable {
    private long id;
    private String name;
    private String latin_name;
    private String habitat;
    private String distribution;
    private String foodplant;
    private String wingspan;
    private String flight_period;
    private String description;
    private String image_thumb;
    private String image_large;

    public Butterfly() {
        super();
    }

    public Butterfly(Parcel in) {

        id = in.readLong();
        name = in.readString();
        latin_name = in.readString();
        habitat = in.readString();
        distribution = in.readString();
        foodplant = in.readString();
        wingspan = in.readString();
        flight_period = in.readString();
        description = in.readString();
        image_thumb = in.readString();
        image_large = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatinName() {
        return latin_name;
    }

    public void setLatinName(String latin_name) {
        this.latin_name = latin_name;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getFoodplant() {
        return foodplant;
    }

    public void setFoodplant(String foodplant) {
        this.foodplant = foodplant;
    }

    public String getWingspan() {
        return wingspan;
    }

    public void setWingspan(String wingspan) {
        this.wingspan = wingspan;
    }

    public String getFlightPeriod() {
        return flight_period;
    }

    public void setFlightPeriod(String flight_period) {
        this.flight_period = flight_period;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageThumb() {
        return image_thumb;
    }

    public void setImageThumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public String getImageLarge() {
        return image_large;
    }

    public void setImageLarge(String image_large) {
        this.image_large = image_large;
    }

    @Override
    public String toString() {
        return name + "\n" + latin_name + "\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(latin_name);
        dest.writeString(habitat);
        dest.writeString(distribution);
        dest.writeString(foodplant);
        dest.writeString(wingspan);
        dest.writeString(flight_period);
        dest.writeString(description);
        dest.writeString(image_thumb);
        dest.writeString(image_large);
    }

    public static final Creator<Butterfly> CREATOR = new Creator<Butterfly>() {

        @Override
        public Butterfly createFromParcel(Parcel source) {
            return new Butterfly(source);
        }

        @Override
        public Butterfly[] newArray(int size) {
            return new Butterfly[size];
        }
    };
}
