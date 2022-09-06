package com.ashish.ashishproductlist.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavClass::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favDao(): FavDAO
}