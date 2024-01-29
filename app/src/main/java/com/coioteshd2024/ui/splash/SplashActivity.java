package com.coioteshd2024.ui.splash;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.coioteshd2024.R;
import com.coioteshd2024.data.model.ads.Ads;
import com.coioteshd2024.data.model.settings.Settings;
import com.coioteshd2024.data.repository.SettingsRepository;
import com.coioteshd2024.databinding.ActivitySplashBinding;
import com.coioteshd2024.di.Injectable;
import com.coioteshd2024.ui.login.LoginActivity;
import com.coioteshd2024.ui.manager.AdsManager;
import com.coioteshd2024.ui.manager.SettingsManager;
import com.coioteshd2024.ui.manager.StatusManager;
import com.coioteshd2024.util.Constants;
import com.coioteshd2024.util.DialogHelper;
import com.coioteshd2024.util.GlideApp;
import com.coioteshd2024.util.Tools;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.android.AndroidInjection;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity implements Injectable {

    ActivitySplashBinding binding;


    @Inject
    SettingsRepository settingsRepository;

    @Inject
    SettingsManager settingsManager;


    @Inject
    AdsManager adsManager;


    @Inject
    StatusManager statusManager;


    @Inject
    ViewModelProvider.Factory viewModelFactory;


    @Inject
    @Named("sniffer")
    @Nullable
    ApplicationInfo provideApplicationInfo;

    @Inject
    @Named("package_name")
    String packageName;



    @Inject
    @Named("vpn")
    boolean checkVpn;


    @Inject
    @Singleton
    @Named("firebaseRemoteUrl")
    FirebaseRemoteConfig provideFirebaseRemoteConfig;

    @SuppressLint("TimberArgCount")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);

        provideFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                if (task.isSuccessful()){
                    Timber.tag("TAG").d("onActivate: " + task.getResult() + ", " + Tools.getApiUrl());
                }else {
                    Timber.tag("TAG").d(task.getException(), "onActivationError: %s");
                }
            }else {
                Timber.tag("TAG").e(task.getException(), "onError: %s");
            }


        });

        onHideTaskBar();
        onLoadLogo();
        onLoadSplashImage();

        if (provideApplicationInfo != null){

            DialogHelper.snifferAppDetectorDialog(this,provideApplicationInfo.loadLabel(this.getPackageManager()).toString());

        }else if (settingsManager.getSettings().getVpn() ==1 && checkVpn) {


            finishAffinity();

            Toast.makeText(this, R.string.vpn_message, Toast.LENGTH_SHORT).show();

        } else {



            if (Constants.FIREBASECONFIG){


                final Handler handler = new Handler();
                handler.postDelayed(() -> settingsRepository.getSettings()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<>() {
                            @Override
                            public void onSubscribe(@NotNull Disposable d) {

                                //

                            }

                            @Override
                            public void onNext(@NonNull Settings settings) {

                                settingsManager.saveSettings(settings);


                                settingsRepository.getAdsSettings()
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<>() {
                                            @Override
                                            public void onSubscribe(@NotNull Disposable d) {

                                                //

                                            }

                                            @Override
                                            public void onNext(@NonNull Ads ads) {

                                                adsManager.saveSettings(ads);

                                            }


                                            @Override
                                            public void onError(@NotNull Throwable e) {


                                                //
                                            }

                                            @Override
                                            public void onComplete() {

                                                //

                                            }
                                        });




                                new Handler(Looper.getMainLooper()).postDelayed(() -> {

                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                    finish();
                                },3000);

                            }


                            @Override
                            public void onError(@NotNull Throwable e) {


                                Toast.makeText(SplashActivity.this, "Error Loading App Settings Make sure you are using the right Api Adress", Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onComplete() {

                                //

                            }
                        }), 3000);


            }else {


                settingsRepository.getSettings()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<>() {
                            @Override
                            public void onSubscribe(@NotNull Disposable d) {

                                //

                            }

                            @Override
                            public void onNext(@NonNull Settings settings) {

                                settingsManager.saveSettings(settings);


                                settingsRepository.getAdsSettings()
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<>() {
                                            @Override
                                            public void onSubscribe(@NotNull Disposable d) {

                                                //

                                            }

                                            @Override
                                            public void onNext(@NonNull Ads ads) {

                                                adsManager.saveSettings(ads);

                                            }


                                            @Override
                                            public void onError(@NotNull Throwable e) {


                                                //

                                            }

                                            @Override
                                            public void onComplete() {

                                                //

                                            }
                                        });




                                new Handler(Looper.getMainLooper()).postDelayed(() -> {

                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                    finish();
                                },500);

                            }


                            @Override
                            public void onError(@NotNull Throwable e) {


                                Toast.makeText(SplashActivity.this, "Error Loading Api  , please check your correct api adress !", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onComplete() {

                                //

                            }
                        });
            }


        }

    }


    private void onLoadSplashImage() {

        GlideApp.with(getApplicationContext()).asBitmap().load(settingsManager.getSettings().getSplashImage())
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(withCrossFade())
                .skipMemoryCache(true)
                .into(binding.splashImage);

    }


    // Hide TaskBar
    private void onHideTaskBar() {

        Tools.hideSystemPlayerUi(this,true,0);
    }


    // Load Logo
    private void onLoadLogo() {

        Tools.loadMiniLogo(this,binding.logoImageTop);


    }
}
