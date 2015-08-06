package com.artv.android.core.log;

import android.os.Handler;
import android.os.Looper;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ZOG on 8/6/2015.
 */
public abstract class ArTvLogger {

    private static final Set<ILogger> mLoggers = new HashSet<>();
    private static final PrintMessageRunnable printRunnable = new PrintMessageRunnable();

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
        printRunnable.setFromNewLine(_fromNewLine);
        printRunnable.setMessage(_message);
        if (Thread.currentThread().getName().equals("main")) {
            printRunnable.run();
        } else {
            new Handler(Looper.getMainLooper()).post(printRunnable);
        }
    }

    private static class PrintMessageRunnable implements Runnable {
        private boolean mFromNewLine;
        private String mMessage;

        public final void setFromNewLine(final boolean _fromNewLine) {
            mFromNewLine = _fromNewLine;
        }

        public final void setMessage(final String _message) {
            mMessage = _message;
        }

        @Override
        public final void run() {
            for (final ILogger logger : mLoggers) {
                logger.printMessage(mFromNewLine, mMessage);
            }
        }
    }

}
