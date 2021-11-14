package com.ouailanwarkamel.technoformatproject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ouailanwarkamel.technoformatproject.R
import com.ouailanwarkamel.technoformatproject.api.Api
import com.ouailanwarkamel.technoformatproject.api.Api.Companion.URL
import com.ouailanwarkamel.technoformatproject.data.ResponseLogin
import kotlinx.android.synthetic.main.activity_signin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)


        btn_sign_up.setOnClickListener {
            val name = edit_name.text.toString().trim()
            val email = edit_email.text.toString().trim()
            val password = edit_password.text.toString().trim()

            if (name.isEmpty()) {
                edtName.error = "Name required"
                edtName.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                edtEmail.error = "Email required"
                edtEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                edtPass.error = "Password required"
                edtPass.requestFocus()
                return@setOnClickListener
            }

            progressBarSu.visibility = View.VISIBLE
            singUp(name, email, password)

        }
        // Go to Login Activity
        edtLogin.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }



    }



    fun singUp(name: String, email: String, password: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api: Api = retrofit.create(Api::class.java)
        val call = api.createUser(name, email, password)

        call.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {

                progressBarSu.visibility = View.GONE
                val responseData = response.body()

                if (responseData?.error == true) {
                    if (responseData.errorCode == 1) {

                        Toast.makeText(application, ""+responseData.error_msg,Toast.LENGTH_LONG).show()

                    } else if(responseData.errorCode == 2) {
                        Toast.makeText(application, ""+responseData.error_msg,Toast.LENGTH_LONG).show()
                    }

                }else if (responseData?.error == false) {
                     Toast.makeText(applicationContext,"Successfully Registered ",Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
               Toast.makeText(this@SigninActivity, "Error ",Toast.LENGTH_SHORT ).show()
            }

        })


    }

}