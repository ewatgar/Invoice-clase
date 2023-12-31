package com.murray.customer.ui.creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.murray.customer.R
import com.murray.customer.databinding.FragmentCustomerCreationBinding
import com.murray.customer.ui.creation.usecase.CustomerCreationState
import com.murray.customer.ui.LayoutTextWatcher
import com.murray.customer.ui.creation.usecase.CustomerCreationViewModel
import com.murray.entities.customers.Customer
import com.murray.entities.email.Email
import com.murray.repositories.CustomerRepository


class CustomerCreationFragment : Fragment() {

    private var _binding: FragmentCustomerCreationBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CustomerCreationViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCustomerCreationBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        addTextWatcher()
        return binding.root
    }

    private fun addTextWatcher(){
        val tieName = binding.tieName
        val tieEmail = binding.tieEmail
        val textWatcherName = LayoutTextWatcher(binding.tilName)
        val textWatcherEmail = LayoutTextWatcher(binding.tilEmail)
        tieName.addTextChangedListener(textWatcherName)
        tieEmail.addTextChangedListener(textWatcherEmail)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener() {
            Toast.makeText(requireContext(), R.string.toastMessage, Toast.LENGTH_SHORT).show()
        }

        viewModel.getState().observe(viewLifecycleOwner) {
            when(it){
                CustomerCreationState.NameIsMandatory -> setNameEmptyError()
                CustomerCreationState.NonExistentContact -> setEmailEmptyError()
                CustomerCreationState.EmailFormatError -> setEmailFormatError()
                else -> onSuccess()
            }
        }
    }

    private fun setEmailFormatError() {
        binding.tilEmail.error = "El email no es válido"
        binding.tilEmail.requestFocus()
    }

    private fun onSuccess() {
        CustomerRepository.addCustomer(Customer(CustomerRepository.getNextId(),binding.tieName.text.toString(), Email(binding.tieEmail.text.toString()), binding.tiePhone.text.toString().takeIf { it.isNotEmpty() }?.toInt() ?: null, binding.tieCity.text.toString(), binding.tieAddress.text.toString()))
        Toast.makeText(requireActivity(), "Cliente creado con éxito!", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun setNameEmptyError() {
        binding.tilName.error = "El nombre no puede estar vacío"
        binding.tilName.requestFocus()
    }

    private fun setEmailEmptyError() {
        binding.tilEmail.error = "El email no puede estar vacío"
        binding.tilEmail.requestFocus()
    }

}