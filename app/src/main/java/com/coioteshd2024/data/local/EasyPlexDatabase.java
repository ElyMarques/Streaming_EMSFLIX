package com.coioteshd2024.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.coioteshd2024.data.local.converters.CastConverter;
import com.coioteshd2024.data.local.converters.GenreConverter;
import com.coioteshd2024.data.local.converters.MediaStreamConverter;
import com.coioteshd2024.data.local.converters.MediaSubstitlesConverter;
import com.coioteshd2024.data.local.converters.SaisonConverter;
import com.coioteshd2024.data.local.converters.VideosConverter;
import com.coioteshd2024.data.local.dao.AnimesDao;
import com.coioteshd2024.data.local.dao.MoviesDao;
import com.coioteshd2024.data.local.dao.DownloadDao;
import com.coioteshd2024.data.local.dao.HistoryDao;
import com.coioteshd2024.data.local.dao.ResumeDao;
import com.coioteshd2024.data.local.dao.SeriesDao;
import com.coioteshd2024.data.local.dao.StreamListDao;
import com.coioteshd2024.data.local.entity.Animes;
import com.coioteshd2024.data.local.entity.History;
import com.coioteshd2024.data.local.entity.Media;
import com.coioteshd2024.data.local.entity.Download;
import com.coioteshd2024.data.local.entity.Series;
import com.coioteshd2024.data.local.entity.Stream;
import com.coioteshd2024.data.model.media.Resume;


/**
 * The Room database that contains the Favorite Movies & Series & Animes table
 * Define an abstract class that extends RoomDatabase.
 * This class is annotated with @Database, lists the entities contained in the database,
 * and the DAOs which access them.
 */
@Database(entities = {Media.class, Series.class, Animes.class, Download.class, History.class, Stream.class, Resume.class}, version =54)
@TypeConverters({GenreConverter.class,
        CastConverter.class,
        VideosConverter.class,
        SaisonConverter.class,
        MediaSubstitlesConverter.class,
        MediaStreamConverter.class})
public abstract class EasyPlexDatabase extends RoomDatabase {

    public abstract MoviesDao favoriteDao();
    public abstract SeriesDao seriesDao();
    public abstract AnimesDao animesDao();
    public abstract DownloadDao progressDao();
    public abstract HistoryDao historyDao();
    public abstract StreamListDao streamListDao();
    public abstract ResumeDao resumeDao();


}
