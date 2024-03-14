package Avataryug.Example.QuickTest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.Avataryug.client.Handler.EconomyHandler
import com.Avataryug.client.Handler.EconomyHandler.GetEconomyBundleByID
import com.Avataryug.client.Handler.EconomyHandler.GetEconomyBundles
import com.Avataryug.client.Handler.EconomyHandler.GetEconomyContainerByID
import com.Avataryug.client.Handler.EconomyHandler.GetEconomyContainers
import com.Avataryug.client.Handler.EconomyHandler.GetEconomyItems
import com.Avataryug.client.Handler.EconomyHandler.GetEconomyItemsByID
import com.Avataryug.client.Handler.EconomyHandler.GetEconomyStores
import com.Avataryug.client.Handler.EconomyHandler.GetStoreItemsByID
import com.Avataryug.client.Handler.EconomyHandler.OnGetEconomyBundleByIDListener
import com.Avataryug.client.Handler.EconomyHandler.OnGetEconomyBundlesListener
import com.Avataryug.client.Handler.EconomyHandler.OnGetEconomyContainerByIDListener
import com.Avataryug.client.Handler.EconomyHandler.OnGetEconomyContainersListener
import com.Avataryug.client.Handler.EconomyHandler.OnGetEconomyItemsByIDListener
import com.Avataryug.client.Handler.EconomyHandler.OnGetEconomyStoresListener
import com.Avataryug.client.Handler.EconomyHandler.OnGetStoreItemsByIDListener
import com.Avataryug.client.Models.GetEconomyBundleByIDResult
import com.Avataryug.client.Models.GetEconomyBundlesResult
import com.Avataryug.client.Models.GetEconomyContainerByIDResult
import com.Avataryug.client.Models.GetEconomyItemsByIDResult
import com.Avataryug.client.Models.GetEconomyItemsResult
import com.Avataryug.client.Models.GetEconomyStoresResult
import com.Avataryug.client.Models.GetStoreItemsByIDResult
import com.example.androidkotlinsdk.R


/**
 *  This ExampleEconomy Class demonstrates API calling methods with temporary data.
 * A simple [Fragment] subclass.
 * Use the [ExampleEconomy.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExampleEconomy : Fragment()
{
    private var itemsByids:String = ""
    private var bundlesByids:String = ""
    private var containersByids:String = ""
    private var storesByids:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_example_economy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val economyItemsBtn = view.findViewById<Button>(R.id.EconomyItemsBtn)
        economyItemsBtn.setOnClickListener {
            GetEconomyItems()
            // Handle button click here
            Toast.makeText(requireContext(), "EconomyItemsBtn Clicked!", Toast.LENGTH_SHORT).show()
        }

        val getEconomyItembyIdsBtn  = view.findViewById<Button>(R.id.EconomyItemsByIDsBtn)
        getEconomyItembyIdsBtn.setOnClickListener {
            GetEconomyItemsbyIds()
            Toast.makeText(activity, "Get Economy Items", Toast.LENGTH_SHORT).show()
        }

        val getBundlesBtn: Button = view.findViewById<Button>(R.id.EconomyBundlesBtn)
        getBundlesBtn.setOnClickListener {
            GetBundles()
            Toast.makeText(activity, "Get Bundles Items", Toast.LENGTH_SHORT).show()
        }

        val getBundlesbyidBtn: Button = view.findViewById<Button>(R.id.EconomyBundleByIDBtn)
        getBundlesbyidBtn.setOnClickListener {
            GetBundlesByIDs()
            Toast.makeText(activity, "Get Bundles By IDs", Toast.LENGTH_SHORT).show()
        }

        val getContainersBtn: Button = view.findViewById<Button>(R.id.EconomyContainersBtn)
        getContainersBtn.setOnClickListener {
            GetContainers()
            Toast.makeText(activity, "Get Containers ", Toast.LENGTH_SHORT).show()
        }

        val getContainersbyidBtn: Button =
            view.findViewById<Button>(R.id.EconomyContainersByIDBtn)
        getContainersbyidBtn.setOnClickListener {
            GetContainersByIDs()
            Toast.makeText(activity, "Get Containers By IDs", Toast.LENGTH_SHORT).show()
        }

        val getStoresBtn: Button = view.findViewById<Button>(R.id.EconomyStoresBtn)
        getStoresBtn.setOnClickListener {
            GetEconomyStore()
            Toast.makeText(activity, "Get Store ", Toast.LENGTH_SHORT).show()
        }

        val getStoresbyidBtn: Button = view.findViewById<Button>(R.id.EconomyStoresByIDBtn)
        getStoresbyidBtn.setOnClickListener {
            GetEconomyStoreById()
            Toast.makeText(activity, "Get Store By IDs", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Get Economy Items.
     */
    fun GetEconomyItems()
    {
        val handler = EconomyHandler(GetEconomyItems("Top", 1, 2,0,500))
        handler.getEconomyItems(object :EconomyHandler.OnGetEconomyItemsListener
        {
            override fun onGetEconomyItemsResult(result: GetEconomyItemsResult)
            {
                val responseText = "API Response: $result"
                itemsByids = result.data!![0].ID
                Log.i("GetEconomyItems Response--", responseText)
            }
            override fun onGetEconomyItemsError(error: Exception)
            {
                val errorText = "API Error: ${error.message}"
                Log.e("GetEconomyItems ERROR--", errorText)
            }
        })
    }

    /**
     * Get Economy Item by ID.
     */
    fun GetEconomyItemsbyIds()
    {
        val handler = EconomyHandler(GetEconomyItemsByID(itemsByids))
        handler.getEconomyItemsByID(object : OnGetEconomyItemsByIDListener
        {
            override fun onGetEconomyItemsByIDResult(result: GetEconomyItemsByIDResult)
            {
                val responseText = "API Response: $result"
                Log.i("getEconomyItemsByID Response--", responseText)
            }
            override fun onGetEconomyItemsByIDError(error: Exception)
            {
                val errorText = "API Error: ${error.message}"
                Log.e("GetEconomyItemsbyIds ERROR--", errorText)
            }
        })
    }

    /**
     * Get Economy Bundles.
     */
    fun GetBundles()
    {
        val handler = EconomyHandler(GetEconomyBundles(1))
        handler.getEconomyBundles(object : OnGetEconomyBundlesListener
        {
            override fun onGetEconomyBundlesResult(result: GetEconomyBundlesResult)
            {
                bundlesByids = result.data!![0].ID
                val responseText = "API Response: $result"
                Log.i("GetBundles Response--", responseText)
            }

            override fun onGetEconomyBundlesError(error: Exception)
            {
                val errorText = "API Error: ${error.message}"
                Log.e("GetBundles ERROR--", errorText)
            }
        })
    }

    /**
     * Get Economy Bundles by ID.
     */
    fun GetBundlesByIDs()
    {
        val handler = EconomyHandler(GetEconomyBundleByID(bundlesByids))
        handler.getEconomyBundleByID(object : OnGetEconomyBundleByIDListener {
            override fun GetEconomyBundleByIDResult(result: GetEconomyBundleByIDResult) {
                val responseText = "API Response: $result"
                Log.i("GetBundlesByIDs Response--", responseText)
            }

            override fun onGetEconomyBundleByIDError(error: Exception) {
                val errorText = "API Error: ${error.message}"
                Log.e("GetBundlesByIDs ERROR--", errorText)
            }
        })
    }

    /**
     * Get Economy Containers.
     */
    fun GetContainers()
    {
        val handler = EconomyHandler(GetEconomyContainers(1))
        handler.getEconomyContainers(object : OnGetEconomyContainersListener
        {
            override fun onGetEconomyContainersResult(result: GetEconomyBundleByIDResult)
            {
                containersByids = result.data?.ID!!
                val responseText = "API Response: $result"
                Log.i("GetContainers Response--", responseText)
            }
            override fun onGetEconomyBundleByIDError(error: Exception)
            {
                val errorText = "API Error: ${error.message}"
                Log.e("GetContainers ERROR--", errorText)
            }
        })
    }

    /**
     * Get Economy Container by ID
     */
    fun GetContainersByIDs()
    {
        val handler = EconomyHandler(GetEconomyContainerByID(containersByids))
        handler.getEconomyContainerByID(object : OnGetEconomyContainerByIDListener
        {
            override fun onGetEconomyContainerByIDResult(result: GetEconomyContainerByIDResult)
            {
                val responseText = "API Response: $result"
                Log.i("GetContainersByIDs Response--", responseText)
            }
            override fun onGetEconomyContainerByIDError(error: Exception)
            {
                val errorText = "API Error: ${error.message}"
                Log.e("GetContainersByIDs ERROR--", errorText)
            }
        })
    }

    /**
     * Get Economy Stores
     */
    fun GetEconomyStore()
    {
        val handler = EconomyHandler(GetEconomyStores(1))
        handler.getEconomyStores(object : OnGetEconomyStoresListener
        {
            override fun onGetEconomyStoresResult(result: GetEconomyStoresResult)
            {
                val responseText = "API Response: $result"
                storesByids = result.data?.get(0)!!.ID
                Log.i("GetEconomyStore Response--", responseText)
            }
            override fun onGetEconomyStoresError(error: Exception)
            {
                val errorText = "API Error: ${error.message}"
                Log.e("GetEconomyStore ERROR--", errorText)
            }
        })
    }

    /**
     * Retrieves the set of items defined for the specified store, including all prices defined.
     */
    fun GetEconomyStoreById()
    {
        val handler = EconomyHandler(GetStoreItemsByID(storesByids))
        handler.getStoreItemsByID(object : OnGetStoreItemsByIDListener
        {
            override fun onGetStoreItemsByIDResult(result: GetStoreItemsByIDResult)
            {
                val responseText = "API Response: $result"
                Log.i("GetEconomyStore Response--", responseText)
            }
            override fun onGetStoreItemsByIDError(error: Exception)
            {
                val errorText = "API Error: ${error.message}"
                Log.e("GetEconomyStore ERROR--", errorText)
            }
        })
    }
}