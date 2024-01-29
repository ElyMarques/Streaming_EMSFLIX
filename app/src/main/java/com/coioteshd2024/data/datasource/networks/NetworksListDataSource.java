package com.coioteshd2024.data.datasource.networks;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import com.coioteshd2024.data.local.entity.Media;
import com.coioteshd2024.data.model.genres.GenresData;
import com.coioteshd2024.data.remote.ApiInterface;
import com.coioteshd2024.data.remote.ServiceGenerator;
import com.coioteshd2024.ui.manager.SettingsManager;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NetworksListDataSource extends PageKeyedDataSource<Integer, Media> {

    private final String genreId;

    private final SettingsManager settingsManager;

    public NetworksListDataSource(String genreId, SettingsManager settingsManager){

        this.settingsManager = settingsManager;
        this.genreId = genreId;

    }

    public static final int PAGE_SIZE = 12;
    private static final int FIRST_PAGE = 1;



    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Media> callback) {

        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GenresData> call = apiInterface.getNetworksByID(genreId,settingsManager.getSettings().getApiKey(),FIRST_PAGE);
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NotNull Call<GenresData> call, @NotNull Response<GenresData> response) {


                if (response.isSuccessful()) {

                    callback.onResult(response.body().getGlobaldata(), null, FIRST_PAGE + 1);


                }
            }

            @Override
            public void onFailure(@NotNull Call<GenresData> call, @NotNull Throwable t) {

                //

            }
        });

    }

    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Media> callback) {


        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GenresData> call = apiInterface.getNetworksByID(genreId,settingsManager.getSettings().getApiKey(),params.key);
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NotNull Call<GenresData> call, @NotNull Response<GenresData> response) {


                if (response.isSuccessful()) {

                    Integer key = (params.key > 1) ? params.key - 1 : null;
                    callback.onResult(response.body().getGlobaldata(), key);


                }
            }

            @Override
            public void onFailure(@NotNull Call<GenresData> call, @NotNull Throwable t) {

                //
            }
        });


    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Media> callback) {

        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);

        Call<GenresData> call = apiInterface.getNetworksByID(genreId,settingsManager.getSettings().getApiKey(),params.key);
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NotNull Call<GenresData> call, @NotNull Response<GenresData> response) {


                if (response.isSuccessful()) {

                    callback.onResult(response.body().getGlobaldata(), params.key + 1);


                }
            }

            @Override
            public void onFailure(@NotNull Call<GenresData> call, @NotNull Throwable t) {
                //
            }
        });


    }

}