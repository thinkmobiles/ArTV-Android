package com.artv.android.system;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ZOG on 7/6/2015.
 */
public final class SpHelper {

    private static final String SP_NAME             = "artv_sp";
    private static final String DEF_STRING_VAL      = "";
    private static final boolean DEF_BOOLEAN_VAL    = false;

    private SharedPreferences mSp;

    public SpHelper(final Context _context) {
        mSp = _context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public final void putString(final String _key, final String _value) {
        mSp.edit().putString(_key, _value).apply();
    }

    public final String getString(final String _key) {
        return mSp.getString(_key, DEF_STRING_VAL);
    }

    public final void putBoolean(final String _key, final boolean _value) {
        mSp.edit().putBoolean(_key, _value).apply();
    }

    public final boolean getBoolean(final String _key) {
        return mSp.getBoolean(_key, DEF_BOOLEAN_VAL);
    }

    public final void removeItem(final String _key) {
        mSp.edit().remove(_key).apply();
    }

    public final void clearAll() {
        mSp.edit().clear().apply();
    }

}
