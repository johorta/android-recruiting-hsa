package cl.accenture.valpo.androidconcrete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.accenture.valpo.androidconcrete.Model.Pulls.PullRequest
import cl.accenture.valpo.androidconcrete.Model.Pulls.User
import cl.accenture.valpo.andrpoid.Adapter.MyRepositoryAdapter
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_details_pull.view.*
import okhttp3.*
import java.io.IOException

/**
 * Created by brianvoong on 12/20/17.
 */

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerRepositories.layoutManager = LinearLayoutManager(this)
        //recyclerRepositories.adapter =PullDetailAdapter()

        // we'll change the nav bar title..
        //val navBarTitle = intent.getStringExtra(MyRepositoryAdapter.MyViewHolder.URL_PULL)
        //supportActionBar?.title = navBarTitle


//        println(courseDetailUrl)

        fetchJSON()
    }

    private fun fetchJSON() {

        val pullDetailUrl: String? = intent.getStringExtra(MyRepositoryAdapter.MyViewHolder.URL_PULL)
        val client = OkHttpClient()
        val request = Request.Builder().url(pullDetailUrl).build()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()

                val pulls = gson.fromJson(body, Array<PullRequest>::class.java)


                runOnUiThread {
                    recyclerRepositories.adapter = PullDetailAdapter(pulls)
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {

            }
        })
    }

     private class PullDetailAdapter(private val pullList: Array<PullRequest>): RecyclerView.Adapter<PullViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullViewHolder {
            val layoutInflater = LayoutInflater.from(parent?.context)
            val customView = layoutInflater.inflate(R.layout.layout_details_pull, parent, false)
            return PullViewHolder(customView)
        }

        override fun getItemCount(): Int {
            return pullList.size
        }

        override fun onBindViewHolder(holder: PullViewHolder, position: Int) {

            var user: User =  this.pullList[position].user!!
            Picasso.get().load(user.avatarUrl).into(holder.image_user)
            holder.txt_autor_pull.text= pullList[position].user!!.login
            holder.txt_detalle_pull.text=pullList[position].body
            holder.txt_titulo_pull.text=pullList[position].title
            PullViewHolder.valorURL = pullList[position].htmlUrl.toString()
        }
     }

    class PullViewHolder(val customView: View): RecyclerView.ViewHolder(customView) {

        companion object {
            val URL_PULL = "URL_PULL_REQUEST"
            var valorURL = ""
        }

        var image_user: ImageView
        var txt_detalle_pull: TextView
        var txt_autor_pull: TextView
        var txt_titulo_pull: TextView

        init {
            image_user = itemView.image_autor
            txt_detalle_pull = itemView.txt_detalle_pull
            txt_autor_pull = itemView.txt_autor_pull
            txt_titulo_pull = itemView.txt_titulo_pull

            customView.setOnClickListener {
                val intent = Intent(customView.context, PullPageActivity::class.java)
                intent.putExtra(URL_PULL, valorURL)
                customView.context.startActivity(intent)
            }
        }
    }
}









