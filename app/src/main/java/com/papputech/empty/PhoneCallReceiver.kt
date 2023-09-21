
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast
import com.papputech.empty.AndroidTV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class PhoneCallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        if (TelephonyManager.EXTRA_STATE_RINGING == state) {
            CoroutineScope(Dispatchers.IO).launch {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.1.10:5050/")
                    .build()

                val api = retrofit.create(AndroidTV::class.java)
                val response = api.pnpTV()
                if (response.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(context, " TV API call successful", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(context, " TV API call not successful", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
