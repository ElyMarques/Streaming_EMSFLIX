package com.coioteshd2024.ui.home.adapters;

import static com.coioteshd2024.util.Constants.ARG_MOVIE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coioteshd2024.R;
import com.coioteshd2024.data.local.entity.Media;
import com.coioteshd2024.databinding.ItemMovieBinding;
import com.coioteshd2024.ui.animes.AnimeDetailsActivity;
import com.coioteshd2024.ui.manager.SettingsManager;
import com.coioteshd2024.ui.moviedetails.MovieDetailsActivity;
import com.coioteshd2024.ui.seriedetails.SerieDetailsActivity;
import com.coioteshd2024.util.AppController;
import com.coioteshd2024.util.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

/**
 * Adapter for Movie.
 *
 * @author Yobex.
 */
public class NovelasAdapter extends RecyclerView.Adapter<NovelasAdapter.MainViewHolder> {

    public List<Media> castList;
    private Context context;
     final SettingsManager settingsManager;
     final AppController appController;


    public NovelasAdapter(SettingsManager settingsManager, AppController appController) {

        this.settingsManager = settingsManager;
        this.appController = appController;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void addMain(List<Media> castList, Context context) {
        this.castList = castList;
        this.context = context;

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NovelasAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new NovelasAdapter.MainViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

        holder.onBind(position);

    }



    @Override
    public int getItemCount() {
        if (castList != null) {
            return castList.size();
        } else {
            return 0;
        }
    }

    class MainViewHolder extends RecyclerView.ViewHolder {

        private final ItemMovieBinding binding;

        MainViewHolder(@NonNull ItemMovieBinding binding)
        {
            super(binding.getRoot());

            this.binding = binding;

            binding.setController(appController);

            appController.isShadowEnabled.set(settingsManager.getSettings().getEnableShadows() == 1);
        }


        void onBind(final int position) {

            final Media media = castList.get(position);


            if (Tools.isRTL(Locale.getDefault())){
                binding.mgenres.setBackgroundResource(R.drawable.bg_label_rtl);
            }

            if (media.getSubtitle() !=null) {

                binding.substitle.setText(media.getSubtitle());

            }else {

                binding.substitle.setVisibility(View.GONE);
            }


            String mediaType = media.getType();
            if ("movie".equals(mediaType)) {
                binding.rootLayout.setOnLongClickListener(v -> {
                    Toast.makeText(context, "Filme: " + media.getTitle(), Toast.LENGTH_SHORT).show();
                    return false;
                });

                binding.rootLayout.setOnClickListener(view -> {

                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra(ARG_MOVIE, media);
                    context.startActivity(intent);


                });

                binding.movietitle.setText(media.getName());
            } else if ("serie".equals(mediaType)) {
                binding.movietitle.setText(media.getName());


                binding.rootLayout.setOnLongClickListener(v -> {
                    Toast.makeText(context, "Série: " + media.getTitle(), Toast.LENGTH_SHORT).show();
                    return false;
                });

                binding.rootLayout.setOnClickListener(view -> {


                    Intent intent = new Intent(context, SerieDetailsActivity.class);
                    intent.putExtra(ARG_MOVIE, media);
                    context.startActivity(intent);


                });


                if (media.getNewEpisodes() == 1) {

                    binding.hasNewEpisodes.setVisibility(View.VISIBLE);
                }
            } else if ("anime".equals(mediaType)) {
                binding.movietitle.setText(media.getName());

                binding.rootLayout.setOnLongClickListener(v -> {
                    Toast.makeText(context, "" + media.getName(), Toast.LENGTH_SHORT).show();
                    return false;
                });

                binding.rootLayout.setOnClickListener(view -> {

                    Intent intent = new Intent(context, AnimeDetailsActivity.class);
                    intent.putExtra(ARG_MOVIE, media);
                    context.startActivity(intent);


                });


                if (media.getNewEpisodes() == 1) {

                    binding.hasNewEpisodes.setVisibility(View.VISIBLE);
                }
            }



            if (media.getPremuim() == 1) {

                binding.moviePremuim.setVisibility(View.VISIBLE);


            }else {

                binding.moviePremuim.setVisibility(View.GONE);
            }


            binding.ratingBar.setRating(media.getVoteAverage() / 2);
            binding.viewMovieRating.setText(String.valueOf(media.getVoteAverage()));
            if (media.getReleaseDate() != null && !media.getReleaseDate().trim().isEmpty()) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
                try {
                    Date releaseDate = sdf1.parse(media.getReleaseDate());
                    binding.viewMovieViews.setText(""+sdf2.format(releaseDate));
                } catch (ParseException e) {

                    Timber.d("%s", Arrays.toString(e.getStackTrace()));

                }
            } else {
                binding.viewMovieViews.setText("");
            }

            Tools.onLoadMediaCoverAdapters(context,binding.itemMovieImage, media.getPosterPath());


            binding.mgenres.setText(media.getGenreName());

        }


    }



}
