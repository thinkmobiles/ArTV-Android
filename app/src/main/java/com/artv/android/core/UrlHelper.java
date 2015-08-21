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

    public static final String buildUrlFrom(final String _path) {
        try {
            new URL(_path);
            return _path;
        } catch (final MalformedURLException _e) {
            _e.printStackTrace();
        }

        final Uri uri = new Uri.Builder()
                .scheme(ApiConst.getProtocol())
                .authority(ApiConst.getAuthority())
                .path(_path)
                .build();

        try {
            return URLDecoder.decode(uri.toString(), "utf-8");
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
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
