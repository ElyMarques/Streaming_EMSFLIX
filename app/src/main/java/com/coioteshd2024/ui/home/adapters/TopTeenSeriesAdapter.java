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

import com.coioteshd2024.data.local.entity.Media;
import com.coioteshd2024.databinding.ItemTopttenBinding;
import com.coioteshd2024.ui.animes.AnimeDetailsActivity;
import com.coioteshd2024.ui.moviedetails.MovieDetailsActivity;
import com.coioteshd2024.ui.seriedetails.SerieDetailsActivity;
import com.coioteshd2024.util.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

/**
 * Adapter for Movie.
 *
 * @author Yobex.
 */
public class TopTeenSeriesAdapter extends RecyclerView.Adapter<TopTeenSeriesAdapter.MainViewHolder> {

    private List<Media> castList;
    private Context context;

    public void addMain(List<Media> mediaList,Context context) {
        this.castList = mediaList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopTeenSeriesAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemTopttenBinding binding = ItemTopttenBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new TopTeenSeriesAdapter.MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopTeenSeriesAdapter.MainViewHolder holder, int position) {
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

        private final ItemTopttenBinding binding;

        MainViewHolder(@NonNull ItemTopttenBinding binding)
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






            String mediaType = media.getType();
            switch (mediaType) {
                case "movie":

                    binding.movietitle.setText(media.getName());

                    binding.mgenres.setText(""+(position + 1));


                    binding.rootLayout.setOnLongClickListener(v -> {
                        Toast.makeText(context, ""+media.getTitle(), Toast.LENGTH_SHORT).show();
                        return false;
                    });

                    binding.rootLayout.setOnClickListener(view -> {


                        Intent intent = new Intent(context, MovieDetailsActivity.class);
                        intent.putExtra(ARG_MOVIE, media);
                        context.startActivity(intent);


                    });

                    break;
                case "serie":

                    binding.movietitle.setText(media.getName());
                    binding.mgenres.setText(""+(position + 1));



                    binding.rootLayout.setOnLongClickListener(v -> {
                        Toast.makeText(context, ""+media.getName(), Toast.LENGTH_SHORT).show();
                        return false;
                    });

                    binding.rootLayout.setOnClickListener(view -> {


                        Intent intent = new Intent(context, SerieDetailsActivity.class);
                        intent.putExtra(ARG_MOVIE, media);
                        context.startActivity(intent);


                    });

                    break;
                case "anime":

                    binding.movietitle.setText(media.getName());
                    binding.mgenres.setText(""+(position + 1));


                    binding.rootLayout.setOnLongClickListener(v -> {
                        Toast.makeText(context, ""+media.getName(), Toast.LENGTH_SHORT).show();
                        return false;
                    });

                    binding.rootLayout.setOnClickListener(view -> {


                        Intent intent = new Intent(context, AnimeDetailsActivity.class);
                        intent.putExtra(ARG_MOVIE, media);
                        context.startActivity(intent);

                    });
                    break;
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

        }

    }

}
