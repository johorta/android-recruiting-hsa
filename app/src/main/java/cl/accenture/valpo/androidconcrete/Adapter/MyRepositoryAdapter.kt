package cl.accenture.valpo.andrpoid.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import cl.accenture.valpo.androidconcrete.DetailActivity
import cl.accenture.valpo.androidconcrete.Model.Repository.Owner
import cl.accenture.valpo.androidconcrete.Model.Repository.Repositories
import cl.accenture.valpo.androidconcrete.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_repository_item.view.*

class MyRepositoryAdapter(val repositoryList: Repositories): RecyclerView.Adapter<MyRepositoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.layout_repository_item, parent, false)

        return MyViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return repositoryList.getItems()!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var owner: Owner =  this.repositoryList.getItems()!!.get(position)!!.owner!!
        Picasso.get().load(owner.avatarUrl).into(holder.image_user)

        holder.txt_name_repository.text = repositoryList.getItems()!!.get(position)!!.name
        holder.txt_description_repository.text =repositoryList.getItems()!!.get(position)!!.description
        holder.txt_autor.text = owner.login
        holder.txt_fork.text = repositoryList.getItems()!!.get(position)!!.forksCount.toString()
        holder.txt_star.text = repositoryList.getItems()!!.get(position)!!.stargazersCount.toString()
        MyViewHolder.valorURL = "https://api.github.com/repos/"+owner.login+"/"+repositoryList.getItems()!!.get(position)!!.name+"/pulls"
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            val URL_PULL = "URL_PULL"
            var valorURL = ""
        }

        var image_user: ImageView
        var txt_name_repository: TextView
        var txt_description_repository: TextView
        var txt_autor: TextView
        var txt_star: TextView
        var txt_fork: TextView

        init {
            image_user = itemView.image_autor
            txt_autor = itemView.txt_autor
            txt_name_repository = itemView.txt_name_repository
            txt_description_repository = itemView.txt_description_repository
            txt_fork= itemView.txt_fork
            txt_star= itemView.txt_star

            //actionListener
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(URL_PULL, valorURL)
                itemView.context.startActivity(intent)
            }
        }
    }
}
