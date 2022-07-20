package com.bchmsl.presentation_recyclerview_diffutil.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import com.bchmsl.presentation_recyclerview_diffutil.R
import com.bchmsl.presentation_recyclerview_diffutil.data.personsData
import com.bchmsl.presentation_recyclerview_diffutil.databinding.ActivityEditPersonBinding
import com.bchmsl.presentation_recyclerview_diffutil.extensions.makeSnackbar
import com.bchmsl.presentation_recyclerview_diffutil.model.Gender
import com.bchmsl.presentation_recyclerview_diffutil.model.Gender.*
import com.bchmsl.presentation_recyclerview_diffutil.model.Person

class EditPersonActivity : AppCompatActivity() {
    companion object {
        const val NONE = -1
    }

    private val userId by lazy { intent.getIntExtra("userId", NONE) }
    private val user by lazy { if (userId != NONE) personsData[userId] else null }

    private lateinit var binding: ActivityEditPersonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        if (userId != NONE) {
            setTexts()
        }
        listeners()
    }

    private fun listeners() {
        binding.btnSave.setOnClickListener {
            checkFields()
        }
    }


    private fun setTexts() {
        user.let { person ->
            binding.apply {
                etFirstName.setText(person?.firstName)
                etLastName.setText(person?.lastName)
                etEmail.setText(person?.email)
                when (person?.gender) {
                    MALE -> {
                        rbtnMale.isChecked = true
                        etFirstName.setBackgroundResource(R.drawable.shape_rounded_rectangle_blue)
                        etLastName.setBackgroundResource(R.drawable.shape_rounded_rectangle_blue)
                        etEmail.setBackgroundResource(R.drawable.shape_rounded_rectangle_blue)
                    }
                    FEMALE -> {
                        rbtnFemale.isChecked = true
                        etFirstName.setBackgroundResource(R.drawable.shape_rounded_rectangle_red)
                        etLastName.setBackgroundResource(R.drawable.shape_rounded_rectangle_red)
                        etEmail.setBackgroundResource(R.drawable.shape_rounded_rectangle_red)
                    }
                    null -> {
                        d("TAG", "PERSON GENDER IS NULL")
                    }
                }
            }
        }
    }

    private fun checkFields() {
        binding.apply {
            when {
                etFirstName.text.isNullOrEmpty() -> etFirstName.makeSnackbar(getString(R.string.first_name_empty))
                etLastName.text.isNullOrEmpty() -> etLastName.makeSnackbar(getString(R.string.last_name_empty))
                etEmail.text.isNullOrEmpty() -> etEmail.makeSnackbar(getString(R.string.email_empty))
                rgGender.checkedRadioButtonId == NONE -> etFirstName.makeSnackbar(getString(R.string.gender_empty))
                else -> {
                    saveUserClicked()
                }
            }
        }
    }

    private fun saveUserClicked() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val gender = if (binding.rbtnMale.isChecked) MALE else FEMALE
        val email = binding.etEmail.text.toString()
        val currentPerson = personsData.find {
            it.id == userId
        }
        if (currentPerson != null) updateUser(currentPerson, firstName, lastName, gender, email)
        else addUser(firstName, lastName, gender, email)
        finish()
    }

    private fun updateUser(
        currentPerson: Person,
        firstName: String,
        lastName: String,
        gender: Gender,
        email: String
    ) {
        val updatedPerson = currentPerson.copy(
            firstName = firstName,
            lastName = lastName,
            gender = gender,
            email = email
        )
        updatedPerson.id = userId
        personsData[personsData.indexOf(currentPerson)] = updatedPerson
    }

    private fun addUser(firstName: String, lastName: String, gender: Gender, email: String) {
        val newPerson = Person(firstName, lastName, gender, email)
        personsData.add(newPerson)
    }
}
