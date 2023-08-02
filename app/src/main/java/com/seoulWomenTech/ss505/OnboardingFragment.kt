import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.seoulWomenTech.ss505.MainActivity
import com.seoulWomenTech.ss505.PaperOnboardingPage
import com.seoulWomenTech.ss505.R
import com.seoulWomenTech.ss505.databinding.FragmentOnboardingBinding
import com.seoulWomenTech.ss505.databinding.ItemOnboardingSlideBinding


class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private lateinit var viewPager: ViewPager2
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.viewPagerOnboarding

        val onboardingData = createOnboardingData()
        val onboardingAdapter = OnboardingAdapter(onboardingData)
        viewPager.adapter = onboardingAdapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // 마지막 페이지인지 확인
                if (position == onboardingData.size - 1) {
                    binding.btnNext.visibility = View.VISIBLE
                } else {
                    binding.btnNext.visibility = View.GONE
                }
            }
        })
    }

    private fun createOnboardingData(): List<PaperOnboardingPage> {
        val onboardingData = mutableListOf<PaperOnboardingPage>()

        onboardingData.add(
            PaperOnboardingPage(
                "",
                "",
                R.color.white,
                R.drawable.sdi_1

            )
        )

        onboardingData.add(
            PaperOnboardingPage(
                "",
                "",
                R.color.white,
                R.drawable.sdi_2

            )
        )

        onboardingData.add(
            PaperOnboardingPage(
                "",
                "",
                R.color.white,
                R.drawable.sdi_3

            )
        )

        return onboardingData
    }


    inner class OnboardingAdapter(private val onboardingData: List<PaperOnboardingPage>) :

        RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

        inner class OnboardingViewHolder(private val binding: ItemOnboardingSlideBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(page: PaperOnboardingPage) {
                binding.onboardingImageView.setImageResource(page.bgImageRes)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemOnboardingSlideBinding.inflate(inflater, parent, false)
            return OnboardingViewHolder(binding)
        }

        override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
            holder.bind(onboardingData[position])

        }

        override fun getItemCount(): Int = onboardingData.size
    }

}
