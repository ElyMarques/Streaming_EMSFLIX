package com.coioteshd2024.di.module;

import com.coioteshd2024.ui.animes.EpisodesFragment;
import com.coioteshd2024.ui.downloadmanager.ui.main.DownloadsFragment;
import com.coioteshd2024.ui.downloadmanager.ui.main.FinishedDownloadsFragment;
import com.coioteshd2024.ui.downloadmanager.ui.main.QueuedDownloadsFragment;
import com.coioteshd2024.ui.home.HomeFragment;
import com.coioteshd2024.ui.library.AnimesFragment;
import com.coioteshd2024.ui.library.LibraryFragment;
import com.coioteshd2024.ui.library.LibraryStyleFragment;
import com.coioteshd2024.ui.library.MoviesFragment;
import com.coioteshd2024.ui.library.NetworksFragment;
import com.coioteshd2024.ui.library.NetworksFragment2;
import com.coioteshd2024.ui.library.SeriesFragment;
import com.coioteshd2024.ui.mylist.AnimesListFragment;
import com.coioteshd2024.ui.mylist.ListFragment;
import com.coioteshd2024.ui.mylist.MoviesListFragment;
import com.coioteshd2024.ui.mylist.SeriesListFragment;
import com.coioteshd2024.ui.mylist.StreamingListFragment;
import com.coioteshd2024.ui.search.DiscoverFragment;
import com.coioteshd2024.ui.settings.SettingsActivity;
import com.coioteshd2024.ui.streaming.StreamingFragment;
import com.coioteshd2024.ui.upcoming.UpComingFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/*
 * @author Yobex.
 * */
@Module
public abstract class FragmentBuildersModule {


    @ContributesAndroidInjector
    abstract EpisodesFragment contributeEpisodesFragment();

    @ContributesAndroidInjector
    abstract FinishedDownloadsFragment contributeFinishedDownloadsFragment();


    @ContributesAndroidInjector
    abstract QueuedDownloadsFragment contributeQueuedDownloadsFragment();

    @ContributesAndroidInjector
    abstract DownloadsFragment contributeDownloadsFragment();

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract UpComingFragment contributeUpcomingFragment();

    @ContributesAndroidInjector
    abstract DiscoverFragment contributeDiscoverFragment();

    @ContributesAndroidInjector
    abstract MoviesFragment contributeMoviesFragment();


    @ContributesAndroidInjector
    abstract LibraryStyleFragment contributeLibraryStyleFragment();

    @ContributesAndroidInjector
    abstract SeriesFragment contributeSeriesFragment();

    @ContributesAndroidInjector
    abstract LibraryFragment contributeLibraryFragment();

    @ContributesAndroidInjector
    abstract MoviesListFragment contributeMyListMoviesFragment();

    @ContributesAndroidInjector
    abstract AnimesFragment contributeAnimesFragment();

    @ContributesAndroidInjector
    abstract StreamingFragment contributeLiveFragment();

    @ContributesAndroidInjector
    abstract SettingsActivity contributeSettingsFragment();

    @ContributesAndroidInjector
    abstract ListFragment contributeListFragment();

    @ContributesAndroidInjector
    abstract SeriesListFragment contributeSeriesListFragment();

    @ContributesAndroidInjector
    abstract AnimesListFragment contributeAnimesListFragment();


    @ContributesAndroidInjector
    abstract NetworksFragment contributeNetworksFragment();

    @ContributesAndroidInjector
    abstract NetworksFragment2 contributeNetworksFragment2();

    @ContributesAndroidInjector
    abstract StreamingListFragment contributeStreamingListFragment();

}
