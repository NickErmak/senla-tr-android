package com.paranoid.paranoidtwitter.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paranoid.paranoidtwitter.R;
import com.paranoid.paranoidtwitter.models.DrawerMenuItem;

import java.util.List;

public class DrawerRecyclerAdapter extends RecyclerView.Adapter<DrawerRecyclerAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tvMenu;

        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.drawer_list_img);
            tvMenu = itemView.findViewById(R.id.drawer_list_tv_menu);
        }
    }

    private List<DrawerMenuItem> mItems;

    public DrawerRecyclerAdapter(List<DrawerMenuItem> items) {
        mItems = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.drawer_menu_item, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DrawerMenuItem item = mItems.get(position);
        holder.tvMenu.setText(item.getMenu());
        holder.img.setImageResource(item.getImg());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
