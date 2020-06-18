package cl.accenture.valpo.androidconcrete.Model.Repository

import cl.accenture.valpo.androidconcrete.Model.Repository.Item
import com.google.gson.annotations.SerializedName

class Repositories {
     @SerializedName("total_count")
     private var totalCount: Int? = null

     @SerializedName("incomplete_results")
     private var incompleteResults: Boolean? = null

     @SerializedName("items")
     private var items: List<Item?>? =
          null

     fun getTotalCount(): Int? {
          return totalCount
     }

     fun setTotalCount(totalCount: Int?) {
          this.totalCount = totalCount
     }

     fun getIncompleteResults(): Boolean? {
          return incompleteResults
     }

     fun setIncompleteResults(incompleteResults: Boolean?) {
          this.incompleteResults = incompleteResults
     }

     fun getItems(): List<Item?>? {
          return items
     }

     fun setItems(items: List<Item?>?) {
          this.items = items
     }

}