package com.br.impacta.doemais.ui.campanha

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.br.impacta.doemais.R
import com.br.impacta.doemais.databinding.FragmentCampanhaBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CampanhaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CampanhaFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentCampanhaBinding
    private var param1: String? = null
    private var param2: String? = null
    private var latitude = -23.6463982
    private var longitude = -46.7324468

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        binding = FragmentCampanhaBinding.inflate(inflater, container, false)





        return binding.root
    }

    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        val viewModel: CampanhaViewModel by activityViewModels()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        //binding.toolbarLayout.title = title
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        binding.btnConfirm2.setOnClickListener {
           viewModel.onClickConfirm()
        }

        val behavior = AppBarLayout.Behavior()
        behavior.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return false
            }
        })
        val params = binding.appBar.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = behavior


        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_campanha_fragment) as NavHostFragment
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        supportMapFragment?.onResume()
            viewModel.liveDataLocation.observe(viewLifecycleOwner) { hashmap ->
                hashmap.entries.forEach { index ->
                    binding.btnConfirm2.text = getString(R.string.confirmar, index.key.name)
                    if (index.value) {

                        val params =  binding.appBar.layoutParams
                        //params.height = tal.height // EXPANDED_HEIGHT

                        binding.appBar.layoutParams = params
                        binding.appBar.setExpanded(true)
                        val bundle = Bundle()
                        bundle.putString("name", index.key.name)
                        bundle.putString("location", index.key.collectPoint)
                        bundle.putString("key", index.key.key)
                        bundle.putString("donation", index.key.type)
                        bundle.putCharSequence("description", index.key.description)
                        bundle.putString("id", index.key.id)
                        bundle.putString("id2", index.key.id2)
                        //bundle.putString("title", index.key.title)


                        supportMapFragment?.getMapAsync(this)


                        navHostFragment.navController.navigate(R.id.action_locationFragment_to_detailFragment, bundle)
                        lifecycleScope.launch {

                        }
                    }}
        }

        viewModel.liveDataButton.observe(viewLifecycleOwner) {
            if (it) {
            binding.btnConfirm2.visibility = View.VISIBLE
            } else {
                binding.btnConfirm2.visibility = View.GONE
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
         * @return A new instance of fragment CampanhaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CampanhaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        // Add a marker in Sydney and move the camera
        val coordinator = LatLng(latitude, longitude)
        mMap.addMarker(MarkerOptions().position(coordinator).title("Centro de São Paulo"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinator))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(13.0F))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_scrolling, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}