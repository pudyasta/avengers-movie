package com.example.uts

import android.annotation.SuppressLint
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.uts.databinding.ActivityPurchaseBinding
import java.text.DateFormat
import java.util.*

class PurchaseActivity : AppCompatActivity() ,OnDateSetListener{
    private val tickets = arrayOf(
        "Regular 2D",
        "Velvet 2D",
        "VIP 2D",

    )
    private val bioskops = arrayOf(
        "Ambarrukmo XXI",
        "Expire XXI",
        "Jwalk CGV"
    )
    private val payments = arrayOf(
        "Bank Transfer",
        "Virtual Account",
        "I-Banking"
    )

    private val banks = arrayOf(
        "BNI",
        "BCA",
        "Mandiri",
        "BSI",
        "BRI",
    )

    private var dates= ""
    private var harga= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPurchaseBinding.inflate(layoutInflater)
        val title = intent.getStringExtra("title")
        val poster = intent.getIntExtra("poster",0)

        with(binding){
            poster1.setImageResource(poster)
            btPickDate.setOnClickListener {
                val mDatePickerDialogFragment: com.example.uts.DatePicker = DatePicker()
                mDatePickerDialogFragment.show(supportFragmentManager, "DATE PICK")
            }
            val adapterSeat = ArrayAdapter(this@PurchaseActivity,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,tickets)
            adapterSeat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSeat.adapter= adapterSeat
            spinnerSeat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedSeat = tickets[position]
                    when (selectedSeat) {
                        "Regular 2D" -> {
                            harga = "Rp 35.000"
                        }
                        "Velvet 2D" -> {
                            harga = "Rp 55.000"
                        }
                        "VIP 2D" -> {
                            harga = "Rp 90.000"
                        }
                    }
                    seatType.text = spinnerSeat.selectedItem.toString()
                    seatPrice.text =harga
                    seatPrice2.text =harga
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }


            val adapterBioskop = ArrayAdapter(this@PurchaseActivity,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,bioskops)
            adapterBioskop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerBioskop.adapter= adapterBioskop

            val adapterPayment = ArrayAdapter(this@PurchaseActivity,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,payments)
            adapterPayment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPayment.adapter= adapterPayment

            val adapterBank = ArrayAdapter(this@PurchaseActivity,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,banks)
            adapterBank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerBank.adapter= adapterBank

            submit.setOnClickListener {
                val intent= Intent(this@PurchaseActivity,ResultActivity::class.java)
                intent.putExtra("title",title)
                intent.putExtra("poster",poster)
                intent.putExtra("bioskop",spinnerBioskop.selectedItem.toString())
                intent.putExtra("seat",spinnerSeat.selectedItem.toString())
                intent.putExtra("bank",spinnerBank.selectedItem.toString())
                intent.putExtra("payment",spinnerPayment.selectedItem.toString())
                intent.putExtra("price",harga)
                intent.putExtra("date",dates)
                startActivity(intent)
                finish()

            }
        }
        setContentView(binding.root)
    }

    override fun onDateSet(
        view: android.widget.DatePicker,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {
        val mCalendar = Calendar.getInstance()
        mCalendar[Calendar.YEAR] = year
        mCalendar[Calendar.MONTH] = month
        mCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        val selectedDate = DateFormat.getDateInstance(DateFormat.LONG).format(mCalendar.time)
        val text : TextView = findViewById(R.id.setTanggal)
        text.text = selectedDate
        dates = selectedDate
    }
}