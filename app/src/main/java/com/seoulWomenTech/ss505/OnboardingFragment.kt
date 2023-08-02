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

    lateinit var fragmentOnboardingBinding: FragmentOnboardingBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentOnboardingBinding = FragmentOnboardingBinding.inflate(layoutInflater)


        val onboardingData = createOnboardingData()
        val onboardingAdapter = OnboardingAdapter(onboardingData)
        fragmentOnboardingBinding.run {

            btnNext.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MAIN_FRAGMENT,true,null)
            }

            viewPagerOnboarding.run {
                adapter = onboardingAdapter
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        // 마지막 페이지인지 확인
                        if (position == onboardingData.size - 1) {
                            fragmentOnboardingBinding.btnNext.visibility = View.VISIBLE
                        } else {
                            fragmentOnboardingBinding.btnNext.visibility = View.GONE
                        }
                    }
                })
            }


        }




        return fragmentOnboardingBinding.root
    }

    private fun createOnboardingData(): List<PaperOnboardingPage> {
        val onboardingData = mutableListOf<PaperOnboardingPage>()

        onboardingData.add(
            PaperOnboardingPage(
                "",
                "",
                R.color.white,
                R.drawable.sdi_default

            )
        )

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
