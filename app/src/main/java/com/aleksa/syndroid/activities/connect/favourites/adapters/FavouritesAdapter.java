package com.aleksa.syndroid.activities.connect.favourites.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.aleksa.syndroid.R;
import com.aleksa.syndroid.activities.connect.favourites.interfaces.OnFavouritesActionListener;
import com.aleksa.syndroid.objects.server.models.Server;
import com.daimajia.swipe.SwipeLayout;
import java.util.Collections;
import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> implements ItemTouchHelperAdapter
{
    private List<Server> serverList;
    private OnFavouritesActionListener actionListener;

    public FavouritesAdapter(List<Server> servers, OnFavouritesActionListener actionListener)
    {
        this.serverList     = servers;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        return new ViewHolder(inflater.inflate(R.layout.fragment_favourites_item, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        Server server = serverList.get(i);

        // setting up server data
        viewHolder.serverName.setText(server.getName());
        viewHolder.serverIp.setText(server.getIp());

        // setting up reorder handle listener
        viewHolder.reorderHandle.setOnTouchListener((v, event) -> {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                actionListener.onStartDrag(viewHolder);
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
        actionListener.onOrderChange(first, second);

        Collections.swap(serverList, fromPosition, toPosition);

        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position)
    {
        serverList.remove(position);
        notifyItemRemoved(position);
    }

    public void setServerList(List<Server> list)
    {
        this.serverList.clear();
        this.serverList.addAll(list);
        new Handler(Looper.getMainLooper()).post(this::notifyDataSetChanged);
    }

    public List<Server> getServerList()
    {
        return this.serverList;
    }

    public void removeServer(Server server)
    {
        this.serverList.remove(server);
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements SwipeLayout.SwipeListener
    {
        TextView serverIp;
        TextView serverName;
        ImageView reorderHandle;
        SwipeLayout swipeLayout;
        ConstraintLayout frontView;
        ImageView editBtn, deleteBtn;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            // getting view references
            serverName    = itemView.findViewById(R.id.server_name);
            serverIp      = itemView.findViewById(R.id.server_ip);
            reorderHandle = itemView.findViewById(R.id.reorder_handle);
            swipeLayout   = itemView.findViewById(R.id.swipe_layout);
            frontView     = itemView.findViewById(R.id.front_view);
            editBtn       = itemView.findViewById(R.id.edit_btn);
            deleteBtn     = itemView.findViewById(R.id.delete_btn);

            // setting up swipe layout
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Right, itemView.findViewById(R.id.bottom_wrapper));
            swipeLayout.addSwipeListener(this);

            // setting up edit and delete buttons
            editBtn.setOnClickListener(v -> actionListener.onFavouriteEdit(serverList.get(getAdapterPosition())));
            deleteBtn.setOnClickListener(v -> actionListener.onFavouriteDelete(serverList.get(getAdapterPosition())));
            frontView.setOnClickListener(v -> actionListener.onFavouriteSelect(serverList.get(getAdapterPosition())));
        }

        @Override
        public void onStartOpen(SwipeLayout layout)
        {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) frontView.getLayoutParams();
            params.setMargins(0, 0, 20, 0);
            frontView.setLayoutParams(params);
        }

        @Override
        public void onOpen(SwipeLayout layout)
        {
            //
        }

        @Override
        public void onStartClose(SwipeLayout layout)
        {
            //
        }

        @Override
        public void onClose(SwipeLayout layout)
        {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) frontView.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            frontView.setLayoutParams(params);
        }

        @Override
        public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset)
        {
            //
        }

        @Override
        public void onHandRelease(SwipeLayout layout, float xvel, float yvel)
        {
            //
        }
    }
}
