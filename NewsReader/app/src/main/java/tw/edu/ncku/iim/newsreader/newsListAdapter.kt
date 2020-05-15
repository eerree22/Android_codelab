package tw.edu.ncku.iim.newsreader

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class newsListAdapter(val context:Context,val titles:List<String>,val images:List<Bitmap>,val descriptions:List<String>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //load layout
        Log.i("getView","666666")

        val inflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView=inflater.inflate(R.layout.news_list_item,null)
        val imageView:ImageView=itemView.findViewById(R.id.imageView)
        imageView.setImageBitmap(images[position])
        val txtTitle:TextView=itemView.findViewById(R.id.txtTitle)
        txtTitle.text=titles[position]
        val txtDescription:TextView=itemView.findViewById(R.id.txtDescription)
        txtDescription.text=descriptions[position]

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