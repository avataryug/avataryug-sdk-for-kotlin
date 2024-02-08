package Avataryug.Example.QuickTest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.Avataryug.client.Handler.AvatarManagementHandler
import com.Avataryug.client.Models.GenerateAvatarMeshResult
import com.Avataryug.client.Models.GetAllBucketVerticesResult
import com.Avataryug.client.Models.GetAvatarPresetByIDRequest
import com.Avataryug.client.Models.GetAvatarPresetsResult
import com.Avataryug.client.Models.GetClipsByIDResult
import com.Avataryug.client.Models.GetClipsResult
import com.Avataryug.client.Models.GetExpressionByIDResult
import com.Avataryug.client.Models.GetExpressionsResult
import com.Avataryug.client.Models.GrantAvatarPresetItemsToUserRequest
import com.Avataryug.client.Models.GrantAvatarPresetItemsToUserResult
import com.Avataryug.client.Models.GrantAvatarPresetToUserRequest
import com.Avataryug.client.Models.GrantAvatarPresetToUserResult
import com.Avataryug.client.Models.RenderAvatarImageRequest
import com.Avataryug.client.Models.RenderAvatarImageResult
import com.Avataryug.client.Models.SyncAvatarsRequest
import com.Avataryug.client.Models.SyncAvatarsResult
import com.example.mykotlinandroidsdk.R

/**
 * This ExampleAvatarmanagement Class demonstrates API calling methods with temporary data.
 * A simple [Fragment] subclass.
 * Use the [ExampleAvatarmanagement.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExampleAvatarmanagement : Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_example_avatarmanagement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val generateAvatarMeshBtn: Button = view.findViewById<Button>(R.id.GenerateAvatarMeshBtn)
        generateAvatarMeshBtn.setOnClickListener {
            GenerateAvatarMesh()
            Toast.makeText(activity, "Generate AvatarMesh", Toast.LENGTH_SHORT).show()
        }

        val getAvatarPresetsBtn: Button = view.findViewById<Button>(R.id.GetAvatarPresetsBtn)
        getAvatarPresetsBtn.setOnClickListener {
            GetAvatarPresets()
            Toast.makeText(activity, "Get Avatar Presets", Toast.LENGTH_SHORT).show()
        }

        val getClipsBtn: Button = view.findViewById<Button>(R.id.GetClipsBtn)
        getClipsBtn.setOnClickListener {
            GetClips()
            Toast.makeText(activity, "Get Clips", Toast.LENGTH_SHORT).show()
        }

        val getClipsByIDBtn: Button = view.findViewById<Button>(R.id.GetClipsByIDBtn)
        getClipsByIDBtn.setOnClickListener {
            GetClipsByID()
            Toast.makeText(activity, "Get Clips By ID", Toast.LENGTH_SHORT).show()
        }

        val getExpressionsBtn: Button = view.findViewById<Button>(R.id.GetExpressionsBtn)
        getExpressionsBtn.setOnClickListener {
            GetExpressions()
            Toast.makeText(activity, "Get Expressions", Toast.LENGTH_SHORT).show()
        }

        val getExpressionsByIDBtn: Button = view.findViewById<Button>(R.id.GetExpressionsByIDBtn)
        getExpressionsByIDBtn.setOnClickListener {
            GetExpressionByID()
            Toast.makeText(activity, "Get Expression By ID", Toast.LENGTH_SHORT).show()
        }

        val getAllBucketVerticesBtn: Button = view.findViewById<Button>(R.id.GetAllBucketVerticesBtn)
        getAllBucketVerticesBtn.setOnClickListener {
            GetAllBucketVertices()
            Toast.makeText(activity, "Get All Bucket Vertices", Toast.LENGTH_SHORT).show()
        }

        val grantAvatarPresetItemsToUserBtn: Button = view.findViewById<Button>(R.id.GrantAvatarPresetItemsToUserBtn)
        grantAvatarPresetItemsToUserBtn.setOnClickListener {
            GrantAvatarPresetItemsToUser()
            Toast.makeText(activity, "Grant Avatar Preset Items To User", Toast.LENGTH_SHORT).show()
        }

        val renderAvatarImageBtn: Button = view.findViewById<Button>(R.id.RenderAvatarImageBtn)
        renderAvatarImageBtn.setOnClickListener {
            RenderAvatarImage()
            Toast.makeText(activity, "Render Avatar Image", Toast.LENGTH_SHORT).show()
        }

        val syncAvatarsBtn: Button = view.findViewById<Button>(R.id.SyncAvatarsBtn)
        syncAvatarsBtn.setOnClickListener {
            SyncAvatars()
            Toast.makeText(activity, "Sync Avatars", Toast.LENGTH_SHORT).show()
        }

        val grantAvatarPresetToUserBtn: Button = view.findViewById<Button>(R.id.GrantAvatarPresetToUserBtn)
        grantAvatarPresetToUserBtn.setOnClickListener {
            GrantAvatarPresetToUser()
            Toast.makeText(activity, "Grant Avatar Preset To User", Toast.LENGTH_SHORT).show()
        }

        val getAvatarPresetsByIDBtn: Button = view.findViewById<Button>(R.id.GetAvatarPresetsByIDBtn)
        getAvatarPresetsByIDBtn.setOnClickListener {
            GetAvatarPresetsByID()
            Toast.makeText(activity, "Get Avatar Presets By ID", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Generates the 3D mesh as per the configuration in the Config panel.
     */
    private fun GenerateAvatarMesh()
    {
        val handler = AvatarManagementHandler(AvatarManagementHandler.GenerateAvatarMesh("sjfj", "Android"))
        handler.generateAvatarMesh(object : AvatarManagementHandler.OnGenerateAvatarMeshListener {
            override fun onGenerateAvatarMeshResult(result: GenerateAvatarMeshResult) {
                if (result != null) {
                    val responseText = "onGenerateAvatarMesh Response: $result"
                    Log.i("Result--", responseText)
                }
            }
            override fun onGenerateAvatarMeshError(error: Exception) {
                val errorText = "onGenerateAvatarMesh Error: " + error.message
                Log.e(" Error--", errorText)
            }
        })
    }

    /**
     * Get all avatar presets.
     */
    private fun GetAvatarPresets()
    {
        val handler = AvatarManagementHandler(AvatarManagementHandler.GetAvatarPresets(1, 1))
        handler.getAvatarPresets(object : AvatarManagementHandler.OnGetAvatarPresetsListener
        {
            override fun onGetAvatarPresetsResult(result: GetAvatarPresetsResult)
            {
                if (result != null) {
                    val responseText = "onGetAvatarPresets Response: $result"
                    Log.i("Result--", responseText)
                }
            }

            override fun onGetAvatarPresetsError(error: Exception)
            {
                val errorText = "onGetAvatarPresets Error: " + error.message
                Log.e(" Error--", errorText)
            }

        })
    }

    /**
     * Get all the clips by Active status.
     */
    private fun GetClips()
    {
        val handler = AvatarManagementHandler(AvatarManagementHandler.GetClips(1))
        handler.getClips(object : AvatarManagementHandler.OnGetClipsListener
        {
            override fun onGetClipsResult(result: GetClipsResult)
            {
                if (result != null)
                {
                    val responseText = "onGetClips Response: $result"
                    Log.i("Result--", responseText)
                }
            }
            override fun onGetClipsError(error: Exception)
            {
                val errorText = "onGetClips Error: " + error.message
                Log.e(" Error--", errorText)
            }

        })
    }

    /**
     * Get the specified clip's details by providing ClipID.
     */
    private fun GetClipsByID()
    {
        val handler = AvatarManagementHandler(AvatarManagementHandler.GetClipsByID("28f4b149-c696-4d72-aa79-dbd8936e1935"))
        handler.getClipsByID(object : AvatarManagementHandler.OnGetClipsByIDListener
        {
            override fun onGetClipsByIDResult(result: GetClipsByIDResult?)
            {
                if (result != null)
                {
                    val responseText = "onGetClipsByID Response: $result"
                    Log.i("Result--", responseText)
                }
            }
            override fun onGetClipsByIDError(error: Exception)
            {
                val errorText = "onGetClipsByID Error: " + error.message
                Log.e(" Error--", errorText)
            }

        })
    }

    /**
     * Get all the active expressions.
     */
    private fun GetExpressions()
    {
        val handler = AvatarManagementHandler(AvatarManagementHandler.GetExpressions(1))
        handler.getExpression(object : AvatarManagementHandler.OnGetExpressionsListener
        {
            override fun onGetExpressionsResult(result: GetExpressionsResult?)
            {
                if (result != null)
                {
                    val responseText = "onGetExpressions Response: " + result.toString()
                    Log.i("Result--", responseText)
                }
            }

            override fun onGetExpressionsError(error: java.lang.Exception?) {
                val errorText = "onGetExpressions Error: " + error!!.message
                Log.e(" Error--", errorText)
            }
        })
    }

    /**
     * Get the specified expression details by providing ExpressionID.
     */
    private fun GetExpressionByID()
    {
        val handler = AvatarManagementHandler(AvatarManagementHandler.GetExpressionByID("b4380b35-57c3-47ea-ad2d-1496f85255f8"))
        handler.getExpressionByID(object : AvatarManagementHandler.OnGetExpressionByIDListener
        {
            override fun onGetExpressionByIDResult(result: GetExpressionByIDResult?)
            {
                if (result != null) {
                    val responseText = "onGetExpressionByID Response: " + result.toString()
                    Log.i("Result--", responseText)
                }
            }
            override fun onGetExpressionByIDError(error: java.lang.Exception?)
            {
                val errorText = "onGetExpressionByID Error: " + error!!.message
                Log.e(" Error--", errorText)
            }
        })
    }

    /**
     * Get vertices for all buckets.
     */
    private fun GetAllBucketVertices()
    {
        val handler = AvatarManagementHandler(AvatarManagementHandler.GetAllBucketVertices("Android"))
        handler.getGetallbucketvertices(object :
            AvatarManagementHandler.OnGetAllBucketVerticesListener
        {
            override fun onGetAllBucketVerticesResult(result: GetAllBucketVerticesResult?)
            {
                if (result != null)
                {
                    val responseText = "onGetAllBucketVertices Response: " + result.toString()
                    Log.i("Result--", responseText)
                }
            }

            override fun onGetAllBucketVerticesError(error: Exception)
            {
                val errorText = "onGetAllBucketVertices Error: " + error!!.message
                Log.e(" Error--", errorText)
            }

        })
    }

    /**
     * Grant Avatar Preset Items To User.
     */
    private fun GrantAvatarPresetItemsToUser()
    {
        val request = GrantAvatarPresetItemsToUserRequest()
        val handler = AvatarManagementHandler(AvatarManagementHandler.GrantAvatarPresetItemsToUser(request))
        handler.grantAvatarPresetItemsToUser(object :
            AvatarManagementHandler.OnGrantAvatarPresetItemsToUserListener
        {
            override fun onGrantAvatarPresetItemsToUserResult(result: GrantAvatarPresetItemsToUserResult?)
            {
                if (result != null)
                {
                    val responseText = "onGrantAvatarPresetItemsToUser Response: " + result.toString()
                    Log.i("Result--", responseText)
                }
            }

            override fun onGrantAvatarPresetItemsToUserError(error: Exception)
            {
                val errorText = "onGrantAvatarPresetItemsToUser Error: " + error.message
                Log.e(" Error--", errorText)
            }
        })
    }

    /**
     * Render Avatar Image.
     */
    private fun RenderAvatarImage()
    {
        val request = RenderAvatarImageRequest(
            avatarID = "",
            platform = ""
        )
        val handler = AvatarManagementHandler(AvatarManagementHandler.RenderAvatarImage(request))
        handler.renderAvatarImage(object : AvatarManagementHandler.OnRenderAvatarImageListener
        {
            override fun onRenderAvatarImageResult(result: RenderAvatarImageResult?)
            {
                if (result != null)
                {
                    val responseText = "onRenderAvatarImage Response: " + result.toString()
                    Log.i("Result--", responseText)
                }
            }

            override fun onRenderAvatarImageError(error: Exception) {
                val errorText = "onRenderAvatarImage Error: " + error.message
                Log.e(" Error--", errorText)
            }
        })
    }

    /**
     * Creates missing avatars into the mentioned platform for the user.
     */
    private fun SyncAvatars()
    {
        val request = SyncAvatarsRequest(
            platform = "",
            mesh = true
        )

        val handler = AvatarManagementHandler(AvatarManagementHandler.SyncAvatars(request))
        handler.syncAvatars(object : AvatarManagementHandler.OnSyncAvatarsListener
        {
            override fun onSyncAvatarsResult(result: SyncAvatarsResult?)
            {
                if (result != null)
                {
                    val responseText = "onSyncAvatars Response: " + result.toString()
                    Log.i("Result--", responseText)
                }
            }
            override fun onSyncAvatarsError(error: Exception?) {
                val errorText = "onSyncAvatars Error: " + error!!.message
                Log.e(" Error--", errorText)
            }
        })
    }

    /**
     * Grant Avatar Preset To User.
     */
    private fun GrantAvatarPresetToUser()
    {
        val request = GrantAvatarPresetToUserRequest(
            avatarID = "kf[psf",
            avatarData = "jasjf"
        )

        val handler = AvatarManagementHandler(AvatarManagementHandler.GrantAvatarPresetToUser(request))
        handler.grantAvatarPresetToUser(object :
            AvatarManagementHandler.OnGrantAvatarPresetToUserListener
        {
            override fun onGrantAvatarPresetToUserResult(result: GrantAvatarPresetToUserResult?)
            {
                if (result != null)
                {
                    val responseText = "onGrantAvatarPresetToUser Response: $result"
                    Log.i("Result--", responseText)
                }
            }
            override fun onGrantAvatarPresetToUserError(error: java.lang.Exception?)
            {
                val errorText = "onGrantAvatarPresetToUser Error: " + error!!.message
                Log.e(" Error--", errorText)
            }

        })
    }

    /**
     * Retrive Avatar preset by ID
     */
    private fun GetAvatarPresetsByID()
    {
        val handler = AvatarManagementHandler(AvatarManagementHandler.GetAvatarPresetByID("fhsfha"))
        handler.getAvatarPresetsByID(object : AvatarManagementHandler.OnGetAvatarPresetsByIDListener
        {
            override fun onGetAvatarPresetsByIDResult(result: GetAvatarPresetByIDRequest?)
            {
                if (result != null)
                {
                    val responseText = "onGetAvatarPresetsByID Response: " + result.toString()
                    Log.i("Result--", responseText)
                }
            }
            override fun onGetAvatarPresetsByIDError(error: Exception?)
            {
                val errorText = "onGetAvatarPresetsByID Error: " + error!!.message
                Log.e(" Error--", errorText)
            }
        })
    }

}