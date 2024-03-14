package Avataryug.Example.QuickTest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.Avataryug.client.Handler.AdvertisingHandler
import com.Avataryug.client.Models.GetAdPlacementByIDResult
import com.Avataryug.client.Models.GrantAdsRewardResult
import com.Avataryug.client.Models.RecordAdsActivityResult
import com.example.androidkotlinsdk.R

/**
 * This ExampleAdvertising Class demonstrates API calling methods with temporary data.
 * A simple [Fragment] subclass.
 * Use the [ExampleAdvertising.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExampleAdvertising : Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_example_advertising, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val getAdPlacementByIDBtn = view.findViewById<Button>(R.id.GetAdPlacementByIDBtn)
        getAdPlacementByIDBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Get Ad Placement By ID..", Toast.LENGTH_LONG).show()
            GetAdPlacementByID()
        }

        val grantAdsRewardBtn = view.findViewById<Button>(R.id.GrantAdsRewardBtn)
        grantAdsRewardBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Grant Ads Reward..", Toast.LENGTH_LONG).show()
            GrantAdsReward()
        }

        val recordAdsActivityBtn = view.findViewById<Button>(R.id.RecordAdsActivityBtn)
        recordAdsActivityBtn.setOnClickListener {
            Toast.makeText(requireContext(),"Record Ads Activity..", Toast.LENGTH_LONG).show()
            RecordAdsActivity()
        }
    }

    /**
     * Retrieves a list of ad placements by ID.
     */
    fun GetAdPlacementByID()
    {
        val adPlacementID = "a363728f-db74-425b-8f45-942e4c8f2a77"
        val adverHandler = AdvertisingHandler(AdvertisingHandler.GetAdPlacementByID(adPlacementID))

        adverHandler.getAdPlacementByID(object : AdvertisingHandler.OnGetAdPlacementByIDListener {
            override fun onGetAdPlacementByIDResult(result: GetAdPlacementByIDResult) {
                // Handle API response here
                val responseText = "API Response: $result"
                Log.i("getAdPlacementByID Response--", responseText)
            }

            override fun onGetAdPlacementByIDError(error: Exception) {
                // Handle API error here
                val errorText = "API Error: ${error.message}"
                Log.e("getAdPlacementByID ERROR--", errorText)
            }
        })
    }

    /**
     * Grants rewards for ads.
     */
    fun GrantAdsReward()
    {
        val adverHandler = AdvertisingHandler(AdvertisingHandler.GrantAdsReward("a363728f-db74-425b-8f45-942e4c8f2a77"))
        adverHandler.grantAdsReward(object : AdvertisingHandler.OnGrantAdsRewardResultListener{
            override fun onGrantAdsRewardResult(result: GrantAdsRewardResult) {
                // Handle API response here
                val responseText = "API Response: $result"
                Log.i("GrantAdsReward Response--", responseText)
            }
            override fun onGrantAdsRewardError(error: Exception) {
                // Handle API error here
                val errorText = "API Error: ${error.message}"
                Log.e("GrantAdsReward ERROR--", errorText)
            }

        })
    }

    /**
     * Stores ads activity data for reporting after ad watch.
     */
    fun RecordAdsActivity()
    {
        val adverHandler = AdvertisingHandler(AdvertisingHandler.RecordAdsActivity("CN",1,"Placement02848"))
        adverHandler.recordAdsActivity(object : AdvertisingHandler.OnRecordAdsActivityResultListener{
            override fun onRecordAdsActivityResult(result: RecordAdsActivityResult) {
                val responseText = "API Response: $result"
                Log.i("RecordAdsActivity Response--", responseText)
            }

            override fun onRecordAdsActivityError(error: Exception) {
                val errorText = "API Error: ${error.message}"
                Log.e("RecordAdsActivity ERROR--", errorText)
            }

        })

    }
}