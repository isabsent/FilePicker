package com.github.isabsent.filepicker.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ListView;

public class SimpleFilePickerItem implements Parcelable {
    private long id;
    private Item item;

    @Override
    public String toString() {
        return item.toString();
    }

    public SimpleFilePickerItem(Item item){
        this(item, ListView.INVALID_ROW_ID);
    }

    public SimpleFilePickerItem(Item item, long id){
        this.id = id;
        this.item = item;
    }

    private SimpleFilePickerItem(Parcel in) {
        id = in.readLong();
        item = in.readParcelable(Item.class.getClassLoader());
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(item, flags);
    }

    public static final Creator<SimpleFilePickerItem> CREATOR = new Creator<SimpleFilePickerItem>() {
        @Override
        public SimpleFilePickerItem createFromParcel(Parcel in) {
            return new SimpleFilePickerItem(in);
        }

        @Override
        public SimpleFilePickerItem[] newArray(int size) {
            return new SimpleFilePickerItem[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
