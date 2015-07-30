package com.artv.android.core;

/**
 * Created by ZOG on 7/30/2015.
 */
public interface ILogger {

    void printMessage(final String _message);
    void printMessage(final boolean _fromNewLine, final String _message);

}
