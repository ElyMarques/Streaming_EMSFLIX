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
 * Adapter for Latest Series.
 *
 * @author Yobex.
 */
public class LatestseriesAdapter extends RecyclerView.Adapter<LatestseriesAdapter.MainViewHolder> {

    private List<Media> castList;
    private Context context;

    public void addLatest(List<Media> castList,Context context) {
        this.castList = castList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LatestseriesAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new LatestseriesAdapter.MainViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull LatestseriesAdapter.MainViewHolder holder, int position) {
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

        @SuppressLint("SetTextI18n")
        void onBind(final int position) {

            if (Tools.isRTL(Locale.getDefault())){
                binding.mgenres.setBackgroundResource(R.drawable.bg_label_rtl);
            }

            final Media serie = castList.get(position);


            if (serie.getSubtitle() !=null) {

                binding.substitle.setText(serie.getSubtitle());
            }else {

                binding.substitle.setVisibility(View.GONE);
            }

            if (serie.getNewEpisodes() == 1) {

                binding.hasNewEpisodes.setVisibility(View.VISIBLE);
            }


            if (serie.getPremuim() == 1) {

                binding.moviePremuim.setVisibility(View.VISIBLE);


            }else {

                binding.moviePremuim.setVisibility(View.GONE);
            }

            binding.movietitle.setText(serie.getName());



            binding.rootLayout.setOnLongClickListener(v -> {
                Toast.makeText(context, ""+serie.getName(), Toast.LENGTH_SHORT).show();
                return false;
            });



            binding.rootLayout.setOnClickListener(view -> {

                Intent intent = new Intent(context, SerieDetailsActivity.class);
                intent.putExtra(ARG_MOVIE, serie);
                context.startActivity(intent);


            });


            if (serie.getPremuim() == 1) {

                binding.moviePremuim.setVisibility(View.VISIBLE);


            }else {

                binding.moviePremuim.setVisibility(View.GONE);
            }

            binding.ratingBar.setRating(serie.getVoteAverage() / 2);
            binding.viewMovieRating.setText(String.valueOf(serie.getVoteAverage()));
            Tools.onLoadMediaCover(context,binding.itemMovieImage,serie.getPosterPath());
            if (serie.getReleaseDate() != null && !serie.getReleaseDate().trim().isEmpty()) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
                try {
                    Date releaseDate = sdf1.parse(serie.getReleaseDate());
                    binding.viewMovieViews.setText(""+sdf2.format(releaseDate));
                } catch (ParseException e) {

                    Timber.d("%s", Arrays.toString(e.getStackTrace()));

                }
            } else {
                binding.viewMovieViews.setText("");
            }

            binding.mgenres.setText(serie.getGenreName());

        }


    }
}
