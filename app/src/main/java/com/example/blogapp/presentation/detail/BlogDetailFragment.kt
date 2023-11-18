package com.example.blogapp.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.blogapp.databinding.FragmentBlogDetailBinding
import com.example.blogapp.domain.extensions.onBackPressed
import com.example.blogapp.domain.model.Blog
import com.example.blogapp.domain.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlogDetailFragment : Fragment() {

    private var _binding:  FragmentBlogDetailBinding? = null
    private val binding: FragmentBlogDetailBinding
        get() = _binding!!

    private val viewModel: BlogDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =  FragmentBlogDetailBinding.inflate(inflater, container, false)

        initComponents()
        subscribeViewModel()

        return binding.root
    }

    private fun initComponents() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun subscribeViewModel() {
        viewModel.blogDetail.observe(viewLifecycleOwner) { state ->
            when(state){
                is Resource.Success -> handleBlogDetail(state.data)
                is Resource.Loading -> showLoadingScreen()
                is Resource.Error -> Unit
                else -> Unit
            }
        }
    }

    private fun showLoadingScreen() {
        binding.blogData.visibility = View.VISIBLE
    }

    private fun handleBlogDetail(blog: Blog) {
        with(binding){
            blogTitulo.text = blog.titulo
            blogAutor.text = blog.autor
            blogFecha.text = blog.fecha
            blogContenido.text = blog.contenido
        }
        showContentScreen()
    }

    private fun showContentScreen() {
        binding.progressIndicator.visibility = View.GONE
    }
}