package com.paranoid.paranoidtwitter.fragments;


import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.paranoid.paranoidtwitter.App;
import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.utils.ImageUtils;
import com.paranoid.paranoidtwitter.utils.PermissionUtils;

public class SaveImageFragment extends AbstractFragment {

    public static final String FRAGMENT_TITLE = App.getInstance().getString(R.string.frag_save_image_title);
    public static final String FRAGMENT_TAG = "FRAGMENT_TAG_SAVE_IMAGE";
    public static final String KEY_SAVE_IMAGE = "KEY_SAVE_IMAGE";
    private String imageUrl;
    private FragmentLifeCircle mFragmentLifeCircle;

    public static SaveImageFragment newInstance(String imageUrl) {
        SaveImageFragment result = new SaveImageFragment();
        Bundle args = new Bundle();
        args.putString(KEY_SAVE_IMAGE, imageUrl);
        result.setArguments(args);
        return result;
    }

    public SaveImageFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragmentLifeCircle = (FragmentLifeCircle) context;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, Bundle savedInstanceState) {
        imageUrl = getArguments().getString(KEY_SAVE_IMAGE);
        return inflater.inflate(R.layout.fragment_save_image, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView) view.findViewById(R.id.frag_save_img_iv);
        ImageUtils.displayImage(imageUrl, imageView);

        view.findViewById(R.id.frag_save_img_btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    ImageUtils.saveImage(imageUrl, getActivity());
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mFragmentLifeCircle != null) {
            mFragmentLifeCircle.onFragmentStart(FRAGMENT_TITLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.MY_PERMISSIONS_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ImageUtils.saveImage(imageUrl, getActivity());
                    Log.e("TAG", "getPermissionResult");
                } else {
                    Toast.makeText(
                            getActivity(),
                            R.string.toast_permission_write_external_not_granted,
                            Toast.LENGTH_SHORT
                    ).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }
}
