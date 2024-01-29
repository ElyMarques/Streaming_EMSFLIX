package com.coioteshd2024;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebViewDialog extends Dialog {
    private Context context;
    private String url;

    public WebViewDialog(Context context, String url) {
        super(context);
        this.context = context;
        this.url = url;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_dialog);

        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String targetUrl = "https://www.mercadopago.com.br/checkout/v1/payment/redirect/";
                String requestUrl = request.getUrl().toString();

                if (requestUrl.startsWith(targetUrl)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(requestUrl));
                    // Você pode verificar se há um aplicativo que possa lidar com essa Intent antes de chamá-la
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                        return true; // Previne a WebView de carregar a URL
                    }
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        webView.loadUrl(url);

//        Button closeButton = findViewById(R.id.close_button);
//        closeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });

        Button backButton = findViewById(R.id.close_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reiniciar o aplicativo
                restartApp();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Redimensionar o Dialog para ocupar toda a largura e altura da janela
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void restartApp() {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.coioteshd2024");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        dismiss();
    }
}

