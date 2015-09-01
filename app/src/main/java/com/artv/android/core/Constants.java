package com.artv.android.core;

import android.os.Environment;

import java.io.File;

/**
 * Created by ZOG on 8/6/2015.
 */
public abstract class Constants {

    public static final String FOLDER_NAME = "artv_data";
    public static final String PATH = Environment.getExternalStorageDirectory() + File.separator + FOLDER_NAME;
    public static final int TIME_API_RECALL = 3;    //sec

}
