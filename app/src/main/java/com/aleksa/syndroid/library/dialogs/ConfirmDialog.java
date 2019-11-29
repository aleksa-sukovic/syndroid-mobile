package com.aleksa.syndroid.library.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aleksa.syndroid.R;

public class ConfirmDialog extends DialogFragment
{
    public static final int ONE_BUTTON = 214514;
    public static final int TWO_BUTTON = 214124;

    private OnConfirm onConfirm;
    private OnDismiss onDismiss;

    public static ConfirmDialog oneButton(OnConfirm onConfirm, String title, String message)
    {
        Bundle bundle = new Bundle();

        bundle.putInt("style", ONE_BUTTON);
        bundle.putString("title", title);
        bundle.putString("message", message);

        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setArguments(bundle);
        confirmDialog.setOnConfirmListener(onConfirm);

        return confirmDialog;
    }

    public static ConfirmDialog twoButton(OnConfirm onConfirm, OnDismiss onDismiss, String title, String message)
    {
        Bundle bundle = new Bundle();

        bundle.putInt("style", TWO_BUTTON);
        bundle.putString("title", title);
        bundle.putString("message", message);

        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setArguments(bundle);
        confirmDialog.setOnConfirmListener(onConfirm);
        confirmDialog.setOnDismissListener(onDismiss);

        return confirmDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        int style = getStyle();
        int layout = style == ONE_BUTTON ? R.layout.fragment_confirm_dialog_style_one : R.layout.fragment_confirm_dialog_style_two;

        View view = inflater.inflate(layout, container, false);

        if (style == ONE_BUTTON) {
            setupOneButtonDialog(view);
        } else {
            setupTwoButtonDialog(view);
        }

        return view;
    }

    private void setupOneButtonDialog(View view)
    {
        if (getArguments() == null) {
            return;
        }

        // Title view
        TextView title = view.findViewById(R.id.title);
        title.setText(getArguments().getString("title", "Default Title"));

        // Message view
        TextView message = view.findViewById(R.id.content);
        message.setText(getArguments().getString("message", "Default Message"));

        // Listeners
        TextView confirmBtn = view.findViewById(R.id.confirm_btn);
        confirmBtn.setText(getString(R.string.dialog_confirm_ok_button));
        confirmBtn.setOnClickListener(e -> {
            if (onConfirm != null) {
                onConfirm.fire();
            }

            dismiss();
        });
    }

    private void setupTwoButtonDialog(View view)
    {
        if (getArguments() == null) {
            return;
        }

        // Title view
        TextView title = view.findViewById(R.id.title);
        title.setText(getArguments().getString("title", "Default Title"));

        // Message view
        TextView message = view.findViewById(R.id.content);
        message.setText(getArguments().getString("message", "Default Message"));

        // Confirm button
        TextView confirmBtn = view.findViewById(R.id.confirm_btn);
        confirmBtn.setText(getString(R.string.dialog_confirm_confirm_button));
        confirmBtn.setOnClickListener(e -> {
            if (onConfirm != null) {
                onConfirm.fire();
            }

            dismiss();
        });

        // Dismiss button
        TextView dismissBtn = view.findViewById(R.id.dismiss_btn);
        dismissBtn.setText(getString(R.string.dialog_confirm_dismiss_button));
        dismissBtn.setOnClickListener(e -> {
            if (onDismiss != null) {
                onDismiss.fire();
            }

            dismiss();
        });
    }

    private int getStyle()
    {
        if (getArguments() == null) {
            return ONE_BUTTON;
        }

        return getArguments().getInt("style", ONE_BUTTON);
    }

    public void setOnConfirmListener(OnConfirm onConfirm)
    {
        this.onConfirm = onConfirm;
    }

    public void setOnDismissListener(OnDismiss onDismiss)
    {
        this.onDismiss = onDismiss;
    }

    public interface OnConfirm
    {
        void fire();
    }

    public interface OnDismiss
    {
        void fire();
    }
}
