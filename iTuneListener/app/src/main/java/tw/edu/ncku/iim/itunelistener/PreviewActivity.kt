package tw.edu.ncku.iim.itunelistener

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import java.lang.Exception

class PreviewActivity : AppCompatActivity() {
    private var title:String?=null
    private var Link:String?=null
    private var Image: Bitmap?=null

    private val textView:TextView by lazy { findViewById<TextView>(R.id.textView) }
    private val imageView:ImageView by lazy { findViewById<ImageView>(R.id.imageView) }
    private val btnPlay:Button by lazy { findViewById<Button>(R.id.btnPlay) }
    private val btnPause:Button by lazy { findViewById<Button>(R.id.btnPause) }
    private val seekBar:SeekBar by lazy { findViewById<SeekBar>(R.id.seekBar) }

    private val mediaPlayer=MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        Link = intent.getStringExtra("myLink")
        title = intent.getStringExtra("myTitle")
        Image = intent.getParcelableExtra<Bitmap>("myImage")

        textView.text=title
        imageView.setImageBitmap(Image)
        setUiEnabled(false)

        try {
            mediaPlayer.setDataSource(Link)//設定mediaPlayer資料來源
            mediaPlayer.setOnPreparedListener{
                seekBar.max=it.duration //將進度條長度與撥放器長度同步
                setUiEnabled(true)
            }
            mediaPlayer.prepareAsync()//非同步播放
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }

        //實作拉動播放器的進度條
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser)
                {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    fun onPreviewClick(view: View) {
        val intent= Intent(Intent.ACTION_VIEW, Uri.parse(Link))

        if (intent.resolveActivity(packageManager)!=null)
        {
            startActivity(intent)
        }
    }

    private fun setUiEnabled(b: Boolean) {
        btnPlay.isEnabled=b
        btnPause.isEnabled=b
        seekBar.isEnabled=b
    }

    fun btnPlayOnClick(view: View) {
        if(view==btnPlay)
        {
            mediaPlayer.start()
            seekBar.postDelayed({updateSeekBar()},1000)
        }
        else if(view==btnPause)
        {
            mediaPlayer.pause()
        }
    }

    //更新進度條
    private fun updateSeekBar() {
        seekBar.progress=mediaPlayer.currentPosition//將進度條更新至mediaPlayer目前播放位置
        if(mediaPlayer.isPlaying)
        {
            seekBar.postDelayed({updateSeekBar()},1000)//還在播放就持續更新進度條
        }
    }
}
