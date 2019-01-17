package com.aleksa.syndroid.activities.connect.favourites.adapters;

public interface ItemTouchHelperAdapter
{
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
