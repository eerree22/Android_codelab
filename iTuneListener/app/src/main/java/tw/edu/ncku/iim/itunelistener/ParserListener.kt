package tw.edu.ncku.iim.itunelistener

import android.graphics.Bitmap

interface ParserListener {

    public fun setTitle(title:String)
    public fun setUrl(link:String)
    public fun setCover(image: Bitmap)
    public fun start()
    public fun finish()
}