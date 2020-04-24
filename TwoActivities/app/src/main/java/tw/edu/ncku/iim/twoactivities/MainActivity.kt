package tw.edu.ncku.iim.twoactivities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    companion object{
        const val myRequestCode = 666
    }

    private val LOG_TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(LOG_TAG,"正在呼叫onCreate")

        if (savedInstanceState!=null)
        {
            val myText=savedInstanceState.getString("myText")

            val myTextView=findViewById<TextView>(R.id.text_message_main)
            myTextView.text=myText
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d(LOG_TAG,"正在呼叫onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG,"正在呼叫onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG,"正在呼叫onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG,"正在呼叫onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG,"正在呼叫onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        //刷新前先將文字儲存起來
        val myText=findViewById<TextView>(R.id.text_message_main).text.toString()

        if (myText.isNotEmpty())
        {
            outState.putString("myText",myText)
        }
    }

    fun launchSecondActivity(view: View) {

        val myEditText=findViewById<EditText>(R.id.editText_main)
        val myText=myEditText.text.toString()

        val intent= Intent(this,SecondActivity::class.java)

        intent.putExtra("message",myText)

        startActivityForResult(intent, myRequestCode)
    }

    //startActivityForResult得到回傳時呼叫
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode== myRequestCode)
        {
            if (resultCode==Activity.RESULT_OK)
            {
                val message= data?.getStringExtra("message")
                val myTextView=findViewById<TextView>(R.id.text_message_main)
                myTextView.text=message
            }
        }
    }
}
