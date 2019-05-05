package com.liuchen.photosshow.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Author: 刘晨
 * Date: 2019/4/30 16:48
 */
public class LocationsInfo implements Parcelable {
    private List<Location> locations;

    protected LocationsInfo(Parcel in) {
        locations = in.createTypedArrayList(Location.CREATOR);
    }

    public LocationsInfo() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(locations);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LocationsInfo> CREATOR = new Creator<LocationsInfo>() {
        @Override
        public LocationsInfo createFromParcel(Parcel in) {
            return new LocationsInfo(in);
        }

        @Override
        public LocationsInfo[] newArray(int size) {
            return new LocationsInfo[size];
        }
    };

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public static class Location implements Parcelable {
        private String url;
        private float amplification;
        private float fromXDelta;
        private float fromYDelta;

        public Location() {
        }

        protected Location(Parcel in) {
            url = in.readString();
            amplification = in.readFloat();
            fromXDelta = in.readFloat();
            fromYDelta = in.readFloat();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(url);
            dest.writeFloat(amplification);
            dest.writeFloat(fromXDelta);
            dest.writeFloat(fromYDelta);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Location> CREATOR = new Creator<Location>() {
            @Override
            public Location createFromParcel(Parcel in) {
                return new Location(in);
            }

            @Override
            public Location[] newArray(int size) {
                return new Location[size];
            }
        };

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public float getAmplification() {
            return amplification;
        }

        public void setAmplification(float amplification) {
            this.amplification = amplification;
        }

        public float getFromXDelta() {
            return fromXDelta;
        }

        public void setFromXDelta(float fromXDelta) {
            this.fromXDelta = fromXDelta;
        }

        public float getFromYDelta() {
            return fromYDelta;
        }

        public void setFromYDelta(float fromYDelta) {
            this.fromYDelta = fromYDelta;
        }
    }
}
