package com.coioteshd2024.data.datasource.anime;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.coioteshd2024.data.local.entity.Media;
import com.coioteshd2024.data.remote.ApiInterface;
import com.coioteshd2024.ui.manager.SettingsManager;

import javax.inject.Inject;

public class AnimeViewsDataSourceFactory extends DataSource.Factory {

    private final MutableLiveData<PageKeyedDataSource<Integer, Media>> serieLiveDataSource = new MutableLiveData<>();

    private final ApiInterface requestInterface;
    private final SettingsManager settingsManager;

    @Inject
    public AnimeViewsDataSourceFactory(ApiInterface requestInterface,SettingsManager settingsManager) {
        this.requestInterface = requestInterface;
        this.settingsManager = settingsManager;
    }

    @Override
    public DataSource create() {

        AnimeViewsDataSource animeViewsDataSource = new AnimeViewsDataSource(requestInterface,settingsManager);
        serieLiveDataSource.postValue(animeViewsDataSource);

        return animeViewsDataSource;

    }

    public MutableLiveData<PageKeyedDataSource<Integer, Media>> getItemLiveDataSource() {
        return serieLiveDataSource;
    }

}
