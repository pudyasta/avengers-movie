package com.example.uts

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uts.api.network.ConnectivityObserver
import com.example.uts.api.network.NetworkConnectivityObserver
import com.example.uts.databinding.FragmentHomeBinding
import com.example.uts.firestoreapp.FirebaseInstance.Companion.MOVIES
import com.example.uts.model.Films
import com.example.uts.room.FilmDB
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    private lateinit var filmList:ArrayList<Films>
    private lateinit var seriesList:ArrayList<Films>
    private lateinit var adapter1: FilmAdapter
    private lateinit var adapter2: FilmAdapter
    private val db by lazy { FilmDB(requireContext()) }
    private lateinit var observer: ConnectivityObserver
    private var stats = "Unavailable"


    private val filmsListLiveData: MutableLiveData<List<Films>> by lazy {
        MutableLiveData<List<Films>>()
    }
    private val seriesListLiveData: MutableLiveData<List<Films>> by lazy {
        MutableLiveData<List<Films>>()
    }
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

        observer = NetworkConnectivityObserver(requireContext().applicationContext)
        lifecycleScope.launch {
            observer.observe().collect { status ->
                when (status) {
                    ConnectivityObserver.Status.Available -> {
                        stats = ConnectivityObserver.Status.Available.toString()
                    }
                    ConnectivityObserver.Status.Losing -> {
                        stats = ConnectivityObserver.Status.Losing.toString()
                    }
                    ConnectivityObserver.Status.Lost -> {
                        stats = ConnectivityObserver.Status.Lost.toString()
                    }
                    ConnectivityObserver.Status.Unavailable -> {
                        stats = ConnectivityObserver.Status.Unavailable.toString()
                    }

                }
                eventChangeListener()
            }
        }

        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view= binding.root

        with(binding) {
            filmList = arrayListOf()
            seriesList = arrayListOf()
            adapter1 = FilmAdapter(filmList)

            name.text = savedString.toString()
            recycler.adapter = adapter1
            recycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)

            adapter2 = FilmAdapter(seriesList)
            recyclerSeries.adapter = adapter2
            recyclerSeries.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)

            layout.setOnRefreshListener {
                eventChangeListener()
                observeFilms()
                layout.isRefreshing = false
            }

        }
        observeFilms()
        return view

    }

    override fun onStart() {
        super.onStart()
        eventChangeListener()
    }

    private fun observeFilms() {
        filmsListLiveData.observe(requireActivity()) { f ->
            adapter1.setData(f.toMutableList())
        }
        seriesListLiveData.observe(requireActivity()) { f ->
            adapter2.setData(f.toMutableList())
        }
    }

    private fun eventChangeListener() {
        CoroutineScope(Dispatchers.Main).launch {
            val item =  db.filmDao().getAllNotes()
            val films2 = item.filter { it.type == "Film" }
            val series2 = item.filter { it.type == "Series" }
            filmsListLiveData.postValue(films2)
            seriesListLiveData.postValue(series2)
        }

        if (stats == ConnectivityObserver.Status.Available.toString()){
            MOVIES.orderBy("year", Query.Direction.DESCENDING).addSnapshotListener{ snapshots, error ->
                if (error != null) {
                    Log.d("MainActivity", "Error listening for budget changes: ", error)
                    return@addSnapshotListener
                }
                val item = snapshots?.toObjects(Films::class.java)

                if (item != null) {
                    val films2 = item.filter { it.type == "Film" }
                    val series2 = item.filter { it.type == "Series" }
                    filmsListLiveData.postValue(films2)
                    seriesListLiveData.postValue(series2)
                    CoroutineScope(Dispatchers.Main).launch {
                        db.filmDao().clearAll()
                        db.filmDao().insertAll(item)
                    }
                }
            }
        }

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