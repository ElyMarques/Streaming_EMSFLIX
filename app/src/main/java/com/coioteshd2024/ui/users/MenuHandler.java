package com.coioteshd2024.ui.users;

import androidx.databinding.ObservableField;

import java.util.Observable;

public class MenuHandler extends Observable {




    public final ObservableField<Boolean> isProfileSettingEnabled = new ObservableField<>(false);


    public final ObservableField<String> manageProfileText = new ObservableField<>("");


    public final ObservableField<Boolean> isUserCreatingProfile = new ObservableField<>(false);

    public final ObservableField<Boolean> isDataLoaded = new ObservableField<>(false);

    public final ObservableField<Boolean> isUserHasProfiles = new ObservableField<>(false);


    public final ObservableField<Boolean> isUserEditMode = new ObservableField<>(false);


}

