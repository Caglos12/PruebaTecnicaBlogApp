package com.example.blogapp.presentation.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.blogapp.R
import com.example.blogapp.data.remote.response.RequestBodies
import com.example.blogapp.databinding.FragmentBlogAddBinding
import com.example.blogapp.domain.extensions.onBackPressed
import com.example.blogapp.domain.util.Resource
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class BlogAddFragment : Fragment() {

    private var _binding: FragmentBlogAddBinding? = null
    private val binding: FragmentBlogAddBinding
        get() = _binding!!

    private val viewModel : BlogAddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlogAddBinding.inflate(inflater, container, false)

        initComponents()

        return binding.root
    }

    private fun initComponents(){
        binding.etDate.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            val picker = builder.build()

            picker.addOnPositiveButtonClickListener { timeInMilliseconds ->
                val dateStr = SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                ).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }.format(timeInMilliseconds)
                binding.etDate.setText(dateStr)
            }
            picker.show(this@BlogAddFragment.parentFragmentManager, picker.toString())
        }

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.imgSend.setOnClickListener{
            val title = binding.etTitle.text.toString().trim()
            val author = binding.etAuthor.text.toString().trim()
            val date = binding.etDate.text.toString().trim()
            val content = binding.etContent.text.toString().trim()

            if (validFields()){
                val body = RequestBodies.BlogBody(title, author, date, content)
                viewModel.registerBlog(body)
                viewModel.registerResponse.observe(requireActivity()) { event ->
                    event.getContentIfNotHandled()?.let { response ->
                        when (response) {
                            is Resource.Success -> {
                                hideProgressBar()
                                response.data.let {
                                    val snackBar = Snackbar
                                        .make(
                                            binding.lyAddFragment,
                                            getString(R.string.exit_save),
                                            Snackbar.LENGTH_LONG
                                        )
                                    snackBar.show()
                                }
                            }
                            is Resource.Error -> {
                                hideProgressBar()
                                val snackBar = Snackbar
                                    .make(
                                        binding.lyAddFragment, getString(R.string.error_save),
                                        Snackbar.LENGTH_LONG
                                    )
                                snackBar.show()
                            }
                            is Resource.Loading -> {
                                showProgressBar()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun validFields(): Boolean {
        var isValid = true

        if (binding.etContent.text.isNullOrEmpty()){
            binding.tilContent.run {
                error = getString(R.string.help_require)
            }
            isValid = false
        } else {
            binding.tilContent.error = null
        }
        if (binding.etDate.text.isNullOrEmpty()){
            binding.tilDate.run {
                error = getString(R.string.help_require)
            }
            isValid = false
        } else {
            binding.tilDate.error = null
        }
        if (binding.etAuthor.text.isNullOrEmpty()){
            binding.tilAuthor.run {
                error = getString(R.string.help_require)
            }
            isValid = false
        } else {
            binding.tilAuthor.error = null
        }
        if (binding.etTitle.text.isNullOrEmpty()){
            binding.tilTitle.run {
                error = getString(R.string.help_require)
            }
            isValid = false
        } else {
            binding.tilTitle.error = null
        }
        return isValid
    }
}