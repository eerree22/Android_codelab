package tw.edu.ncku.iim.newsreader

import android.graphics.BitmapFactory
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.xml.parsers.SAXParserFactory

class NewsSAX(val listener : ParserListener) : DefaultHandler() {
    private val factory = SAXParserFactory.newInstance()
    private val parser =factory.newSAXParser()

    private val service = Executors.newFixedThreadPool(1) //建立一個執行緒來執行下載照片的動作

    private var itemFound=false
    private var titleFound=false
    private var linkFound=false
    private var imageFound=false


    private var element:String=""

    fun parseURL(url : String)
    {
        Thread(Runnable {
            try
            {
                val inputStream= URL(url).openStream()
                parser.parse(inputStream,this)

            }
            catch (e:Throwable)
            {
                e.printStackTrace()
            }
        }).start()
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?)
    {
        super.startElement(uri, localName, qName, attributes)

        if(localName=="item")
        {
            itemFound=true
        }

        if(itemFound)
        {
            if(localName=="title")
            {
                titleFound=true
            }
            else if(localName=="link")
            {
                linkFound=true
            }
            else if(localName=="thumbnail")
            {
                val url=attributes?.getValue("url")

                service.execute {
                    val inputStream=URL(url).openStream()
                    val bitmap=BitmapFactory.decodeStream(inputStream)
                    listener.setImage(bitmap)
                }
            }
        }

        element=""
    }

    override fun characters(ch: CharArray?, start: Int, length: Int)
    {
        super.characters(ch, start, length)

        ch?.let { element+= String(it,start,length) }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?)
    {
        super.endElement(uri, localName, qName)

        if(localName=="item")
        {
            itemFound=false
        }

        if (itemFound&&titleFound)
        {
            listener.setTitle(element)
            titleFound=false
        }

        if (itemFound&&linkFound)
        {
            listener.setLink(element)
            linkFound=false
        }
    }

    override fun startDocument() {
        super.startDocument()

        listener.start()
    }

    override fun endDocument() {
        super.endDocument()

        listener.finish()
    }
}