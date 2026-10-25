package com.example.mini_game_tech_app;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private static final String APP_URL = "https://minigame-tech.github.io/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Attivo la modalità Fullscreen Immersiva
        hideSystemUI();

        setContentView(R.layout.activity_main);

        // 1. INIZIALIZZAZIONE DELLA WEBVIEW (FONDAMENTALE prima di chiamare i metodi)
        webView = findViewById(R.id.webview);

        // Configurazione avanzata delle impostazioni della WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);              // Fondamentale per i giochi JS/Canvas
        webSettings.setDomStorageEnabled(true);               // Permette il salvataggio locale dei punteggi (LocalStorage)
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);                 // Ottimizzazione della vista responsive
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // Impedisce all'app di aprire il browser esterno quando si clicca sui link interni
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });

        // Carico la piattaforma web dei mini-giochi
        if (savedInstanceState == null){
            webView.loadUrl(APP_URL);
        }

        // 2. GESTIONE AGGIORNATA DEL TASTO INDIETRO (Sostituisce il vecchio onBackPressed)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack(); // Torna indietro nella cronologia della WebView
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed(); // Chiude l'app se sei alla pagina iniziale
                }
            }
        });
    }

    private void hideSystemUI(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI(); // Riattiva la modalità immersive quando si riprende l'app
    }
}