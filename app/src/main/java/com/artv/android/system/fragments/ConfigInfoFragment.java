package com.artv.android.system.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.artv.android.R;
import com.artv.android.core.api.ApiConst;
import com.artv.android.core.UrlHelper;
import com.artv.android.core.config_info.ConfigInfo;
import com.artv.android.core.config_info.ConfigInfoWorker;

/**
 * Created by Misha on 6/30/2015.
 */
public final class ConfigInfoFragment extends BaseFragment implements View.OnClickListener {

    private EditText etDeviceId;
    private EditText etMasterDeviceIp;
    private EditText etAddress;
    private EditText etUserName;
    private EditText etPassword;
    private CheckBox cbDebugInfo;

    private ConfigInfoWorker mConfigInfoWorker;

    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        initLogic();
    }

    private final void initLogic() {
        mConfigInfoWorker = getApplicationLogic().getConfigInfoWorker();
    }

    @Override
    public final View onCreateView(final LayoutInflater _inflater, final ViewGroup _container, final Bundle _savedInstanceState) {
        final View view = _inflater.inflate(R.layout.fragment_config_info, _container, false);

        etDeviceId = (EditText) view.findViewById(R.id.etDeviceId_FCI);
        etMasterDeviceIp = (EditText) view.findViewById(R.id.etMasterDeviceIp_FCI);
        etAddress = (EditText) view.findViewById(R.id.etAddress_FCI);
        etUserName = (EditText) view.findViewById(R.id.etUserName_FCI);
        etPassword = (EditText) view.findViewById(R.id.etPassword_FCI);
        cbDebugInfo = (CheckBox) view.findViewById(R.id.cbDebugInfo_FCI);

        view.findViewById(R.id.btnSave_FCI).setOnClickListener(this);

        return view;
    }

    @Override
    public final void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        if (_savedInstanceState == null) showConfigInfoInUi();
    }

    private final void showConfigInfoInUi() {
        mConfigInfoWorker.loadConfigInfo();
        final ConfigInfo info = mConfigInfoWorker.getConfigInfo();
        if (info == null) return;

        etDeviceId.setText(info.getDeviceId());
        etMasterDeviceIp.setText(info.getMasterDeviceIp());
        etAddress.setText(info.getAddress());
        etUserName.setText(info.getUser());
        etPassword.setText(info.getPassword());
        cbDebugInfo.setChecked(info.getShowDebugInfo());
    }

    @Override
    public final void onClick(final View _v) {
        switch (_v.getId()) {
            case R.id.btnSave_FCI:
                onClickBtnSave();
                break;
        }
    }

    private final void onClickBtnSave() {
        saveState();
    }

    private final void saveState() {
        final String deviceId = etDeviceId.getText().toString();
        final String masterDeviceIp = etMasterDeviceIp.getText().toString();
        final String address = etAddress.getText().toString();
        final String userName = etUserName.getText().toString();
        final String password = etPassword.getText().toString();
        final boolean showDebugInfo = cbDebugInfo.isChecked();

        final ConfigInfo configInfo = new ConfigInfo.Builder()
                .setDeviceId(deviceId)
                .setMasterDeviceIp(masterDeviceIp)
                .setAddress(address)
                .setUser(userName)
                .setPassword(password)
                .setShowDebugInfo(showDebugInfo)
                .build();

        if (!configInfo.hasConfigInfo()) {
            Toast.makeText(getActivity().getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!UrlHelper.isValidAddress(address)) {
            Toast.makeText(getActivity().getApplicationContext(), "Bad address, enter like \n\"http://site.com\"", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ApiConst.setProtocol(UrlHelper.getProtocolFrom(address));
            ApiConst.setAuthority(UrlHelper.getAuthorityFrom(address));
            ApiConst.addressUpdated();
        }

        mConfigInfoWorker.notifyEnteredConfigInfo(configInfo);
    }
}
