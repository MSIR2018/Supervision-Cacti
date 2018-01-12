package com.snmp.agent.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.snmp.agent.R;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WebView myWebView;
    SwipeRefreshLayout mySwipeRefreshLayout;
    //String Agent = "";
    //String Agent_Windows = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36";

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mySwipeRefreshLayout = (SwipeRefreshLayout)this.findViewById(R.id.swipeContainer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        myWebView = (WebView) findViewById(R.id.webview);
        //myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setSupportZoom(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDisplayZoomControls(false);
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //myWebView.getSettings().setSavePassword(true);
        myWebView.getSettings().setSaveFormData(true);

        load_view("index_old");
        myWebView.setWebViewClient(new MywebViewClient());

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        myWebView.reload();
                    }
                }
        );
    }

    public void load_view(String site){
        if(site == "index") {
            //myWebView.getSettings().setUserAgentString(Agent_Windows);
            //myWebView.loadUrl(getString(R.string.menu_cacti_index_url));
            //super.setTitle(getString(R.string.menu_index));
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        else if(site == "index_old") {
            //myWebView.getSettings().setUserAgentString(Agent);
            myWebView.loadUrl(getString(R.string.menu_cacti_index_old_url));
            super.setTitle(getString(R.string.menu_index_old));
        }
        else if(site == "host") {
            //myWebView.getSettings().setUserAgentString(Agent_Windows);
            myWebView.loadUrl(getString(R.string.menu_cacti_host_url));
            super.setTitle(getString(R.string.menu_host));
        }
        else if(site == "graph_view") {
            //myWebView.getSettings().setUserAgentString(Agent);
            myWebView.loadUrl(getString(R.string.menu_cacti_graph_view_url));
            super.setTitle(getString(R.string.menu_graph_view));
        }
        else if(site == "settings") {
            //myWebView.getSettings().setUserAgentString(Agent);
            myWebView.loadUrl(getString(R.string.menu_cacti_settings_url));
            super.setTitle(getString(R.string.menu_settings));
        }

    }



    public void Error_Internet(){
        //myWebView.getSettings().setUserAgentString(Agent);
        myWebView.getSettings().setUseWideViewPort(false);
        myWebView.getSettings().setLoadWithOverviewMode(false);
        myWebView.getSettings().setSupportZoom(false);
        myWebView.loadUrl("file:///android_asset/index.html");
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Erreur réseau")
                .setMessage("Aucune connectivité, Merci de vous connecter à internet")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }

                })
                .show();
    }



    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(myWebView.canGoBack()){
                myWebView.goBack();
            }


        if (doubleBackToExitPressedOnce) {
            //finishAffinity();
            finish();
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Veuillez appuyer une deuxième fois pour revenir au menu principal de l'application !", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);
    }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    ProgressDialog pd;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        pd = ProgressDialog.show(this, "Patientez...", "Chargement de la page..",true); //loading page


        int id = item.getItemId();
        if (id == R.id.menu_index) {
            load_view("index");
        } else if (id == R.id.menu_index_old) {
            load_view("index_old");
        } else if (id == R.id.menu_host) {
            load_view("host");
        }else if (id == R.id.menu_graph_view) {
            load_view("graph_view");
        }else if (id == R.id.menu_settings) {
            load_view("settings");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MywebViewClient extends WebViewClient {

        public void onPageFinished(WebView view, String url) {
            if (pd != null && pd.isShowing()) { pd.dismiss(); }
            mySwipeRefreshLayout.setRefreshing(false); // hide swipe
            super.onPageFinished(view, url);
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Error_Internet();
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null && (url.startsWith(getString(R.string.menu_cacti_settings_url)) || url.startsWith(getString(R.string.menu_cacti_graph_view_url)) || url.startsWith("http://192.168.200.20"))){ //ajouter les liens links
                return false;
            }


            else  {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }
        }
    }

}
