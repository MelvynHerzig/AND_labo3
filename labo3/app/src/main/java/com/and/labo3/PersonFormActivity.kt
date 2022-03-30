package com.and.labo3

import android.app.DatePickerDialog
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.Group
import java.util.*

class PersonFormActivity : AppCompatActivity() {


    private var birthdayCalendar = Calendar.getInstance()

    private lateinit var nameField: EditText
    private lateinit var firstnameField: EditText
    private lateinit var birthdayField: EditText
    private lateinit var schoolField: EditText
    private lateinit var graduationYearField: EditText
    private lateinit var companyField: EditText
    private lateinit var specificExperienceField: EditText
    private lateinit var emailField: EditText
    private lateinit var remarksField: EditText

    private lateinit var studentsGroup: Group
    private lateinit var workersGroup: Group

    private lateinit var pictureImage: ImageView

    private lateinit var birthdayImageButton: ImageButton

    private lateinit var occupationRadioButton: RadioGroup

    private lateinit var nationalitySpinner: Spinner
    private lateinit var specificSectorSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_form)

        nameField = findViewById(R.id.main_base_name_field)
        firstnameField = findViewById(R.id.main_base_firstname_field)
        birthdayField = findViewById(R.id.main_base_birthdate_field)
        schoolField = findViewById(R.id.main_specific_school_field)
        graduationYearField = findViewById(R.id.main_specific_graduationyear_field)
        companyField = findViewById(R.id.main_specific_company_field)
        specificExperienceField = findViewById(R.id.main_specific_experience_field)
        emailField = findViewById(R.id.additional_email_field)
        remarksField = findViewById(R.id.additional_remarks_field)

        studentsGroup = findViewById(R.id.group_specific_students)
        workersGroup = findViewById(R.id.group_specific_workers)
        occupationRadioButton = findViewById(R.id.main_base_occupation_options)

        pictureImage = findViewById(R.id.additional_picture_image)

        birthdayImageButton = findViewById(R.id.main_base_birthdate_button)

        nationalitySpinner = findViewById(R.id.main_base_nationality_spinner)
        specificSectorSpinner = findViewById(R.id.main_specific_sector_spinner)


        birthdayImageButton.setOnClickListener {

            val dpd = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->

                    // Display Selected date in textbox
                    birthdayField.setText("$dayOfMonth.${monthOfYear+1}.$year")
                    birthdayCalendar.set(Calendar.YEAR, year)
                    birthdayCalendar.set(Calendar.MONTH, monthOfYear)
                    birthdayCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                },
                birthdayCalendar.get(Calendar.YEAR),
                birthdayCalendar.get(Calendar.MONTH),
                birthdayCalendar.get(Calendar.DAY_OF_MONTH)
            )

            dpd.show()
        }

        occupationRadioButton.setOnCheckedChangeListener { _, id ->

            workersGroup.visibility = View.GONE
            studentsGroup.visibility = View.GONE

            when(id){
                R.id.main_base_occupation_option_student -> studentsGroup.visibility = View.VISIBLE
                R.id.main_base_occupation_option_employee -> workersGroup.visibility = View.VISIBLE

            }
        }
    }
}