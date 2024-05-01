package com.tipiz.newsapp.ui.bookmark

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tipiz.newsapp.data.response.news.ArticlesItem
import com.tipiz.newsapp.databinding.CustomToolbarBinding
import com.tipiz.newsapp.databinding.FragmentBookmarkBinding
import com.tipiz.newsapp.ui.detail.DetailActivity
import com.tipiz.newsapp.ui.news.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val bookmarkModule = module {
    factory { BookmarkFragment() }
}

class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var bindingToolbar: CustomToolbarBinding
    private val viewModel: BookmarkViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        bindingToolbar = binding.toolbar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        bindingToolbar.textTitle.text = viewModel.title  // tanpa menggunakan dataBinding
        bindingToolbar.title = viewModel.title //menggunakan dataBinding *liatXMLnya
        //menggunakan dataBinding viewModel *liatXMLnya
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        NewsAdapter.VIEW_TYPE = NewsAdapter.NEWS
        binding.listBookmark.adapter = bookmarkAdapter

        viewModel.articles.observe(viewLifecycleOwner){
            bookmarkAdapter.clear()
            bookmarkAdapter.add(it)
        }

    }

    private val bookmarkAdapter by lazy {
        NewsAdapter(arrayListOf(), object : NewsAdapter.OnAdapterListener {
            override fun onClick(article: ArticlesItem) {
                val mIntent = Intent(requireContext(), DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.EXTRA_INTENT, article)
                startActivity(mIntent)
            }
        })
    }
}