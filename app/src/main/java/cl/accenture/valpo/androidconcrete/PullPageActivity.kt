package cl.accenture.valpo.androidconcrete

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class PullPageActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_pull_page)

        val linkPull = intent.getStringExtra(DetailActivity.PullViewHolder.URL_PULL)

        if (linkPull != null) {
            val browserIntent =Intent(Intent.ACTION_VIEW, Uri.parse(linkPull))
            startActivity(browserIntent)
        }
    }

}