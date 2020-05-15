package tw.edu.ncku.iim.itunelistener


import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import tw.edu.ncku.iim.itunelistener.iTunePreviewFragmentArgs

/**
 * A simple [Fragment] subclass.
 */
class iTunePreviewFragment : Fragment() {
    private lateinit var btnPlay: Button
    private lateinit var btnPause:Button
    private lateinit var seekBar: SeekBar
    private val mediaPlayer = MediaPlayer()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            val args = iTunePreviewFragmentArgs.fromBundle(it)
            previewSong(args.title, args.cover, args.url)
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.stop()
        seekBar.handler.removeCallbacksAndMessages(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_preview, null)
        btnPlay = view.findViewById<Button>(R.id.btnPlay)
        btnPause = view.findViewById<Button>(R.id.btnPause)
        seekBar = view.findViewById<SeekBar>(R.id.seekBar)

        btnPlay.setOnClickListener() {
            mediaPlayer.start()
            seekBar.postDelayed({updateSeekBar()}, 1000)
        }

        btnPause.setOnClickListener() {
            mediaPlayer.pause()
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        setUiEnabled(false)

        return view
    }

    public fun previewSong(title: String, cover: Bitmap, url: String) {
        val textView = view?.findViewById<TextView>(R.id.textView)
        textView?.setText(title)

        val imageView = view?.findViewById<ImageView>(R.id.imageView)
        imageView?.setImageBitmap(cover)

        try {
            setUiEnabled(false)
            mediaPlayer.reset()
            mediaPlayer.setDataSource(url)
            mediaPlayer.setOnPreparedListener {
                seekBar.setMax(it.duration)
                setUiEnabled(true)
                Log.i("PreviewActivity", "MediaPlayer is ready...")
            }
            mediaPlayer.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUiEnabled(b: Boolean) {
        btnPlay.isEnabled = b
        btnPause.isEnabled = b
        seekBar.isEnabled = b
    }

    fun updateSeekBar() {
        seekBar.progress = mediaPlayer.currentPosition
        if (mediaPlayer.isPlaying) {
            seekBar.postDelayed({updateSeekBar()}, 1000)
        }
    }
}
