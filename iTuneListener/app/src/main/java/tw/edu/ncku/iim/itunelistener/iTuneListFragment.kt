package tw.edu.ncku.iim.itunelistener


import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.ListFragment
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*
import tw.edu.ncku.iim.itunelistener.iTuneListFragmentDirections

/**
 * A simple [Fragment] subclass.
 */
class iTuneListFragment : ListFragment() {

    interface OnSongSelectedListener {
        fun onSongSelected(title: String, cover: Bitmap, url: String)
    }
    public var listener: OnSongSelectedListener? = null

    private var titles = mutableListOf<String>()
    private var covers: MutableList<Bitmap> = mutableListOf<Bitmap>()
    private var urls = mutableListOf<String>()
    private lateinit var adapter: iTuneListAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        downloadList()

        context?.let {
            adapter = iTuneListAdapter(it, titles, covers)
            listAdapter = adapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            titles.clear()
            covers.clear()
            downloadList()
        }


    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        if(nav_host_fragment!=null)
        {
            val action = iTuneListFragmentDirections.actionITuneListFragmentToITunePreviewFragment(
                titles[position], covers[position], urls[position]
            )
            findNavController().navigate(action)
        }
        else
        {
            listener?.onSongSelected(titles[position], covers[position], urls[position])
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        swipeRefreshLayout = inflater.inflate(R.layout.fragment_list, null) as SwipeRefreshLayout
        return swipeRefreshLayout
    }

    private fun downloadList() {
        iTuneSAX(object: ParserListener {
            override fun setUrl(url: String) {
                urls.add(url)
            }

            override fun setCover(cover: Bitmap) {
                activity?.runOnUiThread{
                    covers.add(cover)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun start() {
                activity?.runOnUiThread {
                    swipeRefreshLayout.isRefreshing = true
                }
            }

            override fun finish() {
                activity?.runOnUiThread {
                    swipeRefreshLayout.isRefreshing = false
                }
            }

            override fun setTitle(title: String) {
                activity?.runOnUiThread {
                    titles.add(title)
                    adapter.notifyDataSetChanged()
                }
            }

        }).parseURL("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml")
    }

}
