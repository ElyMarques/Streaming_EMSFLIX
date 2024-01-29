package com.coioteshd2024.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.coioteshd2024.ui.viewmodels.AnimeViewModel;
import com.coioteshd2024.ui.viewmodels.CastersViewModel;
import com.coioteshd2024.ui.viewmodels.HomeViewModel;
import com.coioteshd2024.ui.viewmodels.GenresViewModel;
import com.coioteshd2024.ui.viewmodels.LoginViewModel;
import com.coioteshd2024.ui.viewmodels.MovieDetailViewModel;
import com.coioteshd2024.ui.viewmodels.MoviesListViewModel;
import com.coioteshd2024.ui.viewmodels.NetworksViewModel;
import com.coioteshd2024.ui.viewmodels.PlayerViewModel;
import com.coioteshd2024.ui.viewmodels.RegisterViewModel;
import com.coioteshd2024.ui.viewmodels.SearchViewModel;
import com.coioteshd2024.ui.viewmodels.SerieDetailViewModel;
import com.coioteshd2024.ui.viewmodels.SettingsViewModel;
import com.coioteshd2024.ui.viewmodels.StreamingDetailViewModel;
import com.coioteshd2024.ui.viewmodels.StreamingGenresViewModel;
import com.coioteshd2024.ui.viewmodels.UpcomingViewModel;
import com.coioteshd2024.ui.viewmodels.UserViewModel;
import com.coioteshd2024.viewmodel.MoviesViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/*
 * @author Yobex.
 * */
@Module
public abstract class ViewModelModule {



    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UpcomingViewModel.class)
    abstract ViewModel bindUpcomingViewModel(UpcomingViewModel upcomingViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel.class)
    abstract ViewModel bindMovieDetailViewModel(MovieDetailViewModel movieDetailViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SerieDetailViewModel.class)
    abstract ViewModel bindSerieDetailViewModel(SerieDetailViewModel serieDetailViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel.class)
    abstract ViewModel bindRegisterViewModel(RegisterViewModel registerViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(GenresViewModel.class)
    abstract ViewModel bindGenresViewModel(GenresViewModel genresViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel.class)
    abstract ViewModel bindSettingsViewModel(SettingsViewModel settingsViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(MoviesListViewModel.class)
    abstract ViewModel bindMoviesListViewModel(MoviesListViewModel moviesListViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(AnimeViewModel.class)
    abstract ViewModel bindAnimeViewModel(AnimeViewModel animeViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(StreamingDetailViewModel.class)
    abstract ViewModel bindStreamingDetailViewModel(StreamingDetailViewModel streamingDetailViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(StreamingGenresViewModel.class)
    abstract ViewModel bindStreamingGenresViewModel(StreamingGenresViewModel streamingGenresViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PlayerViewModel.class)
    abstract ViewModel bindStreamingPlayerViewModel(PlayerViewModel playerViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CastersViewModel.class)
    abstract ViewModel bindCastersViewModel(CastersViewModel castersViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(NetworksViewModel.class)
    abstract ViewModel bindNetworksViewModel(NetworksViewModel networksViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(MoviesViewModelFactory factory);


}
