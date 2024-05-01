package com.tipiz.newsapp.ui.detail


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.tipiz.newsapp.R
import com.tipiz.newsapp.data.response.news.ArticlesItem
import com.tipiz.newsapp.databinding.ActivityDetailBinding
import com.tipiz.newsapp.databinding.CustomToolbarBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

import org.koin.dsl.module

val detailModule = module {
    factory { DetailActivity() }
}


class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_INTENT = "extra_intent"
    }

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private lateinit var bindingCustomToolbar: CustomToolbarBinding
    private val viewModel: DetailViewModel by viewModel()
    private val detail by lazy {
        @Suppress("DEPRECATION")
        intent.getParcelableExtra<ArticlesItem>(EXTRA_INTENT) as ArticlesItem
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingCustomToolbar = binding.toolbar
        setContentView(binding.root)
        setSupportActionBar(bindingCustomToolbar.container)

        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }


        detail.let {
            viewModel.find(it)
            val web = binding.webView
            web.loadUrl(it.url!!) // url link
            web.webViewClient = object : WebViewClient() {
                //setelah pagenya selesai di load/finis progressbar ke hide
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.progressTop.visibility = View.GONE
                }
            }
            val settings = web.settings
            //java script biasanya membuka jendela automatis, ini di disable
            settings.javaScriptCanOpenWindowsAutomatically = false
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bookmark, menu)
        val menuBookmark = menu?.findItem(R.id.action_bookmark)
        menuBookmark?.setOnMenuItemClickListener {
            viewModel.bookmark(detail)
            true
        }
        viewModel.isBookmark.observe(this) {
            if (it == 0) menuBookmark?.setIcon(R.drawable.ic_add)
            else menuBookmark?.setIcon(R.drawable.ic_check)
        }
        return super.onCreateOptionsMenu(menu)
    }
}