package tw.edu.ncku.iim.newsreader

import android.graphics.Bitmap

interface ParserListener {
    fun setTitle(title:String)
    fun setLink(link:String)
    fun setImage(image:Bitmap)
    fun setDescription(description: String)
    fun start()
    fun finish()
}