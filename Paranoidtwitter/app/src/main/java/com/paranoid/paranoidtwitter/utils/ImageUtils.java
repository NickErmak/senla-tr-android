package com.paranoid.paranoidtwitter.utils;

import android.os.Environment;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;

import java.io.File;
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

    public static void loadImage(String imageUrl, ImageLoadingListener listener) {
        imageLoader.loadImage(imageUrl, listener);
    }

    public static File getImageFile() {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + DIVIDER + App.getInstance().getString(R.string.twitter_saved_pictures_folder));
        myDir.mkdirs();
        String imageFileNameFormat = App.getInstance().getString(R.string.image_name_format);
        String fileName = String.format(imageFileNameFormat, new Random().nextInt(10000));
        File file = new File(myDir, fileName);
        if (file.exists())
            file.delete();
        return file;
    }
}
