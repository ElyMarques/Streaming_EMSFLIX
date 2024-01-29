package com.coioteshd2024.ui.profile;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;
import static java.util.Objects.requireNonNull;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.TransitionManager;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.coioteshd2024.R;
import com.coioteshd2024.data.model.auth.UserAuthInfo;
import com.coioteshd2024.data.remote.ErrorHandling;
import com.coioteshd2024.data.repository.AuthRepository;
import com.coioteshd2024.ui.manager.AuthManager;
import com.coioteshd2024.ui.manager.SettingsManager;
import com.coioteshd2024.ui.settings.SettingsActivity;
import com.coioteshd2024.ui.viewmodels.LoginViewModel;
import com.coioteshd2024.util.GlideApp;
import com.coioteshd2024.util.Tools;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import javax.inject.Inject;

import at.favre.lib.crypto.bcrypt.BCrypt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class EditProfileActivity extends AppCompatActivity {


    private static final int GALLERY_IMAGE_REQ_CODE = 102;
    private Unbinder unbinder;

    @Inject
    ViewModelProvider.Factory viewModelFactory;



    @Inject
    SettingsManager settingsManager;

    private LoginViewModel loginViewModel;


    @Inject
    AuthRepository authRepository;


    @Inject
    AuthManager authManager;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.user_avatar)
    CircularImageView userImaveAvatar;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.til_name)
    TextInputLayout tilName;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.editText_name)
    TextInputEditText editTextName;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.editText_email)
    TextInputEditText editTextEmail;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.close_profile_fragment)
    ImageView closeProfileActivity;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container)
    ConstraintLayout container;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.form_container)
    LinearLayout formContainer;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.loader)
    ProgressBar loader;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.splash_image)
    ImageView splashImage;

    AwesomeValidation validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        unbinder = ButterKnife.bind(this);

        onLoadSplashImage();

        // LoginViewModel to cache, retrieve data for Authenticated User
        loginViewModel = new ViewModelProvider(this, viewModelFactory).get(LoginViewModel.class);

        onCheckAuthenticatedUser();

        onHideTaskBar();

        setupRules();

        closeProfileActivity.setOnClickListener(v -> finish());


    }







    private void onLoadSplashImage() {

        GlideApp.with(getApplicationContext()).asBitmap().load(settingsManager.getSettings().getSplashImage())
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(withCrossFade())
                .skipMemoryCache(true)
                .into(splashImage);

    }

    private void onCheckAuthenticatedUser() {


        authRepository.getAuth()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                        //

                    }

                    @Override
                    public void onNext(@NonNull UserAuthInfo auth) {



                        if (!auth.getProfiles().isEmpty()){


                            Tools.loadUserAvatar(EditProfileActivity.this, userImaveAvatar, authManager.getSettingsProfile().getAvatar());

                        }else {

                            Tools.loadUserAvatar(EditProfileActivity.this, userImaveAvatar, auth.getAvatar());

                        }



                        GlideApp.with(getApplicationContext()).asDrawable().load(auth.getAvatar())
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .skipMemoryCache(true)
                                .into(userImaveAvatar);
                        editTextName.setText(auth.getName());
                        editTextEmail.setText(auth.getEmail());



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


    private void onHideTaskBar() {

        Tools.hideSystemPlayerUi(this,true,0);

        Tools.setSystemBarTransparent(this);
    }



    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_upload_avatar)
    void pickProfileImage(){

        ImagePicker.with(this)
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // Uri object will not be null for RESULT_OK
            assert data != null;
            Uri uri = data.getData();

            GlideApp.with(getApplicationContext()).asBitmap().load(uri)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transition(withCrossFade())
                    .skipMemoryCache(true)
                    .into(userImaveAvatar);

            RequestBody body = RequestBody.create(new File(uri.getPath()), null);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("avatar", "avatar.png", body);

            loginViewModel.updateUserAvatar(photo).observe(this, login -> {

                if (login.status == ErrorHandling.Status.SUCCESS ) {

                    Toast.makeText(this, R.string.profile_updated, Toast.LENGTH_SHORT).show();

                } else  {

                    Toast.makeText(this, R.string.profile_update_error, Toast.LENGTH_SHORT).show();
                }

            });

        }
        super.onActivityResult(requestCode, resultCode, data);



    }



    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_update)
    void register(){

        String name = requireNonNull(tilName.getEditText()).getText().toString();
        String email = requireNonNull(tilEmail.getEditText()).getText().toString();
        String password = tilPassword.getEditText().getText().toString();

        tilName.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);

        if (validator.validate()) {
            showLoading();



            if (password.isEmpty()) {

                loginViewModel.updateUser(name,email).observe(this, login -> {

                    Toast.makeText(this, "Your profile has been updated successfully ! ", Toast.LENGTH_SHORT).show();

                    if (login.status == ErrorHandling.Status.SUCCESS ) {

                        startActivity(new Intent(this, SettingsActivity.class));
                        finish();


                    } else  {

                        showForms();

                        Toast.makeText(this, "Your profile is not  updated ! ", Toast.LENGTH_SHORT).show();

                    }

                });

            }else {

                String passwordHashed = BCrypt.withDefaults().hashToString(12, password.toCharArray());


                loginViewModel.updateUser(name,email,passwordHashed).observe(this, login -> {

                    Toast.makeText(this, "Your profile has been updated successfully ! ", Toast.LENGTH_SHORT).show();


                    if (login.status == ErrorHandling.Status.SUCCESS ) {

                        startActivity(new Intent(this, SettingsActivity.class));
                        finish();


                    } else  {

                        showForms();

                        Toast.makeText(this, "Your profile is not  updated ! ", Toast.LENGTH_SHORT).show();

                    }

                });

            }


        }

    }


    // show Progressbar on Update Button Submit
    private void showLoading(){
        TransitionManager.beginDelayedTransition(container);
        formContainer.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
    }


    private void showForms(){

        formContainer.setVisibility(View.VISIBLE);
        loader.setVisibility(View.GONE);

    }


    // Get the validation rules that apply to the request.
    public void setupRules(){

        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        validator.addValidation(this, R.id.til_name, RegexTemplate.NOT_EMPTY, R.string.err_name);
        validator.addValidation(this, R.id.til_email, Patterns.EMAIL_ADDRESS, R.string.err_email);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
