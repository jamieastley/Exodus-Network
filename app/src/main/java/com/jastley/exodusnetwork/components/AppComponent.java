package com.jastley.exodusnetwork.components;

import com.jastley.exodusnetwork.Account.viewmodels.AccountStatsViewModel;
import com.jastley.exodusnetwork.Inventory.InventoryViewModel;
import com.jastley.exodusnetwork.MainActivityViewModel;
import com.jastley.exodusnetwork.Milestones.viewmodels.MilestoneViewModel;
import com.jastley.exodusnetwork.Vendors.XurRepository;
import com.jastley.exodusnetwork.Vendors.viewmodels.XurViewModel;
import com.jastley.exodusnetwork.checklists.ChecklistsViewModel;
import com.jastley.exodusnetwork.lfg.viewmodels.LFGViewModel;
import com.jastley.exodusnetwork.manifest.ManifestViewModel;
import com.jastley.exodusnetwork.modules.AppModule;
import com.jastley.exodusnetwork.modules.ContextModule;
import com.jastley.exodusnetwork.modules.RetrofitModule;
import com.jastley.exodusnetwork.modules.RoomModule;
import com.jastley.exodusnetwork.modules.SharedPrefsModule;

import com.jastley.exodusnetwork.onboarding.OnBoardingViewModel;
import com.jastley.exodusnetwork.repositories.AccountRepository;
import com.jastley.exodusnetwork.repositories.AccountStatsRepository;
import com.jastley.exodusnetwork.repositories.ChecklistsRepository;
import com.jastley.exodusnetwork.repositories.InventoryRepository;
import com.jastley.exodusnetwork.repositories.LFGRepository;
import com.jastley.exodusnetwork.repositories.ManifestRepository;
import com.jastley.exodusnetwork.repositories.MilestoneRepository;
import com.jastley.exodusnetwork.repositories.OnBoardingRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        RetrofitModule.class,
        RoomModule.class,
        SharedPrefsModule.class,
        ContextModule.class})
public interface AppComponent {

    void inject(MilestoneRepository milestoneRepository);
    void inject(MilestoneViewModel milestoneViewModel);
    void inject(XurViewModel xurViewModel);
    void inject(XurRepository xurRepository);
    void inject(AccountStatsViewModel accountStatsViewModel);
    void inject(AccountStatsRepository accountStatsRepository);
    void inject(LFGRepository lfgRepository);
    void inject(LFGViewModel lfgViewModel);
    void inject(OnBoardingRepository onBoardingRepository);
    void inject(OnBoardingViewModel onboardingViewModel);
    void inject(ChecklistsRepository checklistRepository);
    void inject(ChecklistsViewModel checklistsViewModel);

    void inject(AccountRepository accountRepository);
    void inject(MainActivityViewModel mainActivityViewModel);

    void inject(ManifestRepository manifestRepository);
    void inject(ManifestViewModel manifestViewModel);

    void inject(InventoryViewModel inventoryViewModel);
    void inject(InventoryRepository inventoryRepository);
}
