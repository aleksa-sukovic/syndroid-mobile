package com.aleksa.syndroid.activities.connect.favourites;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.activities.connect.favourites.adapters.FavouritesAdapter;
import com.aleksa.syndroid.activities.connect.favourites.interfaces.OnFavouritesActionListener;
import com.aleksa.syndroid.activities.connect.favourites.interfaces.FavouritesSwipeTouchCallback;
import com.aleksa.syndroid.library.dialogs.confirm.ConfirmDialog;
import com.aleksa.syndroid.objects.server.models.Server;
import com.aleksa.syndroid.objects.server.repositories.ServerRepository;

import java.util.LinkedList;
import java.util.List;

public class FavouritesFragment extends Fragment implements OnFavouritesActionListener
{

    private OnFavouritesSelect listener;
    private ItemTouchHelper    itemTouchHelper;
    private ServerRepository   serverRepository;
    private FavouritesAdapter  favouritesAdapter;
    private TextView           placeholderText;

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
        View layout               = inflater.inflate(R.layout.fragment_favourites, container, false);
        RecyclerView recyclerView = layout.findViewById(R.id.recycler_view);
        placeholderText           = layout.findViewById(R.id.placeholder_text);

        // Adapter creation
        favouritesAdapter = new FavouritesAdapter(new LinkedList<>(), this);
        recyclerView.setAdapter(favouritesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Setting up touch listener
        ItemTouchHelper.Callback callback = new FavouritesSwipeTouchCallback(favouritesAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // server repository initialization
        serverRepository = new ServerRepository(getActivity().getApplication());
        serverRepository.all().then(data -> {
            favouritesAdapter.setServerList((List<Server>) data);
            recyclerView.invalidate();
            togglePlaceholderText();
        });

        return layout;
    }

    private void togglePlaceholderText()
    {
       if (favouritesAdapter.getServerList().size() < 1) {
           placeholderText.setVisibility(View.VISIBLE);
           return;
       }

       placeholderText.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (!(context instanceof OnFavouritesSelect)) {
            throw new RuntimeException(context.toString() + " must implement OnFavouritesSelect");
        }

        listener = (OnFavouritesSelect) context;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onFavouriteDelete(Server server)
    {
        ConfirmDialog.twoButton(() -> serverRepository.delete(server).then(data -> new Handler(Looper.getMainLooper()).post(() -> {
            favouritesAdapter.removeServer(server);

            togglePlaceholderText();
        })), null, getString(R.string.dialog_confirm_title), getString(R.string.dialog_confirm_delete_resource_message) + ": \"" + server.getName() + "\" ?")
            .show(getActivity().getSupportFragmentManager(), "");
    }

    @Override
    public void onFavouriteEdit(Server server)
    {
        Log.d("FavouritesFragment", "Edit " + server.getName());
    }

    @Override
    public void onFavouriteSelect(Server server)
    {
        listener.onFavouritesSelect(server);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder)
    {
        itemTouchHelper.startDrag(viewHolder);
    }

    public interface OnFavouritesSelect
    {
        void onFavouritesSelect(Server server);
    }
}
