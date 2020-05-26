package tw.edu.ncku.iim.newsreader

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * A simple [Fragment] subclass.
 */
class newsWebviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var url = "https://www.google.com.tw/";

        arguments?.let {
            val args = newsWebviewFragmentArgs.fromBundle(it)
            url=args.NewsURL
        }

        var myView = inflater.inflate(R.layout.fragment_news_webview,container,false)

        var view = myView.findViewById<WebView>(R.id.NewsPage)

        view.webViewClient= WebViewClient()
        view?.loadUrl(url)

        return myView
    }

}
