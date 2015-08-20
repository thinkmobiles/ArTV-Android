package com.artv.android.core;

import android.net.Uri;

import com.artv.android.core.api.ApiConst;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by ZOG on 8/5/2015.
 */
public abstract class UrlHelper {

    public static final boolean isValidAddress(final String _url) {
        //is valid address
        try {
            new URL(_url);
        } catch (final MalformedURLException _e) {
            _e.printStackTrace();
            return false;
        }

        final Uri uri = Uri.parse(_url);
        return uri.getScheme() != null && uri.getAuthority() != null && !uri.getAuthority().isEmpty();
    }

    public static final String getProtocolFrom(final String _url) {
        final Uri uri = Uri.parse(_url);
        return uri.getScheme();
    }

    public static final String getAuthorityFrom(final String _url) {
        final Uri uri = Uri.parse(_url);
        return uri.getAuthority();
    }

    public static final URL buildUrlFrom(final String _path) throws UnsupportedEncodingException, MalformedURLException {
        final URL url;
        try {
            url = new URL(_path);
            return url;
        } catch (final MalformedURLException _e) {
            _e.printStackTrace();
        }

        final Uri uri = new Uri.Builder()
                .scheme(ApiConst.getProtocol())
                .authority(ApiConst.getAuthority())
                .path(_path)
                .build();

        final String address = URLDecoder.decode(uri.toString(), "utf-8");
        return new URL(address);
    }

    public static final boolean isYoutubeUrl(final String _url) {
        try {
            new URL(_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }

        return _url.contains("youtube") || _url.contains("youtu.be");
    }

}
