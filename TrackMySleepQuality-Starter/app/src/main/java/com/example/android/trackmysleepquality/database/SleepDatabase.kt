/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SleepNight::class], version = 1)
abstract class SleepDatabase:RoomDatabase(){
    abstract val sleepDatabaseDao:SleepDatabaseDao

    //companion object裡面的東西都是靜態的(這邊拿來做factory)
    companion object{
        @Volatile
        private var INSTANCE : SleepDatabase? = null // Volatile = 假使有多執行緒使用這個INSTANCE，當INSTANCE改變時會改所有執行緒的

        fun getInstance(context: Context):SleepDatabase
        {
            //singleton pattern
            synchronized(this)//此區塊同時只能有一個執行緒執行
            {
                var instance= INSTANCE
                if(instance == null)
                {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            SleepDatabase::class.java,
                            "sleep_history_database"
                    ).fallbackToDestructiveMigration().build()
                }

                INSTANCE = instance
                return instance
            }
        }
    }
}

