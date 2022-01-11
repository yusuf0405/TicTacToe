package com.example.tictac

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tictac.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    var game: Array<Array<Int>>? = null
    var buttonGame: Array<Button?>? = null
    var turn = false // fasle = X || true = o
    private var score_x: Int = 0
    private var score_O: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        play()
        binding.restart.setOnClickListener {
            restart()
        }
    }

    private fun play() {
        buttonGame = arrayOf(
            binding.a1,
            binding.a2,
            binding.a3,
            binding.b1,
            binding.b2,
            binding.b3,
            binding.c1,
            binding.c2,
            binding.c3
        )
        game = arrayOf( //   1=x || 2= o
            arrayOf(0, 0, 0),  //a1,a2,a3
            arrayOf(0, 0, 0),  //b1,b2,b3
            arrayOf(0, 0, 0)   //c1,c2,c3
        )
        buttonGame?.forEach {
            it?.setOnClickListener {
                checkTrun(turn, it as Button)
                it.isClickable = false
            }

        }

    }

    @SuppressLint("ResourceAsColor")
    fun checkTrun(t: Boolean, b: Button) {
        turn = if (!t) {
            b.text = "X"
            case(b, 1)
            gameEnd(1)
            binding.txtOsc.setTextColor(Color.parseColor("#DB0C0C"))
            binding.txtXsc.setTextColor(Color.parseColor("#BEBEBE"))
            true
        } else {
            b.text = "O"
            case(b, 2)
            gameEnd(2)
            binding.txtXsc.setTextColor(Color.parseColor("#DB0C0C"))
            binding.txtOsc.setTextColor(Color.parseColor("#BEBEBE"))
            false
        }
    }

    private fun case(b: Button, sta: Int) {
        when (b) {
            binding.a1 -> game!![0][0] = sta
            binding.a2 -> game!![0][1] = sta
            binding.a3 -> game!![0][2] = sta
            binding.b1 -> game!![1][0] = sta
            binding.b2 -> game!![1][1] = sta
            binding.b3 -> game!![1][2] = sta
            binding.c1 -> game!![2][0] = sta
            binding.c2 -> game!![2][1] = sta
            binding.c3 -> game!![2][2] = sta

        }
    }

    private fun gameEnd(sta: Int) {
        var win = 0

        if (game!![0][0] == sta && game!![0][1] == sta && game!![0][2] == sta) {// a1 a2 a3
            setColorWin(binding.a1, binding.a2, binding.a3)
            win = sta
        }
        if (game!![1][0] == sta && game!![1][1] == sta && game!![1][2] == sta) {// b1 b2 b3
            setColorWin(binding.b1, binding.b2, binding.b3)
            win = sta
        }
        if (game!![2][0] == sta && game!![2][1] == sta && game!![2][2] == sta) {// b1 b2 b3
            setColorWin(binding.c1, binding.c2, binding.c3)
            win = sta
        }
        /*-----------------------------------------------------------------------------------------*/
        if (game!![0][0] == sta && game!![1][0] == sta && game!![2][0] == sta) {// a1 b1 c1
            setColorWin(binding.a1, binding.b1, binding.c1)
            win = sta
        }
        if (game!![0][1] == sta && game!![1][1] == sta && game!![2][1] == sta) {// a2 b2 c2
            setColorWin(binding.a2, binding.b2, binding.c2)
            win = sta
        }
        if (game!![0][2] == sta && game!![1][2] == sta && game!![2][2] == sta) {// a3 b3 c3
            setColorWin(binding.a3, binding.b3, binding.c3)
            win = sta
        }
        /*------------------------------------------------------------------------------------------*/
        if (game!![0][0] == sta && game!![1][1] == sta && game!![2][2] == sta) {// a1 b2 c3
            setColorWin(binding.a1, binding.b2, binding.c3)
            win = sta
        }
        if (game!![0][2] == sta && game!![1][1] == sta && game!![2][0] == sta) {// c1 b2 a3
            setColorWin(binding.c1, binding.b2, binding.a3)
            win = sta
        }

        if (win > 0) {
            if (win == 1) {
                dialogPlayerWin("X")
                score_x += 1
            } else {
                dialogPlayerWin("O")
                score_O += 1
            }
            gameOver()
        }
    }


    private fun gameOver() {
        buttonGame?.forEach { it?.isClickable = false }
        binding.scoreX.text = score_x.toString()
        binding.score0.text = score_O.toString()


    }

    private fun setColorWin(button1: Button, button2: Button, button3: Button) {

        button1.setBackgroundColor(Color.parseColor("#FF5722"))
        button2.setBackgroundColor(Color.parseColor("#FF5722"))
        button3.setBackgroundColor(Color.parseColor("#FF5722"))
    }

    private fun dialogPlayerWin(win: String) {
        var alertDialogMenu: AlertDialog
        val inflater: LayoutInflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.item_win, null)
        val txtwin: TextView = dialogView.findViewById(R.id.playerwin)
        txtwin.text = win

        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        dialogBuilder.setOnDismissListener { }
        dialogBuilder.setView(dialogView)

        alertDialogMenu = dialogBuilder.create()
        alertDialogMenu.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        alertDialogMenu.show()
    }

    private fun restart() {
        buttonGame?.forEach {
            it?.isClickable = true
            it?.text = ""
            it?.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
            it?.setBackgroundResource(R.drawable.ripple_effect)
        }
        turn = false
        binding.txtXsc.setTextColor(Color.parseColor("#DB0C0C"))
        binding.txtOsc.setTextColor(Color.parseColor("#BEBEBE"))
        play()
    }


}