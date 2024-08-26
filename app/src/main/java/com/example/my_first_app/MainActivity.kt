package com.example.my_first_app

import android.app.usage.NetworkStats
import android.content.Context
import android.content.DialogInterface
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textfield.TextInputLayout
import java.io.IOException
import kotlin.random.Random

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener{
    private lateinit var refresh:SwipeRefreshLayout
    private lateinit var account: EditText
    private lateinit var password: EditText
    private lateinit var input3:EditText
    private lateinit var login_btn:Button
    private lateinit var result:TextView
    private lateinit var restart:Button
    val positiveButtonClick={
            dialog: DialogInterface,which: Int->
        System.exit(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        account = findViewById(R.id.account)
        password = findViewById(R.id.password)
        input3 = findViewById(R.id.number3)
        login_btn = findViewById(R.id.login)
        result = findViewById(R.id.text)
        restart = findViewById(R.id.restart_btn)
        refresh = findViewById(R.id.swiperefresh)
        refresh.setOnRefreshListener(this)
        NetworkStats()
        login_btn.setOnClickListener{
            var a = account.text.toString()
            var b = password.text.toString()
            var c = input3.text.toString()
            var mediaPlayer = MediaPlayer.create(this,R.raw.music)
            if(isConnect()){
                if(a==""&&b==""&&c==""){
                    Toast.makeText(this, "三個值都沒有填寫", Toast.LENGTH_SHORT).show()
                    result.setText("")
                }
                else if(b==""){
                    Toast.makeText(this, "第二個值沒有填寫", Toast.LENGTH_SHORT).show()
                    result.setText("")
                }
                else if(a==""){
                    Toast.makeText(this, "第一個值沒有填寫", Toast.LENGTH_SHORT).show()
                    result.setText("")
                }
                else if(c==""){
                    Toast.makeText(this,"第三個值沒有填寫",Toast.LENGTH_SHORT).show()
                    result.setText("")
                }
                else if(a.toInt()==0||b.toInt()==0||c.toInt()==0){
                    Toast.makeText(this, "第一個值或第二個值或第三個值不能輸入0", Toast.LENGTH_SHORT).show()
                    result.setText("")
                }
                else if(a.toInt()>b.toInt()){
                    Toast.makeText(this, "第一個值不能大於第二個值", Toast.LENGTH_SHORT).show()
                    result.setText("")
                }
                else if(a.toInt()==b.toInt()){
                    Toast.makeText(this,"第一個值不能等於第二個值",Toast.LENGTH_SHORT).show()
                }
                else if(b.toInt()<=c.toInt()){
                    Toast.makeText(this,"第三個值不能大於第二個值",Toast.LENGTH_SHORT).show()
                }
                else{
                    val d =List(c.toInt()) {Random.nextInt(a.toInt(),b.toInt())}
                    result.setText("第一個值:"+a+"\n第二個值:"+b+"\n隨機數:"+d)
                    mediaPlayer.start()
                    login_btn.visibility=View.GONE
                    restart.visibility=View.VISIBLE
                }
            }
            else{
                CheckNetWork()
                account.visibility = View.GONE
                password.visibility = View.GONE
                input3.visibility = View.GONE
                login_btn.visibility=View.GONE
                restart.visibility=View.GONE
            }

        }

        restart.setOnClickListener {
            if(isConnect()){
                login_btn.visibility=View.VISIBLE
                restart.visibility=View.GONE
                account.setText("")
                password.setText("")
                input3.setText("")
                result.setText("")
            }
            else{
                CheckNetWork()
                account.visibility = View.GONE
                password.visibility = View.GONE
                input3.visibility = View.GONE
                login_btn.visibility=View.GONE
                result.visibility=View.GONE
                restart.visibility=View.GONE
            }

        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            ConfirmExit()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
    fun ConfirmExit(){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("你是否要離開?")
        alertDialog.setPositiveButton("是",positiveButtonClick)
        alertDialog.setNegativeButton("否",null)
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun isConnect():Boolean{
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if(networkInfo != null && networkInfo.isConnected){
            return  true
        }
        return false
    }

    fun CheckNetWork(){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("請先檢查網路是否連線正常")
        alertDialog.setPositiveButton("離開app",positiveButtonClick)
        alertDialog.setNegativeButton("請重新整理",null)
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    fun NetworkStats(){
        if (isConnect()){
            account.visibility = View.VISIBLE
            password.visibility = View.VISIBLE
            input3.visibility = View.VISIBLE
            login_btn.visibility = View.VISIBLE
            result.visibility = View.VISIBLE
            account.setText("")
            password.setText("")
            input3.setText("")
            result.setText("")
        }else{
            CheckNetWork()
            account.visibility = View.GONE
            password.visibility = View.GONE
            input3.visibility = View.GONE
            login_btn.visibility = View.GONE
            result.visibility = View.GONE
            restart.visibility = View.GONE
        }
    }

    override fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            Toast.makeText(this,"更新成功~",Toast.LENGTH_SHORT).show()
            NetworkStats()
            refresh.isRefreshing=false
        },300)
    }
}


