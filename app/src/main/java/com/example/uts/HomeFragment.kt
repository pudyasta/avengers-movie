package com.example.uts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uts.databinding.ActivityMainBinding
import com.example.uts.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY", null)

        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view= binding.root

        val films = mutableListOf<Films>(
            Films("Avengers:Endgame",
                        "Action, Superheroes, Adventure",
                        "Avengers: Endgame is a 2019 American superhero film based on the Marvel Comics superhero team the Avengers. Produced by Marvel Studios and distributed by Walt Disney Studios Motion Pictures, it is the direct sequel to Avengers: Infinity War (2018) and the 22nd film in the Marvel Cinematic Universe (MCU).",
                        R.drawable.avengers,2019,16,181,4.9),
            Films("Avengers:Age of Ultrom",
                "Action, Superheroes, Adventure",
                "Avengers: Age of Ultron is a 2015 American superhero film based on the Marvel Comics superhero team the Avengers. Produced by Marvel Studios and distributed by Walt Disney Studios Motion Pictures, it is the sequel to The Avengers (2012) and the 11th film in the Marvel Cinematic Universe (MCU).",
                R.drawable.ultron,
                2015,16,160,4.4
            ),
            Films("Avengers:Secret War",
                "Action, Superheroes, Adventure",
                "Will be the 46th film overall in the Marvel Cinematic Universe, which marks the final installment of Phase 6 and concludes The Multiverse Saga.",
                R.drawable.secret,
                2028,16,180,5.0),
            Films("Avengers:Infinity War",
                "Action, Superheroes, Adventure",
                "The Avengers must stop Thanos, an intergalactic warlord, from getting his hands on all the infinity stones. However, Thanos is prepared to go to any lengths to carry out his insane plan.",
                R.drawable.infinity,
                2018,16,180,4.9),
            Films("Captain America:Civil War",
                "Action, Superheroes, Adventure",
                "In 1991, the brainwashed super-soldier James \"Bucky\" Barnes is dispatched from a Hydra base in Siberia to intercept an automobile carrying a case of super-soldier serum. In the present day, approximately one year after Ultron is defeated by the Avengers in the nation of Sokovia,[a] Steve Rogers, Natasha Romanoff, Sam Wilson, and Wanda Maximoff stop Brock Rumlow",
                R.drawable.civil,
                2016,16,180,4.5),

        )

        val adapter = FilmAdapter(films)

        with(binding) {
            name.text = savedString.toString()
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(requireContext())
        }

        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}