package com.jastley.warmindfordestiny2.components;

import com.jastley.warmindfordestiny2.Account.viewmodels.AccountStatsViewModel;
import com.jastley.warmindfordestiny2.Milestones.viewmodels.MilestoneViewModel;
import com.jastley.warmindfordestiny2.Vendors.XurRepository;
import com.jastley.warmindfordestiny2.Vendors.viewmodels.XurViewModel;
import com.jastley.warmindfordestiny2.modules.AppModule;
import com.jastley.warmindfordestiny2.modules.ContextModule;
import com.jastley.warmindfordestiny2.modules.RetrofitModule;
import com.jastley.warmindfordestiny2.modules.RoomModule;
import com.jastley.warmindfordestiny2.modules.SharedPrefsModule;
import com.jastley.warmindfordestiny2.repositories.AccountStatsRepository;
import com.jastley.warmindfordestiny2.repositories.MilestoneRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RetrofitModule.class, RoomModule.class, SharedPrefsModule.class, ContextModule.class})
public interface AppComponent {
    void inject(MilestoneRepository milestoneRepository);
    void inject(MilestoneViewModel milestoneViewModel);
    void inject(XurViewModel xurViewModel);
    void inject(XurRepository xurRepository);
    void inject(AccountStatsViewModel accountStatsViewModel);
    void inject(AccountStatsRepository accountStatsRepository);
}
