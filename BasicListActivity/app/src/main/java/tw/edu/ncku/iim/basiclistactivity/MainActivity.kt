package tw.edu.ncku.iim.basiclistactivity

import android.app.ListActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter

class MainActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data= listOf("AAA","BBB","CCC")
        //val data= listOf<String>()

        val myAdapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data)
        listAdapter = myAdapter

        myAdapter.notifyDataSetChanged()


        var title= mutableListOf<String>()
        title.add("111")
    }
}
