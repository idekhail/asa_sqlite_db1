package com.idekhail.asa_sqlite_db1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class UpdateActivity : AppCompatActivity() {

    lateinit var user1: String
    lateinit var pass1: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val uid = findViewById<TextView>(R.id.uid)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)

        val update= findViewById<Button>(R.id.update)
        val back = findViewById<Button>(R.id.back)

        user1 = intent.getStringExtra("user1").toString()
        pass1 = intent.getStringExtra("pass1").toString()

        val userOperation = UserOperation(this)

        var cursor1 = userOperation.login(user1, pass1)

        if(cursor1.moveToFirst()){
            uid.setText(cursor1.getString(0)).toString()
            username.setText(cursor1.getString(1))
            password.setText(cursor1.getString(2))
        }


// Create User
        update.setOnClickListener {
            if(username.text.toString().isEmpty() || password.text.toString().isEmpty()){
                Toast.makeText(this, "fields cannot be empty", Toast.LENGTH_SHORT).show()
            }else{
                val user = UserOperation(this)
                user.updateUser(cursor1.getString(0).toInt(), username.text.toString().trim(), password.text.toString().trim())
                var i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }
        }

        back.setOnClickListener{
            var i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
            }
}