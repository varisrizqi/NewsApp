package com.tipiz.newsapp.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tipiz.newsapp.R
import com.tipiz.newsapp.data.news.CategoryModel
import com.tipiz.newsapp.databinding.AdapterCategoryBinding

class CategoryAdapter(
    private val categories: List<CategoryModel>,
    private val listener: OnAdapterListener
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    //untuk mengisi textview
    private val items = arrayListOf<TextView>()

    class ViewHolder(val binding: AdapterCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.category.text = category.name

        items.add(holder.binding.category)
        holder.itemView.setOnClickListener {
            listener.onClick(category)
            setColor(holder.binding.category)
        }
        setColor(items[0]) //index 0 pertama di jalankan colornya
    }

    override fun getItemCount() = categories.size

    private fun setColor(textView: TextView) {

        //mewarnai di awal
        items.forEach { p -> p.setBackgroundResource(R.color.white) }

        //ketika di klik/dipilih warna akan berubah menjadi abu2
        textView.setBackgroundResource(android.R.color.darker_gray)
    }

    interface OnAdapterListener {
        fun onClick(categories: CategoryModel)
    }
}
