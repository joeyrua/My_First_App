package com.example.my_first_app

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import java.io.IOException
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var account: EditText
    private lateinit var password: EditText
    private lateinit var login_btn:Button
    private lateinit var result:TextView
    val positiveButtonClick={
            dialog: DialogInterface,which: Int->
        System.exit(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        account = findViewById(R.id.account)
        password = findViewById(R.id.password)
        login_btn = findViewById(R.id.login)
        result = findViewById(R.id.text)


        login_btn.setOnClickListener{

            var a = account.text.toString()
            var b = password.text.toString()
             if(a==""&&b==""){
                 Toast.makeText(this, "兩個值都沒有填寫", Toast.LENGTH_SHORT).show()
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
            else if(a.toInt()==0||b.toInt()==0){
                 Toast.makeText(this, "第一個值或第二個值不能輸入0", Toast.LENGTH_SHORT).show()
                 result.setText("")
             }
            else if(a.toInt()>b.toInt()){
                 Toast.makeText(this, "第一個值不能大於第二個值", Toast.LENGTH_SHORT).show()
                 result.setText("")
             }
            else{
                val c =List(5) {Random.nextInt(a.toInt(),b.toInt())}
                 result.setText("第一個值:"+a+"\n第二個值:"+b+"\n隨機數:"+c)
             }
            //result.setText(a+"\n"+b)
            //Toast.makeText(this,a+"\n"+b,Toast.LENGTH_SHORT).show()
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
}


