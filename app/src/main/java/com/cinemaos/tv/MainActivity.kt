package com.cinemaos.tv

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    private val dpadJS = """
        (function() {
            if (window.__dpadInjected) return;
            window.__dpadInjected = true;
            var currentIndex = 0;
            function getFocusables() {
                return Array.from(document.querySelectorAll('a, button, input, select, [tabindex], [role="button"], [role="link"], [role="menuitem"]'))
                    .filter(el => {
                        var rect = el.getBoundingClientRect();
                        return rect.width > 0 && rect.height > 0 &&
                               window.getComputedStyle(el).visibility !== 'hidden' &&
                               window.getComputedStyle(el).display !== 'none';
                    });
            }
            function focusElement(el) {
                if (!el) return;
                el.focus();
                el.scrollIntoView({ block: 'center', behavior: 'smooth' });
                el.style.outline = '3px solid #e50914';
                el.style.outlineOffset = '2px';
                el.style.borderRadius = '4px';
            }
            function clearHighlights() {
                document.querySelectorAll('[data-dpad-focused]').forEach(el => {
                    el.style.outline = '';
                    el.style.outlineOffset = '';
                    el.removeAttribute('data-dpad-focused');
                });
            }
            window.dpadMove = function(direction) {
                var focusables = getFocusables();
                if (focusables.length === 0) return;
                var focused = document.activeElement;
                var idx = focusables.indexOf(focused);
                clearHighlights();
                if (direction === 'down' || direction === 'right') {
                    currentIndex = (idx < 0) ? 0 : Math.min(idx + 1, focusables.length - 1);
                } else {
                    currentIndex = (idx < 0) ? 0 : Math.max(idx - 1, 0);
                }
                var target = focusables[currentIndex];
                target.setAttribute('data-dpad-focused', 'true');
                focusElement(target);
            };
            window.dpadClick = function() {
                var el = document.activeElement;
                if (el && el !== document.body) { el.click(); }
                else {
                    var focusables = getFocusables();
                    if (focusables.length > 0) focusElement(focusables[0]);
                }
            };
            setTimeout(function() {
                var focusables = getFocusables();
                if (focusables.length > 0) focusElement(focusables[0]);
            }, 800);
        })();
    """.trimIndent()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        webView = findViewById(R.id.webView)
        setupWebView()
        webView.loadUrl("https://cinemaos.live")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false
            useWideViewPort = true
            loadWithOverviewMode = true
            setSupportZoom(false)
            builtInZoomControls = false
            displayZoomControls = false
            userAgentString = "Mozilla/5.0 (Linux; Android 11; SHIELD Android TV) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36"
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
                view.evaluateJavascript(dpadJS, null)
            }
            override fun onPageStarted(view: WebView, url: String, favicon: android.graphics.Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                view.loadUrl(request.url.toString())
                return true
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressBar.progress = newProgress
                if (newProgress == 100) progressBar.visibility = View.GONE
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val direction = when (keyCode) {
            KeyEvent.KEYCODE_DPAD_UP    -> "up"
            KeyEvent.KEYCODE_DPAD_DOWN  -> "down"
            KeyEvent.KEYCODE_DPAD_LEFT  -> "left"
            KeyEvent.KEYCODE_DPAD_RIGHT -> "right"
            else -> null
        }
        if (direction != null) {
            webView.evaluateJavascript("window.dpadMove('$direction');", null)
            return true
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
            webView.evaluateJavascript("window.dpadClick();", null)
            return true
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) { webView.goBack(); return true }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onResume() { super.onResume(); webView.onResume() }
    override fun onPause() { super.onPause(); webView.onPause() }
}
