package com.rikkei.tra_02t01_13keyevent

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var buttonList: MutableList<Button>? = null
    private val code = "KEYCODE_"
    private var text: String = ""
    private var indexControl = 0
    private val up = "KEYCODE_DPAD_UP"
    private val down = "KEYCODE_DPAD_DOWN"
    private val left = "KEYCODE_DPAD_LEFT"
    private val right = "KEYCODE_DPAD_RIGHT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        buttonList = mutableListOf(
            btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0,
            btnQ, btnW, btnE, btnR, btnT, btnY, btnU, btnI, btnO, btnP,
            btnA, btnS, btnD, btnF, btnG, btnH, btnJ, btnK, btnL, btnSmaller,
            btnZ, btnX, btnC, btnV, btnB, btnN, btnM, btnBigger, btnDEL
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        val keyCodeEvent = event?.keyCode?.let { KeyEvent.keyCodeToString(it) }
        keyCodeEvent?.let { checkEventButton(it, event.action) }
        return super.dispatchKeyEvent(event)
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkEventButton(keyCodeEvent: String, action: Int) {
        for (btn in buttonList!!) {
            val keyButton = btn.text.toString()
            if ((code + keyButton) == keyCodeEvent) {
                indexControl = buttonList!!.indexOf(btn)

                if (action == KeyEvent.ACTION_DOWN) {
                    text += keyButton
                    tvData.text = text
                    btn.setBackgroundColor(Color.BLUE)
                } else {
                    Handler().postDelayed({
                        btn.setBackgroundResource(R.color.btnColor)
                    }, 500)
                }
            }
        }

        if (keyCodeEvent == "KEYCODE_DPAD_UP" || keyCodeEvent == "KEYCODE_DPAD_DOWN"
            || keyCodeEvent == "KEYCODE_DPAD_LEFT" || keyCodeEvent == "KEYCODE_DPAD_RIGHT"
        ) {
            if (action == KeyEvent.ACTION_DOWN) {
                indexControl = control(keyCodeEvent)
                buttonList!![indexControl].setBackgroundColor(Color.BLUE)
            } else {
                Handler().postDelayed({
                    buttonList!![indexControl].setBackgroundResource(R.color.btnColor)
                }, 500)
            }
        }
        if (keyCodeEvent == "KEYCODE_DPAD_CENTER") {
            if (action == KeyEvent.ACTION_DOWN) {
                text += buttonList!![indexControl].text
                tvData.text = text
                buttonList!![indexControl].setBackgroundColor(Color.BLUE)
            } else {
                Handler().postDelayed({
                    buttonList!![indexControl].setBackgroundResource(R.color.btnColor)
                }, 500)
            }
        }
    }

    private fun control(keyCodeEvent: String): Int {

        return when (keyCodeEvent) {
            up -> {
                setUp()
            }
            down -> {
                setDown()
            }
            left -> {
                setLeft()
            }
            right -> {
                setRight()
            }
            else -> indexControl
        }

    }

    private fun setRight(): Int {
        return if (indexControl < buttonList?.size!! - 1) {
            indexControl += 1
            indexControl
        } else {
            indexControl
        }

    }

    private fun setLeft(): Int {
        return if (indexControl > 0) {
            this.indexControl -= 1
            indexControl
        } else {
            indexControl
        }
    }

    private fun setDown(): Int {
        return when (indexControl) {
            in 0..28 -> {
                this.indexControl += 10
                indexControl
            }
            29 -> {
                this.indexControl += 9
                indexControl
            }
            else -> {
                indexControl
            }
        }

    }

    private fun setUp(): Int {
        return when (indexControl) {
            in 0..9 -> {
                indexControl
            }
            else -> {
                this.indexControl = indexControl - 10
                indexControl
            }
        }
    }
}

