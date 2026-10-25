package com.example.mini_game_tech_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen; // <-- IMPORT NECESSARIO PER LA SPLASH SCREEN
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String APP_URL = "https://minigame-tech.github.io/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 1. INSTALLA LA SPLASH SCREEN NATIVA PRIMA DI TUTTO
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        // Attivo la modalità Fullscreen Immersiva
        hideSystemUI();

        setContentView(R.layout.activity_main);

        // 2. INIZIALIZZAZIONE DELLA WEBVIEW E DEL SWIPE REFRESH
        webView = findViewById(R.id.webview);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        // Configurazione avanzata delle impostazioni della WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // Configurazione del Pull-to-Refresh intelligente
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable(MainActivity.this)) {
                    String currentUrl = webView.getUrl();
                    if (currentUrl == null || currentUrl.startsWith("data:") || currentUrl.contains("error")) {
                        webView.loadUrl(APP_URL);
                    } else {
                        webView.reload();
                    }
                } else {
                    showOfflineErrorPage();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        // Gestione avanzata del client WebView
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            // Compatibilità per vecchie e nuove versioni di Android sugli errori di rete
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                showOfflineErrorPage();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (request.isForMainFrame()) {
                    showOfflineErrorPage();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        // Carico la piattaforma web verificando prima la connessione
        if (savedInstanceState == null){
            loadAppContent();
        }

        // 3. GESTIONE AGGIORNATA DEL TASTO INDIETRO
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    }

    private void loadAppContent() {
        if (isNetworkAvailable(this)) {
            webView.loadUrl(APP_URL);
        } else {
            showOfflineErrorPage();
        }
    }

    // Pagina HTML offline con font di sistema nativi (senza dipendenze esterne che bloccano offline)
    private void showOfflineErrorPage() {
        String offlineHtml = "<!DOCTYPE html>" +
                "<html lang=\"it\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<style>" +
                "body {" +
                "  background-color: #0a0a0f;" +
                "  color: #f0f0f5;" +
                "  font-family: sans-serif;" +
                "  margin: 0;" +
                "  padding: 24px;" +
                "  display: flex;" +
                "  justify-content: center;" +
                "  align-items: center;" +
                "  min-height: 100vh;" +
                "  text-align: center;" +
                "}" +
                ".error-card {" +
                "  background: rgba(20, 22, 35, 0.9);" +
                "  border: 1px solid rgba(80, 90, 140, 0.4);" +
                "  border-radius: 16px;" +
                "  padding: 40px 24px;" +
                "  max-width: 400px;" +
                "  width: 100%;" +
                "  box-shadow: 0 10px 30px rgba(0,0,0,0.8), 0 0 20px rgba(93, 202, 165, 0.2);" +
                "}" +
                ".emoji-icon {" +
                "  font-size: 56px;" +
                "  margin-bottom: 20px;" +
                "}" +
                "h2 {" +
                "  font-family: monospace;" +
                "  font-size: 16px;" +
                "  color: #d84a30;" +
                "  margin-bottom: 16px;" +
                "  letter-spacing: 1px;" +
                "}" +
                "p {" +
                "  font-size: 15px;" +
                "  color: #a0a5ba;" +
                "  margin-bottom: 30px;" +
                "  line-height: 1.6;" +
                "}" +
                ".hint {" +
                "  font-size: 12px;" +
                "  color: #5dcaa5;" +
                "  font-weight: bold;" +
                "  background: rgba(93, 202, 165, 0.15);" +
                "  padding: 12px 16px;" +
                "  border-radius: 8px;" +
                "  border: 1px solid rgba(93, 202, 165, 0.3);" +
                "  display: inline-block;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"error-card\">" +
                "  <div class=\"emoji-icon\">⚠️</div>" +
                "  <h2>CONNESSIONE ASSENTE</h2>" +
                "  <p>Sembra che tu sia offline. Attiva il Wi-Fi o i dati mobili per accedere alla piattaforma di mini-giochi.</p>" +
                "  <div class=\"hint\">⬇️ Trascina verso il basso per ricaricare</div>" +
                "</div>" +
                "</body>" +
                "</html>";

        webView.loadDataWithBaseURL("https://minigame-tech.github.io/", offlineHtml, "text/html", "UTF-8", null);
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                if (capabilities != null) {
                    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
                }
            }
        }
        return false;
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
        hideSystemUI();
    }
}