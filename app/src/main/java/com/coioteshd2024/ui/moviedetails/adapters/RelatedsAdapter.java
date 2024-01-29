package com.coioteshd2024.ui.moviedetails.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.coioteshd2024.data.local.entity.Media;
import com.coioteshd2024.databinding.ItemRelatedsBinding;
import com.coioteshd2024.ui.moviedetails.MovieDetailsActivity;
import com.coioteshd2024.util.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static com.coioteshd2024.util.Constants.ARG_MOVIE;

import timber.log.Timber;

/**
 * Adapter for Movie Casts.
 *
 * @author Yobex.
 */
public class RelatedsAdapter extends RecyclerView.Adapter<RelatedsAdapter.MainViewHolder> {

    private List<Media> castList;

    @SuppressLint("NotifyDataSetChanged")
    public void addToContent(List<Media> castList) {
        this.castList = castList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RelatedsAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemRelatedsBinding binding = ItemRelatedsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new RelatedsAdapter.MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedsAdapter.MainViewHolder holder, int position) {
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



        private final ItemRelatedsBinding binding;


        MainViewHolder(@NonNull ItemRelatedsBinding binding)
        {
            super(binding.getRoot());

            this.binding = binding;
        }


        void onBind(final int position) {


            final Media related = castList.get(position);
            Context context = binding.imageMovie.getContext();

            binding.movieName.setText(related.getTitle());
            binding.viewMovieRating.setText(String.valueOf(related.getVoteAverage()));

            if (related.getReleaseDate() != null && !related.getReleaseDate().trim().isEmpty()) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
                try {
                    Date releaseDate = sdf1.parse(related.getReleaseDate());
                    binding.viewMovieViews.setText(""+sdf2.format(releaseDate));
                } catch (ParseException e) {

                    Timber.d("%s", Arrays.toString(e.getStackTrace()));

                }
            } else {
                binding.viewMovieViews.setText("");
            }

            binding.rootLayout.setOnClickListener(v -> {
                ((Activity)context).finish();
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra(ARG_MOVIE, related);
                context.startActivity(intent);

            });

            Tools.onLoadMediaCover(context,binding.imageMovie,related.getPosterPath());


        }
    }
}
