package com.aleksa.syndroid.activities.connect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.activities.connect.favourites.FavouritesAdapter;
import com.aleksa.syndroid.activities.connect.favourites.OnStartDragListener;
import com.aleksa.syndroid.activities.connect.favourites.SimpleItemTouchHelperCallback;
import com.aleksa.syndroid.objects.server.models.Server;
import com.aleksa.syndroid.objects.server.repositories.ServerRepository;
import com.aleksa.syndroid.managers.ThemeManager;

import java.util.LinkedList;

public class ConnectActivity extends AppCompatActivity implements OnStartDragListener
{
    ServerRepository repository;
    ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (ThemeManager.isNightModeOn(this)) {
            setTheme(R.style.ConnectActivityDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        RecyclerView recyclerView = findViewById(R.id.favourites_recycler_view);
        LinkedList<Server> list = new LinkedList<>();

        list.add(new Server("192.168.1.101", "AleksaDTC"));
        list.add(new Server("192.168.1.102", "AleksaLPT"));
        list.add(new Server("192.168.1.103", "Aleksa MacMini"));
        list.add(new Server("192.168.1.104", "ZoranDTC"));
        list.add(new Server("192.168.1.105", "MatijaLPT"));
        list.add(new Server("192.168.1.106", "NevenaDTC"));

        FavouritesAdapter favouritesAdapter = new FavouritesAdapter(list, this);
        recyclerView.setAdapter(favouritesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(favouritesAdapter);

        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        repository = new ServerRepository(getApplication());
    }

    public void scanQrCode(View view)
    {
        ThemeManager.toggleNightMode(this);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder)
    {
        itemTouchHelper.startDrag(viewHolder);
    }
}
