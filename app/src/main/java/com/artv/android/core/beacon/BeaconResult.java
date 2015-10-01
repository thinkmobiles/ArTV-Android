package com.artv.android.core.beacon;

import com.artv.android.core.model.Campaign;
import com.artv.android.core.model.MsgBoardCampaign;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZOG on 10/1/2015.
 */
public final class BeaconResult {

    private boolean mSuccess = false;
    private String mMessage;
    private List<Campaign> mCampaigns;
    private List<Integer> mDeletedCampaignIds;
    private MsgBoardCampaign mMsgBoardCampaign;

    private BeaconResult() {}

    public final boolean getSuccess() {
        return mSuccess;
    }

    public final String getMessage() {
        return mMessage;
    }

    public final List<Campaign> getCampaigns() {
        return mCampaigns;
    }

    public final List<Integer> getDeletedCampaignIds() {
        return mDeletedCampaignIds == null ? new ArrayList<Integer>() : mDeletedCampaignIds;
    }

    public final MsgBoardCampaign getMsgBoardCampaign() {
        return mMsgBoardCampaign;
    }

    public final boolean needProcessCampaigns() {
        return (mCampaigns != null && !mCampaigns.isEmpty()) || (mDeletedCampaignIds != null && !mDeletedCampaignIds.isEmpty());
    }

    public final boolean needProcessMessages() {
        return mMsgBoardCampaign != null;
    }

    public static final class Builder {

        private BeaconResult mBeaconResult;

        public Builder() {
            mBeaconResult = new BeaconResult();
        }

        public Builder setSuccess(final boolean _success) {
            mBeaconResult.mSuccess = _success;
            return this;
        }

        public Builder setMessage(final String _message) {
            mBeaconResult.mMessage = _message;
            return this;
        }

        public final Builder setCampaigns(final List<Campaign> _campaigns) {
            mBeaconResult.mCampaigns = _campaigns;
            return this;
        }

        public final Builder setDeletedCampaignIds(final List<Integer> _deletedCampaignIds) {
            mBeaconResult.mDeletedCampaignIds = _deletedCampaignIds;
            return this;
        }

        public final Builder setMsgBoardCampaign(final MsgBoardCampaign _msgBoardCampaign) {
            mBeaconResult.mMsgBoardCampaign = _msgBoardCampaign;
            return this;
        }

        public final BeaconResult build() {
            return mBeaconResult;
        }

    }

}
