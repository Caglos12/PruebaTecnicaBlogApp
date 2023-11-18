package com.example.blogapp.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.blogapp.R
import com.example.blogapp.databinding.FragmentBlogListBinding
import com.example.blogapp.domain.model.Blog
import com.example.blogapp.domain.util.NetworkUtil
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlogListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentBlogListBinding? = null
    private val binding: FragmentBlogListBinding
        get() = _binding!!

    private val blogListAdapter = BlogListAdapter()

    private val viewModel: BlogListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlogListBinding.inflate(inflater, container, false)

        binding.svBlog.setOnQueryTextListener(this)
        initComponents()
        subscribeObservers()

        return binding.root
    }

    private fun initComponents(){
        binding.rvBlogList.apply {
            adapter = blogListAdapter
        }
        binding.fab.setOnClickListener {
            if (NetworkUtil.hasInternetConnection(context = requireContext())) {
                navigateToBlogAdd()
            } else {
                val snackBar = Snackbar
                    .make(binding.clFragmentList, R.string.message_no_internet, Snackbar.LENGTH_LONG)
                snackBar.show()
            }
        }
    }

    private fun subscribeObservers(){
        viewModel.getAllBlog()
        viewModel.blogList.observe(viewLifecycleOwner){
            blogListAdapter.submitList(it)
            blogListAdapter.setBlogClickedListener { blog ->
                navigateToBlogDetail(blog)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            handleLoadingState(it)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.getBlogByFilter(newText.toString())
        viewModel.blogList.observe(this){ blogListAdapter.submitList(it) }
        viewModel.isLoading.observe(this){ handleLoadingState(it) }
        return true
    }

    private fun handleLoadingState(isLoading: Boolean){
        with(binding){
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                rvBlogList.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                rvBlogList.visibility = View.VISIBLE
            }
        }
    }

    private fun navigateToBlogDetail(blog: Blog){
        val action = BlogListFragmentDirections.actionBlogListFragmentToBlogDetailFragment(
            blogId = blog.id
        )
        findNavController().navigate(action)
    }

    private fun navigateToBlogAdd(){
        val action = BlogListFragmentDirections.actionBlogListFragmentToBlogAddFragment()
        findNavController().navigate(action)
    }
}