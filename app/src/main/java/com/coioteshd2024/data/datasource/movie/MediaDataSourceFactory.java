package com.coioteshd2024.data.datasource.movie;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.coioteshd2024.data.local.entity.Media;
import com.coioteshd2024.data.remote.ApiInterface;
import com.coioteshd2024.ui.manager.SettingsManager;

import javax.inject.Inject;

public class MediaDataSourceFactory extends DataSource.Factory {

    private final MutableLiveData<PageKeyedDataSource<Integer, Media>> itemLiveDataSource = new MutableLiveData<>();

    private final ApiInterface requestInterface;
    private final SettingsManager settingsManager;

    @Inject
    public MediaDataSourceFactory(ApiInterface requestInterface, SettingsManager settingsManager) {
        this.requestInterface = requestInterface;
        this.settingsManager = settingsManager;
    }

    @Override
    public DataSource create() {

        MediaDataSource movieDataSource = new MediaDataSource(requestInterface,settingsManager);
        itemLiveDataSource.postValue(movieDataSource);

        return movieDataSource;

    }

    public MutableLiveData<PageKeyedDataSource<Integer, Media>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }

}
