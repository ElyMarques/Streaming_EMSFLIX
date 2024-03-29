package com.coioteshd2024.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.coioteshd2024.R;
import com.coioteshd2024.data.repository.MediaRepository;
import com.coioteshd2024.databinding.LayoutNetworksBinding;
import com.coioteshd2024.di.Injectable;
import com.coioteshd2024.ui.viewmodels.NetworksViewModel;
import com.coioteshd2024.util.SpacingItemDecoration;
import com.coioteshd2024.util.Tools;

import javax.inject.Inject;


public class NetworksFragment2 extends Fragment implements Injectable {

    LayoutNetworksBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private NetworksViewModel networksViewModel;
    NetworkssAdapter adapter;


    @Inject
    MediaRepository mediaRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.layout_networks, container, false);

        networksViewModel = new ViewModelProvider(this, viewModelFactory).get(NetworksViewModel.class);

        adapter = new NetworkssAdapter();

        onLoadGenres();

        return binding.getRoot();


    }



    // Load Genres
    private void onLoadGenres() {

        networksViewModel.getNetworksLib();
        networksViewModel.networkLibMutableLiveData.observe(getViewLifecycleOwner(), movieDetail -> {

            if (!movieDetail.getNetworks().isEmpty()) {

                adapter.addMain(movieDetail.getNetworks(),requireActivity(),mediaRepository);

                binding.recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 4));
                binding.recyclerView.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(requireActivity(), 0), true));
                binding.recyclerView.setHasFixedSize(true);
                binding.recyclerView.setAdapter(adapter);



            }else {


                binding.noMoviesFound.setVisibility(View.VISIBLE);

            }


        });



    }


    // On Fragment Detach clear binding views & adapters to avoid memory leak
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.recyclerView.setAdapter(null);
        binding.constraintLayout.removeAllViews();
        binding = null;
    }

}
