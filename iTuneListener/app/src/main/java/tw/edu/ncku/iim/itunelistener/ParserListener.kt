package tw.edu.ncku.iim.itunelistener

import android.graphics.Bitmap

interface ParserListener {

    public fun setTitle(title:String)
    public fun setLink(link:String)
    public fun setImage(image: Bitmap)
}