package com.haotang.deving.di.component.other;

import com.haotang.deving.di.module.other.GreenDBModule;
import com.haotang.deving.di.scope.DBScope;
import com.haotang.deving.mvp.model.db.greendao.GreenDBManager;
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
