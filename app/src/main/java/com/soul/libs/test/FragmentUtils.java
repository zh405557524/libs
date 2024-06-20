package com.soul.libs.test;

import android.app.Activity;
import android.os.Bundle;


import com.soul.libs.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-07-04 15:42
 * UpdateUser:
 * UpdateDate: 2019-07-04 15:42
 * UpdateRemark:
 */
public class FragmentUtils {


    public static boolean startFragment(String name, Activity activity, Bundle bundle) {
        boolean isAdd = false;
        FragmentTransaction fragmentTransaction = ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();

        FragmentFactory.hideALl(fragmentTransaction);

        Fragment fragment = FragmentFactory.getFragment(name);
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);

        } else {
            isAdd = true;
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            fragmentTransaction.add(R.id.main_root, fragment);

            if (!name.equals(FragmentFactory.TEXT_VIEW_CLASSIFY)) {
                // 主界面不添加到栈
                fragmentTransaction.addToBackStack(name);
            }
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
        return isAdd;
    }

    public static boolean startFragment(String name, Activity activity) {
        return startFragment(name, activity, null);
    }
}
