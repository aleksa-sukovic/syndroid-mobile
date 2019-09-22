package com.aleksa.syndroid.activities.connect.favourites.interfaces;

import android.support.v7.widget.RecyclerView;
import com.aleksa.syndroid.objects.server.models.Server;

public interface OnFavouritesActionListener
{
    void onFavouriteDelete(Server server);
    void onFavouriteEdit(Server server);
    void onFavouriteSelect(Server server);
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
    void onOrderChange(Server one, Server two);
}
