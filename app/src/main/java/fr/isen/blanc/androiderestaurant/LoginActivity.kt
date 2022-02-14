package fr.isen.blanc.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.blanc.androiderestaurant.databinding.ActivityLoginBinding
import fr.isen.blanc.androiderestaurant.model.UserModel
import org.json.JSONObject

class LoginActivity : MenuActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textCreateAccountClick.setOnClickListener{

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener{
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            val queue = Volley.newRequestQueue(this)
            val url = "http://test.api.catering.bluecodegames.com/user/login"
            val jsonObject = JSONObject()
            jsonObject.put("id_shop",1)
            jsonObject.put("email", email)
            jsonObject.put("password", password)

            val request = JsonObjectRequest(
                Request.Method.POST,url,jsonObject,
                { response ->
                    var gson= Gson()
                    var newAccountResult = gson.fromJson(response.toString(), UserModel::class.java)
                    Log.d("","$response")
                    //faire qqc de la requete
                    val idClient = newAccountResult.id
                    val sharedPreferences = getSharedPreferences(getString(R.string.app_prefs), MODE_PRIVATE)
                    sharedPreferences.edit().putInt(getString(R.string.spUserId), idClient).apply()

                }, {
                    // Error in request
                    Log.e( "","Volley error: $it")
                })

            // Volley request policy, only one time request to avoid duplicate transaction
            request.retryPolicy = DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                // 0 means no retry
                0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

            // Add the volley post request to the request queue
            queue.add(request)
        }



    }
}