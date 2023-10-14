package com.shariati.p2calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.shariati.p2calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onTextChanged()
        onNumberClicked()
        onOperatorClicked()


}

    private var isAcStateAc = true
    private var isParenthesesOpen = false
    private var parenthesesCounter = 0
    private var isEqualBtnClicked = false
    private fun onTextChanged() {
        binding.textViewResult?.addTextChangedListener {
            if (binding.textViewResult?.text.toString().isNotEmpty()) {
                binding.btnAc?.text = "C"
                isAcStateAc = false

            } else {
                binding.btnAc?.text = "AC"
                isAcStateAc = true
            }
        }
    }


    private fun onOperatorClicked() {
        binding.btnAc?.setOnClickListener {
            var oldAnswerText = binding.textViewResult?.text.toString()
            if (isAcStateAc) {
                binding.textViewResult?.text = ""
                binding.textViewCalculate?.text = ""
                parenthesesCounter = 0
                isParenthesesOpen = false
                println("hi")
            } else {
                isParenthesesOpen = false
                val oldCalculateText = binding.textViewCalculate?.text.toString()
                val answerTextLength = binding.textViewResult?.length()
                if (binding.textViewCalculate?.text.toString().contains(oldAnswerText)) {
                    binding.textViewResult?.text = ""
                    binding.textViewCalculate?.text = oldCalculateText.substring(
                        0,
                        oldCalculateText.length - answerTextLength!!.toInt()
                    )
                } else {
                    binding.textViewResult?.text = ""
                }

            }
        }
        binding.btnPN?.setOnClickListener {
            try {
                if (binding.textViewResult?.text.toString().isNotEmpty() && (binding.textViewCalculate?.text?.last() != '+' && binding.textViewCalculate?.text?.last() != '-' && binding.textViewCalculate?.text?.last() != '*' && binding.textViewCalculate?.text.toString()
                        .last() != '/')) {
                    val oldResultText = binding.textViewResult?.text
                    val doubleResultPn = ((oldResultText.toString().toDouble()).unaryMinus())
                    val longResultPn = ((oldResultText.toString().toDouble()).unaryMinus()).toLong()
                    var oldCalculateText = binding.textViewCalculate?.text
                    binding.textViewCalculate?.text =
                        oldCalculateText?.substring(
                            0,
                            (oldCalculateText.length) - (oldResultText!!.length)
                        )
                    if (doubleResultPn == longResultPn.toDouble()) {
                        binding.textViewCalculate?.append(longResultPn.toString())
                        binding.textViewResult?.text = longResultPn.toString()
                    } else {
                        binding.textViewCalculate?.append(doubleResultPn.toString())
                        binding.textViewResult?.text = doubleResultPn.toString()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "متن، حاوی کاراکتر های غیر مجاز برای این عمل است",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
        binding.btnPercent?.setOnClickListener {
            try {
                if (binding.textViewResult?.text.toString().isNotEmpty() && (binding.textViewCalculate?.text?.last() != '+' && binding.textViewCalculate?.text?.last() != '-' && binding.textViewCalculate?.text?.last() != '*' && binding.textViewCalculate?.text.toString()
                        .last() != '/')) {
                    val oldResultText = binding.textViewResult?.text
                    val percentResult = (oldResultText.toString().toDouble()) * 0.01
                    var oldCalculateText = binding.textViewCalculate?.text
                    binding.textViewCalculate?.text =
                        oldCalculateText?.substring(
                            0,
                            (oldCalculateText.length) - (oldResultText!!.length)
                        )
                    binding.textViewCalculate?.append(percentResult.toString())
                    binding.textViewResult?.text = percentResult.toString()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "متن، حاوی کاراکتر های غیر مجاز برای این عمل است",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
        binding.btnDivision?.setOnClickListener {
            if (binding.textViewResult?.text.toString()
                    .isNotEmpty() && binding.textViewCalculate?.text.toString().last() != '/'
            ) {
                if (binding.textViewCalculate?.text.toString()
                        .last() == '+' || binding.textViewCalculate?.text.toString()
                        .last() == '-' || binding.textViewCalculate?.text.toString().last() == '*'
                ) {
                    var oldCalculateText = binding.textViewCalculate?.text
                    binding.textViewCalculate?.text =
                        oldCalculateText?.substring(0, (oldCalculateText.length - 1))
                    appendText("/")
                } else {
                    appendText("/")
                }

            }
        }
        binding.btnMultiplication?.setOnClickListener {
            if (binding.textViewResult?.text.toString()
                    .isNotEmpty() && binding.textViewCalculate?.text.toString().last() != '*'
            ) {
                if (binding.textViewCalculate?.text.toString()
                        .last() == '+' || binding.textViewCalculate?.text.toString()
                        .last() == '-' || binding.textViewCalculate?.text.toString().last() == '/'
                ) {
                    var oldCalculateText = binding.textViewCalculate?.text
                    binding.textViewCalculate?.text =
                        oldCalculateText?.substring(0, (oldCalculateText.length - 1))
                    appendText("*")
                } else {
                    appendText("*")
                }

            }
        }
        binding.btnMinuse?.setOnClickListener {
            if (binding.textViewResult?.text.toString()
                    .isNotEmpty() && binding.textViewCalculate?.text.toString().last() != '-'
            ) {
                if (binding.textViewCalculate?.text.toString()
                        .last() == '+' || binding.textViewCalculate?.text.toString()
                        .last() == '*' || binding.textViewCalculate?.text.toString().last() == '/'
                ) {
                    var oldCalculateText = binding.textViewCalculate?.text
                    binding.textViewCalculate?.text =
                        oldCalculateText?.substring(0, (oldCalculateText.length - 1))
                    appendText("-")
                } else {
                    appendText("-")
                }

            }
        }
        binding.btnPlus?.setOnClickListener {
            if (binding.textViewResult?.text.toString()
                    .isNotEmpty() && binding.textViewCalculate?.text.toString().last() != '+'
            ) {
                if (binding.textViewCalculate?.text.toString()
                        .last() == '*' || binding.textViewCalculate?.text.toString()
                        .last() == '-' || binding.textViewCalculate?.text.toString().last() == '/'
                ) {
                    var oldCalculateText = binding.textViewCalculate?.text
                    binding.textViewCalculate?.text =
                        oldCalculateText?.substring(0, (oldCalculateText.length - 1))
                    appendText("+")
                } else {
                    appendText("+")
                }

            }
        }
        binding.btnEqual?.setOnClickListener {
            try {
                if (binding.textViewCalculate?.text.toString()
                        .isNotEmpty() && (binding.textViewCalculate?.text.toString()
                        .contains('+') || binding.textViewCalculate?.text.toString()
                        .contains('-') || binding.textViewCalculate?.text.toString()
                        .contains('*') || binding.textViewCalculate?.text.toString()
                        .contains('/')) && parenthesesCounter == 0
                ) {
                    val expression =
                        ExpressionBuilder(binding.textViewCalculate?.text.toString()).build()
                    val doubleResult = expression.evaluate()
                    val longResult = expression.evaluate().toLong()
                    if (doubleResult == longResult.toDouble()) {
                        binding.textViewResult?.text = longResult.toString()
                    } else {
                        binding.textViewResult?.text = doubleResult.toString()
                    }
                    isEqualBtnClicked = true


                }
            } catch (e: Exception) {
                binding.textViewResult?.text = "Error"
                binding.textViewCalculate?.text = ""
            }

        }
        binding.btnPower?.setOnClickListener {
            try {
                if (binding.textViewResult?.text.toString().isNotEmpty() && (binding.textViewCalculate?.text?.last() != '+' && binding.textViewCalculate?.text?.last() != '-' && binding.textViewCalculate?.text?.last() != '*' && binding.textViewCalculate?.text.toString()
                        .last() != '/')) {
                    var oldCalculateText = binding.textViewCalculate?.text
                    val oldResultText = binding.textViewResult?.text.toString()
                    val powerResult = oldResultText.toDouble() * oldResultText.toDouble()
                    val longPowerResult: Long = powerResult.toLong()
                    binding.textViewCalculate?.text =
                        oldCalculateText?.substring(
                            0,
                            (oldCalculateText.length) - (oldResultText.length)
                        )
                    if (longPowerResult.toDouble() == powerResult) {
                        if (isEqualBtnClicked) {
                            binding.textViewResult?.text = longPowerResult.toString()
                            binding.textViewCalculate?.text=longPowerResult.toString()
                        } else {
                            binding.textViewResult?.text = longPowerResult.toString()
                            binding.textViewCalculate?.append(longPowerResult.toString())
                        }
                    } else {
                        if (isEqualBtnClicked) {
                            binding.textViewResult?.text = powerResult.toString()
                            binding.textViewCalculate?.text = powerResult.toString()
                        } else {
                            binding.textViewResult?.text = powerResult.toString()
                            binding.textViewCalculate?.append(powerResult.toString())

                        }
                    }

                }

            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "متن، حاوی کاراکتر های غیر مجاز برای این عمل است",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
        binding.btnDel?.setOnClickListener {
            val oldAnswerText = binding.textViewResult?.text.toString()
            val oldCalculateText = binding.textViewCalculate?.text.toString()

            if (oldAnswerText.isNotEmpty() && oldCalculateText.isNotEmpty()) {
                if (binding.textViewResult?.text.toString().last() == '(') {
                    binding.textViewResult?.text =
                        oldAnswerText.substring(0, oldAnswerText.length - 1)
                    binding.textViewCalculate?.text =
                        oldCalculateText.substring(0, oldCalculateText.length - 1)
                    parenthesesCounter--
                } else if (binding.textViewResult?.text.toString().last() == ')') {
                    binding.textViewResult?.text =
                        oldAnswerText.substring(0, oldAnswerText.length - 1)
                    binding.textViewCalculate?.text =
                        oldCalculateText.substring(0, oldCalculateText.length - 1)
                    parenthesesCounter++

                } else {
                    binding.textViewResult?.text =
                        oldAnswerText.substring(0, oldAnswerText.length - 1)
                    binding.textViewCalculate?.text =
                        oldCalculateText.substring(0, oldCalculateText.length - 1)
                }
                if (binding.textViewResult?.text.toString().isEmpty()) {
                    isParenthesesOpen = false
                    parenthesesCounter = 0
                }


            }
        }
        binding.btnParentheses?.setOnClickListener {

            if (!isParenthesesOpen) {
                appendText("(")
                isParenthesesOpen = true
                parenthesesCounter++
            } else {
                if (binding.textViewResult?.text.toString().last() != '(') {
                    if (parenthesesCounter >= 1) {
                        appendText(")")
                        parenthesesCounter--
                    } else {
                        appendText("(")
                        isParenthesesOpen = true
                        parenthesesCounter++
                    }

                } else {
                    appendText("(")
                    isParenthesesOpen = true
                    parenthesesCounter++
                }
            }

        }
    }

    private fun onNumberClicked() {
        binding.btn0?.setOnClickListener {
            if (binding.textViewResult?.text.toString().isNotEmpty()) {
                appendText("0")
            }
        }
        binding.btn1?.setOnClickListener {

            appendText("1")
        }
        binding.btn2?.setOnClickListener {
            appendText("2")
        }
        binding.btn3?.setOnClickListener {
            appendText("3")
        }
        binding.btn4?.setOnClickListener {
            appendText("4")
        }
        binding.btn5?.setOnClickListener {
            appendText("5")
        }
        binding.btn6?.setOnClickListener {
            appendText("6")
        }
        binding.btn7?.setOnClickListener {
            appendText("7")
        }
        binding.btn8?.setOnClickListener {
            appendText("8")
        }
        binding.btn9?.setOnClickListener {
            appendText("9")
        }
        binding.btnDot?.setOnClickListener {
            if (binding.textViewResult?.text.toString().isEmpty()) {
                appendText("0.")
            } else if (!(binding.textViewResult?.text.toString().contains('.'))) {
                appendText(".")
            }
        }

    }

    private fun appendText(text: String) {

        try {
            if (text == "+" || text == "-" || text == "*" || text == "/") {
                if (isEqualBtnClicked) {
                    binding.textViewCalculate?.text = binding.textViewResult?.text
                    isEqualBtnClicked = false
                }
                if (parenthesesCounter == 0) {
                    val expression =
                        ExpressionBuilder(binding.textViewCalculate?.text.toString()).build()
                    val doubleResult = expression.evaluate()
                    val longResult = expression.evaluate().toLong()
                    binding.textViewCalculate?.append(text)

                    if (doubleResult == longResult.toDouble()) {
                        binding.textViewResult?.text = longResult.toString()
                    } else {
                        binding.textViewResult?.text = doubleResult.toString()
                    }

                } else {
                    binding.textViewResult?.append(text)
                    binding.textViewCalculate?.append(text)
                    val viewTree:ViewTreeObserver= binding.horizontalScrollView!!.viewTreeObserver
                    viewTree.addOnGlobalLayoutListener(object:ViewTreeObserver.OnGlobalLayoutListener{
                        override fun onGlobalLayout() {
                            binding.horizontalScrollView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                            binding.horizontalScrollView?.scrollTo(binding.textViewResult!!.width , 0)
                        }

                    })
                }


            } else {
                if (binding.textViewCalculate?.text.toString()
                        .isNotEmpty() && (binding.textViewCalculate?.text?.last() == '+' || binding.textViewCalculate?.text?.last() == '-' || binding.textViewCalculate?.text?.last() == '*' || binding.textViewCalculate?.text.toString()
                        .last() == '/') && parenthesesCounter == 0
                ) {
                    binding.textViewResult?.text = ""
                    binding.textViewResult?.append(text)
                    binding.textViewCalculate?.append(text)
                } else if (isEqualBtnClicked) {
                    isEqualBtnClicked = false
                    isParenthesesOpen = false
                    parenthesesCounter = 0
                    binding.textViewResult?.text = ""
                    binding.textViewCalculate?.text = ""
                    binding.textViewResult?.append(text)
                    binding.textViewCalculate?.append(text)

                } else {
                    binding.textViewResult?.append(text)
                    binding.textViewCalculate?.append(text)
                    val viewTree:ViewTreeObserver= binding.horizontalScrollView!!.viewTreeObserver
                    viewTree.addOnGlobalLayoutListener(object:ViewTreeObserver.OnGlobalLayoutListener{
                        override fun onGlobalLayout() {
                            binding.horizontalScrollView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                            binding.horizontalScrollView?.scrollTo(binding.textViewResult!!.width , 0)
                        }

                    })
                }

            }
        } catch (e: Exception) {
            binding.textViewResult?.text = "Error"
            binding.textViewCalculate?.text = ""

        }
    }

}
