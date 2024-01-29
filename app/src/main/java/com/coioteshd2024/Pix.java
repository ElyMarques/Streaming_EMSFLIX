package com.coioteshd2024;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Pix extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pix_pay);

        ImageView pixImageView = findViewById(R.id.pix);
        pixImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWebViewDialog("https://coio.gmplayx.com.br/public/pagamento");
            }
        });
    }

    private void showWebViewDialog(String url) {
        WebViewDialog dialog = new WebViewDialog(this, url);
        dialog.show();
    }
}
