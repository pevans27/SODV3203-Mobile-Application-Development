package com.example.projectxxx;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
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
        mapWebView.setWebViewClient(new WebViewClient());
        mapWebView.loadUrl("https://www.google.com/maps");

        return dialog;
    }
}
