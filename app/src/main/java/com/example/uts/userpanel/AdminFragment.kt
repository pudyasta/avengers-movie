package com.example.uts.userpanel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uts.AddFilmActivity
import com.example.uts.api.network.ConnectivityObserver
import com.example.uts.api.network.NetworkConnectivityObserver
import com.example.uts.databinding.FragmentAdminBinding
import com.example.uts.firestoreapp.FirebaseInstance.Companion.MOVIES
import com.example.uts.model.Films
import com.example.uts.recyclers.FilmRecyclerAdapter
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
 * Use the [AdminFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAdminBinding? = null
    private lateinit var filmList:ArrayList<Films>
    private lateinit var adapter:FilmRecyclerAdapter
    private val filmsListLiveData: MutableLiveData<List<Films>> by lazy {
        MutableLiveData<List<Films>>()
    }
    private lateinit var observer: ConnectivityObserver
    private var stats = "Unavailable"
    private val db by lazy { FilmDB(requireContext()) }

    private val binding get() = _binding!!

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
    ): View? {
        _binding = FragmentAdminBinding.inflate(inflater, container, false)
        val view = binding.root
        setupRecyclerView()
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
        observeFilms()
        setupListener()
        return view
    }

    override fun onStart() {
        super.onStart()
        eventChangeListener()
    }
    private fun observeFilms() {
        filmsListLiveData.observe(requireActivity()) { pengaduan ->
            adapter.setData(pengaduan.toMutableList())
        }
    }
    private fun eventChangeListener() {
        CoroutineScope(Dispatchers.Main).launch {
            val item =  db.filmDao().getAllNotes()
            val films2 = item.filter { it.type == "Film" }
            val series2 = item.filter { it.type == "Series" }
            with(binding) {
                filmStored.text = films2.size.toString()
                seriesStored.text = series2.size.toString()
            }
            filmsListLiveData.postValue(item)
        }

        if (stats == ConnectivityObserver.Status.Available.toString()) {

            MOVIES.orderBy("year", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        Log.d("MainActivity", "Error listening for budget changes: ", error)
                        return@addSnapshotListener
                    }
                    val item = snapshots?.toObjects(Films::class.java)
                    if (item != null) {
                        val films2 = item.filter { it.type == "Film" }
                        val series2 = item.filter { it.type == "Series" }
                        with(binding) {
                            filmStored.text = films2.size.toString()
                            seriesStored.text = series2.size.toString()
                        }

                        filmsListLiveData.postValue(item)
                    }
                }
        }
    }

    private fun intentEdit(id:String, intentType:Int){
        startActivity(Intent(requireContext(),AddFilmActivity::class.java).putExtra("id",id).putExtra("intentType",intentType))
    }

    private fun setupRecyclerView(){
        filmList = arrayListOf()
        adapter = FilmRecyclerAdapter(filmList,object :FilmRecyclerAdapter.OnAdapterListener{
            override fun onClick(f: Films) {
                intentEdit(f.id,0)
            }

            override fun onUpdate(f: Films) {
                intentEdit(f.id,2)
            }

            override fun onDelete(f: Films) {
                deleteDialog(f)
            }

        })
        with(binding){
            filmrecycler.adapter = adapter
            filmrecycler.layoutManager=LinearLayoutManager(requireContext())

            buttonCreate.setOnClickListener(){
                intentEdit("0",1)
            }

        }
    }


    private  fun setupListener(){
        with(binding){
            layout.setOnRefreshListener {
                eventChangeListener()
                observeFilms()
                layout.isRefreshing = false

            }
        }
    }

    private fun deleteFilm(f: Films) {
        if (f.id.isEmpty()) {
            Log.d("MainActivity", "Error deleting: budget ID is empty!")
            return
        }
        MOVIES.document(f.id).delete().addOnSuccessListener {
            Log.d("oo", "Film berhasil dihapus")

        }
            .addOnFailureListener {
                Log.d("MainActivity", "Error deleting budget: ", it)
            }
    }
    private fun deleteDialog(f: Films){
        val alertDialog= AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Apakah anda yakin?")
            setNegativeButton("Batal"){dialogInterface,i->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus"){dialogInterface,i->
                dialogInterface.dismiss()
                deleteFilm(f)
            }
        }
        alertDialog.show()
    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}