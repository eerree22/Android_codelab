package tw.edu.ncku.iim.itunelistener

import android.graphics.BitmapFactory
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.net.URL
import java.util.concurrent.Executors
import javax.xml.parsers.SAXParserFactory

class iTuneSAX(val listener: ParserListener) : DefaultHandler() {

    private val service = Executors.newFixedThreadPool(1) //建立一個執行緒來執行下載照片的動作

    private val factory= SAXParserFactory.newInstance()
    private val parser = factory.newSAXParser()
    //先將XML Paser建立好給所有呼叫此class的物件用

    private var entryFound=false
    private var titleFound=false
    private var imageFound=false
    private var element:String=""

    //建立與input網站的連結
    fun parseURL(url:String)
    {
        //一個Thread需要一個Runnable把要執行的程式定義好
        Thread(Runnable {
            try//網路存取有出錯的可能所以包在一個try catch 中
            {
                val inputStream=URL(url).openStream()//開啟資料流
                parser.parse(inputStream,this)//丟給parser做解析 dh:出錯時通知誰
            }
            catch (e:Throwable)
            {
                e.printStackTrace()
            }

        }).start()
    }

    //code > override Methods > 選startElement.endElement.characters

    //讀取XML的時候每看到一個Element就會呼叫startElement
    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?)
    {
        super.startElement(uri, localName, qName, attributes)
        //一般在override的時候都會先用super這個語法讓母function執行它原本要做的事情之後再執行我要做的事

        if (localName=="entry")
        {
            entryFound=true
        }

        if (entryFound)
        {
            if(localName=="title")
            {
                titleFound=true
            }
            else if (localName=="image"&&attributes?.getValue("height")=="170")
            {
                imageFound=true
            }
            else if(localName=="link"&&attributes?.getValue("type")=="audio/x-m4a")
            {
                listener.setLink(attributes?.getValue("href"))
            }
        }

        element=""
    }

    //遇到XML標籤中的內容就會呼叫此fun
    override fun characters(ch: CharArray?, start: Int, length: Int) {
        super.characters(ch, start, length)

        //如果裡面有內容才會執行以下程式
        ch?.let { element+= String(it,start,length) }
    }

    //讀取XML的時候每看到一個結尾Element就會呼叫endElement
    override fun endElement(uri: String?, localName: String?, qName: String?) {
        super.endElement(uri, localName, qName)

        if (localName=="entry")
        {
            entryFound=false
        }

        if (entryFound&&titleFound)
        {
            listener.setTitle(element)
            titleFound=false
        }

        if (entryFound&&imageFound)
        {
            val url=element
            service.execute {
                val inputStream=URL(url).openStream()
                val bitmap= BitmapFactory.decodeStream(inputStream)
                listener.setImage(bitmap)
            }
            imageFound=false
        }
    }





}