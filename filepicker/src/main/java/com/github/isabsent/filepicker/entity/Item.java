package com.github.isabsent.filepicker.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private String label;
    private boolean isFile;

    public Item(String path, boolean isFile) {
        this.label = path;
        this.isFile = isFile;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
        dest.writeByte(isFile ? (byte) 1 : (byte) 0);
    }

    protected Item(Parcel in) {
        label = in.readString();
        isFile = in.readByte() != 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
