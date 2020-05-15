package tw.edu.ncku.iim.itunelistener

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class iTuneListAdapter(val context:Context,val titles:List<String>,val images:List<Bitmap>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //load layout
        val inflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView=inflater.inflate(R.layout.activity_i_tune_list_adapter,null)
        val imageView: ImageView =itemView.findViewById(R.id.imageView)
        imageView.setImageBitmap(images[position])
        val textView: TextView =itemView.findViewById(R.id.textView)
        textView.text=titles[position]

        return itemView
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return images.size
    }
}
