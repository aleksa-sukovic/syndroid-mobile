package com.aleksa.syndroid.activities.connect.favourites;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.objects.server.models.Server;

import java.util.LinkedList;

public class FavouritesFragment extends Fragment implements OnStartDragListener
{

    private FavouritesFragmentListener listener;
    private ItemTouchHelper            itemTouchHelper;

    public FavouritesFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View layout = inflater.inflate(R.layout.fragment_favourites, container, false);
        RecyclerView recyclerView = layout.findViewById(R.id.recycler_view);

        // Adapter creation
        FavouritesAdapter favouritesAdapter = new FavouritesAdapter(new LinkedList<>(), this);
        recyclerView.setAdapter(favouritesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Setting up touch listener
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(favouritesAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return layout;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (!(context instanceof FavouritesFragmentListener)) {
            throw new RuntimeException(context.toString() + " must implement FavouritesFragmentListener");
        }

        listener = (FavouritesFragmentListener) context;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder)
    {
        itemTouchHelper.startDrag(viewHolder);
    }

    public interface FavouritesFragmentListener
    {
        void onFavouritesSelect(Server server);
    }
}
