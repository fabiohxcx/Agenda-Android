package es.esy.fabiohideki.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;

public class SiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);

        WebView view = (WebView) findViewById(R.id.wvweb);

        String site = "www.google.com";

        view.loadUrl(site);
    }
}
