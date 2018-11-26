package com.test.transformerbattle.utils;

/**
 * Utils created to deal with Stats values and progress percent of components
 * such as {@link android.widget.ProgressBar} and {@link android.widget.SeekBar}
 * */
public class StatsUtils {

    /**
     * Used to convert stats value into progress percent
     * to be used in some components.
     *
     * @param value The stats value to be converted to progress percent.
     * @return Returns the progress percent.
     * */
    public static int valueToProgress(int value) {
        value *= 10;
        value = Math.max(1, value);
        value = Math.min(100, value);
        return value;
    }

    /**
     * Used to convert progress percent into stats value.
     *
     * @param progress The progress percent to be converted to stats value.
     * @return Returns The stats value.
     * */
    public static int progressToValue(int progress) {
        progress /= 10;
        progress = Math.max(1, progress);
        progress = Math.min(10, progress);
        return progress;
    }

}
