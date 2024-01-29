package com.coioteshd2024.data.datasource.medialibrary;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.coioteshd2024.data.datasource.genreslist.MoviesGenreListDataSource;
import com.coioteshd2024.data.local.entity.Media;
import com.coioteshd2024.ui.manager.SettingsManager;

public class MediaLibraryDataSourceFactory extends DataSource.Factory {

    private final String query;

    private final SettingsManager settingsManager;

    public MediaLibraryDataSourceFactory(String query,SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
        this.query = query;

    }

    @NonNull
    @Override
    public DataSource<Integer, Media> create() {
        return new MoviesGenreListDataSource(query, settingsManager);
    }

}
