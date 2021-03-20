package ruzanna.game.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var reset = true
        var comma = true

        val res = findViewById<TextView>(R.id.result)
        val show = findViewById<TextView>(R.id.show)
        findViewById<Button>(R.id.c).setOnClickListener {
            res.text = ""
            show.text = ""
            reset = true
            comma = true
        }
        findViewById<Button>(R.id.x).setOnClickListener {
            var s = res.text.toString()
            if (s.isNotEmpty()){
                val endSym = s[s.length-1]
                if (endSym == '.') comma = true
                else if (endSym == ' ') s = s.dropLast(2)
                res.text = s.dropLast(1)
            }else{
                reset = true
                comma = true
            }
        }
        findViewById<Button>(R.id.add).setOnClickListener {
            if(reset){
                res.text = "+"
                reset = false
            }else {
                var s = res.text.toString()
                when {
                    s[s.length - 1] == '.' -> {
                        res.text = "${s}0 + "
                        comma = true
                    }
                    s[s.length - 1] == '+' -> {
                        comma = true
                    }
                    s[s.length - 1] == '-' -> {
                        res.text = "+"
                        comma = true
                    }
                    s[s.length - 1] != ' ' -> {
                        res.text = "$s + "
                        comma = true
                    }
                    else -> {
                        s = s.dropLast(3)
                        res.text = "$s + "
                        comma = true
                    }
                }
            }
        }
        findViewById<Button>(R.id.min).setOnClickListener {
            if(reset){
                res.text = "-"
                reset = false
            }else{
                var s = res.text.toString()
                when {
                    s[s.length - 1] == '.' -> {
                        res.text = "${s}0 - "
                        comma = true
                    }
                    s[s.length - 1] == '-' -> {
                        comma = true
                    }
                    s[s.length - 1] == '+' -> {
                        res.text = "-"
                        comma = true
                    }
                    s[s.length - 1] != ' ' -> {
                        res.text = "$s - "
                        comma = true
                    }
                    else -> {
                        s = s.dropLast(3)
                        res.text = "$s - "
                        comma = true
                    }
                }
            }
        }
        findViewById<Button>(R.id.mult).setOnClickListener {
            if(! reset) {
                var s = res.text.toString()
                when {
                    s[s.length - 1] == '.' -> {
                        res.text = "${s}0 × "
                        comma = true
                    }
                    s[s.length - 1] == '-' || s[s.length - 1] == '+' -> {
                        comma = true
                    }
                    s[s.length - 1] != ' ' -> {
                        res.text = "$s × "
                        comma = true
                    }
                    else -> {
                        s = s.dropLast(3)
                        res.text = "$s × "
                        comma = true
                    }
                }
            }
        }
        findViewById<Button>(R.id.div).setOnClickListener{
            if(!reset){
                var s = res.text.toString()
                when {
                    s[s.length - 1] == '.' -> {
                        res.text = "${s}0 ÷ "
                        comma = true
                    }
                    s[s.length - 1] == '-' || s[s.length - 1] == '+' -> {
                        comma = true
                    }
                    s[s.length - 1] != ' ' -> {
                        res.text = "$s ÷ "
                        comma = true
                    }
                    else -> {
                        s = s.dropLast(3)
                        res.text = "$s ÷ "
                        comma = true
                    }
                }
            }
        }
        findViewById<Button>(R.id.percent).setOnClickListener{
            var s = res.text.toString()
            if(s.isNotEmpty() && s[s.length-1] == ' '){
                s = s.dropLast(3)
            }
            val stringArr = s.split(" ")
            when (stringArr.size) {
                1 -> {
                    s = "$s ÷ 100"
                    val newStringArr = s.split(" ")
                    show.text = "$s = ${calculatorCall(newStringArr)}"
                    res.text = ""
                }
                3 -> {
                    reset = true
                    val newStr = if(stringArr[0] == "÷" || stringArr[0] == "×"){
                        val newStrList = listOf(stringArr[2], "÷", "100")
                        calculatorCall(newStrList)
                    } else{
                        val newStrList = listOf(stringArr[2], "÷", "100","×", stringArr[0])
                        calculatorCall(newStrList)
                    }
                    val newStringArr = listOf(stringArr[0], stringArr[1], newStr)
                    show.text = "$s % ${calculatorCall(newStringArr)}"
                    res.text = ""
                }
                else -> {
                    show.text = "Too complicated operation."
                    res.text = ""
                }
            }
        }
        findViewById<Button>(R.id.dot).setOnClickListener{
            if(reset){
                res.text = "0."
                reset = false
                comma = false
            }else if (comma) {
                val s = res.text.toString()
                if(s[s.length-1] == ' ')
                    res.text = s + "0."
                else res.text = "$s."
                comma = false
            }
        }
        findViewById<Button>(R.id.equals).setOnClickListener {
            var s = res.text.toString()
            if(s.isNotEmpty() && s[s.length-1] == ' '){
                s = s.dropLast(3)
            }
            val stringArr = s.split(" ")
            if(stringArr.size == 1){
                res.text = s
            }else{
                reset = true
                show.text = "$s = ${calculatorCall(stringArr)}"
                res.text = ""
            }
        }
        findViewById<Button>(R.id.num0).setOnClickListener{
            if(reset){
                res.text = "0"
                reset = false
            }else{
                val s = res.text.toString()
                val stringArr = s.split(" ")
                val ss = stringArr[stringArr.size - 1]
                if (!s.matches("^0".toRegex()) && !ss.matches("^0".toRegex()))
                    res.text =  s + "0"
            }
        }
        findViewById<Button>(R.id.num1).setOnClickListener{
            if(reset){
                res.text = "1"
                reset = false
            }else{
                res.text = check0(res.text.toString()) + "1"
            }
        }
        findViewById<Button>(R.id.num2).setOnClickListener{
            if(reset){
                res.text = "2"
                reset = false
            }else{
                res.text = check0(res.text.toString()) + "2"
            }
        }
        findViewById<Button>(R.id.num3).setOnClickListener {
            if(reset){
                res.text = "3"
                reset = false
            }else{
                res.text = check0(res.text.toString()) + "3"
            }
        }
        findViewById<Button>(R.id.num4).setOnClickListener{
            if(reset){
                res.text = "4"
                reset = false
            }else{
                res.text = check0(res.text.toString()) + "4"
            }
        }
        findViewById<Button>(R.id.num5).setOnClickListener{
            if(reset){
                res.text = "5"
                reset = false
            }else{
                res.text = check0(res.text.toString()) + "5"
            }
        }
        findViewById<Button>(R.id.num6).setOnClickListener{
            if(reset){
                res.text = "6"
                reset = false
            }else{
                res.text = check0(res.text.toString()) + "6"
            }
        }
        findViewById<Button>(R.id.num7).setOnClickListener{
            if(reset){
                res.text = "7"
                reset = false
            }else{
                res.text = check0(res.text.toString()) + "7"
            }
        }
        findViewById<Button>(R.id.num8).setOnClickListener{
            if(reset){
                res.text = "8"
                reset = false
            }else{
                res.text = check0(res.text.toString()) + "8"
            }
        }
        findViewById<Button>(R.id.num9).setOnClickListener{
            if(reset){
                res.text = "9"
                reset = false
            }else{
                res.text = check0(res.text.toString()) + "9"
            }
        }
    }

    private fun calculatorCall(stringArr: List<String>): String {
        val s = stringArr.size
        return when {
            s == 3 -> {
                try {
                    Log.d("test3", stringArr.toString())
                    calculatorCheckArgO(stringArr[0], stringArr[1].toCharArray()[0], stringArr[2])
                }catch (e:Exception){
                    "ex1"
                }
            }
            s > 4 -> {
                if("×" in stringArr || "÷" in stringArr){
                    Log.d("test4", stringArr.toString())
                    val indexMult = stringArr.indexOf("×")
                    val indexDiv = stringArr.indexOf("÷")
                    var index = indexMult
                    if(index == -1){
                        index = indexDiv
                    }else if (indexDiv != -1 && indexDiv < indexMult){
                        index = indexDiv
                    }
                    val res = calculatorCheckArgO(stringArr[index-1], stringArr[index].toCharArray()[0], stringArr[index+1])
                    val newArr = stringArr.toMutableList()
                    newArr[index] = res
                    newArr.removeAt(index-1)
                    newArr.removeAt(index)
                    calculatorCall(newArr)
                }else {
                    val sArr = mutableListOf(calculatorCheckArgO(stringArr[0], stringArr[1].toCharArray()[0], stringArr[2]))
                    sArr.addAll(stringArr.subList(3, s))
                    calculatorCall(sArr)
                }
            }
            else -> {
                "ex2"
            }
        }
    }

    private fun  calculatorCheckArgO(a: String, ch: Char, b: String): String {
        return if(ch == '+' || ch == '-' || ch == '×'){
            calculator(a.toBigDecimal(), ch, b.toBigDecimal())
        }
        else if(ch == '÷'){
            if (b.toFloat() != 0.toFloat()) calculator(a.toBigDecimal(), ch, b.toBigDecimal()) else "Zero division error"
        }
        else "Unknown operation"
    }

    private fun calculator(a: BigDecimal, c: Char, b: BigDecimal): String {
        return when(c){
            '+' -> (a+b).toString()
            '-' -> (a-b).toString()
            '×' -> (a*b).toString()
            else -> (a.divide(b)).toString()
        }
    }

    private fun check0(s:String): String{
        val stringArr = s.split(" ")
        val ss = stringArr[stringArr.size - 1]
        return if (s.matches("^0".toRegex()) || ss.matches("^0".toRegex())) {
            s.dropLast(1)
        }else s
    }
}