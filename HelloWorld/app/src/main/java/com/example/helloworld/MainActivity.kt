//這是用Kotlin撰寫的程式但因"歷史因素"放在Java資料夾內
package com.example.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random


//the MainActivity class is the main entry point for your app.
class MainActivity : AppCompatActivity() {
    lateinit var MyimgDice1:ImageView  //利用lateinit關鍵字使得可以在APPonCreate前先宣告變數
    lateinit var MyimgDice2:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //指定這個Activity對應的layout R=Resource

        val MybtnRoll:Button = findViewById(R.id.btnRoll)
        MyimgDice1 = findViewById(R.id.imgDice)
        MyimgDice2 = findViewById(R.id.imgDice2)

        MybtnRoll.text="擲骰子!"

        var count=0

        MybtnRoll.setOnClickListener {
            count++
            Toast.makeText(this,"按"+count+"次",Toast.LENGTH_SHORT).show()
            RollDice1()
        }

    }

    public fun MyimgDice2_OnClick(view : View)
    {
        var MyDiceImageID =getRandomDiceImage()

        MyimgDice2.setImageResource(MyDiceImageID)
    }

    private fun RollDice1() {

        var MyDiceImageID = getRandomDiceImage()

        MyimgDice1.setImageResource(MyDiceImageID)
    }

    private fun getRandomDiceImage():Int{
        var random = Random.nextInt(1,7)
        var MyDiceImageID = when (random)
        {
            1->R.drawable.dice_1
            2->R.drawable.dice_2
            3->R.drawable.dice_3
            4->R.drawable.dice_4
            5->R.drawable.dice_5
            6->R.drawable.dice_6
            else->R.drawable.empty_dice
        }

        return MyDiceImageID
    }

    fun btnClear_OnClick(view: View) {
        MyimgDice1.setImageResource(R.drawable.empty_dice)
        MyimgDice2.setImageResource(R.drawable.empty_dice)
    }
}
