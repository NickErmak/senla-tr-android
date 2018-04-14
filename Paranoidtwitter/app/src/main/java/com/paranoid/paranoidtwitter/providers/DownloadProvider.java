package com.paranoid.paranoidtwitter.providers;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.utils.ImageUtils;
import com.paranoid.paranoidtwitter.utils.PermissionUtils;

import java.io.File;
import java.io.FileOutputStream;

import bolts.Task;
import bolts.TaskCompletionSource;

public class DownloadProvider {

    public static void save(Activity activity, String imageUrl) {
        if (PermissionUtils.checkPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            saveAsync(activity, imageUrl);
        }
    }

    private static Task<Void> saveAsync(Activity activity, String imageUrl) {
        return loadImageAsync(imageUrl)
                .onSuccessTask(task -> saveImageAsync(task.getResult()))
                .onSuccessTask(task -> scanImageAsync(task.getResult()))
                .continueWith(task -> {
                    String toastMsg = task.isCompleted() ? activity.getString(R.string.toast_msg_image_saved)
                            : task.getError().getMessage();
                    Toast.makeText(activity, toastMsg, Toast.LENGTH_SHORT).show();
                    return null;
                }, Task.UI_THREAD_EXECUTOR);
    }

    private static Task<Bitmap> loadImageAsync(String imageUrl) {
        final TaskCompletionSource<Bitmap> tcs = new TaskCompletionSource<>();
        ImageUtils.loadImage(imageUrl, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                tcs.setResult(loadedImage);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                tcs.setError((Exception) failReason.getCause());
            }
        });
        return tcs.getTask();
    }

    private static Task<File> saveImageAsync(Bitmap image) {
        final TaskCompletionSource<File> tcs = new TaskCompletionSource<>();
        File imageFile = ImageUtils.getImageFile();
        try {
            FileOutputStream out = new FileOutputStream(imageFile);
            image.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            tcs.setResult(imageFile);
        } catch (Exception e) {
            tcs.setError(e);
            e.printStackTrace();
        }
        return tcs.getTask();
    }

    private static Task<Void> scanImageAsync(File twitterPictureDir) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        MediaScannerConnection.scanFile(
                App.getInstance(),
                new String[]{twitterPictureDir.toString()},
                null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                        tcs.setResult(null);
                    }
                }
        );
        return tcs.getTask();
    }
}
