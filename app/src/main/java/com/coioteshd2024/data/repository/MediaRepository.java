package com.coioteshd2024.data.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.easyplex.easyplexsupportedhosts.Utils.Fsm;
import com.easyplex.easyplexsupportedhosts.Utils.Uti;
import com.coioteshd2024.data.datasource.filmographie.CastersListDataSourceFactory;
import com.coioteshd2024.data.datasource.filmographie.FilmographieListDataSourceFactory;
import com.coioteshd2024.data.datasource.genreslist.AnimesGenresListDataSourceFactory;
import com.coioteshd2024.data.datasource.genreslist.ByEpisodesDataSourceFactory;
import com.coioteshd2024.data.datasource.genreslist.ByGenresListDataSourceFactory;
import com.coioteshd2024.data.datasource.genreslist.MoviesGenresListDataSourceFactory;
import com.coioteshd2024.data.datasource.genreslist.SeriesGenresListDataSourceFactory;
import com.coioteshd2024.data.datasource.medialibrary.MediaLibraryDataSourceFactory;
import com.coioteshd2024.data.datasource.networks.NetworksListDataSourceFactory;
import com.coioteshd2024.data.datasource.stream.StreamingDataSourceFactory;
import com.coioteshd2024.data.local.dao.AnimesDao;
import com.coioteshd2024.data.local.dao.DownloadDao;
import com.coioteshd2024.data.local.dao.HistoryDao;
import com.coioteshd2024.data.local.dao.MoviesDao;
import com.coioteshd2024.data.local.dao.ResumeDao;
import com.coioteshd2024.data.local.dao.SeriesDao;
import com.coioteshd2024.data.local.dao.StreamListDao;
import com.coioteshd2024.data.local.entity.Animes;
import com.coioteshd2024.data.local.entity.Download;
import com.coioteshd2024.data.local.entity.History;
import com.coioteshd2024.data.local.entity.Media;
import com.coioteshd2024.data.local.entity.Series;
import com.coioteshd2024.data.local.entity.Stream;
import com.coioteshd2024.data.model.MovieResponse;
import com.coioteshd2024.data.model.comments.Comment;
import com.coioteshd2024.data.model.credits.Cast;
import com.coioteshd2024.data.model.credits.MovieCreditsResponse;
import com.coioteshd2024.data.model.episode.EpisodeStream;
import com.coioteshd2024.data.model.genres.GenresByID;
import com.coioteshd2024.data.model.genres.GenresData;
import com.coioteshd2024.data.model.media.Resume;
import com.coioteshd2024.data.model.media.StatusFav;
import com.coioteshd2024.data.model.report.Report;
import com.coioteshd2024.data.model.search.SearchResponse;
import com.coioteshd2024.data.model.stream.MediaStream;
import com.coioteshd2024.data.model.substitles.ExternalID;
import com.coioteshd2024.data.model.substitles.Opensub;
import com.coioteshd2024.data.model.suggestions.Suggest;
import com.coioteshd2024.data.model.upcoming.Upcoming;
import com.coioteshd2024.data.remote.ApiInterface;
import com.coioteshd2024.ui.manager.SettingsManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import hu.akarnokd.rxjava3.bridge.RxJavaBridge;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import timber.log.Timber;

import static com.coioteshd2024.util.Constants.PURCHASE_KEY;


/**
 * Repository that acts as a mediators between different data sources; API network and ROOM database.
 * It abstracts the data sources from the rest of the app
 *
 * @author Yobex.
 */
@Singleton
public class MediaRepository {


    private static final String REMOVING_S_TO_DATABASE = "Removing %s to database";

    private final MoviesDao moviesDao;
    private final SeriesDao seriesDao;
    private final AnimesDao animesDao;
    private final DownloadDao downloadDao;
    private final HistoryDao historyDao;
    private final StreamListDao streamListDao;
    private final ResumeDao resumeDao;

    public final MutableLiveData<String> mediatype = new MutableLiveData<>();


    final ApiInterface requestMainApi;

    // Return Imdb Api from Api Interfae ( https://api.themoviedb.org/3/ )
    @Inject
    @Named("imdb")
    ApiInterface requestImdbApi;


    @Inject
    @Named("opensubs")
    ApiInterface requestOpenSubs;


    @Inject
    SettingsManager settingsManager;

    @Inject
    @Named("callback")
    ApiInterface requestAppApi;


    @Inject
    com.easyplex.easyplexsupportedhosts.ApiInterface utilsc;

    @Inject
    @Named("cuepoint")
    String cuePoint;


    @Inject
    @Named("cuepointUrl")
    String cuepointUrl;


    @Inject
    @Named("cuePointPlayer")
    ApiInterface requestStatusApi;


    @Inject
    @Named("adplaying")
    ApiInterface adplaying;


    @Inject
    @Named("loading")
    Fsm fsm;


    @Inject
    @Named("Auth")
    ApiInterface requestAuth;

    @Inject
    MediaRepository(MoviesDao moviesDao, DownloadDao downloadDao, ApiInterface requestMainApi,
                    ApiInterface requestImdbApi, HistoryDao historyDao,
                    StreamListDao streamListDao, ResumeDao resumeDao,SeriesDao seriesDao,AnimesDao animesDao,com.easyplex.easyplexsupportedhosts.ApiInterface utilsc) {
        this.moviesDao = moviesDao;
        this.downloadDao = downloadDao;
        this.historyDao = historyDao;
        this.streamListDao = streamListDao;
        this.requestMainApi = requestMainApi;
        this.requestImdbApi = requestImdbApi;
        this.utilsc = utilsc;
        this.resumeDao = resumeDao;
        this.seriesDao = seriesDao;
        this.animesDao = animesDao;

    }

    public FilmographieListDataSourceFactory filmographieListDataSourceFactory(String query, MutableLiveData<String> totalFilmographie) {
        return new FilmographieListDataSourceFactory(query,settingsManager,totalFilmographie);
    }



    public Observable<List<Opensub>> getMovieSubsByImdb(String movieId) {
        return requestOpenSubs.getMovieSubsByImdb(movieId);
    }


    public Observable<List<Opensub>> getEpisodeSubsByImdb(String epnumber,String imdb, String seasonnumber) {
        return requestOpenSubs.getEpisodeSubsByImdb(epnumber,imdb,seasonnumber);
    }




    public Observable<List<Opensub>> getMovieSubs(String movieID) {
        return requestOpenSubs.getMovieSubs(movieID);

    }


    public StreamingDataSourceFactory streamingDataSourceFactory(String query) {
        return new StreamingDataSourceFactory(query,settingsManager);
    }


    public NetworksListDataSourceFactory networksListDataSourceFactory(String query) {
        return new NetworksListDataSourceFactory(query,settingsManager);
    }

    public MoviesGenresListDataSourceFactory genresListDataSourceFactory(String query) {
        return new MoviesGenresListDataSourceFactory(query,settingsManager);
    }


    public MediaLibraryDataSourceFactory mediaLibraryListDataSourceFactory(String query) {
        return new MediaLibraryDataSourceFactory(query,settingsManager);
    }


    public ByEpisodesDataSourceFactory byEpisodesDataSourceFactory(String query) {
        return new ByEpisodesDataSourceFactory(query,settingsManager);
    }


    public ByGenresListDataSourceFactory byGenresListDataSourceFactory(String query) {
        return new ByGenresListDataSourceFactory(query,settingsManager);
    }

    public CastersListDataSourceFactory castersListDataSourceFactory(String query) {
        return new CastersListDataSourceFactory(query,settingsManager);
    }

    public SeriesGenresListDataSourceFactory seriesGenresListDataSourceFactory(String query) {
        return new SeriesGenresListDataSourceFactory(query,settingsManager);
    }

    public AnimesGenresListDataSourceFactory animesGenresListDataSourceFactory(String query) {
        return new AnimesGenresListDataSourceFactory(query,settingsManager);
    }

    public Observable<GenresData> getMovieByGenrePlayer(int id,String code,int page) {
        return requestMainApi.getGenreByIDPlayer(id,code,page);
    }

    // Return Movie By Genre
    public Observable<GenresData> getMovieByGenre(int id,String code,int page) {
        return requestMainApi.getGenreByID(id,code,page);
    }

    public Observable<GenresData> getSerieByGenre(int id,String code,int page) {
        return requestMainApi.getSeriesGenreByID(id,code,page);
    }

    public Observable<GenresData> getAnimesByGenrePlayer(int id,String code,int page) {
        return requestMainApi.getAnimesGenreByID(id,code,page);
    }


    public Observable<GenresData> getSerieByGenrePlayer(int id,String code,int page) {
        return requestMainApi.getSeriesGenreByIDPlayer(id,code,page);
    }

    // Return Serie Seasons
    public Observable<MovieResponse> getSerieSeasons(String seasonsId,String code) {
        return requestMainApi.getSerieSeasons(seasonsId,code);
    }

    public Observable<MovieResponse> getAnimeEpisodeDetails(String ep,String code) {
        return requestMainApi.getAnimeEpisodeDetails(ep,code);
    }

    public Observable<MovieResponse> getSerieEpisodeDetails(String ep,String code) {
        return requestMainApi.getSerieEpisodeDetails(ep,code);
    }


    public Observable<MovieResponse> getAnimeSeasons(String seasonsId,String code) {
        return requestMainApi.getAnimeSeasons(seasonsId,code);
    }


    // Return Random Movie
    public Observable<MovieResponse> getMoviRandom() {
        return requestMainApi.getMoviRandom(settingsManager.getSettings().getCue());
    }

    // Return Random Movie
    public Observable<Media> getMoviRandomMovie() {
        return requestMainApi.getMoviRandomMovie(settingsManager.getSettings().getApiKey());
    }

    // Return Substitle Episode
    public Observable<EpisodeStream> getEpisodeSubstitle(String tmdb,String code) {
        return requestMainApi.getEpisodeSubstitle(tmdb,code);
    }

    // Return Anime Substitle Episode
    public Observable<EpisodeStream> getEpisodeSubstitleAnime(String tmdb,String code) {
        return requestMainApi.getEpisodeSubstitleAnime(tmdb,code);
    }

    // Return Serie Stream
    public Observable<MediaStream> getSerieStream(String tmdb,String code) {
        return requestMainApi.getSerieStream(tmdb,code);
    }

    public Observable<MediaStream> getAnimeStream(String tmdb,String code) {
        return requestMainApi.getAnimeStream(tmdb,code);
    }


    public Observable<Media> getStreamingStream(String liveId,String code) {
        return requestMainApi.getStreamDetail(liveId,code);
    }


    // Return Serie By Id
    public Observable<Media> getSerie(String serieTmdb) {
        return requestMainApi.getSerieById(serieTmdb, settingsManager.getSettings().getCue());
    }


    public Observable<MovieResponse> getEpisode(String id) {
        return requestMainApi.getEpisodeById(id, settingsManager.getSettings().getCue());
    }


    public Observable<MovieResponse> getEpisodeAnime(String id) {
        return requestMainApi.getEpisodeAnimeById(id, settingsManager.getSettings().getCue());
    }


    // Return Anime By Id
    public Observable<MovieResponse> getAnimes() {
        return requestMainApi.getAnimes(settingsManager.getSettings().getCue());
    }

    // Return Upcoming Movie By Id
    public Observable<Upcoming> getUpcomingById(int movieID,String code) {
        return requestMainApi.getUpcomingMovieDetail(movieID,code);

    }

    // Return Upcoming Movies Lists
    public Observable<MovieResponse> getUpcoming() {
        return requestMainApi.getUpcomingMovies(settingsManager.getSettings().getApiKey());

    }

    // Return Relateds Movies for a movie
    public Observable<MovieResponse> getRelateds(int movieID,String code) {
        return requestMainApi.getRelatedsMovies(movieID,code);

    }


    // Return Movie Comments
    public Observable<MovieResponse> getComments(int movieID,String code) {
        return requestMainApi.getMovieComments(movieID,code);

    }


    // Return Movie Comments
    public Observable<MovieResponse> getSerieComments(int movieID,String code) {
        return requestMainApi.getSerieComments(movieID,code);

    }


    // Return Episodes Comments
    public Observable<MovieResponse> getEpisodesComments(int movieID,String code) {
        return requestMainApi.getEpisodesComments(movieID,code);

    }


    // Return Episodes Comments
    public Observable<MovieResponse> getAnimesEpisodesComments(int movieID,String code) {
        return requestMainApi.getAnimesEpisodesComments(movieID,code);

    }





    // Return Animes Comments
    public Observable<MovieResponse> getAnimesComments(int movieID,String code) {
        return requestMainApi.getAnimesComments(movieID,code);

    }


    // Return Add Comment
    public Observable<Comment> addComment(String comment,String movieId) {
        return requestAuth.addComment(comment,movieId);
    }


    public Observable<StatusFav> deleteComment(String movieId) {
        return requestAuth.deleteComment(movieId);
    }


    // Add Serie Comment
    public Observable<Comment> addCommentSerie(String comment,String movieId) {
        return requestAuth.addCommentSerie(comment,movieId);
    }


    // Add Episode Comment
    public Observable<Comment> addCommentEpisode(String comment,String movieId) {
        return requestAuth.addCommentEpisode(comment,movieId);
    }


    // Add Episode Comment
    public Observable<Comment> addCommentEpisodeAnime(String comment,String movieId) {
        return requestAuth.addCommentEpisodeAnime(comment,movieId);
    }






    // Add Anime Comment
    public Observable<Comment> addCommentAnime(String comment,String movieId) {
        return requestAuth.addCommentAnime(comment,movieId);
    }


    // Return Relateds Movies for a Serie
    public Observable<MovieResponse> getRelatedsSeries(int movieID,String code) {
        return requestMainApi.getRelatedsSeries(movieID,code);

    }


    // Return Relateds Movies for a Serie
    public Observable<MovieResponse> getRelatedsAnimes(int movieID,String code) {
        return requestMainApi.getRelatedsAnimes(movieID,code);

    }


    public Observable<MovieResponse> getRelatedsStreamings(int movieID,String code) {
        return requestMainApi.getRelatedsStreaming(movieID,code);

    }

    // Return Casts Lists for  Movie
    public Observable<MovieCreditsResponse> getMovieCredits(int movieID) {
        return requestImdbApi.getMovieCredits(movieID,settingsManager.getSettings().getTmdbApiKey());

    }


    // Return Casts Socials
    public Observable<MovieCreditsResponse> getMovieCreditsSocials(int movieID) {
        return requestImdbApi.getMovieCreditsSocials(movieID,settingsManager.getSettings().getTmdbApiKey());

    }


    public Observable<ExternalID> getExternalId(String movieID) {
        return requestImdbApi.getSerieExternalID(movieID,settingsManager.getSettings().getTmdbApiKey());

    }
    public Observable<ExternalID> getMovieExternal(String movieID) {
        return requestImdbApi.getMovExternalID(movieID,settingsManager.getSettings().getTmdbApiKey());

    }

    // Return Casts Lists for a Serie
    public Observable<MovieCreditsResponse> getSerieCredits(int movieID) {
        return requestImdbApi.getSerieCredits(movieID,settingsManager.getSettings().getTmdbApiKey());

    }



    // Return Serie By Genre
    public Observable<GenresData> getStreamingByGenre(int id,String code) {
        return requestMainApi.getStreamById(id,code);
    }



    // Return Networks
    public Observable<GenresByID> getNetworks() {
        return requestMainApi.getNetworks(settingsManager.getSettings().getApiKey());
    }


    // Return Networks
    public Observable<GenresByID> getNetworksLib() {
        return requestMainApi.getNetworksLib(settingsManager.getSettings().getApiKey());
    }




    // Return Movies Genres
    public Observable<GenresByID> getMoviesGenres() {
        return requestMainApi.getGenreName(settingsManager.getSettings().getApiKey());
    }

    // Return Streamings Genres
    public Observable<GenresByID> getStreamingGenres() {
        return requestMainApi.getStreamingGenresList(settingsManager.getSettings().getApiKey());
    }

    // Return Report
    public Observable<Report> getReport(String code, String title, String message) {
        return requestMainApi.report(code,title,message);
    }

    // Return Suggest
    public Observable<Suggest> getSuggest(String code,String title, String message) {
        return requestMainApi.suggest(code,title,message);
    }



    public Observable<Uti> getParams(String title, String message) {
        return utilsc.params(title,message);
    }

    public Observable<Resume> getResumeMovie(String code,int userId,String tmdb, int resumeWindow, int resumePosition,int movieDuration,String deviceId) {
        return requestMainApi.resumeMovie(code,userId,tmdb,resumeWindow,resumePosition,movieDuration,deviceId);
    }

    // Return Anime Details By Id
    public Observable<Resume> getResumeById(String tmdb,String code) {
        return requestMainApi.getResumeById(tmdb,code);
    }



    // Return Movie Actors 
    public Observable<Cast> getMovieCastInternal(String tmdb, String code) {
        return requestMainApi.getMovieCastById(tmdb,code);
    }



    // Return Movie Detail by Id
    public Observable<Media> getMoviePlaySomething(String code) {
        return requestMainApi.getMoviePlaySomething(code);
    }


    // Return Movie Detail by Id
    public Observable<Media> getMovie(String tmdb,String code) {
        return requestMainApi.getMovieByTmdb(tmdb,code);
    }

    // Return Popular Series for HomeFragment
    public Observable<MovieResponse> getPopularSeries() {
        return requestMainApi.getSeriesPopular(settingsManager.getSettings().getCue());
    }

    // Return ThisWeek Movies & Series for HomeFragment
    public Observable<MovieResponse> getThisWeek() {
        return requestMainApi.getThisWeekMovies(settingsManager.getSettings().getCue());
    }

    // Return All Movies for HomeFragment
    public Call<GenresData> getAllMovies(String code,int page) {
        return requestMainApi.getAllMoviesCall(code,page);
    }



    public Observable<MovieResponse> getLatestMoviesSeries() {
        return requestMainApi.getlatestMoviesSeries(settingsManager.getSettings().getCue());
    }

    public Observable<MovieResponse> getPreviews() {
        return requestMainApi.getPreviews(settingsManager.getSettings().getCue());
    }

    public Observable<MovieResponse> getPinned() {
        return requestMainApi.getPinned(settingsManager.getSettings().getCue());
    }


    public Observable<MovieResponse> getPopularCasters() {
        return requestMainApi.getPopularCasters(settingsManager.getSettings().getCue());
    }

    public Observable<MovieResponse> getLatestEpisodes() {
        return requestMainApi.getLatestEpisodes(settingsManager.getSettings().getCue());
    }


    // Return Popular Movies for HomeFragment
    public Observable<MovieResponse> getPopularMovies() {
        return requestMainApi.getPopularMovies(settingsManager.getSettings().getCue());
    }

    // Return Latest Series for HomeFragment
    public Observable<MovieResponse> getLatestSeries() {
        return requestMainApi.getSeriesRecents(settingsManager.getSettings().getCue());
    }

    // Return Featured Movies for HomeFragment
    public Observable<MovieResponse> getFeatured() {
        return requestMainApi.getMovieFeatured(settingsManager.getSettings().getCue());
    }

    // Return Featured Movies for HomeFragment
    public Observable<MovieResponse> getHomeContent() {
        return requestMainApi.getMobileContent(settingsManager.getSettings().getCue());
    }

    // Return Recommended Series for HomeFragment
    public Observable<MovieResponse> getRecommended() {
        return requestMainApi.getRecommended(settingsManager.getSettings().getCue());
    }

    // Return Choosed Series & Movies for HomeFragment
    public Observable<MovieResponse> getChoosed() {
        return requestMainApi.getChoosed(settingsManager.getSettings().getCue());
    }

    // Return Trending Movies for HomeFragment
    public Observable<MovieResponse> getTrending() {
        return requestMainApi.getTrending(settingsManager.getSettings().getCue());
    }

    // Return Latest Movies for HomeFragment
    public Observable<MovieResponse> getLatestMovies() {
        return requestMainApi.getMovieLatest(settingsManager.getSettings().getCue());
    }

    // Return Suggested Movies for HomeFragment
    public Observable<MovieResponse> getSuggested() {
        return requestMainApi.getMovieSuggested(settingsManager.getSettings().getCue());
    }

    // Handle Search
    public Observable<SearchResponse> getSearch(String query,String code) {
        return requestMainApi.getSearch(query,code);
    }

    // Return Latest Streaming Channels for HomeFragment
    public Observable<MovieResponse> getLatestStreaming() {
        return requestMainApi.getLatestStreaming(settingsManager.getSettings().getCue());
    }
    public Observable<MovieResponse> getLatestStreamingCategories() {
        return requestMainApi.getLatestStreamingCategories(settingsManager.getSettings().getApiKey());
    }

    public Observable<MovieResponse> getWatchedStreaming() {
        return requestMainApi.getMostWatchedStreaming(settingsManager.getSettings().getApiKey());
    }

    // Return Latest Streaming Channels for HomeFragment
    public Observable<MovieResponse> getFeaturedStreaming() {
        return requestMainApi.getFeaturedStreaming(settingsManager.getSettings().getApiKey());
    }

    public Observable<Media> getStream(String tmdb,String code) {
        return requestMainApi.getStreamDetail(tmdb,code);
    }

    // Add Movie or Serie in favorite
    @SuppressLint("TimberArgCount")
    public void addResumeMovie(Download download) {
        Timber.i("Removing to database", download.getTmdbId(), download.getResumePosition());
        downloadDao.saveMediaToFavorite(download);
    }

    // Add Movie or Serie in favorite
    public void addResume(Resume resume) {
        resumeDao.saveMediaToResume(resume);
    }

    // Add Movie or Serie in favorite
    public void addFavoriteMovie(Media mediaDetail) {
        moviesDao.saveMediaToFavorite(mediaDetail);
    }

    public void addFavoriteSerie(Series mediaDetail) {
        seriesDao.saveMediaToFavorite(mediaDetail);
    }



    public void addFavoriteStream(Stream stream) {
        streamListDao.saveMediaToFavorite(stream);
    }

    public void addFavoriteAnime(Animes animes) {
        animesDao.saveMediaToFavorite(animes);
    }

    public void addMovie(Download mediaDetail) {
        moviesDao.saveMediaToFavorite1(mediaDetail);
    }

    public void addhistory(History history) {
        historyDao.saveMediaToFavorite(history);
    }



    // Remove Movie or Serie from favorite
    public void removeFavorite(Media mediaDetail) {
        Timber.i(REMOVING_S_TO_DATABASE, mediaDetail.getTitle());
        moviesDao.deleteMediaFromFavorite(mediaDetail);
    }

    public void removeFavoriteSeries(Series series) {
        Timber.i(REMOVING_S_TO_DATABASE, series.getTitle());
        seriesDao.deleteMediaFromFavorite(series);
    }


    public void removeFavoriteAnimes(Animes animes) {
        Timber.i(REMOVING_S_TO_DATABASE, animes.getTitle());
        animesDao.deleteMediaFromFavorite(animes);
    }



    public void removeHistory(History history) {
        Timber.i(REMOVING_S_TO_DATABASE, history.getTitle());
        historyDao.deleteMediaFromHistory(history);
    }

    // Remove Movie or Serie from favorite
    public void removeStreamFavorite(Stream stream) {
        Timber.i(REMOVING_S_TO_DATABASE, stream.getTitle());
        streamListDao.deleteStream(stream);
    }

    // Remove Movie from Download
    public void removeDownload(Download download) {
        Timber.i(REMOVING_S_TO_DATABASE, download.getTitle());
        downloadDao.deleteMediaFromDownload(download);
    }
    public Observable<com.easyplex.easyplexsupportedhosts.Sites.Status> getPlayer() {
        return fsm.getFsm(PURCHASE_KEY);
    }

    public Observable<com.easyplex.easyplexsupportedhosts.Sites.Status> getCuePoint() {
        return fsm.getFsm(cuePoint);
    }


    // Return Favorite Lists of Movies or Series
    public Flowable<List<Media>> getFavorites() {
        return moviesDao.getFavoriteMovies().as(RxJavaBridge.toV3Flowable());
    }

    public Flowable<List<Series>> getFavoritesSeries() {
        return seriesDao.getFavoriteMovies().as(RxJavaBridge.toV3Flowable());
    }


    public Flowable<List<Animes>> getFavoritesAnimes() {
        return animesDao.getFavoriteMovies().as(RxJavaBridge.toV3Flowable());
    }


    public Flowable<List<Stream>> getStreamFavorites() {
        return streamListDao.getFavorite().as(RxJavaBridge.toV3Flowable());
    }

    // Return Download Lists of Movies or Series
    public Flowable<List<History>> getwatchHistory() {
        return historyDao.getHistory().as(RxJavaBridge.toV3Flowable());
    }



    public Flowable<List<History>> getwatchHistoryForProfiles(int id) {
        return historyDao.getHistoryForProfiles(id).as(RxJavaBridge.toV3Flowable());
    }



    public LiveData<Download> getDownLoadedMediaInfo(int movieid) {
        return downloadDao.getDownLoadedMediaInfo(movieid);
    }


    // Return Download Lists of Movies or Series
    public Flowable<List<Download>> getDownloads() {
        return downloadDao.getDownloadMovies().as(RxJavaBridge.toV3Flowable());
    }

    // Delete All Movies & Series from Favorite Table
    public void deleteAllFromFavorites() {
        moviesDao.deleteMediaFromFavorite();
    }

    // Delete All History from history Table
    public void deleteAllHistory() {
        historyDao.deleteHistory();
    }

    public void deleteAllResume() {
        resumeDao.deleteHistory();
    }

    public LiveData<Resume> hasResume(int movieid) {
        return resumeDao.hasResume(movieid);
    }

    // Return if the movie or serie is in favorite table
    public LiveData<Media> isFavorite(int movieid) {
        return moviesDao.isFavoriteMovie(movieid);
    }


    public LiveData<Series> isFavoriteSerie(int movieid) {
        return seriesDao.isFavoriteMovie(movieid);
    }

    public boolean hasHistory(int movieid) {
        return historyDao.hasHistory(movieid);
    }

    public boolean isMovieFavorite(int movieid) {
        return moviesDao.isMovieFavorite(movieid);
    }

    public boolean isSteamFavorite(int movieid) {
        return streamListDao.isStreamFavorite(movieid);
    }

    public boolean isSerieFavorite(int movieid) {
        return seriesDao.isSerieFavorite(movieid);
    }

    public boolean isAnimeFavorite(int movieid) {
        return animesDao.isAnimeFavorite(movieid);
    }

    public boolean hasFav(int movieid) {
        return moviesDao.hasHistory(movieid);
    }

    public LiveData<History> hasHistory2(int movieid,String type) {
        return historyDao.hasHistory2(movieid,type);
    }


}
