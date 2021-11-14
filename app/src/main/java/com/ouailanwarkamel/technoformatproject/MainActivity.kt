package com.ouailanwarkamel.technoformatproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ouailanwarkamel.technoformatproject.data.SharedPrefManager
import com.ouailanwarkamel.technoformatproject.ui.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()

        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var user =  SharedPrefManager.getInstance(this).user


        startActivity(Intent(this  , MainActivity2::class.java))

        tv_name.text = user.name.toString()


        btn_logout.setOnClickListener {
            logout()
        }


    }


    private fun logout() {
        SharedPrefManager.getInstance(this).clear()
        val intent = Intent(applicationContext,LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}