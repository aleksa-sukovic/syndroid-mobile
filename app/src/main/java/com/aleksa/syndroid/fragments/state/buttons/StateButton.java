package com.aleksa.syndroid.fragments.state.buttons;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.aleksa.syndroid.R;
import com.aleksa.syndroid.library.router.request.Request;
import com.aleksa.syndroid.managers.ThemeManager;
import org.greenrobot.eventbus.EventBus;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class StateButton extends FrameLayout implements View.OnClickListener
{
    private TextView textView;

    {
        setOnClickListener(this);
    }

    protected abstract String getStateTag();

    public StateButton(@NonNull Context context)
    {
        super(context);

        initialize(context);
    }

    public StateButton(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        initialize(context);
        applyAttributes(context, attrs);
    }

    public StateButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        initialize(context);
        applyAttributes(context, attrs);
    }

    public StateButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);

        initialize(context);
        applyAttributes(context, attrs);
    }

    private void initialize(Context context)
    {
        addTextView(context);

        setBackground(getBackgroundDrawable(context));
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
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StateButton, 0, 0);

        try {
            textView.setText(typedArray.getString(R.styleable.StateButton_display_value));
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void onClick(View v)
    {
        sendStateCommand(getStateTag());
    }

    protected void sendStateCommand(String stateTag)
    {
        EventBus.getDefault().post(
            new Request.Builder()
                .setRouteByPath("/state/change/" + stateTag)
                .setType("request")
                .autoincrement()
                .build()
        );
    }

    private Drawable getBackgroundDrawable(Context context)
    {
        int backgroundId;

        if (ThemeManager.isLightModeOn(context)) {
            backgroundId = R.drawable.ic_state_button_light;
        } else {
            backgroundId = R.drawable.ic_state_button_dark;
        }

        return ContextCompat.getDrawable(context, backgroundId);
    }
}
