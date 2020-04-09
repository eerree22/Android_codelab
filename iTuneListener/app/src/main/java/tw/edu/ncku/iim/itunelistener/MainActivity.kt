package tw.edu.ncku.iim.itunelistener

import android.app.ListActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast


class MainActivity : ListActivity() {
    var iTuneURL :String="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml"
    var titleList= mutableListOf<String>()
    //val myAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,titleList)
    val myAdapter : ArrayAdapter<String> by lazy {
        ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titleList)
    }

    //user按下list中的item時觸發
    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        val title=titleList.get(position)
        Toast.makeText(this,title,Toast.LENGTH_SHORT).show()

        val viewIntent = Intent(
            "android.intent.action.VIEW",
            Uri.parse("http://www.stackoverflow.com/")
        )
        startActivity(viewIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val MyLinearLayout: LinearLayout=findViewById(R.id.linearLayout)
        var count=1

        listAdapter = myAdapter  //指定list的資料來源

        iTuneSAX(object:ParserListener{
            override fun setTitle(title: String) {
                runOnUiThread{ //在MainThread執行(只有MainThread能改UI)

                    titleList.add("No.$count-$title")
                    myAdapter.notifyDataSetChanged()//Notifies the attached observers that the underlying data has been changed and any View reflecting the data set should refresh itself.

                    count++
                    //val NewTextView=TextView(this@MainActivity)
                    //NewTextView.text = "No.$count-$title"
                    //MyLinearLayout.addView(NewTextView)
                }
            }
        }).parseURL(iTuneURL)
    }
}
