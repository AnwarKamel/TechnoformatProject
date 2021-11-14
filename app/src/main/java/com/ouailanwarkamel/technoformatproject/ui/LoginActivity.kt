package com.ouailanwarkamel.technoformatproject.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ouailanwarkamel.technoformatproject.MainActivity
import com.ouailanwarkamel.technoformatproject.R
import com.ouailanwarkamel.technoformatproject.api.Api
import com.ouailanwarkamel.technoformatproject.data.ResponseLogin
import com.ouailanwarkamel.technoformatproject.data.SharedPrefManager
import com.ouailanwarkamel.technoformatproject.data.UserDataInfo
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {


    override fun onStart() {
        super.onStart()

        if (SharedPrefManager.getInstance(this).isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        btn_login.setOnClickListener {
            val email = edit_email.text.toString().trim()
            val password = edit_password.text.toString().trim()

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
            signIn(email, password)

        }

        // // Go to SingUp Activity
        edtSignUp.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }

    }


    private fun signIn(email: String, password: String) {

        var retrofit = Retrofit.Builder()
            .baseUrl(Api.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var api: Api = retrofit.create(Api::class.java)

        val call = api.userLogin(email, password)

        call.enqueue(object : Callback<ResponseLogin> {

            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {

                progressBarSu.visibility = View.GONE

                val responseData = response.body()
                Toast.makeText(this@LoginActivity, " " + responseData.toString(), Toast.LENGTH_SHORT).show()

                if (responseData?.error == false) {
                    var id = responseData.id!!.toInt()
                    var name = responseData.name.toString()
                    var email = responseData.email.toString()
                    var pass = responseData.password.toString()
                    var created_at = responseData.createdAt.toString()

                    Toast.makeText( this@LoginActivity, " " + responseData.toString(), Toast.LENGTH_SHORT).show()
                    SharedPrefManager.getInstance(applicationContext)
                        .saveUser(UserDataInfo(id, name, email, pass, created_at))

                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else if (responseData?.error == true) {


                    if (responseData.errorCode == 5) {
                        Toast.makeText(this@LoginActivity, "Login credentials are wrong. Please try again!", Toast.LENGTH_SHORT).show()
                    }

                }

            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                progressBarSu.visibility = View.GONE
                Toast.makeText(this@LoginActivity, "error " + t.message, Toast.LENGTH_SHORT).show()
            }

        })


    }


}