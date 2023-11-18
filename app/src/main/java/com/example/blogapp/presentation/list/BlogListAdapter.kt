package com.example.blogapp.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.blogapp.databinding.ItemBlogBinding
import com.example.blogapp.domain.model.Blog

class BlogListAdapter : ListAdapter<Blog, BaseListViewHolder<*>>(DiffUtilCallback) {

    private object DiffUtilCallback : DiffUtil.ItemCallback<Blog>(){
        override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListViewHolder<*> {
        val itemBinding = ItemBlogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return BindViewHolderList(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseListViewHolder<*>, position: Int) {
        when (holder){
            is BindViewHolderList -> holder.bind(getItem(position), position)
        }
    }

    inner class BindViewHolderList(private val binding: ItemBlogBinding): BaseListViewHolder<Blog>(binding.root){
        override fun bind(item: Blog, position: Int) = with(binding){
            tvTitulo.text = item.titulo

            clBlogItem.setOnClickListener {
                onBlogClickedListener?.let { click ->
                    click(item)
                }
            }
        }
    }

    private var onBlogClickedListener: ((Blog) -> Unit)? = null

    fun setBlogClickedListener(listener: (Blog) -> Unit) {
        onBlogClickedListener = listener
    }
}