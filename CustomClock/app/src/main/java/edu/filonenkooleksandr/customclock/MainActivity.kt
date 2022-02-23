package edu.filonenkooleksandr.customclock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.filonenkooleksandr.customclock.basic_clock.AnalogClockActivity
import edu.filonenkooleksandr.customclock.custom_analog_clock.CustomClockActivity
import edu.filonenkooleksandr.customclock.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.analogClock.setOnClickListener {
            startActivity(Intent(this, AnalogClockActivity::class.java))
        }
        binding.customizingClock.setOnClickListener {
            startActivity(Intent(this, CustomClockActivity::class.java))
        }
    }
}