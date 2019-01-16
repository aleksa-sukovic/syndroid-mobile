package com.aleksa.syndroid.fragments.keyboard.buttons;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.library.router.request.OutgoingRequest;
import com.aleksa.syndroid.managers.ThemeManager;

import org.greenrobot.eventbus.EventBus;

public abstract class KeyboardButton extends FrameLayout implements View.OnClickListener, View.OnLongClickListener
{
    private TextView  textView;

    private boolean toggled;

    {
        setOnClickListener(this);
        setOnLongClickListener(this);
        setForegroundGravity(Gravity.CENTER);
    }

    protected abstract String getKeyCode();

    public KeyboardButton(@NonNull Context context)
    {
        super(context);

        initialize(context);
    }

    public KeyboardButton(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        initialize(context);
        applyAttributes(context, attrs);
    }

    public KeyboardButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        initialize(context);
        applyAttributes(context, attrs);
    }

    public KeyboardButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);

        initialize(context);
        applyAttributes(context, attrs);
    }

    private void initialize(Context context)
    {
        setBackground(getBackgroundDrawable(context));

        addTextView(context);
    }

    private void addTextView(Context context)
    {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        textView = new TextView(context);

        addView(textView, params);
    }

    private void applyAttributes(Context context, AttributeSet attrs)
    {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.KeyboardButton, 0, 0);

        try {
            textView.setText(typedArray.getString(R.styleable.KeyboardButton_text));
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public boolean onLongClick(View v)
    {
        toggled = !toggled;

        setBackground(getBackgroundDrawable(getContext()));

        return true;
    }

    @Override
    public void onClick(View v)
    {
        EventBus.getDefault().post(
            new OutgoingRequest.Builder()
                .setRoutePath("/keyboard/type")
                .addParam("key", getKeyCode())
                .addParam("modifierInput", "true")
                .build()
        );
    }

    private Drawable getBackgroundDrawable(Context context)
    {
        int backgroundId;

        if (ThemeManager.isNightModeOn(context)) {
            backgroundId = toggled ? R.drawable.ic_keyboard_button_dark_pressed : R.drawable.ic_keyboard_button_dark;
        } else {
            backgroundId = toggled ? R.drawable.ic_keyboard_button_light_pressed : R.drawable.ic_keyboard_button_light;
        }

        return ContextCompat.getDrawable(context, backgroundId);
    }

    public boolean isToggled()
    {
        return toggled;
    }
}
