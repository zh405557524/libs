package com.soul.lib.utils;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class AppUtil {

    public static String getProcessName(Context context, int pid) {
        // get by ams
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = am.getRunningAppProcesses();
        // getRunningAppProcesses返回可能为空，失败的原因没有去分析
        if (infos != null) {
            for (ActivityManager.RunningAppProcessInfo info : infos) {
                if (info.pid == pid) {
                    return info.processName;
                }
            }
        }

        // get from kernel
        return getProcessName(pid);
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean isMainProcess(Context context) {
        return context.getApplicationInfo().processName.equals(getProcessName(context, android.os.Process.myPid()));
    }

    /**
     * 判断 APP 处于前台还是后台
     *
     * @return
     */
    public static boolean isForeground(Context context, String packageName) {
        return TextUtils.equals(packageName, getForegroundPackageName(context));
    }

    public static String getForegroundPackageName(Context context) {
        String packageName = "";
        do {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                // RunningTask 5.0此方法被废弃，只能返回本应用和launcher
                // 应用内置在系统目录下依旧有效
                // <uses-permission android:name="android.permission.GET_TASKS"/>
                List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
                packageName = runningTasks.get(0).topActivity.getPackageName();
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                // 在Android 5.0中，google新增了getRunningAppProcesses()方法，用来获取所有当前运行的应用
                ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
                if (runningAppProcesses == null || runningAppProcesses.size() == 0) {
                    break;
                }
                for (ActivityManager.RunningAppProcessInfo process : runningAppProcesses) {
                    // 当App存在后台常驻的Service时失效，进程被判定为前台，以下一直成立
                    if (process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        packageName = process.processName;
                    }
                }
            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                // 在Android 5.1及之后版本，推荐使用UsageStatsManager来获取前台应用
                // <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />
                // 打开手机设置，点击安全-高级，在有权查看使用情况的应用中，为这个App打上勾
                // Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                // startActivity(intent);
                UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
                if (usm == null) {
                    break;
                }
                long time = System.currentTimeMillis();
                List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 30 * 1000, time);
                if (usageStatsList == null) {
                    break;
                }
                TreeMap<Long, UsageStats> mySortedMap = new TreeMap<>();
                for (UsageStats usageStats : usageStatsList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (!mySortedMap.isEmpty()) {
                    packageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } while (false);
        //
        return packageName;
    }

    //5.0以上，没有usage state权限
    public static final int AID_APP = 10000;
    public static final int AID_USER = 100000;

    private static String getForegroundApp() {
        // Android 7.0 之后也不好用了
        File[] files = new File("/proc").listFiles();
        int lowestOomScore = Integer.MAX_VALUE;
        String foregroundProcess = null;
        for (File file : files) {
            if (!file.isDirectory()) {
                continue;
            }
            int pid;
            try {
                pid = Integer.parseInt(file.getName());
            } catch (NumberFormatException e) {
                continue;
            }
            try {
                String cgroup = read(String.format("/proc/%d/cgroup", pid));
                String[] lines = cgroup.split("\n");
                String cpuSubsystem;
                String cpuaccctSubsystem;
                for (int i = 0; i < lines.length; i++) {
                    //                    LogUtil.e("PKG", lines[i]);
                }
                if (lines.length == 2) {//有的手机里cgroup包含2行或者3行，我们取cpu和cpuacct两行数据
                    cpuSubsystem = lines[0];
                    cpuaccctSubsystem = lines[1];
                } else if (lines.length == 3) {
                    cpuSubsystem = lines[0];
                    cpuaccctSubsystem = lines[2];
                } else if (lines.length == 5) {//6.0系统
                    cpuSubsystem = lines[2];
                    cpuaccctSubsystem = lines[4];
                } else {
                    continue;
                }
                if (!cpuaccctSubsystem.endsWith(Integer.toString(pid))) {
                    // not an application process
                    continue;
                }
                if (cpuSubsystem.endsWith("bg_non_interactive")) {
                    // background policy
                    continue;
                }
                String cmdline = read(String.format("/proc/%d/cmdline", pid));
                if (cmdline.contains("com.android.systemui")) {
                    continue;
                }
                int uid = Integer.parseInt(
                        cpuaccctSubsystem.split(":")[2].split("/")[1].replace("uid_", ""));
                if (uid >= 1000 && uid <= 1038) {
                    // system process
                    continue;
                }

                int appId = uid - AID_APP;
                int userId = 0;
                // loop until we get the correct user id.
                // 100000 is the offset for each user.
                while (appId > AID_USER) {
                    appId -= AID_USER;
                    userId++;
                }

                if (appId < 0) {
                    continue;
                }

                // u{user_id}_a{app_id} is used on API 17+ for multiple user account support.
                // String uidName = String.format("u%d_a%d", userId, appId);
                File oomScoreAdj = new File(String.format("/proc/%d/oom_score_adj", pid));
                if (oomScoreAdj.canRead()) {
                    int oomAdj = Integer.parseInt(read(oomScoreAdj.getAbsolutePath()));
                    if (oomAdj != 0) {
                        continue;
                    }
                }
                int oomscore = Integer.parseInt(read(String.format("/proc/%d/oom_score", pid)));
                if (oomscore < lowestOomScore) {
                    lowestOomScore = oomscore;
                    foregroundProcess = cmdline;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return foregroundProcess;
    }

    private static String read(String path) throws IOException {
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        output.append(reader.readLine());
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            output.append('\n').append(line);
        }
        reader.close();
        return output.toString().trim();
    }


    public static List<String> getAppNameList(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        ArrayList<String> appNameList = new ArrayList<>();
        for (PackageInfo packageInfo : packages) {

            boolean sysApp = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0;

            if (sysApp) {//非系统应用
                appNameList.add(packageInfo.applicationInfo.loadLabel(packageManager)
                        .toString());
            }
        }
        return appNameList;
    }


    public static boolean startAppByName(Context context, String appName) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            if (appName.equals(packageInfo.applicationInfo.loadLabel(packageManager)
                    .toString())) {
                Intent intent = packageManager.getLaunchIntentForPackage(packageInfo.packageName);
                context.startActivity(intent);
                return true;
            }
        }
        return false;
    }


    private AppUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }
}
