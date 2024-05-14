package com.example.news_app.view

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.news_app.R

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val webView: WebView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()

        val url = intent.getStringExtra("url")
        if (!url.isNullOrEmpty()) {
            webView.loadUrl(url)
        }
    }
}
