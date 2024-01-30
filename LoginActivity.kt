package com.idekhail.asa_sqlite_db1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)

        val login = findViewById<Button>(R.id.login)
        val clear = findViewById<Button>(R.id.clear)
        val close = findViewById<Button>(R.id.close)
        val create = findViewById<Button>(R.id.create)


        // Login
        login.setOnClickListener {
            if(username.text.toString().isEmpty() || password.text.toString().isEmpty()){
                Toast.makeText(this, "fields cannot be empty", Toast.LENGTH_SHORT).show()
            }

            else{
                val userOperation= UserOperation(this)

                var cursor = userOperation.login(username.text.toString().trim(),password.text.toString().trim())

                if (cursor.moveToFirst()){
                    var user1 =  cursor.getString(1)
                    var pass1 =  cursor.getString(2)

                    var i = Intent(this, ShowActivity::class.java)
                   i.putExtra("user1", user1)
                   i.putExtra("pass1", pass1)

                    startActivity(i)
                }else
                    Toast.makeText(this, "username or password is wrong", Toast.LENGTH_SHORT).show()
            }
        }


        create.setOnClickListener{
            var i = Intent(this, CreateActivity::class.java)
            startActivity(i)
        }

        clear.setOnClickListener{
            username.text.clear()
            password.text.clear()
        }

        close.setOnClickListener{
            System.exit(-1)
        }

    }
}