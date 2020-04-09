package tw.edu.ncku.iim.newsreader

import android.app.ListActivity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MainActivity : ListActivity() {

    var newsURL="https://www.times-series.co.uk/news/national/rss/"
    var titleList= mutableListOf<String>()
    var linkList= mutableListOf<String>()
    val myAdapter : ArrayAdapter<String> by lazy {
        ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titleList)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        val link=linkList.get(position)
        val viewIntent = Intent(
            "android.intent.action.VIEW",
            Uri.parse(link)
        )
        startActivity(viewIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listAdapter = myAdapter  //指定list的資料來源

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

        }).parseURL(newsURL)
    }

    fun btnReFlash_OnClick(view: View) {
        this.recreate()
    }
}
