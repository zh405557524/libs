package com.soul.lib.test;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import dalvik.system.DexFile;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-07-04 15:37
 * UpdateUser:
 * UpdateDate: 2019-07-04 15:37
 * UpdateRemark:
 */
public class FragmentFactory {

    private static HashMap<String, Fragment> fragments = new HashMap<>();

    public static final String TEXT_VIEW_CLASSIFY = "测试界面分类";

    public static final String AUDIO_FOCUS = "音频焦点测试";


    public static List<String> initFragmentCount(Context context) {
        return initFragmentCount(context, "com.autoai.assistantext.fragment");
    }

    /**
     * 初始化fragment工厂
     */
    public static List<String> initFragmentCount(Context context, String pageName) {
        fragments.clear();
        //获取所有的Fragment，并实例化
        final List<String> classNames = getClassName(pageName, context);
        List<String> classNameNew = new ArrayList<>();
        for (String className : classNames) {
            try {
                final Class<?> aClass = Class.forName(className);
                final Fragment fragment = (Fragment) aClass.newInstance();


                Method method = aClass.getMethod("getClassName");
                final String invoke = (String) method.invoke(fragment);
                Log.i("Tag", "invoke:" + invoke);

                fragments.put(invoke, fragment);
                classNameNew.add(invoke);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return classNameNew;
    }

    public static Fragment getFragment(String name) {
        Fragment fragment = fragments.get(name);
        if (fragment != null) {
            return fragment;
        }
        switch (name) {
            case TEXT_VIEW_CLASSIFY:
                fragment = new TextFragment();
                break;
        }
        return fragment;
    }


    private static List<String> getClassName(String packageName, Context context) {
        List<String> classNameList = new ArrayList<>();
        try {
            final DexFile dexFile = new DexFile(context.getPackageCodePath());
            final Enumeration<String> enumeration = dexFile.entries();
            while (enumeration.hasMoreElements()) {
                final String className = enumeration.nextElement();
                if (className.contains(packageName) && !className.contains("$")) {
                    classNameList.add(className);
                    Log.i("Tag", "className:" + className);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classNameList;
    }

    public static void hideALl(FragmentTransaction fragmentTransaction) {
        for (Map.Entry<String, Fragment> entry : fragments.entrySet()) {
            final Fragment value = entry.getValue();
            if (value != null && !value.isHidden()) {
                fragmentTransaction.hide(value);
            }
        }
    }
}
