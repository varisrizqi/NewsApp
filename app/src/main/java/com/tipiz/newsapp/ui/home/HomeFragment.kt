package com.tipiz.newsapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.tipiz.newsapp.R
import com.tipiz.newsapp.data.news.CategoryModel
import com.tipiz.newsapp.data.response.news.ArticlesItem
import com.tipiz.newsapp.databinding.CustomToolbarBinding
import com.tipiz.newsapp.databinding.FragmentHomeBinding
import com.tipiz.newsapp.ui.detail.DetailActivity
import com.tipiz.newsapp.ui.news.CategoryAdapter
import com.tipiz.newsapp.ui.news.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module
import timber.log.Timber

val homeModule = module {
    factory { HomeFragment() }
}

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var bindingToolbar: CustomToolbarBinding
    private val viewModel: HomeViewModel by viewModel() //ktx


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        bindingToolbar = binding.toolbar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        bindingToolbar.textTitle.text = viewModel.title  // tanpa menggunakan dataBinding
        bindingToolbar.title = viewModel.title

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        bindingToolbar.container.inflateMenu(R.menu.menu_search)
        val menu = bindingToolbar.container.menu
        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                firstLoad()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.query = it }
                return true
            }
        })

        binding.listCategory.adapter = categoryAdapter
        viewModel.category.observe(viewLifecycleOwner) { it ->
            NewsAdapter.VIEW_TYPE = if (it.isEmpty()) {
                NewsAdapter.HEADLINES
            } else {
                NewsAdapter.NEWS
            }
            firstLoad()
//        }


            binding.listNews.adapter = newsAdapter
            viewModel.news.observe(viewLifecycleOwner) { response ->
                Timber.e(response.status)
//            binding.imageAlert.visibility = if (it.articles.isEmpty()) View.VISIBLE else View.GONE
//            binding.textAlert.visibility = if (it.articles.isEmpty()) View.VISIBLE else View.GONE
                if (viewModel.page == 1) newsAdapter.clear()
                newsAdapter.add(response.articles)
            }

            viewModel.message.observe(viewLifecycleOwner) {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    viewModel.loading.postValue(false)
                }
            }

            binding.scroll.setOnScrollChangeListener { v: NestedScrollView, _, scrollY, _, _ ->
                if (scrollY == v.getChildAt(0)!!.measuredHeight - v.measuredHeight) {
                    if (viewModel.page <= viewModel.total && viewModel.loadMore.value == false) {
                        viewModel.fetch()
                    }

                }
            }
        }
    }

    private fun firstLoad() {
        binding.scroll.scrollTo(0, 0)
        viewModel.page = 1
        viewModel.total = 1
        viewModel.fetch()
    }

    private val categoryAdapter by lazy {
        CategoryAdapter(viewModel.categories, object : CategoryAdapter.OnAdapterListener {
            override fun onClick(categories: CategoryModel) {
                viewModel.category.postValue(categories.id)
            }
        })
    }

    private val newsAdapter by lazy {
        NewsAdapter(arrayListOf(), object : NewsAdapter.OnAdapterListener {
            override fun onClick(article: ArticlesItem) {
                val mIntent = Intent(requireContext(), DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.EXTRA_INTENT, article)
                startActivity(mIntent)

            }
        })
    }

}
