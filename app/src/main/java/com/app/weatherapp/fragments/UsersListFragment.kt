package com.app.weatherapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.R
import com.app.weatherapp.adapters.UsersListAdapter
import com.app.weatherapp.databinding.FragmentUsersListBinding
import com.app.weatherapp.viewmodels.MainViewModel

class UsersListFragment : Fragment() {

    private var binding: FragmentUsersListBinding? = null
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: UsersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_users_list,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            show()
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            elevation = 0F
        }
        setHasOptionsMenu(true)
        adapter = UsersListAdapter()
        binding?.usersRecyclerView?.adapter = adapter
        viewModel.getAllUsers()

        viewModel.userListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        itemTouchHelper?.attachToRecyclerView(binding?.usersRecyclerView)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.user_list_screen_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_user) {
            NavHostFragment.findNavController(this).navigate(R.id.userFormFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private var itemTouchHelper:ItemTouchHelper? =
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteUser(adapter.getUser(viewHolder.adapterPosition))
                Toast.makeText(
                    context, getString(R.string.deleted_msg),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}