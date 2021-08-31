package com.zero.simasterid

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.zero.simasterid.databinding.ActivityMainBinding
import com.zero.simasterid.db.Sessions
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val sessions: Sessions by lazy {
        Sessions(this)
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (checkRoot()) {
            binding.btnGetAccess.setOnClickListener {

                val runtime = Runtime.getRuntime()
                val p =
                    runtime.exec("su -c cat /data/data/id.ac.ugm.simaster/shared_prefs/SIMASTER.xml")
                val standardIn = BufferedReader(
                    InputStreamReader(p.inputStream)
                )


                val response = standardIn
                    .readText()
                    .replace("&quot;", "")

                Log.e("Data", response)
                if (response != ""){
                    val userAgent = System.getProperty("http.agent")

                    val name = response
                        .split("<string name=\"user_nama_lengkap\">")[1]
                        .split("</string>")[0]

                    val groupID = response
                        .split("<string name=\"group_menu\">")[1]
                        .split("</string>")[0]

                    val deviceID = response
                        .split("<string name=\"bearer\">")[1]
                        .split("</string>")[0]

                    binding.tvName.text = name
                    binding.tvIDDevice.text = "Device ID : $deviceID"
                    binding.tvIDGroup.text = "Group ID : $groupID"
                    binding.tvUserAgent.text = userAgent
                }else{
                    binding.tvIDDevice.text = "Simaster not found or you are not logged in!"
                }

            }
        } else {
            binding.tvIDDevice.text = "Your device is not rooted !"
        }
    }


    private fun checkRoot(): Boolean {
        var z = true
        val process: Process? = null
        return try {
            val exec = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            if (BufferedReader(InputStreamReader(exec!!.inputStream)).readLine() != null) {
                z = false
            }
            Log.e("Root", BufferedReader(InputStreamReader(exec.inputStream)).readLine())
            exec.destroy()
            z
        } catch (unused: Throwable) {
            process?.destroy()
            true
        }
    }
}