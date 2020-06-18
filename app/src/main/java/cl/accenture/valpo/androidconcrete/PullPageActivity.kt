package cl.accenture.valpo.androidconcrete

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_pull_page.*

class PullPageActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_pull_page)

        val courseLink = intent.getStringExtra(DetailActivity.PullViewHolder.URL_PULL)

        page.settings.javaScriptEnabled = true
        page.settings.loadWithOverviewMode = true
        page.settings.useWideViewPort = true

        if (courseLink != null) {
            page.loadUrl(courseLink)
        }
    }

}