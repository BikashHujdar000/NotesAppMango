package com.example.notes.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notes.Model.NoteRequest
import com.example.notes.Model.NoteResponse
import com.example.notes.R
import com.example.notes.Utils.NetworkResults
import com.example.notes.ViewModel.NoteViewModel
import com.example.notes.databinding.FragmentLoginBinding
import com.example.notes.databinding.FragmentNoteBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment() {

          private var _binding : FragmentNoteBinding? = null
        val binding get() = _binding!!
    private var  note :NoteResponse? = null
    private  val noteViewModel by viewModels<NoteViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentNoteBinding.inflate(inflater,container,false)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
        binHandlers()
        bindObserver()
    }

    private fun bindObserver() {
        noteViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when (it)
            {
                is NetworkResults.Success->{
                    findNavController().popBackStack()
                }
                is NetworkResults.Error->{}
                is NetworkResults.Loading->{}

            }
        })
    }

    private fun binHandlers() {
        binding.btnDelete.setOnClickListener{
            note?.let {
                noteViewModel.deleteNotes(it!!._id)
            }
        }
        binding.btnSubmit.setOnClickListener {
            val title= binding.txtTitle.text.toString()
            val description = binding.txtDescription.text.toString()
            val noteRequest = NoteRequest(title,description)
            if (note == null)
            {
                noteViewModel.createNotes(noteRequest)
            }
            else
            {
                noteViewModel.updateNotes(note!!._id,noteRequest)

            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setInitialData() {
        val jsonNote = arguments?.getString("notes")
     if (jsonNote != null)
      {
        note =Gson().fromJson(jsonNote,NoteResponse::class.java)
          note?.let {
              binding.txtTitle.setText(it.title)
              binding.txtDescription.setText(it.description)

          }
       }
        else
       {
          binding.addEditText.text="Add Text"
       }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }


}