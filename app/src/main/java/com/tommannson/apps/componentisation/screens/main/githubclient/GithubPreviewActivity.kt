package com.tommannson.apps.componentisation.screens.main.githubclient

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.jmoraes.componentizationsample.components.github_components.controlpanel.GithubControlPanelComponent
import com.tommannson.apps.componentisation.R
import com.tommannson.apps.componentisation.arch.ScopedEventBusFactory
import com.tommannson.apps.componentisation.arch.UINewHost
import com.tommannson.apps.componentisation.components.github_components.search_list.GithubSearchResultComponent
import com.tommannson.apps.componentisation.model.pipe.resolvers.GithubDataResolver
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class GithubPreviewActivity : DaggerAppCompatActivity() {

    lateinit var host: UINewHost
    @Inject
    lateinit var model: GithubDataResolver

    companion object {

        fun start(ctx: Context){
            ctx.startActivity(Intent(ctx, GithubPreviewActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        model.start()
        initComponents(findViewById(R.id.root))
    }

    @SuppressLint("CheckResult")
    private fun initComponents(rootViewContainer: ViewGroup) {
        host = UINewHost.create(this)
            .composition {
                add(
                    GithubControlPanelComponent(
                        findViewById(R.id.control_panel),
                        ScopedEventBusFactory.get(this@GithubPreviewActivity)
                    )
                )
                add(
                    GithubSearchResultComponent(
                        findViewById(R.id.list),
                        ScopedEventBusFactory.get(this@GithubPreviewActivity)
                    )
                )
            }

    }

}
