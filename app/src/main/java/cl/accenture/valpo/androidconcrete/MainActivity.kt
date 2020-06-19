package cl.accenture.valpo.androidconcrete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import cl.accenture.valpo.androidconcrete.Model.Repository.Repositories
import cl.accenture.valpo.andrpoid.Adapter.MyRepositoryAdapter
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import androidx.recyclerview.widget.LinearLayoutManager as LinearLayoutManager

class MainActivity : AppCompatActivity() {

    var isScrolling: Boolean?=false
    var currentItems: Int?= 0
    var totalItems: Int?= 0
    var scrollOutItems: Int?= 0
    var layout: LinearLayoutManager?=null
    var progressBar: ProgressBar?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.layout = LinearLayoutManager(this)
        progressBar = findViewById(R.id.progressMain)


        recyclerRepositories.layoutManager = LinearLayoutManager(this)
        recyclerRepositories.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                isScrolling=true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                currentItems=layout!!.childCount
                totalItems=layout!!.itemCount
                scrollOutItems=layout!!.findFirstVisibleItemPosition()



                if (isScrolling==true && ( totalItems== currentItems!! + scrollOutItems!!)){
                    isScrolling=false;
                    fetchJson()
                }

            }
        })

        fetchJson()
    }

    fun fetchJson() {
        progressBar!!.visibility=View.VISIBLE
        val url = "https://api.github.com/search/repositories?q=language:Java&sort=stars&page=1"
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()

                val repository = gson.fromJson(body, Repositories::class.java)

                runOnUiThread {
                    recyclerRepositories.adapter = MyRepositoryAdapter(repository)
                    MyRepositoryAdapter(repository).notifyDataSetChanged()
                    progressBar!!.visibility=View.GONE
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })
    }
}