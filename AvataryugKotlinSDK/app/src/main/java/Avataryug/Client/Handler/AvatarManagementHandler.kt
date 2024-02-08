package com.Avataryug.client.Handler

import com.Avataryug.client.Apis.AvatarManagementApi
import com.Avataryug.client.Handler.Base.OnApiResultListener
import com.Avataryug.client.Infrastructure.ClientException
import com.Avataryug.client.Infrastructure.ServerException
import com.Avataryug.client.Models.GenerateAvatarMeshRequest
import com.Avataryug.client.Models.GenerateAvatarMeshResult
import com.Avataryug.client.Models.GetAllBucketVerticesResult
import com.Avataryug.client.Models.GetAvatarPresetByIDRequest
import com.Avataryug.client.Models.GetAvatarPresetsByIDResult
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
import com.android.volley.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * The "AvatarManagementHandler" class is responsible for managing avatars and making API calls through the "Base" class.
 * It provides multiple methods for creating, updating, and retrieving avatars related functionality.
 */
class AvatarManagementHandler (private val apiBase: Base)
{
    /**
     * Generates the 3D mesh as per the configuration in the Config panel.
     * @param listener
     */
    fun generateAvatarMesh(listener: OnGenerateAvatarMeshListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GenerateAvatarMeshResult) {
                    listener.onGenerateAvatarMeshResult(response)
                } else {
                    listener.onGenerateAvatarMeshError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onGenerateAvatarMeshError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GenerateAvatarMesh method
     */
    interface OnGenerateAvatarMeshListener
    {
        fun onGenerateAvatarMeshResult(result: GenerateAvatarMeshResult)
        fun onGenerateAvatarMeshError(error: Exception)
    }

    /**
     * Generates the 3D mesh as per the configuration in the Config panel
     */
    class GenerateAvatarMesh(private val avatarID: String, private val platform: String) : Base
    {

        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val request = GenerateAvatarMeshRequest(
                        avatarID = avatarID,
                        platform = platform
                    )
                    val apiInstance = AvatarManagementApi()
                    val result: GenerateAvatarMeshResult = apiInstance.generateAvatarMesh(request)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Get all avatar presets
     * @param listener
     */
    fun getAvatarPresets(listener: OnGetAvatarPresetsListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GetAvatarPresetsResult) {
                    listener.onGetAvatarPresetsResult(response)
                } else {
                    listener.onGetAvatarPresetsError(Exception("Invalid response type"))
                }
            }
            override fun onError(error: Exception) {
                listener.onGetAvatarPresetsError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GetAvatarPresets method
     */
    interface OnGetAvatarPresetsListener
    {
        fun onGetAvatarPresetsResult(result: GetAvatarPresetsResult)
        fun onGetAvatarPresetsError(error: Exception)
    }

    /**
     * Get all avatar presets
     */
    class GetAvatarPresets(private val status: Int, private val gender: Int) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = AvatarManagementApi()
                    val result: GetAvatarPresetsResult = apiInstance.getAvatarPresets(status = status, Gender = gender)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Get all the clips by Active status
     * @param listener
     */
    fun getClips(listener: OnGetClipsListener)
    {
        apiBase.callApi(object : Base.OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GetClipsResult) {
                    listener.onGetClipsResult(response)
                } else {
                    listener.onGetClipsError(Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener.onGetClipsError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GetClips method
     */
    interface OnGetClipsListener
    {
        fun onGetClipsResult(result: GetClipsResult)
        fun onGetClipsError(error: Exception)
    }

    /**
     * Get all the clips by Active status
     */
    class GetClips(private val status: Int) : Base
    {
        override fun callApi(listener: Base.OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = AvatarManagementApi()
                    val result: GetClipsResult = apiInstance.getClips(status = status)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     *Get the specified clip's details by providing ClipID.
     * @param listener
     */
    open fun getClipsByID(listener: OnGetClipsByIDListener?)
    {
        apiBase.callApi(object : OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GetClipsByIDResult) {
                    listener?.onGetClipsByIDResult(response)
                } else {
                    listener?.onGetClipsByIDError(java.lang.Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener?.onGetClipsByIDError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GetClipsByID method
     */
    interface OnGetClipsByIDListener
    {
        fun onGetClipsByIDResult(result: GetClipsByIDResult?)
        fun onGetClipsByIDError(error: Exception)
    }

    /**
     * Get the specified clip's details by providing ClipID
     */
    class GetClipsByID(private val clipId: String) : Base
    {
        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = AvatarManagementApi()
                    val result: GetClipsByIDResult = apiInstance.getClipsByID(clipID = clipId)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Retrieve all expressions based on the provided status.
     * 0 = Draft,
     * 1 = Active,
     * 2 = Inactive,
     * 3 = Expired
     * @param listener
     */
    fun getExpression(listener: OnGetExpressionsListener?)
    {
        apiBase.callApi(object : OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GetExpressionsResult) {
                    listener?.onGetExpressionsResult(response as GetExpressionsResult)
                } else {
                    listener?.onGetExpressionsError(java.lang.Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener?.onGetExpressionsError(error)
            }
        })
    }

    /** Define the listener interface for the GetExpression method
     *
     */
    interface OnGetExpressionsListener
    {
        fun onGetExpressionsResult(result: GetExpressionsResult?)
        fun onGetExpressionsError(error: Exception?)
    }

    /**
     * Get all the active expressions
     */
    class GetExpressions(private val status: Int) : Base
    {
        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = AvatarManagementApi()
                    val result: GetExpressionsResult = apiInstance.getExpressions(status = status)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Get the specified expression details by providing ExpressionID
     * @param listener
     */
    fun getExpressionByID(listener: OnGetExpressionByIDListener?)
    {
        apiBase.callApi(object : OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is GetExpressionByIDResult)
                {
                    listener?.onGetExpressionByIDResult(response as GetExpressionByIDResult)
                } else
                {
                    listener?.onGetExpressionByIDError(java.lang.Exception("Invalid response type"))
                }
            }
            override fun onError(error: java.lang.Exception)
            {
                listener?.onGetExpressionByIDError(error)
            }
        })
    }

    /** Define the listener interface for the GetExpressionByID method
     *
     */
    interface OnGetExpressionByIDListener
    {
        fun onGetExpressionByIDResult(result: GetExpressionByIDResult?)
        fun onGetExpressionByIDError(error: java.lang.Exception?)
    }

    /**
     * Get the specified expression details by providing ExpressionID.
     */
    class GetExpressionByID(private val expressionID: String) : Base
    {
        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = AvatarManagementApi()
                    val result: GetExpressionByIDResult = apiInstance.getExpressionByID(expressionID = expressionID)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Get vertices for all buckets.
     * @param listener
     */
    fun getGetallbucketvertices(listener: OnGetAllBucketVerticesListener?)
    {
        apiBase.callApi(object : OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is GetAllBucketVerticesResult)
                {
                    listener?.onGetAllBucketVerticesResult(response as GetAllBucketVerticesResult)
                } else
                {
                    listener?.onGetAllBucketVerticesError(java.lang.Exception("Invalid response type"))
                }
            }
            override fun onError(error:Exception)
            {
                listener?.onGetAllBucketVerticesError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GetGetallbucketvertices method
     */
    interface OnGetAllBucketVerticesListener
    {
        fun onGetAllBucketVerticesResult(result: GetAllBucketVerticesResult?)
        fun onGetAllBucketVerticesError(error:Exception)
    }

    /**
     * Get vertices for all buckets
     */
    class GetAllBucketVertices(private val platform: String) : Base
    {
        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = AvatarManagementApi()
                    val result: GetAllBucketVerticesResult = apiInstance.getGetallbucketvertices(platform = platform)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Grant the selected avatar and its associated items to the user's inventory.
     * @param listener
     */
    fun grantAvatarPresetItemsToUser(listener: OnGrantAvatarPresetItemsToUserListener?)
    {
        apiBase.callApi(object : OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GrantAvatarPresetItemsToUserResult) {
                    listener?.onGrantAvatarPresetItemsToUserResult(response as GrantAvatarPresetItemsToUserResult)
                } else {
                    listener?.onGrantAvatarPresetItemsToUserError(java.lang.Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception) {
                listener?.onGrantAvatarPresetItemsToUserError(error)
            }
        })
    }

    /** Define the listener interface for the GrantAvatarPresetItemsToUser method
     *
     */
    interface OnGrantAvatarPresetItemsToUserListener
    {
        fun onGrantAvatarPresetItemsToUserResult(result: GrantAvatarPresetItemsToUserResult?)
        fun onGrantAvatarPresetItemsToUserError(error: Exception)
    }

    /**Grant Avatar Preset Items To User
     *
     */
    class GrantAvatarPresetItemsToUser(request: GrantAvatarPresetItemsToUserRequest) : Base
    {
        private val request: GrantAvatarPresetItemsToUserRequest

        init {
            this.request = request
        }

        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = AvatarManagementApi()
                    val result: GrantAvatarPresetItemsToUserResult = apiInstance.grantAvatarPresetItemsToUser(request)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Utilize the specified API to render the image of the avatar associated with the provided avatar ID.
     * This API is specifically designed to generate and retrieve the rendered image of the avatar.
     * @param listener
     */
    fun renderAvatarImage(listener: OnRenderAvatarImageListener?)
    {
        apiBase.callApi(object : OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is RenderAvatarImageResult)
                {
                    listener?.onRenderAvatarImageResult(response as RenderAvatarImageResult)
                } else
                {
                    listener?.onRenderAvatarImageError(java.lang.Exception("Invalid response type"))
                }
            }

            override fun onError(error:Exception)
            {
                listener?.onRenderAvatarImageError(error)
            }
        })
    }

    /**
     * Define the listener interface for the RenderAvatarImage method
     */
    interface OnRenderAvatarImageListener
    {
        fun onRenderAvatarImageResult(result:  RenderAvatarImageResult?)
        fun onRenderAvatarImageError(error: Exception)
    }

    /**
     * Render Avatar Image
     */
    class RenderAvatarImage(request: RenderAvatarImageRequest) : Base
    {
        private val request: RenderAvatarImageRequest

        init {
            this.request = request
        }

        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = AvatarManagementApi()
                    val result: RenderAvatarImageResult = apiInstance.renderAvatarImage(request)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Creates missing avatars into the mentioned platform for the user.
     * @param listener
     */
    fun syncAvatars(listener: OnSyncAvatarsListener?)
    {
        apiBase.callApi(object : OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is SyncAvatarsResult)
                {
                    listener?.onSyncAvatarsResult(response as SyncAvatarsResult)
                } else
                {
                    listener?.onSyncAvatarsError(java.lang.Exception("Invalid response type"))
                }
            }

            override fun onError(error:Exception)
            {
                listener?.onSyncAvatarsError(error)
            }
        })
    }

    /**
     * Define the listener interface for the SyncAvatars method
     */
    interface OnSyncAvatarsListener
    {
        fun onSyncAvatarsResult(result: SyncAvatarsResult?)
        fun onSyncAvatarsError(error: Exception?)
    }

    /**
     * The SyncAvatars class is responsible for making an API call to Creates missing avatars into the mentioned platform for the user.
     */
    class SyncAvatars(syncAvatarsRequest: SyncAvatarsRequest) : Base
    {
        private val syncAvatarsRequest: SyncAvatarsRequest

        init {
            this.syncAvatarsRequest = syncAvatarsRequest
        }

        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = AvatarManagementApi()
                    val result: SyncAvatarsResult = apiInstance.syncAvatars(syncAvatarsRequest)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Grant Avatar Preset To User
     * @param listener
     */
    fun grantAvatarPresetToUser(listener: OnGrantAvatarPresetToUserListener?)
    {
        apiBase.callApi(object : OnApiResultListener {
            override fun onResult(response: Any) {
                if (response is GrantAvatarPresetToUserResult) {
                    listener?.onGrantAvatarPresetToUserResult(response as GrantAvatarPresetToUserResult)
                } else {
                    listener?.onGrantAvatarPresetToUserError(java.lang.Exception("Invalid response type"))
                }
            }

            override fun onError(error: java.lang.Exception) {
                listener?.onGrantAvatarPresetToUserError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GrantAvatarPresetToUser method
     */
    interface OnGrantAvatarPresetToUserListener
    {
        fun onGrantAvatarPresetToUserResult(result: GrantAvatarPresetToUserResult?)
        fun onGrantAvatarPresetToUserError(error: java.lang.Exception?)
    }

    /**
     * The GrantAvatarPresetToUser class is responsible for making an API call to Grant Avatar Preset To User.
     */
    class GrantAvatarPresetToUser(request: GrantAvatarPresetToUserRequest) : Base
    {
        private val request: GrantAvatarPresetToUserRequest

        init {
            this.request = request
        }

        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = AvatarManagementApi()
                    val result: GrantAvatarPresetToUserResult = apiInstance.grantAvatarPresetToUser(request)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }

    /**
     * Retrive Avatar preset by ID
     * @param listener
     */
    fun getAvatarPresetsByID(listener: OnGetAvatarPresetsByIDListener?)
    {
        apiBase.callApi(object : OnApiResultListener
        {
            override fun onResult(response: Any)
            {
                if (response is GetAvatarPresetByIDRequest)
                {
                    listener?.onGetAvatarPresetsByIDResult(response as GetAvatarPresetByIDRequest)
                } else
                {
                    listener?.onGetAvatarPresetsByIDError(java.lang.Exception("Invalid response type"))
                }
            }

            override fun onError(error: Exception)
            {
                listener?.onGetAvatarPresetsByIDError(error)
            }
        })
    }

    /**
     * Define the listener interface for the GetAvatarPresetsByID method
     */
    interface OnGetAvatarPresetsByIDListener
    {
        fun onGetAvatarPresetsByIDResult(result: GetAvatarPresetByIDRequest?)
        fun onGetAvatarPresetsByIDError(error: Exception?)
    }

    /**
     * The GetAvatarPresetByID class is responsible for making an API call to retrieve an avatar preset by its ID.
     */
    class GetAvatarPresetByID(private val avatarPresetID: String) : Base
    {
        override fun callApi(listener: OnApiResultListener)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                try
                {
                    val apiInstance = AvatarManagementApi()
                    val result: GetAvatarPresetByIDRequest = apiInstance.getAvatarPresetsByID(avatarPresetID = avatarPresetID)
                    listener.onResult(result)
                }
                catch (e: ClientException)
                {
                    listener.onError(e)
                }
                catch (e: ServerException)
                {
                    listener.onError(e)
                }
                catch (e: Exception)
                {
                    listener.onError(e)
                }
            }
        }
    }
}