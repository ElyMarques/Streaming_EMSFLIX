package com.coioteshd2024.data.datasource.filmographie;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.coioteshd2024.data.model.credits.Cast;
import com.coioteshd2024.ui.manager.SettingsManager;

public class CastersListDataSourceFactory extends DataSource.Factory<Integer, Cast> {

    private final  SettingsManager settingsManager;
    private final String query;

    public CastersListDataSourceFactory(String query, SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
        this.query = query;
    }

    @NonNull
    @Override
    public DataSource<Integer, Cast> create() {
        return new CastersListDataSource (query, settingsManager);
    }
}
