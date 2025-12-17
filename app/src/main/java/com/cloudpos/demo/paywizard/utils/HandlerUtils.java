package com.cloudpos.demo.paywizard.utils;

import android.os.Handler;
import android.os.Looper;

public final class HandlerUtils {

    private HandlerUtils() {
    }

    // MainThread Handler
    private static Handler sMainHandler;

    /**
     * getMainThread Handler
     * @return MainThread Handler
     */
    public static Handler getMainHandler() {
        if (sMainHandler == null) {
            synchronized (HandlerUtils.class) {
                if (sMainHandler == null) {
                    sMainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return sMainHandler;
    }

    /**
     * in MainThread Handler postRunnable
     * @param runnable
     */
    public static void postRunnable(final Runnable runnable) {
        if (runnable != null) {
            getMainHandler().post(runnable);
        }
    }

    /**
     * in MainThread Handler postDelayRunnable
     * @param runnable
     * @param delayMillis
     */
    public static void postRunnable(final Runnable runnable, final long delayMillis) {
        if (runnable != null) {
            getMainHandler().postDelayed(runnable, delayMillis);
        }
    }

    /**
     * in MainThread Handler postDelayRunnable
     * @param runnable
     * @param delayMillis
     * @param number
     * @param interval
     */
    public static void postRunnable(final Runnable runnable, final long delayMillis, final int number, final int interval) {
        postRunnable(runnable, delayMillis, number, interval, null);
    }

    /**
     * in MainThread Handler postDelayRunnable
     * @param runnable
     * @param delayMillis
     * @param number
     * @param interval
     * @param onEndListener
     */
    public static void postRunnable(final Runnable runnable, final long delayMillis, final int number, final int interval, final OnEndListener onEndListener) {
        if (runnable != null) {
            Runnable loop = new Runnable() {
                private int mNumber;

                @Override
                public void run() {
                    if (mNumber < number) {
                        mNumber++;
                        if (runnable != null) {
                            try {
                                runnable.run();
                            } catch (Exception e) {
                            }
                        }
                        // Determine if the number of attempts has exceeded the limit.
                        if (mNumber < number) {
                            getMainHandler().postDelayed(this, interval);
                        }
                    }

                    // Determine if the number of attempts has exceeded the limit.
                    if (mNumber >= number) {
                        if (onEndListener != null) {
                            onEndListener.onEnd(delayMillis, number, interval);
                        }
                    }
                }
            };
            getMainHandler().postDelayed(loop, delayMillis);
        }
    }

    /**
     * in MainThread Handler clear task
     * @param runnable
     */
    public static void removeRunnable(final Runnable runnable) {
        if (runnable != null) {
            getMainHandler().removeCallbacks(runnable);
        }
    }

    // =

    /**
     * detail:  end callback event
     * @author Ttt
     */
    public interface OnEndListener {

        /**
         * End of Notice
         * @param delayMillis
         * @param number
         * @param interval
         */
        void onEnd(long delayMillis, int number, int interval);
    }
}