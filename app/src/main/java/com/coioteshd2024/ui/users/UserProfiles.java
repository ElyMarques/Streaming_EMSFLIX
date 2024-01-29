package com.coioteshd2024.ui.users;

import static android.view.View.*;
import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.coioteshd2024.R;
import com.coioteshd2024.data.model.auth.Profile;
import com.coioteshd2024.data.model.auth.UserAuthInfo;
import com.coioteshd2024.data.repository.AuthRepository;
import com.coioteshd2024.databinding.ActivityProfilesSelectionBinding;
import com.coioteshd2024.di.Injectable;
import com.coioteshd2024.ui.base.BaseActivity;
import com.coioteshd2024.ui.login.LoginActivity;
import com.coioteshd2024.ui.manager.AuthManager;
import com.coioteshd2024.ui.manager.TokenManager;
import com.coioteshd2024.util.SpacingItemDecoration;
import com.coioteshd2024.util.Tools;
import com.facebook.login.LoginManager;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserProfiles extends AppCompatActivity implements Injectable {



    private  MultipartBody.Part tempororyAvatar;

    private static final int GALLERY_IMAGE_REQ_CODE = 102;


    ActivityProfilesSelectionBinding binding;

    @Inject
    AuthRepository authRepository;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    AuthManager authManager;

    @Inject
    TokenManager tokenManager;

    @Inject
    MenuHandler menuHandler;


    @Inject
    ProfilesAdapter profilesAdapter;


    private String currentProfileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profiles_selection);


        binding.closeProfile.setOnClickListener(v -> {

            startActivity(new Intent(UserProfiles.this, BaseActivity.class));
            finish();

        });


        binding.setController(menuHandler);

        binding.rvProfiles.setHasFixedSize(true);
        binding.rvProfiles.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvProfiles.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(this, 0), true));
        binding.rvProfiles.setAdapter(profilesAdapter);

         onLoadAuth();
         onAddProfile();


         binding.manageProfileLinear.setOnClickListener(v -> onSetEditMode());


        if (Boolean.TRUE.equals(menuHandler.isUserEditMode.get())){


            menuHandler.manageProfileText.set(getString(R.string.editmod_profiles));

        }else {

            menuHandler.manageProfileText.set(getString(R.string.manage_profiles));

        }


        profilesAdapter.setOnItemClickListener(new ProfilesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Profile profile, int pos) {


                currentProfileId = String.valueOf(profile.getId());

                ImagePicker.with(UserProfiles.this)
                        // Crop Image(User can choose Aspect Ratio)
                        .crop()
                        // User can only select image from Gallery
                        .galleryOnly()

                        .galleryMimeTypes(new String[]{"image/png",
                                "image/jpg",
                                "image/jpeg"
                        })
                        // Image resolution will be less than 1080 x 1920
                        .maxResultSize(1080, 1920)
                        .cropSquare()
                        .start(GALLERY_IMAGE_REQ_CODE);
            }
        });


        profilesAdapter.setonDeleteCommentListner(isDeleted -> authRepository.getAuth()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                        //

                    }

                    @Override
                    public void onNext(@NonNull UserAuthInfo userAuthInfo) {

                        profilesAdapter.addMain(userAuthInfo.getProfiles(),UserProfiles.this,authManager,authRepository);


                        binding.progressBar.setVisibility(GONE);


                        if (!userAuthInfo.getProfiles().isEmpty()){

                            binding.formContainer.setVisibility(VISIBLE);
                            menuHandler.isDataLoaded.set(true);
                            menuHandler.isUserHasProfiles.set(true);


                        }



                    }


                    @Override
                    public void onError(@NotNull Throwable e) {


                       //

                    }

                    @Override
                    public void onComplete() {

                        //

                    }
                }));

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // Uri object will not be null for RESULT_OK
            assert data != null;
            Uri uri = data.getData();

            RequestBody body = RequestBody.create(new File(uri.getPath()), null);

            RequestBody id =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), currentProfileId);

            RequestBody randomId =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), String.valueOf(Tools.createRandomCode(2)));

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part imagenPerfil = MultipartBody.Part.createFormData("avatar", "avatar.png", body);


            authRepository.updateUserSubProfileAvatar(imagenPerfil,id,randomId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                            //

                        }

                        @Override
                        public void onNext(@NotNull UserAuthInfo userAuthInfo) {

                            Toast.makeText(UserProfiles.this, R.string.profile_updated, Toast.LENGTH_SHORT).show();


                            authRepository.getAuth()
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<>() {
                                        @Override
                                        public void onSubscribe(@NotNull Disposable d) {

                                            //

                                        }

                                        @Override
                                        public void onNext(@NonNull UserAuthInfo userAuthInfo) {

                                            profilesAdapter.addMain(userAuthInfo.getProfiles(),UserProfiles.this,authManager,authRepository);


                                            binding.progressBar.setVisibility(GONE);


                                            if (!userAuthInfo.getProfiles().isEmpty()){

                                                binding.formContainer.setVisibility(VISIBLE);
                                                menuHandler.isDataLoaded.set(true);
                                                menuHandler.isUserHasProfiles.set(true);


                                            }



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



                        }

                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public void onError(@NotNull Throwable e) {

                            Toast.makeText(UserProfiles.this, R.string.profile_updated, Toast.LENGTH_SHORT).show();


                            authRepository.getAuth()
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<>() {
                                        @Override
                                        public void onSubscribe(@NotNull Disposable d) {

                                            //

                                        }

                                        @Override
                                        public void onNext(@NonNull UserAuthInfo userAuthInfo) {

                                            profilesAdapter.addMain(userAuthInfo.getProfiles(),UserProfiles.this,authManager,authRepository);


                                            binding.progressBar.setVisibility(GONE);


                                            if (!userAuthInfo.getProfiles().isEmpty()){

                                                binding.formContainer.setVisibility(VISIBLE);
                                                menuHandler.isDataLoaded.set(true);
                                                menuHandler.isUserHasProfiles.set(true);


                                            }



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

                        }

                        @Override
                        public void onComplete() {

                            //

                        }
                    });

        }
        super.onActivityResult(requestCode, resultCode, data);



    }

    private void onLoadAuth() {


        authRepository.getAuth()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                        //

                    }

                    @Override
                    public void onNext(@NonNull UserAuthInfo userAuthInfo) {

                        profilesAdapter.addMain(userAuthInfo.getProfiles(),UserProfiles.this,authManager,authRepository);


                        binding.progressBar.setVisibility(GONE);


                        if (!userAuthInfo.getProfiles().isEmpty()){

                            binding.formContainer.setVisibility(VISIBLE);
                            menuHandler.isDataLoaded.set(true);
                            menuHandler.isUserHasProfiles.set(true);


                        }



                    }


                    @Override
                    public void onError(@NotNull Throwable e) {


                        LoginManager.getInstance().logOut();
                        tokenManager.deleteToken();
                        authManager.deleteAuth();
                        startActivity(new Intent(UserProfiles.this, LoginActivity.class));
                        finish();

                    }

                    @Override
                    public void onComplete() {

                        //

                    }
                });

    }

    private void onSetEditMode() {

        if (Boolean.FALSE.equals(menuHandler.isUserEditMode.get())){

            menuHandler.isUserEditMode.set(true);

            menuHandler.manageProfileText.set(getString(R.string.manage_profiles));

        }else {

            menuHandler.isUserEditMode.set(false);

            menuHandler.manageProfileText.set(getString(R.string.editmod_profiles));
        }

        profilesAdapter.setEditMode(Boolean.FALSE.equals(menuHandler.isUserEditMode.get()));

    }

    private void onAddProfile() {

        binding.addProfileLinear.setOnClickListener(v -> menuHandler.isUserCreatingProfile.set(true));

        binding.btClose.setOnClickListener(v -> menuHandler.isUserCreatingProfile.set(false));
        
        
        binding.userProfileImage.setOnClickListener(v -> {


            if (!Objects.requireNonNull(binding.userProfileName.getEditText()).getText().toString().isEmpty()){

                ImagePicker.with(UserProfiles.this)


                        // Crop Image(User can choose Aspect Ratio)
                        .crop()
                        // User can only select image from Gallery
                        .galleryOnly()

                        .galleryMimeTypes(new String[]{"image/png",
                                "image/jpg",
                                "image/jpeg"
                        })
                        .maxResultSize(1080, 1920)
                        .cropSquare()
                        .start(GALLERY_IMAGE_REQ_CODE);

            }else {


                Toast.makeText(UserProfiles.this, "You must choose a profile name before choosing an avatar !", Toast.LENGTH_SHORT).show();

            }


        });


        binding.addProfile.setOnClickListener(v -> authRepository.addUserProfile(Objects.requireNonNull(binding.userProfileName.getEditText()).getText().toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                        //

                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onNext(@NotNull UserAuthInfo userAuthInfo) {



                        menuHandler.isUserCreatingProfile.set(false);
                        profilesAdapter.notifyDataSetChanged();
                        onLoadAuth();


                    }

                    @Override
                    public void onError(@NotNull Throwable e) {

                        Toast.makeText(UserProfiles.this, "Limite de perfis atingido", Toast.LENGTH_SHORT).show();
                        menuHandler.isUserCreatingProfile.set(true);

                    }

                    @Override
                    public void onComplete() {

                        //

                    }
                }));


    }



}
