package com.jastley.exodusnetwork.checklists;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.jastley.exodusnetwork.app.App;
import com.jastley.exodusnetwork.repositories.ChecklistsRepository;

import javax.inject.Inject;

public class ChecklistsViewModel extends ViewModel {

    @Inject
    ChecklistsRepository repository;

    public ChecklistsViewModel(@NonNull Application application) {
        App.getApp().getAppComponent().inject(this);
    }


}
