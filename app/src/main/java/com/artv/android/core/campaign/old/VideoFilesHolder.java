package com.artv.android.core.campaign.old;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZOG on 7/30/2015.
 */
public final class VideoFilesHolder {

    private List<File> mVideoFiles;

    public VideoFilesHolder() {
        mVideoFiles = new ArrayList<>();
    }

    public final boolean addFile(final File _file) {
        return mVideoFiles.add(_file);
    }

    public final boolean removeFile(final File _file) {
        return mVideoFiles.remove(_file);
    }

    public final List<File> getFiles() {
        return mVideoFiles;
    }

    public final void clearFiles() {
        mVideoFiles.clear();
    }
}
