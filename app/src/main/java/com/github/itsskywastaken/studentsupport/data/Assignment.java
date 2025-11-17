package com.github.itsskywastaken.studentsupport.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Assignment implements Parcelable {
    private String name;
    private long due;

    public Assignment(String name, long due) {
        this.name = name;
        this.due = due;
    }

    protected Assignment(Parcel in) {
        this.name = in.readString();
        this.due = in.readLong();
    }

    public static final Creator<Assignment> CREATOR = new Creator<>() {
        @Override
        public Assignment createFromParcel(Parcel in) {
            return new Assignment(in);
        }

        @Override
        public Assignment[] newArray(int size) {
            return new Assignment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.due);
    }
}
