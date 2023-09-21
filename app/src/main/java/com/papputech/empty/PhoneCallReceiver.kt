
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast
import com.papputech.empty.AndroidTV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
class PhoneCallReceiver : BroadcastReceiver() {

    private var wasRinging = false

    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)

        if (TelephonyManager.EXTRA_STATE_RINGING == state) {
            wasRinging = true
            makeApiCall(context, "API call on ringing successful",1)
        } else if (TelephonyManager.EXTRA_STATE_IDLE == state && wasRinging ) {
            wasRinging = false
            makeApiCall(context, "API call on call end successful",0)
        }
    }

    private fun makeApiCall(context: Context, successMessage: String,pause: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.10:5050/")
                .build()

            val api = retrofit.create(AndroidTV::class.java)
            val response: Response<Unit>? = null;
            if (pause == 1){
                val response = api.pauseTV()
            }
            else {
                val response = api.playTV()
            }
            if (response!!.isSuccessful) {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }


}