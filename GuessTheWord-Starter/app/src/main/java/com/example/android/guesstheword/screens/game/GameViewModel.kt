package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.guesstheword.databinding.GameFragmentBinding

class GameViewModel : ViewModel() {
    // The current word
    private var _word = MutableLiveData<String>()  //使用getter來呼叫參數，程式較為穩固
    val word:LiveData<String>
        get() = _word

    // The current score
    private var _score = MutableLiveData<Int>()
    val score:LiveData<Int>
        get() = _score


    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>


    init {
        Log.i("GameViewModel","GameViewModel created!")
        _word.value = ""
        _score.value = 0

        resetList()
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel","GameViewModel cleared!")

    }

    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        if (!wordList.isEmpty()) {
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        }

    }

    /** Methods for buttons presses **/
    fun onSkip() {
        _score.value=(_score.value)?.minus(1)//minus=減法
        nextWord()
    }

    fun onCorrect() {
        _score.value=(_score.value)?.plus(1)//minus=加法
        nextWord()
    }
}