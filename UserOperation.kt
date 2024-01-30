package com.idekhail.asa_sqlite_db1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class UserOperation(context: Context) : SQLiteOpenHelper(context, "myDb", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                "$ID_COL INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$USER_COL TEXT," +
                "$PASS_COL TEXT)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(drop_table)
        onCreate(db)
    }

    fun insertUser(username: String, password: String): Boolean {
        var db = this.writableDatabase
        var cv = ContentValues()
        cv.put(USER_COL, username)
        cv.put(PASS_COL, password)
        val success = db.insert(TABLE_NAME, null, cv)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    fun login(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cols = arrayOf(ID_COL, USER_COL, PASS_COL)
        val selections = "$USER_COL=? AND $PASS_COL=?"
        val args = arrayOf(username, password)
        // cursor
        val cursor = db.query(TABLE_NAME, cols, selections, args, null, null, null)
        var count = cursor.count
        return count != 0
    }

    fun searchUser(username: String): Cursor {
        val db = this.readableDatabase
        val cols = arrayOf(ID_COL, USER_COL, PASS_COL)
        val selections = "$USER_COL=?"
        val args = arrayOf(username)
        // cursor
        val cursor = db.query(TABLE_NAME, cols, selections, args, null, null, null)
        return cursor
    }

    fun update(id: Int, username: String?, password: String?): Int {
        var db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_COL, username)
        contentValues.put(PASS_COL, password)
        return db.update(TABLE_NAME, contentValues, ID_COL + " = " + id,null)
    }

    fun deleteUser(id: Int){
        var db = this.writableDatabase
        db.delete(TABLE_NAME, ID_COL + "=" + id, null);
    }

    @SuppressLint("Range")
    fun readData(): MutableList<Users> {
        val list: MutableList<Users> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val user = Users()
                user.Id = Integer.parseInt(result.getString(result.getColumnIndex(ID_COL)))
                user.Username = result.getString(result.getColumnIndex(USER_COL)).toString()
                user.Password = result.getString(result.getColumnIndex(PASS_COL)).toString()
                list.add(user)
            }while (result.moveToNext())
        }
        return list
    }
    @SuppressLint("Range")
    fun readAllData(): List<Users> {
        val list = ArrayList<Users>()
        val db = this.writableDatabase
        val query = "Select * from $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val user = Users()
                    user.Id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID_COL)))
                    user.Username = cursor.getString(cursor.getColumnIndex(USER_COL))
                    user.Password = cursor.getString(cursor.getColumnIndex(PASS_COL))
                    list.add(user)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return list
    }

    @SuppressLint("Range")
    fun  getUserbyId(username: String): Users {
        val user = Users();
        val db = writableDatabase

        val query ="SELECT * FROM $TABLE_NAME WHERE $USER_COL = $username"
        val cursor = db.rawQuery(query, null)

        if(cursor.moveToFirst()){
            user.Id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID_COL)))
            user.Username = cursor.getString(cursor.getColumnIndex(USER_COL))
            user.Password = cursor.getString(cursor.getColumnIndex(PASS_COL))
        }

        cursor.close()
        return user
    }
    fun readUserByUsername(username: String): Cursor{
        val db = this.readableDatabase
        return db.query(
            TABLE_NAME,
            null,
            "USER_COL = ?",
            arrayOf<String>(ID_COL, USER_COL, PASS_COL),
            null,
            null,
            null,
            null
        )
    }
    fun searchData(name: String): Cursor? {
        val db = this.writableDatabase
        //String qry = "SELECT * FROM "+TABLE_NAME+" WHERE ID="+id;
        return db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $USER_COL = $name",
            null
        )
    }

    fun fetch(): Cursor? {
        val db = this.readableDatabase

        val cursor: Cursor = db.query(
            TABLE_NAME,
            arrayOf<String>(ID_COL, USER_COL, PASS_COL),
            null,
            null,
            null,
            null,
            null
        )

        if (cursor != null) {
            cursor.moveToFirst()
        }
        return cursor
    }


    companion object {
        const val TABLE_NAME = "Users"
        const val ID_COL = "Id"
        const val USER_COL = "Username"
        const val PASS_COL = "Password"
    }
}

class Users{
    var Id: Int  = 0
    var Username: String = ""
    var Password: String = ""
}
/*
// fetch
open fun fetch(): Cursor? {
    val db = this.readableDatabase

    val columns = arrayOf(ID_COL, USER_COL, PASS_COL)
    val cursor: Cursor = db.query(TABLE_NAME, columns, null,
        null, null, null, null)
    if (cursor != null) {
        cursor.moveToFirst()
    }
    return cursor
}
*/