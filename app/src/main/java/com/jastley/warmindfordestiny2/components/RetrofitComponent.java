package com.jastley.warmindfordestiny2.components;

import com.jastley.warmindfordestiny2.modules.AppModule;
import com.jastley.warmindfordestiny2.modules.RetrofitModule;
import com.jastley.warmindfordestiny2.repositories.MilestoneRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RetrofitModule.class})
public interface RetrofitComponent {
    void inject(MilestoneRepository milestoneRepository);
}
