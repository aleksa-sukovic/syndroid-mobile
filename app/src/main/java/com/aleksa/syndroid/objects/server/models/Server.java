package com.aleksa.syndroid.objects.server.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

@Entity(tableName = "servers")
public class Server implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String name;

    @NonNull
    private String ip;

    private int position = 99999;

    @Ignore
    public Server(@NonNull String ip, @NonNull String name) {
        this.ip = ip;
        this.name = name;
    }

    public Server(@NonNull String ip, @NonNull String name, int position) {
        this.ip = ip;
        this.name = name;
        this.position = position;
    }

    public Server(Parcel in)
    {
        this.id = in.readLong();
        this.position = in.readInt();
        this.name = Objects.requireNonNull(in.readString(), "Name must not be null");
        this.ip = Objects.requireNonNull(in.readString(), "IP must not be null");
    }

    @NonNull
    public String getIp() {
        return this.ip;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    public int getPosition() {
        return this.position;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setName(@NonNull String name)
    {
        this.name = name;
    }

    public void setIp(@NonNull String ip)
    {
        this.ip = ip;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    @Override
    public boolean equals(@Nullable Object obj)
    {
        if (!(obj instanceof Server)) {
            return false;
        }

        return ((Server) obj).id == this.id;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(this.id);
        dest.writeInt(this.position);
        dest.writeString(this.name);
        dest.writeString(this.ip);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Server>() {
        public Server createFromParcel(Parcel in) {
            return new Server(in);
        }

        public Server[] newArray(int size) {
            return new Server[size];
        }
    };
}
