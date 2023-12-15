package com.murray.account.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.murray.account.R
import com.murray.account.databinding.FragmentAccountSignUpBinding
import com.murray.account.ui.signup.usecase.SignUpState
import com.murray.account.ui.signup.usecase.SignUpViewModel
import com.murray.entities.accounts.TypeAccounts
import com.murray.entities.accounts.TypeAccounts.Companion.getTypeAccountsList
import com.murray.entities.accounts.UserSignUp
import com.murray.entities.accounts.VisibilityAccounts
import com.murray.entities.accounts.VisibilityAccounts.Companion.getVisibilityAccountsList
import java.lang.reflect.Type


class AccountSignUpFragment : Fragment() {

    private var _binding: FragmentAccountSignUpBinding? = null

    private val viewmodel: SignUpViewModel by viewModels()
    private val binding get() = _binding!!

    private lateinit var twatcher: LogInTextWatcher


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountSignUpBinding.inflate(inflater, container, false)

        binding.viewmodel = this.viewmodel
        
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinnerTypeAccounts()
        initSpinnerVisibilityAccounts()

        twatcher = LogInTextWatcher(binding.tieEmailSignUp)
        binding.tilEmailSignUp.addTextChangedListener(twatcher)

        twatcher = LogInTextWatcher(binding.tiePasswordSignUp)
        binding.tilPasswordSignUp.addTextChangedListener(twatcher)

        twatcher = LogInTextWatcher(binding.tieConfirmsPasswordsSignUp)
        binding.tilConfirmsPasswordsSignUp.addTextChangedListener(twatcher)

        viewmodel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                SignUpState.EmailEmptyError -> setEmailEmptyError()
                SignUpState.PasswordEmptyError -> setPasswordEmptyError()
                SignUpState.PasswordEmptyError2 -> setPasswordEmptyError2()
                SignUpState.PasswordsNotEquals -> setDifferentPasswordError()
                SignUpState.EmailDuplicatedError -> setEmailDuplicatorError()
                else -> onSuccess()
            }
        })
    }


    private fun initSpinnerTypeAccounts() {
        val typeAccountsList = getTypeAccountsList()
        val typeAccountsAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, typeAccountsList)
        typeAccountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spType.adapter = typeAccountsAdapter

        /*
        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val profile = parent?.adapter?.getItem(position)
                Toast.makeText(requireActivity(), "Elemento pulsado $profile", Toast.LENGTH_SHORT)
                    .show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        with(binding.spProfile) {
            this.adapter = adapter
            onItemSelectedListener = listener
        }*/
    }

    private fun initSpinnerVisibilityAccounts() {
        val visibilityAccountsList = getVisibilityAccountsList()
        val visibilityAccountsAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                visibilityAccountsList
            )
        visibilityAccountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spVisibility.adapter = visibilityAccountsAdapter
    }

    private fun setEmailEmptyError() {
        binding.tieEmailSignUp.error = getString(com.murray.account.R.string.errEmailEmpty)
        binding.tieEmailSignUp.requestFocus()
    }

    private fun setPasswordEmptyError() {
        binding.tiePasswordSignUp.error = getString(com.murray.account.R.string.errPasswordEmpty)
        binding.tiePasswordSignUp.requestFocus()
    }

    private fun setPasswordEmptyError2() {
        binding.tieConfirmsPasswordsSignUp.error =
            getString(com.murray.account.R.string.errPasswordEmpty)
        binding.tieConfirmsPasswordsSignUp.requestFocus()
    }

    private fun setDifferentPasswordError() {
        binding.tieConfirmsPasswordsSignUp.error = "Las contraseñas deben de ser iguales"
        binding.tieConfirmsPasswordsSignUp.requestFocus()
    }

    private fun setEmailDuplicatorError() {
        Toast.makeText(requireActivity(), "Ya existe un usuario con ese email", Toast.LENGTH_SHORT).show()
    }

    private fun onSuccess() {
        Toast.makeText(requireContext(), "Se ha registrado una nueva cuenta", Toast.LENGTH_SHORT)
            .show()
        findNavController().popBackStack()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open inner class LogInTextWatcher(var tilError: TextInputLayout) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            tilError.error = null
        }

    }
}