package me.ashutoshkk.carecompass.presentation.fragments.selfDiagnosis

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import me.ashutoshkk.carecompass.R
import me.ashutoshkk.carecompass.databinding.FragmentSelfDiagnosisBinding
import me.ashutoshkk.carecompass.domain.model.Symptom
import me.ashutoshkk.carecompass.presentation.adapters.SymptomRecyclerAdapter
import java.lang.reflect.Field

class SelfDiagnosisFragment : Fragment(), SymptomRecyclerAdapter.SymptomClickListener {

    private var _binding: FragmentSelfDiagnosisBinding? = null
    private val binding: FragmentSelfDiagnosisBinding get() = _binding!!

    private val symptomList = mutableListOf(
        Symptom(1, "Itching", "itching", false),
        Symptom(2, "Skin Rash", "skin_rash", false),
        Symptom(3, "Nodal Skin Eruptions", "nodal_skin_eruptions", false),
        Symptom(4, "Continuous Sneezing", "continuous_sneezing", false),
        Symptom(5, "Shivering", "shivering", false),
        Symptom(6, "Chills", "chills", false),
        Symptom(7, "Joint Pain", "joint_pain", false),
        Symptom(8, "Stomach Pain", "stomach_pain", false),
        Symptom(9, "Acidity", "acidity", false),
        Symptom(10, "Ulcers on Tongue", "ulcers_on_tongue", false),
        Symptom(11, "Muscle Wasting", "muscle_wasting", false),
        Symptom(12, "Vomiting", "vomiting", false),
        Symptom(13, "Burning Micturition", "burning_micturition", false),
        Symptom(14, "Spotting Urination", "spotting_ urination", false),
        Symptom(15, "Fatigue", "fatigue", false),
        Symptom(16, "Weight Gain", "weight_gain", false),
        Symptom(17, "Anxiety", "anxiety", false),
        Symptom(18, "Cold Hands and Feets", "cold_hands_and_feets", false),
        Symptom(19, "Mood Swings", "mood_swings", false),
        Symptom(20, "Weight Loss", "weight_loss", false),
        Symptom(21, "Restlessness", "restlessness", false),
        Symptom(22, "Lethargy", "lethargy", false),
        Symptom(23, "Patches in Throat", "patches_in_throat", false),
        Symptom(24, "Irregular Sugar Level", "irregular_sugar_level", false),
        Symptom(25, "Cough", "cough", false),
        Symptom(26, "High Fever", "high_fever", false),
        Symptom(27, "Sunken Eyes", "sunken_eyes", false),
        Symptom(28, "Breathlessness", "breathlessness", false),
        Symptom(29, "Sweating", "sweating", false),
        Symptom(30, "Dehydration", "dehydration", false),
        Symptom(31, "Indigestion", "indigestion", false),
        Symptom(32, "Headache", "headache", false),
        Symptom(33, "Yellowish Skin", "yellowish_skin", false),
        Symptom(34, "Dark Urine", "dark_urine", false),
        Symptom(35, "Nausea", "nausea", false),
        Symptom(36, "Loss of Appetite", "loss_of_appetite", false),
        Symptom(37, "Pain behind the eyes", "pain_behind_the_eyes", false),
        Symptom(38, "Back Pain", "back_pain", false),
        Symptom(39, "Constipation", "constipation", false),
        Symptom(40, "Abdominal Pain", "abdominal_pain", false),
        Symptom(41, "Diarrhoea", "diarrhoea", false),
        Symptom(42, "Mild Fever", "mild_fever", false),
        Symptom(43, "Yellow Urine", "yellow_urine", false),
        Symptom(44, "Yellowing of Eyes", "yellowing_of_eyes", false),
        Symptom(45, "Acute Liver Failure", "acute_liver_failure", false),
        Symptom(46, "Fluid Overload", "fluid_overload", false),
        Symptom(47, "Swelling of Stomach", "swelling_of_stomach", false),
        Symptom(48, "Swelled Lymph Nodes", "swelled_lymph_nodes", false),
        Symptom(49, "Malaise", "malaise", false),
        Symptom(50, "Blurred And Distorted Vision", "blurred_and_distorted_vision", false),
        Symptom(51, "Phlegm", "phlegm", false),
        Symptom(52, "Throat Irritation", "throat_irritation", false),
        Symptom(53, "Redness Of Eyes", "redness_of_eyes", false),
        Symptom(54, "Sinus Pressure", "sinus_pressure", false),
        Symptom(55, "Runny Nose", "runny_nose", false),
        Symptom(56, "Congestion", "congestion", false),
        Symptom(57, "Chest Pain", "chest_pain", false),
        Symptom(58, "Weakness In Limbs", "weakness_in_limbs", false),
        Symptom(59, "Fast Heart Rate", "fast_heart_rate", false),
        Symptom(60, "Pain During Bowel Movements", "pain_during_bowel_movements", false),
        Symptom(61, "Pain In Anal Region", "pain_in_anal_region", false),
        Symptom(62, "Bloody Stool", "bloody_stool", false),
        Symptom(63, "Irritation In Anus", "irritation_in_anus", false),
        Symptom(64, "Neck Pain", "neck_pain", false),
        Symptom(65, "Dizziness", "dizziness", false),
        Symptom(66, "Cramps", "cramps", false),
        Symptom(67, "Bruising", "bruising", false),
        Symptom(68, "Obesity", "obesity", false),
        Symptom(69, "Swollen Legs", "swollen_legs", false),
        Symptom(70, "Swollen Blood Vessels", "swollen_blood_vessels", false),
        Symptom(71, "Puffy Face And Eyes", "puffy_face_and_eyes", false),
        Symptom(72, "Enlarged Thyroid", "enlarged_thyroid", false),
        Symptom(73, "Brittle Nails", "brittle_nails", false),
        Symptom(74, "Swollen Extremeties", "swollen_extremeties", false),
        Symptom(75, "Excessive Hunger", "excessive_hunger", false),
        Symptom(76, "Extra Marital Contacts", "extra_marital_contacts", false),
        Symptom(77, "Drying And Tingling Lips", "drying_and_tingling_lips", false),
        Symptom(78, "Slurred Speech", "slurred_speech", false),
        Symptom(79, "Knee Pain", "knee_pain", false),
        Symptom(80, "Hip Joint Pain", "hip_joint_pain", false),
        Symptom(81, "Muscle Weakness", "muscle_weakness", false),
        Symptom(82, "Stiff Neck", "stiff_neck", false),
        Symptom(83, "Swelling Joints", "swelling_joints", false),
        Symptom(84, "Movement Stiffness", "movement_stiffness", false),
        Symptom(85, "Spinning Movements", "spinning_movements", false),
        Symptom(86, "Loss Of Balance", "loss_of_balance", false),
        Symptom(87, "Unsteadiness", "unsteadiness", false),
        Symptom(88, "Weakness Of One Body Side", "weakness_of_one_body_side", false),
        Symptom(89, "Loss Of Smell", "loss_of_smell", false),
        Symptom(90, "Bladder Discomfort", "bladder_discomfort", false),
        Symptom(91, "Foul Smell Of Urine", "foul_smell_of urine", false),
        Symptom(92, "Continuous Feel of Urine", "continuous_feel_of_urine", false),
        Symptom(93, "Passage Of Gases", "passage_of_gases", false),
        Symptom(94, "Internal Itching", "internal_itching", false),
        Symptom(95, "Toxic Look (typhos)", "toxic_look_(typhos)", false),
        Symptom(96, "Depression", "depression", false),
        Symptom(97, "Irritability", "irritability", false),
        Symptom(98, "Muscle Pain", "muscle_pain", false),
        Symptom(99, "Altered Sensorium", "altered_sensorium", false),
        Symptom(100, "Red Spots Over Body", "red_spots_over_body", false),
        Symptom(101, "Belly Pain", "belly_pain", false),
        Symptom(102, "Abnormal Menstruation", "abnormal_menstruation", false),
        Symptom(103, "Dischromic Patches", "dischromic _patches", false),
        Symptom(104, "Watering from Eyes", "watering_from_eyes", false),
        Symptom(105, "Increased Appetite", "increased_appetite", false),
        Symptom(106, "Polyuria", "polyuria", false),
        Symptom(107, "Family History", "family_history", false),
        Symptom(108, "Mucoid Sputum", "mucoid_sputum", false),
        Symptom(109, "Rusty Sputum", "rusty_sputum", false),
        Symptom(110, "Lack of Concentration", "lack_of_concentration", false),
        Symptom(111, "Visual Disturbances", "visual_disturbances", false),
        Symptom(112, "Receiving Blood Transfusion", "receiving_blood_transfusion", false),
        Symptom(113, "Receiving Unsterile Injections", "receiving_unsterile_injections", false),
        Symptom(114, "Coma", "coma", false),
        Symptom(115, "Stomach Bleeding", "stomach_bleeding", false),
        Symptom(116, "Distention Of Abdomen", "distention_of_abdomen", false),
        Symptom(117, "History Of Alcohol Consumption", "history_of_alcohol_consumption", false),
        Symptom(118, "Fluid Overload", "fluid_overload", false),
        Symptom(119, "Blood In Sputum", "blood_in_sputum", false),
        Symptom(120, "Prominent Veins On Calf", "prominent_veins_on_calf", false),
        Symptom(121, "Palpitations", "palpitations", false),
        Symptom(122, "Painful Walking", "painful_walking", false),
        Symptom(123, "Pus Filled Pimples", "pus_filled_pimples", false),
        Symptom(124, "Blackheads", "blackheads", false),
        Symptom(125, "Scurring", "scurring", false),
        Symptom(126, "Skin Peeling", "skin_peeling", false),
        Symptom(127, "Silver Like Dusting", "silver_like_dusting", false),
        Symptom(128, "Small Dents In Nails", "small_dents_in_nails", false),
        Symptom(129, "Inflammatory Nails", "inflammatory_nails", false),
        Symptom(130, "Blister", "blister", false),
        Symptom(131, "Red Sore Around Nose", "red_sore_around_nose", false),
        Symptom(132, "Yellow Crust Ooze", "yellow_crust_ooze", false)
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelfDiagnosisBinding.inflate(inflater, container, false)

        val symptomRecyclerAdapter = SymptomRecyclerAdapter(symptomList, this)
        binding.symptomsRecyclerView.adapter = symptomRecyclerAdapter
        binding.symptomsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val searchText =
            binding.searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        val font = ResourcesCompat.getFont(requireContext(), R.font.montserrat)
        searchText.typeface = font
        searchText.setTextColor(ContextCompat.getColor(requireContext(), R.color.color4))
        searchText.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.color4))

        binding.searchView.setIconifiedByDefault(false)

        searchText.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        if (Build.VERSION.SDK_INT >= 29) {
            searchText.textCursorDrawable =
                AppCompatResources.getDrawable(requireContext(), R.drawable.cursor_2)
        } else {
            try {
                val f: Field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
                f.isAccessible = true
                f.set(searchText, R.drawable.cursor_2)
            } catch (_: Exception) {
            }
        }

        val icon = binding.searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        icon.setImageResource(R.drawable.ic_search_icon)


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                symptomRecyclerAdapter.filter.filter(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                symptomRecyclerAdapter.filter.filter(newText.toString())
                return false
            }

        })

        binding.nextBtn.setOnClickListener {
            val list = ArrayList<String>()
            symptomList.forEach{
                if(it.isPicked){
                    list.add(it.nameWithUnderScore)
                }
            }
            val bundle = Bundle()
            bundle.putStringArrayList("list", list)
            findNavController().navigate(R.id.action_selfDiagnosisFragment_to_predictionFragment, bundle)
        }

        binding.toolbar.findViewById<ImageView>(R.id.backBtn).setOnClickListener{
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(isPicked: Boolean, index: Int) {
        symptomList[index].isPicked = isPicked
    }
}