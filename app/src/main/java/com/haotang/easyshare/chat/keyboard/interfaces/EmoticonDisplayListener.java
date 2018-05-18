package com.haotang.easyshare.chat.keyboard.interfaces;

import android.view.ViewGroup;

import com.haotang.easyshare.chat.keyboard.adpater.EmoticonsAdapter;


public interface EmoticonDisplayListener<T> {

    void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, T t, boolean isDelBtn);
}
