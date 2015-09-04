package com.artv.android.system.fragments.playback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by ZOG on 9/3/2015.
 */
public abstract class PictureHelper {

    /**
     * Returns closest biggest image scale in according to container size.
     * @param _container container view size.
     * @param _image current image size.
     * @return scale value of power of 2.
     */
    protected static final int getScale(final Size _container, final Size _image) {
        final int iWidth = _image.getWidth();
        final int iHeight = _image.getHeight();

        final int cWidth = _container.getWidth();
        final int cHeight = _container.getHeight();

        int scale = 1;

        if (iWidth > cWidth || iHeight > cHeight) {

            final int halfWidth = iWidth / 2;
            final int halfHeight = iHeight / 2;

            while ((halfWidth / scale) > cWidth
                    && (halfHeight / scale) > cHeight) {
                scale *= 2;
            }

        }

        return scale;
    }

    protected static final Size getImageSize(final String _path) throws FileNotFoundException {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(new FileInputStream(_path), null, options);
        final Size imgSize = new Size(options.outWidth, options.outHeight);
        return imgSize;
    }

    protected static final Bitmap getScaledBitmap(final String _path, final int _scale) throws FileNotFoundException {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = _scale;
        return BitmapFactory.decodeStream(new FileInputStream(_path), null, options);
    }

}
