package com.soul.libs.lib;

import android.Manifest;
import android.view.View;

import com.soul.libs.test.ButtonTextFragment;
import com.soul.lib.utils.LogUtil;
import com.soul.lib.utils.PermissionsUtils;

/**
 * Description: 权限判断
 * Author: 祝明
 * CreateDate: 2024/6/3 12:01
 * UpdateUser:
 * UpdateDate: 2024/6/3 12:01
 * UpdateRemark:
 */
public class Permissions extends ButtonTextFragment implements View.OnClickListener {


    private static final String TAG = "Permissions";

    @Override
    public String getClassName() {
        return "权限判断";
    }

    @Override
    protected void initEvent() {
        addTextName("权限判断", this);
    }

    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        switch (tag) {
            case "权限判断":
                boolean checkPermissions = PermissionsUtils.checkPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO);
                LogUtil.i(TAG, "checkPermissions:" + checkPermissions);
                break;
        }
    }
}
