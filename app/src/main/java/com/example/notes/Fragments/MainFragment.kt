package com.example.notes.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.whenResumed
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.Api.NoteApi
import com.example.notes.Model.NoteResponse
import com.example.notes.Model.UserResponse
import com.example.notes.NoteAdapter
import com.example.notes.R
import com.example.notes.Utils.NetworkResults
import com.example.notes.ViewModel.NoteViewModel
import com.example.notes.databinding.FragmentMainBinding
import com.google.gson.Gson
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {


    private var _binding : FragmentMainBinding? = null
    private  val binding get()= _binding!!
    private  val noteViewModel by viewModels<NoteViewModel>()
    lateinit var  adapter: NoteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        _binding = FragmentMainBinding.inflate(inflater,container,false)
         adapter=NoteAdapter(::onNoteClicked)
        return  binding.root

           }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        noteViewModel.getnotes()
        binding.noteList.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.noteList.adapter=adapter

        binding.addNote.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_noteFragment)
        }

    }

    private fun bindObserver() {
       noteViewModel.notesLiveData.observe(viewLifecycleOwner, Observer {
           binding.progressBar.isVisible=false

          when(it)
          {
              is NetworkResults.Loading -> {
                   binding.progressBar.isVisible= true
              }
              is NetworkResults.Error ->{
                  Toast.makeText(requireContext(),it.message.toString(),Toast.LENGTH_SHORT)
                      .show()

              }
              is NetworkResults.Success ->{

                  // sucess honey par kya
                  adapter.submitList(it.data)
              }

          }

       })
    }
    fun onNoteClicked (noteResponse: NoteResponse)
    {

        val bundle= Bundle()
        bundle.putString("notes",Gson().toJson(noteResponse))
        findNavController().navigate(R.id.action_mainFragment_to_noteFragment,bundle)
        Toast.makeText(requireContext(),"note : ${noteResponse.title}",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}
