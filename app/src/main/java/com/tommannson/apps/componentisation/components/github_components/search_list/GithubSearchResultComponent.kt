package com.tommannson.apps.componentisation.components.github_components.search_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tommannson.apps.componentisation.R
import com.tommannson.apps.componentisation.arch.*
import com.tommannson.apps.componentisation.arch.bus.ScopedEventBusFactory
import com.tommannson.apps.componentisation.arch.component.UIComponent
import com.tommannson.apps.componentisation.components.events.GithubIntaractionEvent
import com.tommannson.apps.componentisation.components.github_components.GithubListState
import com.tommannson.apps.componentisation.model.ws.github.GithubSearchResult

@SuppressLint("CheckResult")
open class GithubSearchResultComponent(container: ViewGroup, private val bus: ScopedEventBusFactory) :
    UIComponent<GithubIntaractionEvent, GithubListState>(
        container,
        GithubListState(null, false)
    ) {
    override fun render(localState: GithubListState) {

    }

    private val list: RecyclerView by bindView(R.id.recycler_view)
    private val listProgress: View by bindView(R.id.list_progress)
    val listAdapter = GithubSearchAdapter()

    override fun build() {
        if(container.findViewById<View>(R.id.recycler_view) == null) {
            LayoutInflater.from(container.context).inflate(R.layout.github_list_component, container, true)
        }
        list.apply {
            layoutManager = LinearLayoutManager(container.context)
            this.adapter = listAdapter;
        }
    }

    init {
        listenMainStram2<GithubEvents, GithubIntaractionEvent, RxAction>(bus)
            .subscribe {
                when (it) {
                    is GithubEvents.LoadRequestSuccess -> {
                        showList()
                        hideProgress()
                        setSearchResult(it.result);
                    }
                    is GithubEvents.ClearList -> {
                        setSearchResult(null);
                    }
                    is GithubIntaractionEvent.LoadRequest -> {
                        showProgress()
                    }
                }
            }
    }

    fun showList() {
        list.visibility = View.VISIBLE
    }

    fun showProgress() {
        listProgress.visibility = View.VISIBLE
    }

    fun hideProgress() {
        listProgress.visibility = View.GONE
    }

    fun setSearchResult(githubResult: GithubSearchResult?) {
        listAdapter.githubSearchResult = githubResult
        listAdapter.notifyDataSetChanged()
    }


}

sealed class GithubEvents : ComponentEvent() {
    object ClearList : GithubEvents()
    class LoadRequestSuccess(val result: GithubSearchResult) : GithubEvents()
}