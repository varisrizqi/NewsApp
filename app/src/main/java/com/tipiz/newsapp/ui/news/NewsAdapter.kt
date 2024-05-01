package com.tipiz.newsapp.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tipiz.newsapp.data.response.news.ArticlesItem
import com.tipiz.newsapp.data.util.DateUtil
import com.tipiz.newsapp.databinding.AdapterHeadlineBinding
import com.tipiz.newsapp.databinding.AdapterNewsBinding
import java.util.ArrayList

class NewsAdapter(
    private val articles: ArrayList<ArticlesItem>,
    private val listener: OnAdapterListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val HEADLINES = 1
        const val NEWS = 2
        var VIEW_TYPE = HEADLINES
    }

    class ViewHolderNews(private val binding: AdapterNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesItem) {
            binding.article = article
            binding.format = DateUtil()
        }
    }

    class ViewHolderHeadlines(private val binding: AdapterHeadlineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesItem) {
            binding.article = article
            binding.format = DateUtil()
        }
    }

    override fun getItemViewType(position: Int)  = VIEW_TYPE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        return if (viewType == HEADLINES){
            ViewHolderHeadlines(AdapterHeadlineBinding.inflate(LayoutInflater.from(parent.context), parent, false) )
        } else {
            ViewHolderNews( AdapterNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = articles[position]
        if (VIEW_TYPE == HEADLINES) {
            (holder as ViewHolderHeadlines).bind(article)
        }
        else{
            (holder as ViewHolderNews).bind(article)
        }
//        holder.binding.title.text = article.title
//        holder.binding.publishedAt.text = article.publishedAt
        holder.itemView.setOnClickListener {
            listener.onClick(article)
        }
    }

    override fun getItemCount() = articles.size

    interface OnAdapterListener {
        fun onClick(article: ArticlesItem)
    }

    fun add(data: List<ArticlesItem>) {
        articles.addAll(data)
        notifyItemRangeInserted( (articles.size - data.size), data.size)

    }

    fun clear(){
        articles.clear()
        notifyDataSetChanged()
    }

}
