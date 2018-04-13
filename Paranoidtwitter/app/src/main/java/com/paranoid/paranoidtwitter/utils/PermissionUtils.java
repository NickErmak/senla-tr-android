package com.paranoid.paranoidtwitter.utils;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.paranoid.paranoidtwitter.dialogs.PermissionDialog;

public class PermissionUtils {

    public static final int MY_PERMISSIONS_REQUEST = 123;

    public static boolean checkPermission(
            Activity activity, String permission) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                Log.e("TAG", "checkSelf permission = true");
                Log.e("TAG", "permission = " + ContextCompat.checkSelfPermission(activity, permission));
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Log.e("TAG", "shouldShowRequestPermissionRationale = true");
                    PermissionDialog.newInstance(permission).show(activity.getFragmentManager(), null);

                } else {
                    ActivityCompat.requestPermissions(
                            activity,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST);
                }
                return false;
            }
        }
        return true;
    }
}
