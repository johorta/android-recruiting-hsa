package cl.accenture.valpo.androidconcrete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import cl.accenture.valpo.androidconcrete.Model.Repository.Repositories
import cl.accenture.valpo.andrpoid.Adapter.MyRepositoryAdapter
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerRepositories.layoutManager = LinearLayoutManager(this)
        fetchJson()
    }

    fun fetchJson() {
        val url = "https://api.github.com/search/repositories?q=language:Java&sort=stars&page=1"
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                //println(body)

                val gson = GsonBuilder().create()

                val repository = gson.fromJson(body, Repositories::class.java)

                runOnUiThread {
                    recyclerRepositories.adapter = MyRepositoryAdapter(repository)
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })
    }
}