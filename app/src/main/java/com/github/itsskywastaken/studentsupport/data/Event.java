package com.github.itsskywastaken.studentsupport.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Event implements Parcelable {
    private String name;
    private long start;
    private long end;

    public Event(String name, long start, long end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    protected Event(Parcel in) {
        this.name = in.readString();
        this.start = in.readLong();
        this.end = in.readLong();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.start);
        dest.writeLong(this.end);
    }
}
