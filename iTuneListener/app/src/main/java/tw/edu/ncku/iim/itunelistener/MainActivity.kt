package tw.edu.ncku.iim.itunelistener

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var iTuneURL :String="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml"
        val MyLinearLayout: LinearLayout=findViewById(R.id.linearLayout)
        var count=1

        iTuneSAX(object:ParserListener{
            override fun setTitle(title: String) {
                runOnUiThread{ //在MainThread執行(只有MainThread能改UI)
                    val NewTextView=TextView(this@MainActivity)
                    NewTextView.text = "No.$count-$title"
                    MyLinearLayout.addView(NewTextView)
                    count++
                }
            }
        }).parseURL(iTuneURL)
    }
}
