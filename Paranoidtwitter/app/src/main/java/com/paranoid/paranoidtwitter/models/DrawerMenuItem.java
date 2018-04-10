package com.paranoid.paranoidtwitter.models;

public class DrawerMenuItem {

    private int img;
    private int menu;

    public DrawerMenuItem(int img, int menu) {
        this.img = img;
        this.menu = menu;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(int menu) {
        this.menu = menu;
    }
}
