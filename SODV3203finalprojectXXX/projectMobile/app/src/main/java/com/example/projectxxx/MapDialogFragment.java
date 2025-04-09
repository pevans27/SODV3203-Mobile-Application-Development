package com.example.projectxxx;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.DialogFragment;

public class MapDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a dialog
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // Remove title bar for a full-screen map
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set the layout for the Dialog
        dialog.setContentView(R.layout.dialog_map);

        // Set up the WebView to load Google Maps
        WebView mapWebView = dialog.findViewById(R.id.map_webview);

        // Enable JavaScript (required for Google Maps)
        WebSettings webSettings = mapWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Allow zoom controls
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // Set a WebViewClient to handle page loading within the WebView
        mapWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Optional: Add any additional setup after page load
            }
        });

        // Load Google Maps
        mapWebView.loadUrl("https://www.google.com/maps");

        return dialog;
    }
}