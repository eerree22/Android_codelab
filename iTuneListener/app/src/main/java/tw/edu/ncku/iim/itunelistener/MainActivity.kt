package tw.edu.ncku.iim.itunelistener

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listFragment = supportFragmentManager.findFragmentById(R.id.list_fragment) as iTuneListFragment?

        listFragment?.listener = object : iTuneListFragment.OnSongSelectedListener {
            override fun onSongSelected(title: String, cover: Bitmap, url: String) {
                val previewFragment =
                    supportFragmentManager.findFragmentById(R.id.preview_fragment) as iTunePreviewFragment?
                previewFragment?.previewSong(title, cover, url)
            }
        }

    }
}
