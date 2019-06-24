package com.tommannson.apps.componentisation.components.github_components.search_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tommannson.apps.componentisation.model.ws.github.GithubSearchResult
import com.tommannson.apps.componentisation.R

class GithubSearchAdapter : RecyclerView.Adapter<GithubSearchAdapter.VH>() {

    var githubSearchResult: GithubSearchResult? = null;

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VH {
        return VH(LayoutInflater.from(p0.context).inflate(R.layout.recycler_view, p0, false))
    }

    override fun getItemCount() = githubSearchResult?.items?.size ?: 0

    override fun onBindViewHolder(p0: VH, p1: Int) {
        githubSearchResult?.items?.let {
            p0.login.text = it[p1].name
            p0.repos.text = it[p1].fullName
        }
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var login: TextView
        lateinit var repos: TextView
        lateinit var blog: TextView

        init {
            login = itemView.findViewById(R.id.login)
            repos = itemView.findViewById(R.id.repos)
            blog = itemView.findViewById(R.id.blog)
        }

    }

}

