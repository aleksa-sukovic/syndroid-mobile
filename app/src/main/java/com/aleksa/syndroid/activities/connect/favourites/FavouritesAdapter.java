package com.aleksa.syndroid.activities.connect.favourites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.objects.server.models.Server;

import java.util.Collections;
import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> implements ItemTouchHelperAdapter
{

    private List<Server> serverList;
    private OnStartDragListener onStartDrag;

    public FavouritesAdapter(List<Server> servers, OnStartDragListener listener) {
        this.serverList  = servers;
        this.onStartDrag = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View serverView = inflater.inflate(R.layout.server_favourites_item, parent, false);

        return new ViewHolder(serverView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        Server server = serverList.get(i);

        viewHolder.serverName.setText(server.getName());
        viewHolder.serverIp.setText(server.getIp());
        viewHolder.reorderHandle.setOnTouchListener((v, event) -> {
            if (event.getActionMasked() ==
                MotionEvent.ACTION_DOWN) {
                onStartDrag.onStartDrag(viewHolder);
            }

            v.performClick();
            return false;
        });
    }

    @Override
    public int getItemCount()
    {
        return serverList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition)
    {
        Server first  = serverList.get(fromPosition);
        Server second = serverList.get(toPosition);

        first.setPosition(toPosition);
        second.setPosition(fromPosition);

        Collections.swap(serverList, fromPosition, toPosition);

        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position)
    {
        serverList.remove(position);
        notifyItemRemoved(position);
    }

    public void setServerList(List<Server> list) {
        this.serverList = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView serverName;
        TextView serverIp;
        ImageView reorderHandle;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            serverName    = itemView.findViewById(R.id.server_name);
            serverIp      = itemView.findViewById(R.id.server_ip);
            reorderHandle = itemView.findViewById(R.id.reorder_handle);
        }
    }
}
