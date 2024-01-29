package com.coioteshd2024.ui.home.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coioteshd2024.data.local.entity.Media;
import com.coioteshd2024.databinding.ItemPinnedBinding;
import com.coioteshd2024.ui.animes.AnimeDetailsActivity;
import com.coioteshd2024.ui.moviedetails.MovieDetailsActivity;
import com.coioteshd2024.ui.seriedetails.SerieDetailsActivity;
import com.coioteshd2024.util.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.coioteshd2024.util.Constants.ARG_MOVIE;

import timber.log.Timber;


/**
 * Adapter for Movie.
 *
 * @author Yobex.
 */
public class PinnedAdapter extends RecyclerView.Adapter<PinnedAdapter.MainViewHolder> {

    private List<Media> castList;
    private Context context;

    public void addMain(List<Media> mediaList,Context context) {
        this.castList = mediaList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PinnedAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemPinnedBinding binding = ItemPinnedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new PinnedAdapter.MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PinnedAdapter.MainViewHolder holder, int position) {
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

        private final ItemPinnedBinding binding;

        MainViewHolder(@NonNull ItemPinnedBinding binding)
        {
            super(binding.getRoot());

            this.binding = binding;
        }

        void onBind(final int position) {

            final Media media = castList.get(position);

            if (media.getSubtitle() !=null) {

                binding.substitle.setText(media.getSubtitle());
            }else {

                binding.substitle.setVisibility(View.GONE);
            }

            if (media.getType().equals("anime")) {

                binding.movietitle.setText(media.getName());

                if (media.getNewEpisodes() == 1) {

                    binding.hasNewEpisodes.setVisibility(View.VISIBLE);
                }


                binding.rootLayout.setOnLongClickListener(v -> {
                    Toast.makeText(context, ""+media.getName(), Toast.LENGTH_SHORT).show();
                    return false;
                });

                binding.rootLayout.setOnClickListener(view -> {


                    Intent intent = new Intent(context, AnimeDetailsActivity.class);
                    intent.putExtra(ARG_MOVIE, media);
                    context.startActivity(intent);


                });



            }else   if (media.getType().equals("serie"))  {



                binding.movietitle.setText(media.getName());

                if (media.getNewEpisodes() == 1) {

                    binding.hasNewEpisodes.setVisibility(View.VISIBLE);
                }


                binding.rootLayout.setOnLongClickListener(v -> {
                    Toast.makeText(context, ""+media.getName(), Toast.LENGTH_SHORT).show();
                    return false;
                });

                binding.rootLayout.setOnClickListener(view -> {


                    Intent intent = new Intent(context, SerieDetailsActivity.class);
                    intent.putExtra(ARG_MOVIE, media);
                    context.startActivity(intent);


                });


            }else  {


                binding.rootLayout.setOnLongClickListener(v -> {
                    Toast.makeText(context, ""+media.getTitle(), Toast.LENGTH_SHORT).show();
                    return false;
                });

                binding.rootLayout.setOnClickListener(view -> {


                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra(ARG_MOVIE, media);
                    context.startActivity(intent);


                });

                binding.movietitle.setText(media.getName());

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

            Tools.onLoadMediaCoverAdapters(context,binding.itemMovieImage, media.getBackdropPath());

        }

    }

}
