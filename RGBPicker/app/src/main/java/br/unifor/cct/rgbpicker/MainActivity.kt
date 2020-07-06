package br.unifor.cct.rgbpicker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private lateinit var collorView: View

    private lateinit var seekBarColor: SeekBar
    private lateinit var seekBarColorRed: SeekBar
    private lateinit var seekBarColorBlue: SeekBar
    private lateinit var seekBarColorGreen: SeekBar

    private var color = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        collorView = findViewById(R.id.main_viewer_collor)
        seekBarColor = findViewById(R.id.main_seekBar_collor)
        seekBarColorRed = findViewById(R.id.main_seekBar_collorRed)
        seekBarColorGreen = findViewById(R.id.main_seekBar_collorGreen)
        seekBarColorBlue = findViewById(R.id.main_seekBar_collorBlue)

        seekBarColor.setOnSeekBarChangeListener(this)
        seekBarColorRed.setOnSeekBarChangeListener(this)
        seekBarColorGreen.setOnSeekBarChangeListener(this)
        seekBarColorBlue.setOnSeekBarChangeListener(this)

    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when(seekBar?.id)    {
           R.id.main_seekBar_collor ->  {
               collorView.setBackgroundColor(Color.argb(seekBar.progress,main_seekBar_collorRed.progress,main_seekBar_collorGreen.progress,main_seekBar_collorBlue.progress))
           }

           R.id.main_seekBar_collorRed ->  {
               collorView.setBackgroundColor(Color.argb(seekBar.progress,main_seekBar_collorRed.progress,main_seekBar_collorGreen.progress,main_seekBar_collorBlue.progress))           }

           R.id.main_seekBar_collorGreen ->  {
               collorView.setBackgroundColor(Color.argb(seekBar.progress,main_seekBar_collorRed.progress,main_seekBar_collorGreen.progress,main_seekBar_collorBlue.progress))           }

           R.id.main_seekBar_collorBlue ->  {
               collorView.setBackgroundColor(Color.argb(seekBar.progress,main_seekBar_collorRed.progress,main_seekBar_collorGreen.progress,main_seekBar_collorBlue.progress))           }
       }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }
}
