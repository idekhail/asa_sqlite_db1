package com.idekhail.asa_sqlite_db1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class ShowActivity : AppCompatActivity() {
    lateinit var show: TextView
    lateinit var user1: String
    lateinit var pass1: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        val logout = findViewById<Button>(R.id.logout)
        val search = findViewById<Button>(R.id.search)
        val update = findViewById<Button>(R.id.update)

        show = findViewById<TextView>(R.id.show)
        val uid = findViewById<TextView>(R.id.uid)
        val name = findViewById<TextView>(R.id.name)
        val pass = findViewById<TextView>(R.id.pass)
        val username = findViewById<EditText>(R.id.username)

        user1 = intent.getStringExtra("user1").toString()
        pass1 = intent.getStringExtra("pass1").toString()

        val userOperation = UserOperation(this)

        var cursor1 = userOperation.login(user1, pass1)

        if(cursor1.moveToFirst()){
            uid.setText(cursor1.getString(0)).toString()
            name.setText(cursor1.getString(1))
            pass.setText(cursor1.getString(2))
        }



        val data = userOperation.readAllData()

        showAllData(data)

        /* search.setOnClickListener {
             Toast.makeText(this, "search ${username.text.toString()}", Toast.LENGTH_SHORT).show()
             val user = userOperation.getUserbyId(username.text.toString().trim())
             info.append(user.Id.toString() + "    " + user.Username + "    " + user.Password)
         }*/

        search.setOnClickListener{
            val cursor = userOperation.searchUser(username.text.toString().trim())
            if(cursor.moveToFirst()){
                uid.setText(cursor.getString(0)).toString()
                name.setText(cursor.getString(1))
                pass.setText(cursor.getString(2))
            }else{
                Toast.makeText(this, "No data", Toast.LENGTH_LONG).show()
            }
        }

        update.setOnClickListener {
            var i = Intent(this, UpdateActivity::class.java)
            i.putExtra("user1", user1)
            i.putExtra("pass1", pass1)
            startActivity(i)
        }

        logout.setOnClickListener {
            var i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }
    fun showAllData(data: List<Users>){
        var s: String = ""
        for (i in 0 until data.size) {
            s += data[i].Id.toString() + "    " + data[i].Username + "    " + data[i].Password + "\n"
        }
        show.text = s
    }
    fun showAllData2(data: List<Users>){
        show.text = ""
        for (i in 0 until data.size) {
            show.append(
                data[i].Id.toString() + "    " + data[i].Username + "    " + data[i].Password + "\n"
            )
        }
    }
}