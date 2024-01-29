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

import com.coioteshd2024.R;
import com.coioteshd2024.data.local.entity.Media;
import com.coioteshd2024.databinding.ItemMovieBinding;
import com.coioteshd2024.ui.animes.AnimeDetailsActivity;
import com.coioteshd2024.ui.moviedetails.MovieDetailsActivity;
import com.coioteshd2024.ui.seriedetails.SerieDetailsActivity;
import com.coioteshd2024.util.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.coioteshd2024.util.Constants.ARG_MOVIE;

import timber.log.Timber;


/**
 * Adapter for This Week Movie.
 *
 * @author Yobex.
 */
public class NewThisWeekAdapter extends RecyclerView.Adapter<NewThisWeekAdapter.MainViewHolder> {

    private List<Media> castList;
    private Context context;

    public void addThisWeek(List<Media> castList,Context context) {
        this.castList = castList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewThisWeekAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new NewThisWeekAdapter.MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewThisWeekAdapter.MainViewHolder holder, int position) {
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
        }


        void onBind(final int position) {

            if (Tools.isRTL(Locale.getDefault())){
                binding.mgenres.setBackgroundResource(R.drawable.bg_label_rtl);
            }

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
            switch (mediaType) {
                case "movie":

                    binding.rootLayout.setOnLongClickListener(v -> {
                        Toast.makeText(context, "" + media.getName(), Toast.LENGTH_SHORT).show();
                        return false;
                    });

                    binding.rootLayout.setOnClickListener(view -> {


                        Intent intent = new Intent(context, MovieDetailsActivity.class);
                        intent.putExtra(ARG_MOVIE, media);
                        context.startActivity(intent);


                    });

                    binding.movietitle.setText(media.getName());

                    break;
                case "serie":

                    binding.movietitle.setText(media.getName());


                    binding.rootLayout.setOnLongClickListener(v -> {
                        Toast.makeText(context, "" + media.getName(), Toast.LENGTH_SHORT).show();
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

                    break;
                case "anime":

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

                    break;
            }




            if (media.getPremuim() == 1) {

                binding.moviePremuim.setVisibility(View.VISIBLE);


            } else {

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

            Tools.onLoadMediaCoverAdapters(context, binding.itemMovieImage, media.getPosterPath());


            binding.mgenres.setText(media.getGenreName());

        }
    }





}
