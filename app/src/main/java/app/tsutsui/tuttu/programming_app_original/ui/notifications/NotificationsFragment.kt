package app.tsutsui.tuttu.programming_app_original.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.os.Handler
import app.tsutsui.tuttu.programming_app_original.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val timeTextView=binding.timeTextView

        val handler=Handler()
        var timeValue=0

        val runnable=object:Runnable{
            override fun run(){
                timeValue++
                timeToText(timeValue)?.let{
                    timeTextView.text=it
                }

                handler.postDelayed(this,1000)

            }
        }

        binding.buttonStart.setOnClickListener {
            handler.post(runnable)
        }

        binding.buttonStop.setOnClickListener {
            handler.removeCallbacks(runnable)
        }

        binding.buttonReset.setOnClickListener {
            handler.removeCallbacks(runnable)



            timeValue=0
            timeToText()?.let{
                timeTextView.text=it
            }


        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun timeToText(time:Int=0):String?{
        return if (time<0){
            null
        }else if (time==0){
            "00:00:00"
        }else{
            val h=time/3600
            val m=time%3600/60
            val s=time%60
            "%1$02d:%2$02d:%3$02d".format(h,m,s)
        }
    }

}