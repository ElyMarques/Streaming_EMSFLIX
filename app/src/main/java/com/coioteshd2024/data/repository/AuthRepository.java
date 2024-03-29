package com.coioteshd2024.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coioteshd2024.data.model.auth.Login;
import com.coioteshd2024.data.model.auth.Profile;
import com.coioteshd2024.data.model.auth.Rating;
import com.coioteshd2024.data.model.auth.StripeStatus;
import com.coioteshd2024.data.model.auth.UserAuthInfo;
import com.coioteshd2024.data.model.media.StatusFav;
import com.coioteshd2024.data.remote.ApiInterface;
import com.coioteshd2024.data.remote.ErrorHandling;
import com.coioteshd2024.ui.manager.TokenManager;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class AuthRepository {


    @Inject
    @Named("Auth")
    ApiInterface requestAuth;


    final ApiInterface requestMainApi;


    @Inject
    TokenManager tokenManager;


    @Inject
    AuthRepository (ApiInterface requestLogin, TokenManager tokenManager,ApiInterface requestMainApi) {
        this.tokenManager = tokenManager;
        this.requestAuth = requestLogin;
        this.requestMainApi = requestMainApi;
    }

    public Observable<UserAuthInfo> getUserDetail(String userId, String code) {
        return requestMainApi.getUserDetail(userId,code);
    }



    public Observable<Rating> addRating(String movieId, double rating, String type) {

        return requestAuth.addRating(movieId,rating,type);
    }

    public LiveData<ErrorHandling<UserAuthInfo>> editUserProfile2(String name,String email) {
        final MutableLiveData<ErrorHandling<UserAuthInfo>> register = new MutableLiveData<>();


        Call<UserAuthInfo> call = requestAuth.updateUserProfile(name,email);
        call.enqueue(new Callback<UserAuthInfo>() {

            @Override
            public void onResponse(@NotNull Call<UserAuthInfo> call, @NotNull Response<UserAuthInfo> response) {
                if (response.body() != null) {
                    UserAuthInfo body = response.body();
                    register.setValue(ErrorHandling.success(body));
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserAuthInfo> call, @NotNull Throwable t) {
                register.setValue(ErrorHandling.error(t.getMessage(),null));
            }
        });

        return register;
    }

    public LiveData<ErrorHandling<UserAuthInfo>> editUserProfile(String name,String email, String password) {
        final MutableLiveData<ErrorHandling<UserAuthInfo>> register = new MutableLiveData<>();


        Call<UserAuthInfo> call = requestAuth.updateUserProfile(name,email,password);
        call.enqueue(new Callback<UserAuthInfo>() {

            @Override
            public void onResponse(@NotNull Call<UserAuthInfo> call, @NotNull Response<UserAuthInfo> response) {
                if (response.body() != null) {
                    UserAuthInfo body = response.body();
                    register.setValue(ErrorHandling.success(body));
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserAuthInfo> call, @NotNull Throwable t) {
                register.setValue(ErrorHandling.error(t.getMessage(),null));
            }
        });

        return register;
    }



    public LiveData<ErrorHandling<UserAuthInfo>> editUserAvatar(MultipartBody.Part avatar) {
        final MutableLiveData<ErrorHandling<UserAuthInfo>> register = new MutableLiveData<>();


        Call<UserAuthInfo> call = requestAuth.updateUserProfileAvatar(avatar);
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NotNull Call<UserAuthInfo> call, @NotNull Response<UserAuthInfo> response) {
                if (response.body() != null) {
                    UserAuthInfo body = response.body();
                    register.setValue(ErrorHandling.success(body));
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserAuthInfo> call, @NotNull Throwable t) {
                register.setValue(ErrorHandling.error(t.getMessage(), null));
            }
        });

        return register;
    }





    // Update User to Premuim
    public LiveData<ErrorHandling<UserAuthInfo>> getUpgradePlan(String transactionId,String stripePlanId,String stripePlanPrice,String packName,String packDuration) {
        final MutableLiveData<ErrorHandling<UserAuthInfo>> login = new MutableLiveData<>();


        Call<UserAuthInfo> call = requestAuth.upgradePlan(transactionId,stripePlanId,stripePlanPrice,packName,packDuration);
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NotNull Call<UserAuthInfo> call, @NotNull Response<UserAuthInfo> response) {

                if (response.isSuccessful()) {
                    UserAuthInfo body = response.body();
                    login.setValue(ErrorHandling.success(body));


                } else {

                    UserAuthInfo body = response.body();

                    login.setValue(ErrorHandling.error("Error", body));

                    Timber.i("Errror" + body);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserAuthInfo> call, @NotNull Throwable t) {
                login.setValue(ErrorHandling.error(t.getMessage(), null));
            }
        });

        return login;
    }



    public LiveData<ErrorHandling<UserAuthInfo>> getUpgradePaypal(String packId,String transactionId,String packName,String packDuration,String type) {
        final MutableLiveData<ErrorHandling<UserAuthInfo>> login = new MutableLiveData<>();


        Call<UserAuthInfo> call = requestAuth.userPaypalUpdate(packId,transactionId,packName,packDuration,type);
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NonNull Call<UserAuthInfo> call, @NonNull Response<UserAuthInfo> response) {


                if (response.isSuccessful()) {
                    UserAuthInfo body = response.body();
                    login.setValue(ErrorHandling.success(body));


                } else {

                    UserAuthInfo body = response.body();

                    login.setValue(ErrorHandling.error("Error", body));

                    Timber.i("Errror%s", body);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserAuthInfo> call, @NonNull Throwable t) {
                login.setValue(ErrorHandling.error(t.getMessage(), null));
            }
        });

        return login;
    }


    public Observable<StatusFav> getAddMovieOnline(String movieid) {
        return requestAuth.addMovieToFavOnline(movieid);
    }

    public Observable<StatusFav> getAddStreamingOnline(String movieid) {
        return requestAuth.addStreamingToFavOnline(movieid);
    }


    public Observable<StatusFav> getAddSerieOnline(String movieid) {
        return requestAuth.addSerieToFavOnline(movieid);
    }


    public Observable<StatusFav> getAddAnimeOnline(String movieid) {
        return requestAuth.addAnimeToFavOnline(movieid);
    }




    public Observable<StatusFav> getisMovieFavoriteOnline(String movieid) {
        return requestAuth.isMovieFavoriteOnline(movieid);
    }


    public Observable<StatusFav> getisStreamingFavoriteOnline(String movieid) {
        return requestAuth.isStreamingFavoriteOnline(movieid);
    }


    public Observable<StatusFav> getisSerieFavoriteOnline(String movieid) {
        return requestAuth.isSerieFavoriteOnline(movieid);
    }



    public Observable<StatusFav> getisAnimeFavoriteOnline(String movieid) {
        return requestAuth.isAnimeFavoriteOnline(movieid);
    }



    public Observable<StatusFav> getDeleteMovieOnline(String movieid) {
        return requestAuth.deleteMovieToFavOnline(movieid);
    }



    public Observable<StatusFav> getDeleteStreamingOnline(String movieid) {
        return requestAuth.deleteStreamingToFavOnline(movieid);
    }




    public Observable<StatusFav> getDeleteSerieOnline(String movieid) {
        return requestAuth.deleteSerieToFavOnline(movieid);
    }



    public Observable<StatusFav> getDeleteAnimeOnline(String movieid) {
        return requestAuth.deleteAnimeToFavOnline(movieid);
    }




    // Return Authenticated User with informations(Name,Email,etc...)
    public Observable<UserAuthInfo> getAuth() {
        return requestAuth.userAuthInfo();
    }


    public Observable<UserAuthInfo> updateUserStatus(String id) {
        return requestAuth.updateUserStatus(id);
    }


    // Return Authenticated User with informations(Name,Email,etc...)
    public Observable<UserAuthInfo> addUserProfile(String name) {
        return requestAuth.addUserProfile(name);
    }



    public Observable<Profile> deleteUserProfile(String profileId) {
        return requestAuth.deleteUserProfile(profileId);
    }


    public Observable<Profile> deleteDevice(String id) {
        return requestAuth.deleteDevice(id);
    }

    public Observable<UserAuthInfo> addDevice(String serialNumber,String name) {
        return requestAuth.addUserDevice(serialNumber,name);
    }


    // User Logout
    public Observable<UserAuthInfo> getUserLogout() {
        return requestAuth.userLogout();
    }


    public Observable<StripeStatus> getStripeStatus() {
        return requestAuth.isSubscribed();
    }


    public Observable<StatusFav> getIsExpired() {
        return requestAuth.isExpired();
    }





    // Cancel User Subcription
    public Observable<UserAuthInfo> cancelAuthSubcription() {
        return requestAuth.cancelUserAuthInfo();
    }


    // Cancel User Subcription
    public Observable<UserAuthInfo> cancelAuthSubcriptionPaypal() {
        return requestAuth.cancelUserAuthInfoPaypal();
    }




    public Observable<UserAuthInfo> updateUserSubProfileAvatar(MultipartBody.Part avatar, RequestBody id,RequestBody randomid) {
        return requestAuth.updateUserSubProfileAvatar(avatar,id,randomid);
    }




    public LiveData<ErrorHandling<Login>> getFacebookLogin(String token) {


        final MutableLiveData<ErrorHandling<Login>> login = new MutableLiveData<>();

        Call<Login> call = requestAuth.facebookLogin(token);
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NotNull Call<Login> call, @NotNull Response<Login> response) {


                if (response.isSuccessful()) {
                    Login body = response.body();
                    login.setValue(ErrorHandling.success(body));


                } else {

                    Login body = response.body();
                    login.setValue(ErrorHandling.error(response.message(), body));


                }
            }

            @Override
            public void onFailure(@NotNull Call<Login> call, @NotNull Throwable t) {
                login.setValue(ErrorHandling.error(t.getMessage(), null));
                Timber.i("%s", t.getMessage());

            }
        });

        return login;
    }



    public LiveData<ErrorHandling<Login>> getGoogleLogin(String token) {


        final MutableLiveData<ErrorHandling<Login>> login = new MutableLiveData<>();

        Call<Login> call = requestAuth.googlelogin(token);
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NotNull Call<Login> call, @NotNull Response<Login> response) {


                if (response.isSuccessful()) {
                    Login body = response.body();
                    login.setValue(ErrorHandling.success(body));



                } else {

                    Login body = response.body();
                    login.setValue(ErrorHandling.error(response.message(), body));


                }
            }

            @Override
            public void onFailure(@NotNull Call<Login> call, @NotNull Throwable t) {
                login.setValue(ErrorHandling.error(t.getMessage(), null));
                Timber.i("%s", t.getMessage());

            }
        });

        return login;
    }



    // Handle User Login
    public Observable<Login> getLogin(String username,String password) {
        return requestMainApi.login(username,password);
    }


    public LiveData<ErrorHandling<Login>> getForgetPassword(String username) {
        final MutableLiveData<ErrorHandling<Login>> login = new MutableLiveData<>();


        Call<Login> call = requestAuth.forgetPassword(username);
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NotNull Call<Login> call, @NotNull Response<Login> response) {


                if (response.isSuccessful()) {
                    Login body = response.body();
                    login.setValue(ErrorHandling.success(body));


                } else {

                    Login body = response.body();

                    login.setValue(ErrorHandling.error(response.message(), body));
                }
            }

            @Override
            public void onFailure(@NotNull Call<Login> call, @NotNull Throwable t) {
                login.setValue(ErrorHandling.error(t.getMessage(), null));
            }
        });

        return login;
    }





    public LiveData<ErrorHandling<UserAuthInfo>> getVerifyEmail() {

        final MutableLiveData<ErrorHandling<UserAuthInfo>> login = new MutableLiveData<>();


        Call<UserAuthInfo> call = requestAuth.getSendEmailToken();
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NotNull Call<UserAuthInfo> call, @NotNull Response<UserAuthInfo> response) {


                if (response.isSuccessful()) {
                    UserAuthInfo body = response.body();
                    login.setValue(ErrorHandling.success(body));


                } else {

                    UserAuthInfo body = response.body();

                    assert response.body() != null;
                    login.setValue(ErrorHandling.error(response.body().getMessage(), body));
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserAuthInfo> call, @NotNull Throwable t) {
                login.setValue(ErrorHandling.error(t.getMessage(), null));
            }
        });

        return login;
    }



    public LiveData<ErrorHandling<Login>> getPasswordUpdate(String token,String email,String password,String passwordConfirm) {
        final MutableLiveData<ErrorHandling<Login>> login = new MutableLiveData<>();

        Call<Login> call = requestAuth.forgetPasswordUpdate(token,email,password,passwordConfirm);
        call.enqueue(new Callback<Login>() {

            @Override
            public void onResponse(@NotNull Call<Login> call, @NotNull Response<Login> response) {


                if (response.isSuccessful()) {
                    Login body = response.body();
                    assert body != null;
                    login.setValue(ErrorHandling.success(body));


                }else {

                    Login body = response.body();

                    login.setValue(ErrorHandling.error("Error",body));
                }
            }

            @Override
            public void onFailure(@NotNull Call<Login> call, @NotNull Throwable t) {
                login.setValue(ErrorHandling.error(t.getMessage(),null));
            }
        });

        return login;
    }



    public Observable<Login> getRegister(String name,String email, String password) {
        return requestMainApi.register(name,email,password);
    }


}

