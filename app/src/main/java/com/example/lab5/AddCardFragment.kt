package com.example.lab5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lab5.databinding.FragmentAddCardBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddCardFragment : Fragment() {
    private var _binding: FragmentAddCardBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCardBinding.inflate(layoutInflater, container, false)

        binding.cardImage.setOnClickListener {
            getSystemContent.launch("image/*")
        }

        binding.addButton.setOnClickListener {
            val question = when {
                binding.enterQuestion.text.toString()
                    .isNotEmpty() -> binding.enterQuestion.text.toString()

                else -> "Поле вопроса отсутствует"
            }
            val example = when {
                binding.enterExample.text.toString()
                    .isNotEmpty() -> binding.enterExample.text.toString()

                else -> "Поле примера отсутствует"
            }
            val answer = when {
                binding.enterAnswer.text.toString()
                    .isNotEmpty() -> binding.enterAnswer.text.toString()

                else -> "Поле ответа отсутствует"
            }
            val translation = when {
                binding.enterTranslation.text.toString()
                    .isNotEmpty() -> binding.enterTranslation.text.toString()

                else -> "Поле перевода отсутствует"
            }
            val newCard = Model.createNewCard(
                question, example, answer, translation, imageUri
            )
            Model.addCard(newCard)
            val action = AddCardFragmentDirections.actionAddCardFragmentToListCardFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }


    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        val name = requireActivity().packageName
        requireActivity().grantUriPermission(name, it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        binding.cardImage.setImageURI(it)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddCardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}