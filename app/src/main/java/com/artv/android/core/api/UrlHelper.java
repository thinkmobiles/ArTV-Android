package com.artv.android.core.api;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

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

}
