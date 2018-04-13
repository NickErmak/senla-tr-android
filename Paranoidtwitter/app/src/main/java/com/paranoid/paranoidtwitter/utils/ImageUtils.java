package com.paranoid.paranoidtwitter.utils;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class ImageUtils {

    private static final ImageLoader imageLoader;
    private static final String DIVIDER = "/";

    static {
        DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(App.getInstance())
                .defaultDisplayImageOptions(imageOptions)
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    public static void displayImage(String imageUrl, ImageView imageView) {
        imageLoader.displayImage(imageUrl, imageView);
    }

    public static void saveImage(String imageUrl, Activity activity) {
        imageLoader.loadImage(imageUrl, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (PermissionUtils.checkPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    saveToExternalStorage(loadedImage);
                    Log.e("TAG", "permission granted");
                }
            }
        });
    }

    private static void saveToExternalStorage(Bitmap image) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + DIVIDER + App.getInstance().getString(R.string.twitter_saved_pictures_folder));
        myDir.mkdirs();
        String imageFileNameFormat = App.getInstance().getString(R.string.image_name_format);
        String fileName = String.format(imageFileNameFormat, new Random().nextInt(10000));
        File file = new File(myDir, fileName);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(App.getInstance(), new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }
}
