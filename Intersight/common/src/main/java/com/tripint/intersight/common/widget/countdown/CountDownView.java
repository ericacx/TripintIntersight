package com.tripint.intersight.common.widget.countdown;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tripint.intersight.common.R;

import java.text.DecimalFormat;
import java.util.Calendar;

public class CountDownView extends RelativeLayout {
    private TextView mHours, mMinutes, mSeconds, mMilliseconds;
    private long mCurrentMillis, mBeginTime;
    private boolean mIsTimerRunning = false, mIsAlarmRunning = false;
    private String mAlarmSoundPath;
    private TimerListener listener;
    private CountDownTimer mTimer;


    private static final Calendar mTime = Calendar.getInstance();
    private static final DecimalFormat mFormatter = new DecimalFormat("00");

    private Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mCurrentMillis = (Long) msg.obj;
            if (mCurrentMillis == 0) {
                mIsAlarmRunning = true;
                onCountDownFinished();
            }
            updateUI(mCurrentMillis);
        }
    });

    private void onCountDownFinished() {
        listener.timerElapsed();
        mIsTimerRunning = false;
//        startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.blink));
    }

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater.from(context).inflate(R.layout.countdownview_main, this, true);

        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        int numColorId = values.getResourceId(R.styleable.CountDownView_numberColor, android.R.attr.textColor);
        int unitColorId = values.getResourceId(R.styleable.CountDownView_unitColor, android.R.attr.textColor);

        if (values.getBoolean(R.styleable.CountDownView_showHour, false)) {
            View v = ((ViewStub) findViewById(R.id.hours_stub)).inflate();
            mHours = (TextView) v.findViewById(R.id.hours);
            ((TextView) v.findViewById(R.id.hours_unit)).setTextColor(getResources().getColorStateList(unitColorId));
            mHours.setTextColor(getResources().getColorStateList(numColorId));
        }

        if (values.getBoolean(R.styleable.CountDownView_showMin, false)) {
            View v = ((ViewStub) findViewById(R.id.minutes_stub)).inflate();
            mMinutes = (TextView) v.findViewById(R.id.minutes);
            ((TextView) v.findViewById(R.id.minutes_unit)).setTextColor(getResources().getColorStateList(unitColorId));
            mMinutes.setTextColor(getResources().getColorStateList(numColorId));
        }

        if (values.getBoolean(R.styleable.CountDownView_showSec, false)) {
            View v = ((ViewStub) findViewById(R.id.seconds_stub)).inflate();
            mSeconds = (TextView) v.findViewById(R.id.seconds);
            ((TextView) v.findViewById(R.id.seconds_unit)).setTextColor(getResources().getColorStateList(unitColorId));
            mSeconds.setTextColor(getResources().getColorStateList(numColorId));
        }

        if (values.getBoolean(R.styleable.CountDownView_showMilli, false)) {
            View v = ((ViewStub) findViewById(R.id.milliseconds_stub)).inflate();
            mMilliseconds = (TextView) v.findViewById(R.id.milliseconds);
            ((TextView) v.findViewById(R.id.milliseconds_unit)).setTextColor(getResources().getColorStateList(unitColorId));
            mMilliseconds.setTextColor(getResources().getColorStateList(numColorId));
        }

    }

    /**
     * Set listner to notify when timer reaches zero
     */
    public void setListener(TimerListener listener) {
        this.listener = listener;
    }

    /**
     * Sets the initial time for this countdown. This is fixed and will
     * not change unless a call to {@link #setInitialTime} is made.
     *
     * @param millisInFuture - Time in milliseconds to countdown from.
     */
    public void setInitialTime(long millisInFuture) {
        mBeginTime = millisInFuture;
        setCurrentTime(millisInFuture);
    }

    /**
     * Sets the current countdown time. May not necessarily be the same
     * as the initial countdown time.
     *
     * @param millisInFuture - Time in milliseconds to countdown from.
     */
    public void setCurrentTime(long millisInFuture) {
        mCurrentMillis = millisInFuture;
        updateUI(millisInFuture);
    }

    private void updateUI(long millisInFuture) {
        mTime.setTimeInMillis(millisInFuture);
        if (mHours != null)
            mHours.setText(mFormatter.format(mTime.get(Calendar.HOUR)));

        if (mMinutes != null)
            mMinutes.setText(mFormatter.format(mTime.get(Calendar.MINUTE)));

        if (mSeconds != null)
            mSeconds.setText(mFormatter.format(mTime.get(Calendar.SECOND)));

        if (mMilliseconds != null)
            mMilliseconds.setText(mFormatter.format(mTime.get(Calendar.MILLISECOND)));
    }

    /**
     * Starts the timer.
     */
    public void start() {
        mTimer = new CountDownTimer(mCurrentMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCurrentMillis = millisUntilFinished;
                postTime(mCurrentMillis);
            }

            @Override
            public void onFinish() {
                postTime(0);
                mTimer = null;
            }
        }.start();
        mIsTimerRunning = true;

    }

    /**
     * Stops the timer.
     */
    public void stop() {
        mIsTimerRunning = false;
        mTimer.cancel();
        mTimer = null;
//        clearAnimation();

    }

    /**
     * Resets the timer.
     */
    public void reset() {
        stop();
        mCurrentMillis = mBeginTime;
        updateUI(mCurrentMillis);
    }

    /**
     * Checks if the countdown timer is currently running.
     *
     * @return true if running, false otherwise.
     */
    public boolean isTimerRunning() {
        return mIsTimerRunning;
    }

    /**
     * Gets the current time of the countdown timer.
     *
     * @return a long that represents the current time in milliseconds.
     */
    public long getCurrentMillis() {
        return mCurrentMillis;
    }

    /**
     * Sets a custom alarm sound to be played when timer goes off.
     *
     * @param assetPath - Relative path under assets directory.
     *                  i.e. "sounds/Timer_Expire.ogg"
     */
    public void setAlarmSound(String assetPath) {
        mAlarmSoundPath = assetPath;
    }


    private void postTime(long timeInMillis) {
        Message m = Message.obtain();
        m.obj = timeInMillis;
        try {
            mMessenger.send(m);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
