package tw.edu.ncku.iim.twoactivities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val message=intent.getStringExtra("message")
        val myTextView=findViewById<TextView>(R.id.text_message)

        myTextView.text=message
    }

    fun returnReply(view: View) {

        val myEditText=findViewById<EditText>(R.id.editText_second)
        val myMessage=myEditText.text.toString()

        val intent= Intent()
        intent.putExtra("message",myMessage)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
}
