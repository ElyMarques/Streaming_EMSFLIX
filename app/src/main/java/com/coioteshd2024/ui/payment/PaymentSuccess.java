package com.coioteshd2024.ui.payment;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.coioteshd2024.R;
import com.coioteshd2024.databinding.PaymentSuccessBinding;
import com.coioteshd2024.ui.splash.SplashActivity;


import dagger.android.AndroidInjection;

public class PaymentSuccess extends AppCompatActivity {



        PaymentSuccessBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.payment_success);



        binding.closePaymentSuccess.setOnClickListener(v -> {


            Intent intent = new Intent(PaymentSuccess.this, SplashActivity.class);
            startActivity(intent);
            finish();

        });


    }
}
