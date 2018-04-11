package com.haotang.newpet.di.component.other;

import com.haotang.newpet.di.module.other.GreenDBModule;
import com.haotang.newpet.di.scope.DBScope;
import com.haotang.newpet.mvp.model.db.greendao.GreenDBManager;
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
