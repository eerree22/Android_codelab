package tw.edu.ncku.iim.newsreader

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.ListFragment
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 */
class newsListFragment : ListFragment() {

    var newsURL="https://www.times-series.co.uk/news/national/rss/"
    var titleList= mutableListOf<String>()//存新聞標題
    var linkList= mutableListOf<String>()//存新聞連結
    var imageList= mutableListOf<Bitmap>()//存新聞圖片
    var descriptionList= mutableListOf<String>()//存新聞描述

    private lateinit var myAdapter: newsListAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        swipeRefreshLayout = inflater.inflate(R.layout.fragment_news_list, null) as SwipeRefreshLayout
        return swipeRefreshLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        context?.let {
            myAdapter = newsListAdapter(it,titleList, imageList,descriptionList)
            listAdapter = myAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            titleList.clear()
            linkList.clear()
            imageList.clear()
            downloadList()
        }

        downloadList()
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

            val action = newsListFragmentDirections.actionNewsListFragmentToNewsWebviewFragment(linkList[position])
            findNavController().navigate(action)

    }

    private fun downloadList()
    {
        NewsSAX(object :ParserListener{
            override fun setTitle(title: String) {
                activity?.runOnUiThread{
                    titleList.add(title)
                    myAdapter.notifyDataSetChanged()//Notifies the attached observers that the underlying data has been changed and any View reflecting the data set should refresh itself.
                }
            }

            override fun setLink(link: String) {
                activity?.runOnUiThread{
                    linkList.add(link)
                }
            }

            override fun setImage(image: Bitmap) {
                activity?.runOnUiThread{
                    imageList.add(image)
                    myAdapter.notifyDataSetChanged()//Notifies the attached observers that the underlying data has been changed and any View reflecting the data set should refresh itself.
                }
            }

            override fun setDescription(description: String) {
                activity?.runOnUiThread {
                    descriptionList.add(description)
                    myAdapter.notifyDataSetChanged()
                }
            }

            override fun start() {
                activity?.runOnUiThread {
                    swipeRefreshLayout.isRefreshing=true
                }
            }

            override fun finish() {
                activity?.runOnUiThread {
                    swipeRefreshLayout.isRefreshing=false
                }
            }

        }).parseURL(newsURL)
    }
}
