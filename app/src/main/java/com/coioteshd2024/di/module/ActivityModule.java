package com.coioteshd2024.di.module;

import com.coioteshd2024.ui.animes.AnimeDetailsActivity;
import com.coioteshd2024.ui.base.BaseActivity;
import com.coioteshd2024.ui.casts.CastDetailsActivity;
import com.coioteshd2024.ui.downloadmanager.ui.main.DownloadManagerFragment;
import com.coioteshd2024.ui.login.LoginActivity;
import com.coioteshd2024.ui.login.PasswordForget;
import com.coioteshd2024.ui.moviedetails.MovieDetailsActivity;
import com.coioteshd2024.ui.notifications.NotificationManager;
import com.coioteshd2024.ui.payment.Payment;
import com.coioteshd2024.ui.payment.PaymentDetails;
import com.coioteshd2024.ui.payment.PaymentPaypal;
import com.coioteshd2024.ui.payment.PaymentStripe;
import com.coioteshd2024.ui.player.activities.EasyPlexMainPlayer;
import com.coioteshd2024.ui.player.activities.EmbedActivity;
import com.coioteshd2024.ui.profile.EditProfileActivity;
import com.coioteshd2024.ui.register.RegisterActivity;
import com.coioteshd2024.ui.register.RegistrationSucess;
import com.coioteshd2024.ui.seriedetails.EpisodeDetailsActivity;
import com.coioteshd2024.ui.seriedetails.SerieDetailsActivity;
import com.coioteshd2024.ui.splash.SplashActivity;
import com.coioteshd2024.ui.streaming.StreamingetailsActivity;
import com.coioteshd2024.ui.trailer.TrailerPreviewActivity;
import com.coioteshd2024.ui.upcoming.UpcomingTitlesActivity;
import com.coioteshd2024.ui.users.PhoneAuthActivity;
import com.coioteshd2024.ui.users.UserProfiles;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app. Add bindings for other sub-components here.
 * @ContributesAndroidInjector was introduced removing the need to:
 * a) Create separate components annotated with @Subcomponent (the need to define @Subcomponent classes.)
 * b) Write custom annotations like @PerActivity.
 *
 * @author Yobex.
 */
@Module
public abstract class ActivityModule {




    @ContributesAndroidInjector()
    abstract PhoneAuthActivity contributePhoneAuthActivity();

    @ContributesAndroidInjector()
    abstract UserProfiles contributeUserProfiles();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract BaseActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract DownloadManagerFragment contributeMainActivityDown();

    @ContributesAndroidInjector()
    abstract Payment contributePayment();

    @ContributesAndroidInjector()
    abstract PaymentPaypal contributePaymentPaypal();

    @ContributesAndroidInjector()
    abstract PaymentStripe contributePaymentStripe();

    @ContributesAndroidInjector()
    abstract NotificationManager contributeNotificationManager();

    @ContributesAndroidInjector()
    abstract PaymentDetails contributePaymentDetails();

    @ContributesAndroidInjector()
    abstract RegistrationSucess contributeRegistrationSucess();

    @ContributesAndroidInjector()
    abstract EditProfileActivity contributeEditProfileActivity();

    @ContributesAndroidInjector()
    abstract MovieDetailsActivity contributeMovieDetailActivity();

    @ContributesAndroidInjector()
    abstract SerieDetailsActivity contributeSerieDetailActivity();

    @ContributesAndroidInjector()
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector()
    abstract RegisterActivity contributeRegisterActivity();

    @ContributesAndroidInjector()
    abstract TrailerPreviewActivity contributeTrailerPreviewActivity();

    @ContributesAndroidInjector()
    abstract UpcomingTitlesActivity contributeUpcomingTitlesActivity();

    @ContributesAndroidInjector()
    abstract AnimeDetailsActivity contributeAnimeDetailsActivity();

    @ContributesAndroidInjector()
    abstract SplashActivity contributeSplashActivity();

    @ContributesAndroidInjector()
    abstract EmbedActivity contributeEmbedActivity();

    @ContributesAndroidInjector()
    abstract EasyPlexMainPlayer contributeEasyPlexMainPlayer();

    @ContributesAndroidInjector()
    abstract PasswordForget contributePasswordForget();

    @ContributesAndroidInjector()
    abstract CastDetailsActivity contributeCastDetailsActivity();

    @ContributesAndroidInjector()
    abstract StreamingetailsActivity contributeStreamingetailsActivity();

    @ContributesAndroidInjector()
    abstract EpisodeDetailsActivity contributeEpisodeDetailsActivity();
}
