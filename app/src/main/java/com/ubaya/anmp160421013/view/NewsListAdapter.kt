package com.ubaya.anmp160421013.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.anmp160421013.databinding.NewsItemBinding
import com.ubaya.anmp160421013.model.Berita

class NewsListAdapter(val newsList:ArrayList<Berita>)
    :RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>()
{
    class NewsViewHolder(var binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.txtTitle.text = newsList[position].nama
        holder.binding.txtAuthor.text = "@"+newsList[position].writer_username
        holder.binding.txtIsi.text = newsList[position].desc

        holder.binding.btnRead.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(newsList[position].nama,newsList[position].writer_username,newsList[position].url,newsList[position].id)
            Navigation.findNavController(it).navigate(action)

        }
        val picasso = Picasso.Builder(holder.itemView.context)
        picasso.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        picasso.build().load(newsList[position].url).into(holder.binding.imageView, object:
            Callback {
            override fun onSuccess() {
                holder.binding.progressLoadImg.visibility = View.INVISIBLE
                holder.binding.imageView.visibility = View.VISIBLE
            }

            override fun onError(e: Exception?) {
                Log.e("picasso_error", e.toString())
            }

        })
    }

    fun updateNewsList(newNewsList: ArrayList<Berita>) {
        newsList.clear()
        newsList.addAll(newNewsList)
        notifyDataSetChanged()
    }

}