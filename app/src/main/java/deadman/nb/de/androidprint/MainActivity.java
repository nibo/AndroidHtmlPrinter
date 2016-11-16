package deadman.nb.de.androidprint;

import android.content.Context;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private FloatingActionButton fabRight;
    private boolean pageIsLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webview);
        setupWebView();
        fabRight = (FloatingActionButton) findViewById(R.id.fab);
        fabRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pageIsLoaded) {
                    printWebViewContent();
                }else{
                    Toast.makeText(MainActivity.this,"Page not completely loaded",Toast.LENGTH_SHORT).show();
                }
            }
        });

        webView.loadUrl("file:///android_asset/index.html");
    }

    /**
        API 19 REQUIRED
     */
    private void printWebViewContent() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
            PrintManager printManager = (PrintManager) this
                    .getSystemService(Context.PRINT_SERVICE);

            String jobName = getString(R.string.app_name) + " Print Test";

            printManager.print(jobName, printAdapter,
                    new PrintAttributes.Builder().build());
        }else{
            Toast.makeText(this,"Print WebView requires API 19",Toast.LENGTH_SHORT).show();
        }
    }

    private void setupWebView() {
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pageIsLoaded = true;

            }
        });
        webView.getSettings().setJavaScriptEnabled(true );
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
    }
}
