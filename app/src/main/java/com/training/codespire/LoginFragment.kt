package com.training.codespire

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.training.codespire.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)



        navToRegister()



        checkLogin()



        return binding.root
    }

    private fun navToHome() {

    }

    private fun navToRegister() {
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun checkLogin() {
        binding.btnSignIn.setOnClickListener {
            validateField(
                binding.etEmail,
                binding.tvEmailError,
                "Email is required"
            )
            validateField(
                binding.etPassword,
                binding.tvPasswordError,
                "Password is required"
            )
        }

        setupPasswordVisibilityToggle(binding.etPassword, R.drawable.ic_eye, R.drawable.ic_eye_off)
    }

    private fun validateField(
        field: EditText,
        errorTextView: TextView,
        errorMessage: String
    ) {
        if (field.text.isEmpty()) {
            setFieldError(field, errorTextView, errorMessage)
        } else {
            setFieldNormal(field, errorTextView)
        }
    }

    private fun setFieldError(
        field: EditText,
        errorTextView: TextView,
        errorMessage: String
    ) {
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = errorMessage
        field.setBackgroundResource(R.drawable.et_border_error)
    }

    private fun setFieldNormal(field: EditText, errorTextView: TextView) {
        errorTextView.visibility = View.GONE
        field.setBackgroundResource(R.drawable.et_border_selector)
    }

    private fun setupPasswordVisibilityToggle(
        passwordField: EditText,
        visibleIcon: Int,
        hiddenIcon: Int
    ) {
        passwordField.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2 // index for drawableRight
                val drawable = passwordField.compoundDrawables[drawableEnd]

                if (drawable != null && event.rawX >= (passwordField.right - drawable.bounds.width())) {
                    val selection = passwordField.selectionEnd
                    if (passwordField.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        passwordField.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        passwordField.setCompoundDrawablesWithIntrinsicBounds(0, 0, hiddenIcon, 0)
                    } else {
                        passwordField.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        passwordField.setCompoundDrawablesWithIntrinsicBounds(0, 0, visibleIcon, 0)
                    }
                    passwordField.setSelection(selection)
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }
    }


}



