package com.coioteshd2024.data.datasource.genreslist;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.coioteshd2024.data.local.entity.Media;
import com.coioteshd2024.ui.manager.SettingsManager;

public class AnimesGenresListDataSourceFactory extends DataSource.Factory<Integer, Media> {

    private final String query;
    private final SettingsManager settingsManager;


    public AnimesGenresListDataSourceFactory(String query, SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
        this.query = query;
    }

    @NonNull
    @Override
    public DataSource<Integer, Media> create() {
        return new AnimesGenreListDataSource(query, settingsManager);
    }
}
