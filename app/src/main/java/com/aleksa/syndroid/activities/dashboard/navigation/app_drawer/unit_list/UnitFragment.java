package com.aleksa.syndroid.activities.dashboard.navigation.app_drawer.unit_list;

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
import com.aleksa.syndroid.activities.dashboard.navigation.app_drawer.unit_list.adapters.UnitListAdapter;
import com.aleksa.syndroid.activities.dashboard.navigation.app_drawer.unit_list.interfaces.UnitActionListener;
import com.aleksa.syndroid.activities.dashboard.navigation.app_drawer.unit_list.interfaces.UnitSwipeTouchCallback;
import com.aleksa.syndroid.activities.dashboard.navigation.listeners.UnitSelectListener;
import com.aleksa.syndroid.objects.unit_item.models.Unit;
import com.aleksa.syndroid.objects.unit_item.repositories.UnitRepository;

import java.util.LinkedList;
import java.util.List;

public class UnitFragment extends Fragment implements UnitActionListener
{
    private RecyclerView recyclerView;
    private UnitSelectListener listener;
    private UnitRepository unitRepository;
    private UnitListAdapter unitListAdapter;

    public UnitFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View layout = inflater.inflate(R.layout.unit_app_drawer_list_fragment, container, false);
        recyclerView = layout.findViewById(R.id.recycler_view);

        // Adapter creation
        unitListAdapter = new UnitListAdapter(new LinkedList<>(), this);
        recyclerView.setAdapter(unitListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Setting up touch listener
        ItemTouchHelper.Callback callback = new UnitSwipeTouchCallback(unitListAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // server repository initialization
        unitRepository = new UnitRepository(getActivity().getApplication());

        return layout;
    }

    private void refreshData()
    {
        unitRepository.all().then(data -> {
            unitListAdapter.setUnitList((List<Unit>) data);

            recyclerView.invalidate();
        });
    }

    @Override
    public void onStart()
    {
        refreshData();

        super.onStart();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (!(context instanceof UnitSelectListener)) {
            throw new RuntimeException(context.toString() + " must implement OnUnitSelect");
        }

        listener = (UnitSelectListener) context;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();

        listener = null;
    }

    @Override
    public void handleOrderChange(Unit one, Unit two)
    {
        unitRepository.updateAll(one, two).then(response -> listener.handleUnitOrderChange(one, two));
    }

    @Override
    public void handleUnitSelect(Unit unit)
    {
        listener.handleUnitSelect(unit);
    }
}
