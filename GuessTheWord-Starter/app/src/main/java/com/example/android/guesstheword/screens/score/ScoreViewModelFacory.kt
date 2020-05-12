package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class ScoreViewModelFacory(private val findScore:Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ScoreViewModel::class.java))
        {
            return ScoreViewModel(findScore) as T
        }
        else
        {
            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}