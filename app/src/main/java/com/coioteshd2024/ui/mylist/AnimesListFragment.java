package com.coioteshd2024.ui.mylist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.coioteshd2024.R;
import com.coioteshd2024.data.repository.AnimeRepository;
import com.coioteshd2024.data.repository.AuthRepository;
import com.coioteshd2024.data.repository.MediaRepository;
import com.coioteshd2024.databinding.FragmentFavouriteMoviesBinding;
import com.coioteshd2024.di.Injectable;
import com.coioteshd2024.ui.manager.SettingsManager;
import com.coioteshd2024.ui.manager.TokenManager;
import com.coioteshd2024.ui.viewmodels.LoginViewModel;
import com.coioteshd2024.ui.viewmodels.MoviesListViewModel;
import com.coioteshd2024.util.SpacingItemDecoration;
import com.coioteshd2024.util.Tools;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;


public class AnimesListFragment extends Fragment implements Injectable , DeleteFavoriteDetectListner {

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    FragmentFavouriteMoviesBinding binding;

    @Inject
    AnimeRepository animeRepository;


    @Inject
    AuthRepository authRepository;


    @Inject
    MediaRepository mediaRepository;

    @Inject
    SettingsManager settingsManager;

    @Inject
    TokenManager tokenManager;

    private AnimesOnlineMyListdapter animesOnlineMyListdapter;

    private LoginViewModel loginViewModel;

    private MoviesListViewModel moviesListViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_movies, container, false);

        // ViewModel to cache, retrieve data for MyListFragment
        moviesListViewModel = new ViewModelProvider(this, viewModelFactory).get(MoviesListViewModel.class);

        loginViewModel = new ViewModelProvider(this, viewModelFactory).get(LoginViewModel.class);

        animesOnlineMyListdapter = new AnimesOnlineMyListdapter(animeRepository,authRepository,this);

        onLoadListData();

        binding.rvMylist.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        binding.rvMylist.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(requireActivity(), 0), true));
        binding.rvMylist.setHasFixedSize(true);


        return  binding.getRoot();

    }

    private void onLoadListData() {


        if (settingsManager.getSettings().getFavoriteonline() == 1 && tokenManager.getToken().getAccessToken() !=null) {

            onLoadAnimesListOnline();

        } else {

            onLoadAnimesListOffline();
        }
    }

    private void onLoadAnimesListOffline() {

        AnimesMyListdapter animesMyListdapter = new AnimesMyListdapter(animeRepository);

        moviesListViewModel.getAnimesFavorites().observe(getViewLifecycleOwner(), favoriteMovies -> {

            animesMyListdapter.addToContent(favoriteMovies,requireActivity());
            binding.rvMylist.setAdapter(animesMyListdapter);
            binding.rvMylist.setEmptyView(binding.noResults);

        });
    }

    private void onLoadAnimesListOnline() {

        loginViewModel.getAuthDetails();
        loginViewModel.authDetailMutableLiveData.observe(getViewLifecycleOwner(), auth -> {

            animesOnlineMyListdapter.addToContent(auth.getFavoritesAnimes(),requireActivity());
            binding.rvMylist.setAdapter(animesOnlineMyListdapter);
            binding.rvMylist.setEmptyView(binding.noResults);
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.rvMylist.setAdapter(null);
        binding =null;


    }



    @Override
    public void onResume() {
        super.onResume();
        onLoadListData();
        animesOnlineMyListdapter.notifyDataSetChanged();
    }

    @Override
    public void onMediaDeletedSuccess(boolean clicked) {
        if (clicked) {
            onLoadListData();
            animesOnlineMyListdapter.notifyDataSetChanged();
        }
    }
}
