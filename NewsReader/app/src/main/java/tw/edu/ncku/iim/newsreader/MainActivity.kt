package tw.edu.ncku.iim.newsreader

import android.app.ListActivity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : ListActivity() {

    var newsURL="https://www.times-series.co.uk/news/national/rss/"
    var titleList= mutableListOf<String>()//存新聞標題
    var linkList= mutableListOf<String>()//存新聞連結
    var imageList= mutableListOf<Bitmap>()//存新聞圖片

    //val myAdapter : ArrayAdapter<String> by lazy {
    //   ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titleList)
    //}

    val myAdapter = newsListAdapter(this, titleList, imageList)

    val swipeRefreshLayout by lazy {
        findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        val link=linkList.get(position)
        val viewIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(viewIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_refresh)
        listAdapter = myAdapter  //指定list的資料來源

        swipeRefreshLayout.setOnRefreshListener {
            titleList.clear()
            linkList.clear()
            downloadList()
        }

        downloadList()
    }

    fun btnReFlash_OnClick(view: View) {
        this.recreate()
    }

    private fun downloadList()
    {
        var count=1

        NewsSAX(object :ParserListener{
            override fun setTitle(title: String) {
                runOnUiThread{
                    titleList.add("$count - $title")
                    myAdapter.notifyDataSetChanged()//Notifies the attached observers that the underlying data has been changed and any View reflecting the data set should refresh itself.
                    count++
                }
            }

            override fun setLink(link: String) {
                runOnUiThread{
                    linkList.add(link)
                }
            }

            override fun setImage(image: Bitmap) {
                runOnUiThread{
                    imageList.add(image)
                    myAdapter.notifyDataSetChanged()
                }
            }

            override fun start() {
                runOnUiThread {
                    swipeRefreshLayout.isRefreshing=true
                }
            }

            override fun finish() {
                runOnUiThread {
                    swipeRefreshLayout.isRefreshing=false

                }
            }

        }).parseURL(newsURL)
    }
}
