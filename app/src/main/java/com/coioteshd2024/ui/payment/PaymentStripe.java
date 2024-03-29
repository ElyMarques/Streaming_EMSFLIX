package com.coioteshd2024.ui.payment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.coioteshd2024.R;
import com.coioteshd2024.data.model.plans.Plan;
import com.coioteshd2024.data.remote.ErrorHandling;
import com.coioteshd2024.ui.manager.SettingsManager;
import com.coioteshd2024.ui.splash.SplashActivity;
import com.coioteshd2024.ui.viewmodels.LoginViewModel;
import com.coioteshd2024.util.DialogHelper;
import com.coioteshd2024.util.Tools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.model.StripeIntent.Status;
import com.stripe.android.view.CardInputWidget;
import org.jetbrains.annotations.NotNull;
import java.lang.ref.WeakReference;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.coioteshd2024.util.Constants.ARG_PAYMENT;

/**
 * HoneyStream - Android Movie Portal App
 * @package     HoneyStream - Android Movie Portal App
 * @author      @Y0bEX
 * @copyright   Copyright (c) 2020 Y0bEX,
 * @license     http://codecanyon.net/wiki/support/legal-terms/licensing-terms/
 * @profile     https://codecanyon.net/user/yobex
 * @link        yobexd@gmail.com
 * @skype       yobexd@gmail.com
 **/


public class PaymentStripe extends AppCompatActivity {


    private Unbinder unbinder;


    private LoginViewModel loginViewModel;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.stripe_top)
    RelativeLayout relativeLayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cardInputWidget)
    CardInputWidget cardInputWidget;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sumbit_subscribe)
    Button sumbitSubscribe;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.form_container)
    LinearLayout formContainer;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.loader)
    ProgressBar loader;


    private Stripe mStripe;


    @Inject
    SettingsManager settingsManager;

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    private String planId;
    private String planPrice;
    private String planName;
    private String planDuraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);

        setContentView(R.layout.payment_stripe);

        unbinder = ButterKnife.bind(this);


        loginViewModel = new ViewModelProvider(this, viewModelFactory).get(LoginViewModel.class);


        Intent intent = getIntent();
        Plan plan = intent.getParcelableExtra(ARG_PAYMENT);


        this.planId =  plan.getstripePlanId();
        this.planPrice = plan.getStripePlanPrice();
        this.planName = plan.getName();
        this.planDuraction = plan.getPackDuration();


        Tools.hideSystemPlayerUi(this, true, 0);

        Tools.setSystemBarTransparent(this);

        if (settingsManager.getSettings().getStripePublishableKey() !=null ) {
            PaymentConfiguration.init(PaymentStripe.this, settingsManager.getSettings().getStripePublishableKey());
        }

        if (settingsManager.getSettings().getStripePublishableKey() !=null ) {
            mStripe = new Stripe(PaymentStripe.this, settingsManager.getSettings().getStripePublishableKey());
        }

            // Hook up the pay button to the card widget and stripe instance

            sumbitSubscribe.setOnClickListener(v -> {

                formContainer.setVisibility(View.GONE);
                loader.setVisibility(View.VISIBLE);

                cardInputWidget = findViewById(R.id.cardInputWidget);
                PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();

                cardInputWidget.setCardHint("12121");

                if (params == null) {
                    return;
                }
                mStripe.createPaymentMethod(params, new ApiResultCallback<PaymentMethod>() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onSuccess(@NonNull PaymentMethod result) {
                        
                        onSuccessPayment(result.id, null);
                    }

                    @Override
                    public void onError(@NonNull Exception e) {

                        Toast.makeText(PaymentStripe.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            });

            this.findViewById(R.id.pix).setOnClickListener(v -> {


            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ultraflix.net/assinar/"));
            this.startActivity(browserIntent);
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Handle the result of stripe.confirmPayment
        super.onActivityResult(requestCode, resultCode, data);
        mStripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));

    }

    private class PaymentResultCallback implements ApiResultCallback<PaymentIntentResult> {
        private final WeakReference<PaymentStripe> activityRef;

        PaymentResultCallback(@NonNull PaymentStripe activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final PaymentStripe activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            Status status = paymentIntent.getStatus();
            if (status == Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String data = gson.toJson(paymentIntent);

                onSuccessPayment(data, status);

            } else if (status == Status.RequiresPaymentMethod) {
                Toast.makeText(activity, "Payment failed", Toast.LENGTH_SHORT).show();
            }
        }





        @Override
        public void onError(@NotNull Exception e) {


            //

        }
    }



    public void onSuccessPayment(@Nullable String paymentMethodId, Status status){

        loginViewModel.getSubscribePlan(paymentMethodId, planId, planPrice, planName, planDuraction).observe(this, login -> {

            if (login.status == ErrorHandling.Status.SUCCESS) {


                formContainer.setVisibility(View.GONE);
                loader.setVisibility(View.GONE);


                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.dialog_success_payment);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WRAP_CONTENT;
                lp.height = WRAP_CONTENT;

                dialog.findViewById(R.id.btn_start_watching).setOnClickListener(v -> {


                    Intent intent = new Intent(PaymentStripe.this, SplashActivity.class);
                    startActivity(intent);
                    finish();


                });


                dialog.show();
                dialog.getWindow().setAttributes(lp);


            } else {


                formContainer.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);

                DialogHelper.erroPayment(this);


            }

        });


    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish();

        unbinder.unbind();


    }
}