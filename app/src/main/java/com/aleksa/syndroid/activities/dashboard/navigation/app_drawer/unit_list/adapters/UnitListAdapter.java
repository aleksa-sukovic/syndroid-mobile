package com.aleksa.syndroid.activities.dashboard.navigation.app_drawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.activities.connect.favourites.adapters.ItemTouchHelperAdapter;
import com.aleksa.syndroid.objects.unit_item.models.Unit;

import java.util.Collections;
import java.util.List;

public class UnitListAdapter extends RecyclerView.Adapter<UnitListAdapter.ViewHolder> implements ItemTouchHelperAdapter
{
    private List<Unit>               unitList;
    private OnUnitSelectListener actionListener;

    public UnitListAdapter(List<Unit> units, OnUnitSelectListener actionListener) {
        this.unitList     = units;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public UnitListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context         = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        return new UnitListAdapter.ViewHolder(inflater.inflate(R.layout.unit_app_drawer_list_item, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull UnitListAdapter.ViewHolder viewHolder, int i)
    {
        Unit unit = unitList.get(i);
    }

    @Override
    public int getItemCount()
    {
        return unitList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition)
    {
        Unit first  = unitList.get(fromPosition);
        Unit second = unitList.get(toPosition);

        first.setPosition(toPosition);
        second.setPosition(fromPosition);
        actionListener.onOrderChange(first, second);

        Collections.swap(unitList, fromPosition, toPosition);

        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position)
    {
        unitList.remove(position);
        notifyItemRemoved(position);
    }

    public void setServerList(List<Unit> list)
    {
        this.unitList.clear();
        this.unitList.addAll(list);

        new Handler(Looper.getMainLooper()).post(this::notifyDataSetChanged);
    }

    public List<Unit> getServerList()
    {
        return this.unitList;
    }

    public void removeServer(Unit unit)
    {
        this.unitList.remove(unit);

        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }

    public interface OnUnitSelectListener {
        void onOrderChange(Unit first, Unit second);
    }
}
