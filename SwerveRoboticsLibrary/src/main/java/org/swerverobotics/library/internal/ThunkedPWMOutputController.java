package org.swerverobotics.library.internal;

import com.qualcomm.robotcore.hardware.PWMOutputController;
import com.qualcomm.robotcore.util.SerialNumber;

/**
 * Another in our series
 */
public class ThunkedPWMOutputController implements PWMOutputController
    {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public PWMOutputController target;          // can only talk to him on the loop thread

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    private ThunkedPWMOutputController(PWMOutputController target)
        {
        if (target == null) throw new NullPointerException("null " + this.getClass().getSimpleName() + " target");
        this.target = target;
        }

    static public ThunkedPWMOutputController create(PWMOutputController target)
        {
        return target instanceof ThunkedPWMOutputController ? (ThunkedPWMOutputController)target : new ThunkedPWMOutputController(target);
        }

    //----------------------------------------------------------------------------------------------
    // PWMOutputController
    //----------------------------------------------------------------------------------------------

    @Override public void close()
        {
        (new ThunkForWriting()
        {
        @Override protected void actionOnLoopThread()
            {
            target.close();
            }
        }).doWriteOperation();
        }

    @Override public int getVersion()
        {
        return (new ThunkForReading<Integer>()
        {
        @Override protected void actionOnLoopThread()
            {
            this.result = target.getVersion();
            }
        }).doReadOperation();
        }

    @Override public String getDeviceName()
        {
        return (new ThunkForReading<String>()
        {
        @Override protected void actionOnLoopThread()
            {
            this.result = target.getDeviceName();
            }
        }).doReadOperation();
        }

    @Override public SerialNumber getSerialNumber()
        {
        return (new ThunkForReading<SerialNumber>()
            {
            @Override protected void actionOnLoopThread()
                {
                this.result = target.getSerialNumber();
                }
            }).doReadOperation();
        }

    @Override public void setPulseWidthOutputTime(final int channel, final int time)
        {
        (new ThunkForWriting()
            {
            @Override protected void actionOnLoopThread()
                {
                target.setPulseWidthOutputTime(channel, time);
                }
            }).doWriteOperation();
        }

    @Override public void setPulseWidthPeriod(final int channel, final int period)
        {
        (new ThunkForWriting()
            {
            @Override protected void actionOnLoopThread()
                {
                target.setPulseWidthPeriod(channel, period);
                }
            }).doWriteOperation();
        }

    @Override public double getPulseWidthOutputTime(final int channel)
        {
        return (new ThunkForReading<Double>()
            {
            @Override protected void actionOnLoopThread()
                {
                this.result = target.getPulseWidthOutputTime(channel);
                }
            }).doReadOperation();
        }

    @Override public double getPulseWidthPeriod(final int channel)
        {
        return (new ThunkForReading<Double>()
            {
            @Override protected void actionOnLoopThread()
                {
                this.result = target.getPulseWidthPeriod(channel);
                }
            }).doReadOperation();
        }
    }