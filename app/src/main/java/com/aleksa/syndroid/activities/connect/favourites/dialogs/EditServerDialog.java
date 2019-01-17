package com.aleksa.syndroid.activities.connect.favourites.dialogs;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.objects.server.models.Server;
import com.aleksa.syndroid.objects.server.repositories.ServerRepository;

public class EditServerDialog extends DialogFragment
{

    private Server server;
    private ServerRepository serverRepository;
    private OnUpdate onUpdate;

    public static EditServerDialog getInstance(Server server, ServerRepository serverRepository, OnUpdate onUpdate)
    {
        EditServerDialog fragment = new EditServerDialog();

        fragment.setServer(server);
        fragment.setServerRepository(serverRepository);
        fragment.setOnUpdateListener(onUpdate);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_favourites_edit_dialog, container, false);

        // Title
        TextView title = view.findViewById(R.id.title);
        String titleText = getString(R.string.favourites_fragment_edit_dialog_title) + " " + server.getName();
        title.setText(titleText);

        // Edit fields
        EditText serverNameField = view.findViewById(R.id.server_name);
        serverNameField.setText(server.getName());

        EditText serverIpField = view.findViewById(R.id.server_ip);
        serverIpField.setText(server.getIp());

        // Dismiss button
        TextView dismissBtn = view.findViewById(R.id.dismiss_btn);
        dismissBtn.setOnClickListener(e -> dismiss());

        // Confirm button
        TextView confirmBtn = view.findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(e -> {
            if (!validate(serverNameField.getText().toString(), serverIpField.getText().toString())) {
                return;
            }

            server.setName(serverNameField.getText().toString());
            server.setIp(serverIpField.getText().toString());

            serverRepository.update(server).then(data -> new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(getActivity(), getString(R.string.dialog_edit_server_update_success), Toast.LENGTH_SHORT).show();

                if (onUpdate != null) {
                    onUpdate.fire(server);
                }

                dismiss();
            }));
        });

        return view;
    }

    private boolean validate(String serverName, String serverIp)
    {
        if (serverName == null || serverName.length() < 1) {
            Toast.makeText(getActivity(), getString(R.string.dialog_edit_server_name_error), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (serverIp == null || serverIp.length() < 1) {
            Toast.makeText(getActivity(), getString(R.string.dialog_edit_server_ip_error), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void setServer(Server server)
    {
        this.server = server;
    }

    private void setServerRepository(ServerRepository repository)
    {
        this.serverRepository = repository;
    }

    private void setOnUpdateListener(OnUpdate onUpdate)
    {
        this.onUpdate = onUpdate;
    }

    public interface OnUpdate
    {
        void fire(Server server);
    }
}
