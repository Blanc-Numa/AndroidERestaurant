package fr.isen.blanc.androiderestaurant

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.blanc.androiderestaurant.model.UserModel
import org.json.JSONObject
import java.io.File

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        val msg =  File(cacheDir.absolutePath+"/inBacket.json")
        val idUser = this.getSharedPreferences(getString(R.string.app_prefs), Context.MODE_PRIVATE).getInt(getString(R.string.spUserId), 0)

        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/user/order"
        val jsonObject = JSONObject()
        jsonObject.put("id_shop",1)
        jsonObject.put("id_user", idUser)
        jsonObject.put("msg", msg)

        val request = JsonObjectRequest(
            Request.Method.POST,url,jsonObject,
            { response ->
                var gson= Gson()
                var newAccountResult = gson.fromJson(response.toString(), UserModel::class.java)
                Log.d("","$response")

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
