package ru.kavunov.runnotebook

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.math.BigDecimal


class CalculatorFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view = inflater.inflate(R.layout.fragment_calculator, container, false)

        view.findViewById<Button>(R.id.button_result).setOnClickListener {
            try {
                val result = view.findViewById<TextView>(R.id.itog)
                var kilometr = proverka(view.findViewById<TextView>(R.id.km).text.toString())
                var metr = proverka(view.findViewById<TextView>(R.id.metr).text.toString())
                var hour = proverka(view.findViewById<TextView>(R.id.hour).text.toString())
                var minuts = proverka(view.findViewById<TextView>(R.id.minutes).text.toString())
                var second = proverka(view.findViewById<TextView>(R.id.second).text.toString())

                val dist_metr = kilometr.toInt()*1000 + metr.toInt()
                val time_sek = hour.toInt()*3600 + minuts.toInt()*60 + second.toInt()
                result.visibility = View.VISIBLE
                if(dist_metr == 0) result.text = "Введите расстояние"
                else if(time_sek == 0)result.text = "Введите Время"
                else {
                    val sek_km = (time_sek.toDouble() / dist_metr.toDouble()) * 1000
                    val min_v_km = (sek_km % 60) / 100
                    result.text =
                        (sek_km.toInt() / 60).toString() + ":" + BigDecimal(min_v_km.toString()).setScale(
                            2,
                            BigDecimal.ROUND_HALF_UP
                        ).toString().substring(2)
                }
            }
            catch (e: Exception) {
                view.findViewById<TextView>(R.id.itog).text = "Проверьте введенные данные"
            }


        }


        return view
    }

    fun proverka(x: String): String {
        var y = ""
        if(x == "") y = "0"
        else y = x
        return y
    }

}