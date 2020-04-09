package tw.edu.ncku.iim.newsreader

interface ParserListener {
    fun setTitle(title:String)
    fun setLink(link:String)
}