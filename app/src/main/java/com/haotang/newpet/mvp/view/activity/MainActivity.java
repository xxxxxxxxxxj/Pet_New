package com.haotang.newpet.mvp.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.haotang.newpet.R;
import com.haotang.newpet.mvp.model.entity.table.MovieCollect;

import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Subscribe
    public void onDeleteMovie(MovieCollect movieCollect) {

    }
}
