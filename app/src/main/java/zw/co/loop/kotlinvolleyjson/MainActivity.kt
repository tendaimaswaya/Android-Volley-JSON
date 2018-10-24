package zw.co.loop.kotlinvolleyjson

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONException
import org.json.JSONObject
import zw.co.loop.kotlinvolleyjson.application.KVJApp

class MainActivity : AppCompatActivity(), ClickListener {
    override fun onLikeClicked(dataModel: DataModel) {
        showToast("You've liked " +dataModel.title)
    }

    override fun onCommentClicked(dataModel: DataModel) {
        showToast("Comment item " +dataModel.id.toString())
    }

    override fun onContainerClicked(dataModel: DataModel) {
        showToast("Clicked on container " +dataModel.id.toString())
    }

    override fun onMoreClicked(dataModel: DataModel) {
        showToast("More Options")
    }

    //this is the location of your JSON file or your php file which echoes JSON formatted data
    private val url:String = "http://ip_address/feed.JSON"      //here if you are using xampp on your local server, run cmd, type in ipconfig and detect your ip address (Dont forget to specify port number, if not using default one)


    //this is the arrayList of Type DataModel. It will hold data structured as DataModel
    private var feedList:ArrayList<DataModel> = ArrayList()

    //this is the adapter for handling DataModel data. lateinit means we will initialize this variable later
    private lateinit var adapter:DataAdapter

    //this is the view in which your list items will be displayed
    private lateinit var recyclerView:RecyclerView

    //this is the entry point of our activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
        fetchFeed(url)
    }

    fun initAdapter(){
        adapter = DataAdapter(feedList,applicationContext,this)
        initRecyclerView()
    }

    fun initRecyclerView(){
        if (applicationContext != null) {
            recyclerView = findViewById(R.id.feed_view)
            val mLayoutManager = LinearLayoutManager(applicationContext)
            recyclerView.setLayoutManager(mLayoutManager)
            recyclerView.setItemAnimator(DefaultItemAnimator())
            recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, 0))
            recyclerView.adapter = adapter
        }
    }

    internal fun fetchFeed(url: String) {
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
            var jsonObject: JSONObject?
            try {
                feedList.clear()
                val obj = response.getJSONArray("feedJSON")
                for (i in 0 until obj.length()) {
                    jsonObject = obj.getJSONObject(i)
                    val dataModel =
                        DataModel(jsonObject!!.getInt("id"),jsonObject.getString("title"),
                        jsonObject.getString("date"), jsonObject.getString("image_url"))
                        feedList.add(dataModel)
                }
                adapter.notifyDataSetChanged()
            } catch (e: JSONException) {
                showToast(e.message!!)
            }
        }, Response.ErrorListener { error ->
            showToast(error.message!!)
        })
        KVJApp.instance?.addToRequestQueue(jsonObjectRequest, "feed_data")
    }

    fun showToast(msg:String){
        Toast.makeText(applicationContext,msg,Toast.LENGTH_LONG).show()
    }

}
