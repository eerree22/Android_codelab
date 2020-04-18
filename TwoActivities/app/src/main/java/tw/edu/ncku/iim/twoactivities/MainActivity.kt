package tw.edu.ncku.iim.twoactivities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    companion object{
        const val myRequestCode = 666
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
