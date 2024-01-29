package com.coioteshd2024.ui.login;

import static android.view.View.GONE;
import static com.basgeekball.awesomevalidation.ValidationStyle.TEXT_INPUT_LAYOUT;
import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;
import static com.coioteshd2024.util.Constants.APP_PASSWORD;
import static com.coioteshd2024.util.Constants.FIRST_PASSWORD_CHECK;
import static com.coioteshd2024.util.Constants.GOOGLE_CLIENT_ID;
import static com.coioteshd2024.util.Constants.SERVER_BASE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.coioteshd2024.R;
import com.coioteshd2024.data.model.auth.Login;
import com.coioteshd2024.data.model.auth.UserAuthInfo;
import com.coioteshd2024.data.remote.ErrorHandling;
import com.coioteshd2024.data.repository.AuthRepository;
import com.coioteshd2024.di.Injectable;
import com.coioteshd2024.ui.base.BaseActivity;
import com.coioteshd2024.ui.manager.SettingsManager;
import com.coioteshd2024.ui.manager.TokenManager;
import com.coioteshd2024.ui.register.RegisterActivity;
import com.coioteshd2024.ui.users.PhoneAuthActivity;
import com.coioteshd2024.ui.users.UserProfiles;
import com.coioteshd2024.ui.viewmodels.LoginViewModel;
import com.coioteshd2024.ui.viewmodels.SettingsViewModel;
import com.coioteshd2024.util.DialogHelper;
import com.coioteshd2024.util.GlideApp;
import com.coioteshd2024.util.Tools;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.stringcare.library.SC;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;


/**
 * EasyPlex - Android Movie Portal App
 * @package     EasyPlex - Android Movie Portal App
 * @author      @Y0bEX
 * @copyright   Copyright (c) 2020 Y0bEX,
 * @license     http://codecanyon.net/wiki/support/legal-terms/licensing-terms/
 * @profile     https://codecanyon.net/user/yobex
 * @link        yobexd@gmail.com
 * @skype       yobexd@gmail.com
 **/



public class LoginActivity extends AppCompatActivity implements Injectable {


    com.facebook.AccessTokenTracker accessTokenTracker;
    private Unbinder unbinder;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    SharedPreferences.Editor sharedPreferencesEditor;

    @Inject
    TokenManager tokenManager;

    @Inject
    SettingsManager settingsManager;

    @Inject
    AuthRepository authRepository;


    @Inject
    ViewModelProvider.Factory viewModelFactory;


    private LoginViewModel loginViewModel;
    private SettingsViewModel settingsViewModel;


    AwesomeValidation validator;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.til_password_code)
    TextInputLayout til_password_code;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.logo_image_top)
    ImageView logoimagetop;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.splash_image)
    ImageView splashImage;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.code_access_enable)
    LinearLayout code_access_enable;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_enter_password_access)
    Button btnEnterPasswordAccess;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_skip)
    LinearLayout skipe;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_login)
    LinearLayout btnLogin;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.form_container)
    LinearLayout formContainer;


    @BindView(R.id.textViewCheckingAuth)
    TextView textViewCheckingAuth;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.loader)
    ProgressBar loader;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.login_button)
    com.facebook.login.widget.LoginButton mLoginButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_facebook)
    ImageButton mLoginButtonIcon;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_google)
    ImageButton mButtonGoogle;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sign_in_button)
    com.google.android.gms.common.SignInButton mSignGoogleButton;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_get_code)
    TextView get_code;

    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String AUTH_TYPE = "rerequest";
    private CallbackManager mCallbackManager;

    private GoogleSignInClient mGoogleSignInClient;

    private static final int RC_GET_TOKEN = 9002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        unbinder = ButterKnife.bind(this);

        loginViewModel = new ViewModelProvider(this, viewModelFactory).get(LoginViewModel.class);

        settingsViewModel = new ViewModelProvider(this, viewModelFactory).get(SettingsViewModel.class);

        mCallbackManager = CallbackManager.Factory.create();

        Tools.hideSystemPlayerUi(this, true, 0);

        Tools.setSystemBarTransparent(this);

        onLoadAppLogo();
        onLoadSplashImage();
        onLoadValitator();
        onSetupRules();
        onLoadGoogleOneTapSigning();

        if (!sharedPreferences.getBoolean(FIRST_PASSWORD_CHECK, false)) {
            sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putBoolean(FIRST_PASSWORD_CHECK, Boolean.TRUE);
            sharedPreferencesEditor.apply();

        }



        if (settingsManager.getSettings().getForce_password_access() == 1) {


            String savedPassword = sharedPreferences.getString(APP_PASSWORD,null);

            loader.setVisibility(View.GONE);
            settingsViewModel.getAppPasswordCheck(savedPassword);
            settingsViewModel.appPasswordMutableLiveData.observe(this, passwordcheck -> {

                if (passwordcheck !=null && passwordcheck.getPassword().equals("match")){



                    if (tokenManager.getToken().getAccessToken() != null) {

                        code_access_enable.setVisibility(View.GONE);

                        onRedirect();

                    }else {

                        savePassword(savedPassword);
                        code_access_enable.setVisibility(View.GONE);
                        formContainer.setVisibility(View.VISIBLE);
                    }



                }else {

                    loader.setVisibility(View.GONE);
                    code_access_enable.setVisibility(View.VISIBLE);
                    formContainer.setVisibility(View.GONE);
                }
            });


        }else if (tokenManager.getToken().getAccessToken() != null) {

            onRedirect();

        }else {


            code_access_enable.setVisibility(View.GONE);
            formContainer.setVisibility(View.VISIBLE);

        }

        btnEnterPasswordAccess.setOnClickListener(v -> {

            String passwordMatch = til_password_code.getEditText().getText().toString();


            settingsViewModel.getAppPasswordCheck(passwordMatch);
            settingsViewModel.appPasswordMutableLiveData.observe(this, passwordcheck -> {

                if (passwordcheck !=null && passwordcheck.getPassword().equals("match")){

                    savePassword(passwordMatch);
                    code_access_enable.setVisibility(View.GONE);
                    formContainer.setVisibility(View.VISIBLE);

                }else {

                    Toast.makeText(LoginActivity.this, R.string.access_code, Toast.LENGTH_SHORT).show();
                }
            });

        });

        get_code.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(settingsManager.getSettings().getInstagramUrl()))));

        if (settingsManager.getSettings().getForceLogin() == 1){
            skipe.setVisibility(View.GONE);
        }


        mLoginButtonIcon.setOnClickListener(v -> mLoginButton.performClick());

        // Set the initial permissions to request from the user while logging in
        mLoginButton.setPermissions(Arrays.asList(EMAIL, USER_POSTS));

        mLoginButton.setAuthType(AUTH_TYPE);

        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                onLoadAuthFromFacebook(loginResult);
            }

            @Override
            public void onCancel() {
                Timber.i("Login attempt canceled.");

            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Timber.i("Login attempt failed.");
            }
        });


        mButtonGoogle.setOnClickListener(v -> signIn());
        mSignGoogleButton.setOnClickListener(v -> signIn());
        skipe.setOnClickListener(v -> skip());
        btnLogin.setOnClickListener(v -> login());
    }

    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GET_TOKEN);
    }

    private void onLoadGoogleOneTapSigning() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(SC.reveal(GOOGLE_CLIENT_ID))
                .requestEmail()
                .requestServerAuthCode(SC.reveal(GOOGLE_CLIENT_ID))
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }


    private void onLoadAuthFromFacebook(LoginResult loginResult) {

        loginViewModel.getLoginFromFacebook(loginResult.getAccessToken().getToken()).observe(LoginActivity.this, login -> {

            hideKeyboard();
            formContainer.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);

            if (login.status == ErrorHandling.Status.SUCCESS) {
                assert login.data != null;
                tokenManager.saveToken(login.data);
                Timber.i(login.data.getAccessToken());
                onRedirect();

            } else {

                formContainer.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
                DialogHelper.erroLogin(this);


            }

        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode , resultCode , data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GET_TOKEN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            onLoadAuthFromGoogle(account);
        } catch (ApiException e) {
            Timber.tag("TAG").w(e, "handleSignInResult:error");


        }
    }

    private void onLoadAuthFromGoogle(GoogleSignInAccount account) {

        loginViewModel.getLoginFromGoogle(account.getServerAuthCode()).observe(LoginActivity.this, login -> {

            hideKeyboard();
            formContainer.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);

            if (login.status == ErrorHandling.Status.SUCCESS) {
                assert login.data != null;
                tokenManager.saveToken(login.data);
                Timber.i(login.data.getAccessToken());
                onRedirect();

            } else {

                formContainer.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
                DialogHelper.erroLogin(this);


            }

        });
    }

    private void onLoadSplashImage() {

        GlideApp.with(getApplicationContext()).asBitmap().load(settingsManager.getSettings().getSplashImage())
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(withCrossFade())
                .skipMemoryCache(true)
                .into(splashImage);

    }


    void skip(){

        startActivity(new Intent(LoginActivity.this, BaseActivity.class));
        finish();


    }

    private void onRedirect() {

        textViewCheckingAuth.setVisibility(View.VISIBLE);
        loader.setVisibility(View.VISIBLE);
        formContainer.setVisibility(GONE);

        authRepository.getAuth()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                        //

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull UserAuthInfo userAuthInfo) {

                        loader.setVisibility(GONE);
                        textViewCheckingAuth.setVisibility(GONE);


                        if (settingsManager.getSettings().getPhoneVerification() == 1){




                            if (userAuthInfo.getVerified() != 1) {


                                startActivity(new Intent(LoginActivity.this, PhoneAuthActivity.class));
                                finish();


                            }else {


                                if (settingsManager.getSettings().getProfileSelection() == 1 ){

                                    if (!userAuthInfo.getProfiles().isEmpty()) {

                                        startActivity(new Intent(LoginActivity.this, UserProfiles.class));
                                        finish();


                                    }else {


                                        loader.setVisibility(GONE);
                                        textViewCheckingAuth.setVisibility(GONE);

                                        startActivity(new Intent(LoginActivity.this, BaseActivity.class));
                                        finish();


                                    }

                                }else {


                                    loader.setVisibility(GONE);
                                    textViewCheckingAuth.setVisibility(GONE);

                                    startActivity(new Intent(LoginActivity.this, BaseActivity.class));
                                    finish();
                                }


                            }



                        }else {


                            if (settingsManager.getSettings().getProfileSelection() == 1 ){

                                if (!userAuthInfo.getProfiles().isEmpty()) {

                                    startActivity(new Intent(LoginActivity.this, UserProfiles.class));
                                    finish();


                                }else {


                                    loader.setVisibility(GONE);
                                    textViewCheckingAuth.setVisibility(GONE);

                                    startActivity(new Intent(LoginActivity.this, BaseActivity.class));
                                    finish();


                                }

                            }else {


                                loader.setVisibility(GONE);
                                textViewCheckingAuth.setVisibility(GONE);

                                startActivity(new Intent(LoginActivity.this, BaseActivity.class));
                                finish();
                            }
                        }


                    }


                    @Override
                    public void onError(@NotNull Throwable e) {

                        formContainer.setVisibility(View.VISIBLE);
                        loader.setVisibility(View.GONE);
                        textViewCheckingAuth.setVisibility(GONE);

                    }

                    @Override
                    public void onComplete() {

                        //

                    }
                });



    }


    void login() {

        String email = tilEmail.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();
        tilEmail.setError(null);
        tilPassword.setError(null);
        validator.clear();


        if (validator.validate()) {


            hideKeyboard();
            formContainer.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);

            authRepository.getLogin(email, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                            //

                        }

                        @Override
                        public void onNext(@NotNull Login login) {


                            tokenManager.saveToken(login);
                            onRedirect();

                        }

                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public void onError(@NotNull Throwable e) {

                            formContainer.setVisibility(View.VISIBLE);
                            loader.setVisibility(View.GONE);
                            DialogHelper.erroLogin(LoginActivity.this);

                        }

                        @Override
                        public void onComplete() {

                            //

                        }
                    });
        }

    }



    private void onLoadValitator() {

        validator = new AwesomeValidation(TEXT_INPUT_LAYOUT);
        validator.setTextInputLayoutErrorTextAppearance(R.style.TextInputLayoutErrorStyle);
    }


    // Display Main Logo
    private void onLoadAppLogo() {

        Glide.with(getApplicationContext()).asBitmap().load(SERVER_BASE_URL +"image/minilogo")
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(withCrossFade())
                .skipMemoryCache(true)
                .into(logoimagetop);

    }


    // Register Button
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.go_to_register)
    void goToRegister() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        Animatoo.animateFade(this);
    }


    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.forget_password)
    void goToForgetPassword() {
        startActivity(new Intent(LoginActivity.this, PasswordForget.class));
        Animatoo.animateFade(this);
    }



    // Hide Keyboard on Submit
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    // Input Email & Password Validation
    public void onSetupRules() {
        validator.addValidation(this, R.id.til_email, Patterns.EMAIL_ADDRESS, R.string.err_email);
        validator.addValidation(this, R.id.til_password, RegexTemplate.NOT_EMPTY, R.string.err_password);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (accessTokenTracker !=null){
            accessTokenTracker.stopTracking();
            accessTokenTracker = null;
        }
        logoimagetop = null;
        mLoginButtonIcon = null;
        mLoginButton.unregisterCallback(mCallbackManager);
        mLoginButton = null;
        unbinder.unbind();
    }

    private void savePassword(String password){
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(APP_PASSWORD,password);
        sharedPreferencesEditor.apply();
    }

}
