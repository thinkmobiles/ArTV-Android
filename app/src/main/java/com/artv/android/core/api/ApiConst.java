package com.artv.android.core.api;

/**
 * Created by ZOG on 6/30/2015.
 */
public abstract class ApiConst {

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DEFAULTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static final int CONN_TIMEOUT					= 10 * 1000;			//10 seconds
    public static final int READ_TIMEOUT					= 30 * 1000;			//30 seconds
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ END DEFAULTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SERVER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static final String PROTOCOL						= "http";
    public static final String SERVER_AUTHORITY 			= "tvm.hasbrain.ru";
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

}
