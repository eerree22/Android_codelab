package tw.edu.ncku.iim.newsreader

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
    private var Image:Bitmap?=null

    private val btnPlay: Button by lazy {
        findViewById<Button>(R.id.btnPlay)
    }
    private val btnPause: Button by lazy {
        findViewById<Button>(R.id.btnPause)
    }
    private val seekBar: SeekBar by lazy {
        findViewById<SeekBar>(R.id.seekBar)
    }
    private val mediaPlayer=MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        Link = intent.getStringExtra("myLink")
        title = intent.getStringExtra("myTitle")
        Image = intent.getParcelableExtra<Bitmap>("myImage")

        //連結先寫死
        //todo
        Link="https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview113/v4/7c/a0/1b/7ca01be8-27d9-6bbd-520b-82b137b81b09/mzaf_13267143359898123134.plus.aac.p.m4a"

        val textView = findViewById<TextView>(R.id.textView)
        textView.text=title

        val imageView=findViewById<ImageView>(R.id.imageView)
        imageView.setImageBitmap(Image)

        setUiEnabled(false)

        try {
            //設定mediaPlayer
            mediaPlayer.setDataSource(Link)
            mediaPlayer.setOnPreparedListener{
                seekBar.max=it.duration
                setUiEnabled(true)
            }
            mediaPlayer.prepareAsync()//非同步播放
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }

        //實作拉動播放器
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser)
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

    private fun setUiEnabled(b: Boolean) {
        btnPlay.isEnabled=b
        btnPause.isEnabled=b
        seekBar.isEnabled=b
    }

    fun btnPlayOnClick(view: View) {

        if (view==btnPlay)
        {
            mediaPlayer.start() //開始撥放
            seekBar.postDelayed({
                updateSeekBar()
            },1000) //每秒更新seekBar
        }
        else if(view==btnPause)
        {
            mediaPlayer.pause()
        }
    }

    private fun updateSeekBar() {
        seekBar.progress = mediaPlayer.currentPosition //讓seekBar的位置跟著mediaPlayer的currentPosition跑
        if (mediaPlayer.isPlaying)//正在播放就繼續
        {
            seekBar.postDelayed({updateSeekBar()},1000)
        }
    }

}
