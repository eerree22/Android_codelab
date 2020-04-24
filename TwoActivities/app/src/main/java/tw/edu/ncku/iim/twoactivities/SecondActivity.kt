package tw.edu.ncku.iim.twoactivities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    private val LOG_TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val message=intent.getStringExtra("message")
        val myTextView=findViewById<TextView>(R.id.text_message)

        myTextView.text=message

        Log.d(LOG_TAG,"Second_onCreate")
    }

    override fun onStart() {
        super.onStart()

        Log.d(LOG_TAG,"Second_onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG,"Second_onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG,"Second_onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG,"Second_onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG,"Second_onDestroy")
    }

    fun returnReply(view: View) {

        val myEditText=findViewById<EditText>(R.id.editText_second)
        val myMessage=myEditText.text.toString()

        val intent= Intent()
        intent.putExtra("message",myMessage)
        setResult(Activity.RESULT_OK,intent)
        Log.d(LOG_TAG,"End secondActivity")
        finish()
    }
}
