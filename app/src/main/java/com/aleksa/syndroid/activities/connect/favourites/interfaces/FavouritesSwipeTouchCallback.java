package com.aleksa.syndroid.activities.connect.favourites.interfaces;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.aleksa.syndroid.activities.connect.favourites.adapters.ItemTouchHelperAdapter;

public class FavouritesSwipeTouchCallback extends ItemTouchHelper.Callback
{

    private final ItemTouchHelperAdapter adapter;

    public FavouritesSwipeTouchCallback(ItemTouchHelperAdapter adapter)
    {
        this.adapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled()
    {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled()
    {
        return false;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder)
    {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
    {
        adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i)
    {
        adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
