package com.coioteshd2024;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coioteshd2024.databinding.ActivityBenefitPremiumBinding;
import com.coioteshd2024.ui.settings.SettingsActivity;
import com.coioteshd2024.util.Tools;

public class BenefitPremiumActivity extends AppCompatActivity {

    private Context context;
    private ActivityBenefitPremiumBinding binding;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_benefit_premium);


        Tools.hideSystemPlayerUi(this, true, 0);

        Tools.setSystemBarTransparent(this);

        Button assinar = (Button) findViewById(R.id.assinar);
        assinar.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent1);
        });


        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        binding.porque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://ultraflix.net/assinar/")));

            }
        });

    }
}