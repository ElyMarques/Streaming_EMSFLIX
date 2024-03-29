package com.coioteshd2024.data.model;

import com.coioteshd2024.data.local.entity.Media;
import com.coioteshd2024.data.model.comments.Comment;
import com.coioteshd2024.data.model.credits.Cast;
import com.coioteshd2024.data.model.episode.LatestEpisodes;
import com.coioteshd2024.data.model.featureds.Featured;
import com.coioteshd2024.data.model.genres.Genre;
import com.coioteshd2024.data.model.networks.Network;
import com.coioteshd2024.data.model.plans.Plan;
import com.coioteshd2024.data.model.upcoming.Upcoming;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.coioteshd2024.data.model.episode.Episode;
import com.coioteshd2024.data.model.episode.EpisodeStream;

import java.util.List;

/**
 * @author Yobex.
 */
public class MovieResponse {
    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    @SerializedName("networks")
    @Expose
    private List<Network> networks;

    public List<Media> getGlobaldata() {
        return globaldata;
    }

    public void setGlobaldata(List<Media> globaldata) {
        this.globaldata = globaldata;
    }

    @SerializedName("data")
    @Expose
    private List<Media> globaldata = null;


    public List<Media> getAll() {
        return all;
    }

    public void setAll(List<Media> all) {
        this.all = all;
    }

    @SerializedName("all")
    @Expose
    private List<Media> all = null;


    @SerializedName("latest")
    @Expose
    private List<Media> latest = null;

    public List<Media> getFeaturedStreaming() {
        return featuredStreaming;
    }

    public void setFeaturedStreaming(List<Media> featuredStreaming) {
        this.featuredStreaming = featuredStreaming;
    }

    @SerializedName("featured")
    @Expose
    private List<Featured> featured = null;


    @SerializedName("featured_streaming")
    @Expose
    private List<Media> featuredStreaming = null;

    public List<Media> getDrama() {
        return drama;
    }

    public void setDrama(List<Media> drama) {
        this.drama = drama;
    }

    @SerializedName("drama")
    @Expose
    private List<Media> drama = null;

    @SerializedName("recommended")
    @Expose
    private List<Media> recommended = null;

    public List<Media> getChoosed() {
        return choosed;
    }

    public void setChoosed(List<Media> choosed) {
        this.choosed = choosed;
    }

    @SerializedName("choosed")
    @Expose
    private List<Media> choosed = null;


    public List<LatestEpisodes> getLatestEpisodes() {
        return latestEpisodes;
    }

    public void setLatestEpisodes(List<LatestEpisodes> latestEpisodes) {
        this.latestEpisodes = latestEpisodes;
    }


    public List<LatestEpisodes> getLatestEpisodesAnimes() {
        return latestEpisodesAnimes;
    }

    public void setLatestEpisodesAnimes(List<LatestEpisodes> latestEpisodesAnimes) {
        this.latestEpisodesAnimes = latestEpisodesAnimes;
    }

    @SerializedName("latest_episodes_animes")
    @Expose
    private List<LatestEpisodes> latestEpisodesAnimes = null;

    @SerializedName("latest_episodes")
    @Expose
    private List<LatestEpisodes> latestEpisodes = null;

    public List<Media> getTop10() {
        return top10;
    }

    public void setTop10(List<Media> top10) {
        this.top10 = top10;
    }

    @SerializedName("top10")
    @Expose
    private List<Media> top10 = null;


    public List<Media> getTop10series() {
        return top10series;
    }

    public void setTop10series(List<Media> top10series) {
        this.top10series = top10series;
    }

    @SerializedName("top10series")
    @Expose
    private List<Media> top10series = null;


    public List<Media> getPreviews() {
        return previews;
    }

    public void setPreviews(List<Media> previews) {
        this.previews = previews;
    }

    @SerializedName("previews")
    @Expose
    private List<Media> previews = null;

    public List<Cast> getPopularCasters() {
        return popularCasters;
    }

    public void setPopularCasters(List<Cast> popularCasters) {
        this.popularCasters = popularCasters;
    }

    @SerializedName("popular_casters")
    @Expose
    private List<Cast> popularCasters = null;


    @SerializedName("upcoming")
    @Expose
    private List<Upcoming> upcoming = null;

    public List<Media> getGratis() {
        return gratis;
    }

    public void setGratis(List<Media> gratis) {
        this.gratis = gratis;
    }

    @SerializedName("gratis")
    @Expose
    private List<Media> gratis = null;



    public List<Media> getPinned() {
        return pinned;
    }


    public List<Media> getNovelas() {
        return novelas;
    }

    public void setNovelas(List<Media> novelas) {
        this.novelas = novelas;
    }

    @SerializedName("novelas")
    @Expose
    private List<Media> novelas = null;

    public List<Media> getMarvel() {
        return marvel;
    }

    public void setMarvel(List<Media> marvel) {
        this.marvel = marvel;
    }

    @SerializedName("marvel")
    @Expose
    private List<Media> marvel = null;

    public void setPinned(List<Media> pinned) {
        this.pinned = pinned;
    }

    @SerializedName("pinned")
    @Expose
    private List<Media> pinned = null;

    @SerializedName("suggested")
    @Expose
    private List<Media> suggested = null;



    @SerializedName("random")
    @Expose
    private List<Media> random = null;


    @SerializedName("trending")
    @Expose
    private List<Media> trending = null;


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @SerializedName("relateds")
    @Expose
    private List<Media> relateds = null;


    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;


    @SerializedName("popularSeries")
    @Expose
    private List<Media> popular = null;


    public List<Genre> getCategories() {
        return categories;
    }

    public void setCategories(List<Genre> categories) {
        this.categories = categories;
    }

    @SerializedName("livetv")
    @Expose
    private List<Media> streaming = null;


    @SerializedName("categories")
    @Expose
    private List<Genre> categories = null;

    public List<Media> getWatched() {
        return watched;
    }

    public void setWatched(List<Media> watched) {
        this.watched = watched;
    }

    @SerializedName("watched")
    @Expose
    private List<Media> watched = null;

    @SerializedName("recents")
    @Expose
    private List<Media> latestSeries = null;




    @SerializedName("anime")
    @Expose
    private List<Media> animes = null;



    @SerializedName("episodes")
    @Expose
    private List<Episode> episodes = null;


    @SerializedName("thisweek")
    @Expose
    private List<Media> thisweek = null;


    @SerializedName("plans")
    @Expose
    private List<Plan> plans = null;


    @SerializedName("popular")
    @Expose
    private List<Media> popularMedia = null;



    @SerializedName("videos")
    @Expose
    private List<EpisodeStream> videos = null;


    public List<Media> getLatest() {
        return latest;
    }

    public void setLatest(List<Media> latest) {
        this.latest = latest;
    }


    public List<Featured> getFeatured() {
        return featured;
    }


    public void setFeatured(List<Featured> featured) {
        this.featured = featured;
    }


    public List<Media> getRecommended() {
        return recommended;
    }

    public void setRecommended(List<Media> recommended) {
        this.recommended = recommended;
    }


    public List<Media> getSuggested() {
        return suggested;
    }

    public void setSuggested(List<Media> suggested) {
        this.suggested = suggested;
    }



    public List<Media> getRandom() {
        return random;
    }

    public void setRandom(List<Media> random) {
        this.random = random;
    }


    public List<Media> getTrending() {
        return trending;
    }

    public void setTrending(List<Media> trending) {
        this.trending = trending;
    }


    public List<Media> getRelateds() {
        return relateds;
    }

    public void setRelateds(List<Media> relateds) {
        this.relateds = relateds;
    }


    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }


    public List<EpisodeStream> getVideos() {
        return videos;
    }

    public void setVideos(List<EpisodeStream> videos) {
        this.videos = videos;
    }


    public List<Media> getPopular() {
        return popular;
    }

    public void setPopular(List<Media> popular) {
        this.popular = popular;
    }


    public List<Media> getStreaming() {
        return streaming;
    }

    public void setStreaming(List<Media> streaming) {
        this.streaming = streaming;
    }


    public List<Media> getLatestSeries() {
        return latestSeries;
    }

    public void setLatestSeries(List<Media> latestSeries) {
        this.latestSeries = latestSeries;
    }


    public List<Media> getAnimes() {
        return animes;
    }

    public void setAnimes(List<Media> animes) {
        this.animes = animes;
    }


    public List<Upcoming> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(List<Upcoming> upcoming) {
        this.upcoming = upcoming;
    }



    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }


    public List<Media> getThisweek() {
        return thisweek;
    }

    public void setThisweek(List<Media> thisweek) {
        this.thisweek = thisweek;
    }



    public List<Media> getPopularMedia() {
        return popularMedia;
    }

    public void setPopularMedia(List<Media> popularMedia) {
        this.popularMedia = popularMedia;
    }

}
