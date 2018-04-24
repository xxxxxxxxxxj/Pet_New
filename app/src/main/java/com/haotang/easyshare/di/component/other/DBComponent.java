package com.haotang.easyshare.di.component.other;

import com.haotang.easyshare.di.module.other.GreenDBModule;
import com.haotang.easyshare.di.scope.DBScope;
import com.haotang.easyshare.mvp.model.db.greendao.GreenDBManager;
import com.ljy.devring.di.component.RingComponent;

import dagger.Component;

/**
 * author:  ljy
 * date:    2018/3/10
 * description: GreenDao和原生数据库的Component
 */

@DBScope
@Component(modules = {GreenDBModule.class}, dependencies = RingComponent.class)
public interface DBComponent {

    void inject(GreenDBManager greenDbManager);

}
