package tw.edu.ncku.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.getSystemService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun clickHandlerFunction(view: View) {
        val MyEditText:EditText = findViewById<EditText>(R.id.nickname_edit)
        val MyNicknameText:TextView = findViewById<TextView>(R.id.nickname_text)

        MyNicknameText.text=MyEditText.text

        //設定控制項可見度
        MyEditText.visibility=View.GONE
        view.visibility=View.GONE
        MyNicknameText.visibility=View.VISIBLE

        //在user輸入後將小鍵盤縮下去(預設不會縮)
        val inputMethodManager= getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)

    }

    fun updateNickname(view: View) {
        val MyEditText:EditText = findViewById<EditText>(R.id.nickname_edit)
        val MyNicknameText:TextView = findViewById<TextView>(R.id.nickname_text)
        val MyDoneBtn:Button=findViewById<Button>(R.id.done_button)

        MyEditText.text.clear()
        MyEditText.visibility = View.VISIBLE
        MyNicknameText.visibility=View.GONE
        MyDoneBtn.visibility=View.VISIBLE

        //將Focus設定到輸入欄位上
        MyEditText.requestFocus()

        //將小鍵盤打開
        val inputMethodManager= getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(MyEditText,0)
    }
}
