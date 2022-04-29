package com.example.firebasenotification.sqlLite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object
    {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UsersDataDatabase"
        private const val TABLE_CONTACTS = "UsersDataTable"

        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_NUMBER = "number"
        private const val KEY_GENDER = "gender"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createDataTable = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_NUMBER + " TEXT, "
                + KEY_GENDER + " TEXT " + ")" )
        db?.execSQL(createDataTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addUser(emp:UserModel):Long
    {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME,emp.contact_name)
        contentValues.put(KEY_NUMBER,emp.contact_number)
        contentValues.put(KEY_GENDER,emp.gender)

        //inserting Raw
        val success = db.insert(TABLE_CONTACTS, null,contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range", "Recycle")
    fun viewUser():MutableList<UserModel> {
        val userList: MutableList<UserModel> = ArrayList()

        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userID: Int
        var name: String
        var number: String
        var gender: String

        if (cursor != null && cursor.count > 0) {
            if (cursor.moveToFirst()) {
                do {

                    userID = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                    name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                    number = cursor.getString(cursor.getColumnIndex(KEY_NUMBER))
                    gender = cursor.getString(cursor.getColumnIndex(KEY_GENDER))

                    val user =
                        UserModel(userID,name,number,gender)
                    userList.add(user)
                } while (cursor.moveToNext())
            }
        }
        return userList
    }

    fun updateUser(emp: UserModel):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID,emp.userId)
        contentValues.put(KEY_NAME,emp.contact_name)
        contentValues.put(KEY_NUMBER,emp.contact_number)
        contentValues.put(KEY_GENDER,emp.gender)

        val success = db.update(TABLE_CONTACTS,contentValues, KEY_ID + "=" + emp.userId , null)
        db.close()
        return success
    }

    fun deleteUser(emp: UserModel):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME,emp.contact_name)

        val success = db.delete(TABLE_CONTACTS, KEY_ID + "=" + emp.userId,null)
        db.close()
        return success
    }
}