package com.coioteshd2024.data.datasource.genreslist;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.coioteshd2024.data.local.entity.Media;
import com.coioteshd2024.ui.manager.SettingsManager;

public class ByGenresListDataSourceFactory extends DataSource.Factory<Integer, Media> {

    private final  SettingsManager settingsManager;
    private final String query;

    public ByGenresListDataSourceFactory(String query, SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
        this.query = query;
    }

    @NonNull
    @Override
    public DataSource<Integer, Media> create() {
        return new ByGenreListDataSource(query, settingsManager);
    }
}
