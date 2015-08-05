package com.artv.android.core.api;

import com.artv.android.core.api.rest_client.TestRestClient;

/**
 * Created by ZOG on 6/30/2015.
 */
public abstract class ApiConst {

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DEFAULTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static final int CONN_TIMEOUT					= 10 * 1000;			//10 seconds
    public static final int READ_TIMEOUT					= 30 * 1000;			//30 seconds
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ END DEFAULTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SERVER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static String mProtocol                         = "http";
    private static String mAuthority                        = "tvm.hasbrain.ru";
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ END SERVER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ API PATHS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static final String PATH_GET_TOKEN		        = "/api/GetToken.xml";
    public static final String PATH_GET_GLOBAL_CONFIG       = "/api/GetGlobalConfig.xml";
    public static final String PATH_GET_DEVICE_CONFIG       = "/api/GetDeviceConfig.xml";
    public static final String PATH_BEACON	                = "/api/Beacon.xml";
    public static final String PATH_GET_CAMPAIGN	        = "/api/GetCampaign.xml";
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ END API PATHS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ API PATHS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static final String KEY_USERNAME                 = "Username";
    public static final String KEY_PASSWORD                 = "Password";
    public static final String KEY_TAG_ID                   = "TagID";
    public static final String KEY_TOKEN                    = "Token";
    public static final String KEY_CAMPAIGN_ID              = "CampaignID";
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ END API PATHS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public static final void setProtocol(final String _protocol) {
        mProtocol = _protocol;
    }

    public static final String getProtocol() {
        return mProtocol;
    }

    public static final void setAuthority(final String _authority) {
        mAuthority = _authority;
    }

    public static final String getAuthority() {
        return mAuthority;
    }

    /**
     * Call this if you change some of server address.
     */
    public static final void addressUpdated() {
        TestRestClient.setupRestClient();
    }

}
