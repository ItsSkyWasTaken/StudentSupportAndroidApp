package com.github.itsskywastaken.studentsupport.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Course implements Parcelable {

    public static final String[] DAYS = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    private String name;

    private long start;

    private long end;

    private long[] meetings;

    public Course(String name, long start, long end, long[] meetings) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.meetings = meetings;
    }

    protected Course(Parcel in) {
        this.name = in.readString();
        this.start = in.readLong();
        this.end = in.readLong();
        this.meetings = in.createLongArray();
    }

    public static final Creator<Course> CREATOR = new Creator<>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
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
        dest.writeLongArray(this.meetings);
    }

    public String getName() {
        return this.name;
    }

    public String getMeetings() {
        StringBuilder result = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for(long meeting : this.meetings) {
            int start = (int) (meeting >>> 32);
            int end = (int) (meeting & 0xFFFFFFFFL);

            String day = DAYS[start / 86400];
            int startSeconds = start % 86400;
            int endSeconds = end % 86400;

            LocalTime startTime = LocalTime.of(startSeconds / 3600, startSeconds % 3600 / 60);
            LocalTime endTime = LocalTime.of(endSeconds / 3600, endSeconds % 3600 / 60);

            result.append(day).append(" ").append(formatter.format(startTime)).append("–").append(formatter.format(endTime)).append(", ");
        }

        return result.delete(result.length() - 2, result.length()).toString();
    }
}
