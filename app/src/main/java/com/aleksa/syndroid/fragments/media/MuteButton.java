package com.aleksa.syndroid.fragments.media;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.library.router.request.OutgoingRequest;

import org.greenrobot.eventbus.EventBus;

public class MuteButton extends android.support.v7.widget.AppCompatImageView implements View.OnClickListener
{
    private boolean muted;

    {
        setOnClickListener(this);
        setImageResource(R.drawable.ic_volume_off);
    }

    public MuteButton(Context context)
    {
        super(context);
    }

    public MuteButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MuteButton(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onClick(View v)
    {
        muted = !muted;

        EventBus.getDefault().post(new OutgoingRequest.Builder().setRoutePath("/media/volume/mute").build());

        setImage();
    }

    private void setImage()
    {
        if (muted) {
            setImageResource(R.drawable.ic_volume_mute);
        } else {
            setImageResource(R.drawable.ic_volume_off);
        }
    }
}
