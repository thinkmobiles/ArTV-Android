package com.artv.android.core.log;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.artv.android.system.ArTvApplication;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ZOG on 8/6/2015.
 */
public abstract class ArTvLogger {

    private static final Set<ILogger> mLoggers = new HashSet<>();

    public static final boolean addLogger(final ILogger _logger) {
        return mLoggers.add(_logger);
    }

    public static final boolean removeLogger(final ILogger _logger) {
        return mLoggers.remove(_logger);
    }

    public static final void printMessage(final String _message) {
        printMessage(true, _message);
    }

    public static final void printMessage(final boolean _fromNewLine, final String _message) {
        final PrintMessageRunnable runnable = new PrintMessageRunnable()
                .setFromNewLine(_fromNewLine)
                .setMessage(_message);
        if (Thread.currentThread().getName().equals("main")) {
            runnable.run();
        } else {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    private static class PrintMessageRunnable implements Runnable {
        private boolean mFromNewLine;
        private String mMessage;

        public final PrintMessageRunnable setFromNewLine(final boolean _fromNewLine) {
            mFromNewLine = _fromNewLine;
            return this;
        }

        public final PrintMessageRunnable setMessage(final String _message) {
            mMessage = _message;
            return this;
        }

        @Override
        public final void run() {
            Log.d(ArTvApplication.class.getSimpleName(), mMessage);

            for (final ILogger logger : mLoggers) {
                logger.printMessage(mFromNewLine, mMessage);
            }
        }
    }

}
