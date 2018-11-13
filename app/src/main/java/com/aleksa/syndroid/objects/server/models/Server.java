package com.aleksa.syndroid.objects.server.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "servers")
public class Server
{
    @PrimaryKey(autoGenerate = true)
    private int id;

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

    public String getIp() {
        return this.ip;
    }

    public String getName() {
        return this.name;
    }

    public int getPosition() {
        return this.position;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id)
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

}
