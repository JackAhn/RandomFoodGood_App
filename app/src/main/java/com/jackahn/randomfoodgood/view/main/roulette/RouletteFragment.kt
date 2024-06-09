package com.jackahn.randomfoodgood.view.main.roulette

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jackahn.randomfoodgood.dao.PlaceResult
import com.jackahn.randomfoodgood.databinding.FragmentRouletteBinding
import com.jackahn.randomfoodgood.view.main.MainActivity
import com.jackahn.randomfoodgood.view.main.home.DetailFoodActivity
import kotlinx.coroutines.selects.select
import java.util.Random

class RouletteFragment : Fragment() {

    private var _binding: FragmentRouletteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var result = ArrayList<PlaceResult>()
    private var number: Int = -1
    private lateinit var random: Random

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRouletteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        result = (requireActivity() as MainActivity).getResult()
        random = Random()
        init()
    }


    private fun init(){
        origin()

        binding.startRouletteBtn.setOnClickListener {
            reroll()
        }

        binding.rouletteResetBtn.setOnClickListener {
            origin()
        }
    }

    // 초기 상태
    private fun origin(){
        binding.rouletteText1.visibility = View.INVISIBLE
        binding.roulettePlaceText.visibility = View.INVISIBLE
        binding.rouletteText2.visibility = View.INVISIBLE
        binding.rouletteShareBtn.visibility = View.INVISIBLE
        binding.rouletteResetBtn.visibility = View.INVISIBLE
        binding.imageView2.visibility = View.INVISIBLE
        binding.rouletteAddrText.visibility = View.INVISIBLE

        binding.startRouletteBtn.visibility = View.VISIBLE
    }

    // 룰렛 결과 표시
    private fun reroll(){
        binding.rouletteText1.visibility = View.VISIBLE
        binding.roulettePlaceText.visibility = View.VISIBLE
        binding.rouletteText2.visibility = View.VISIBLE
        binding.rouletteShareBtn.visibility = View.VISIBLE
        binding.rouletteResetBtn.visibility = View.VISIBLE
        binding.imageView2.visibility = View.VISIBLE
        binding.imageView2.visibility = View.VISIBLE
        binding.rouletteAddrText.visibility = View.VISIBLE

        binding.startRouletteBtn.visibility = View.INVISIBLE

        number = random.nextInt(result.count())

        val selected = result.get(number)
        binding.roulettePlaceText.text = selected.place_name
        binding.rouletteAddrText.text = selected.road_address_name

        //공유하기 버튼
        binding.rouletteShareBtn.setOnClickListener {
            val textIntent = Intent(Intent.ACTION_SEND)
            textIntent.addCategory(Intent.CATEGORY_DEFAULT)
            textIntent.setType("text/plain")

            textIntent.putExtra(Intent.EXTRA_TEXT, selected.place_name + "\n" + selected.place_url)

            val shareIntent = Intent.createChooser(textIntent, "공유하기")
            startActivity(shareIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}