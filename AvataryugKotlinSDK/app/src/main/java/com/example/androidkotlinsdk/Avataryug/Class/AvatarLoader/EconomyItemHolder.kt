package Avataryug.Class.AvatarLoader

import Avataryug.Class.AvataryugData
import Avataryug.Client.Models.GetUserAvatarAllDataResult
import SkinToneArtifact
import ThreeDArtifact
import android.util.Log
import com.Avataryug.client.Models.GetEconomyItemsResult
import com.google.gson.Gson


class EconomyItemHolder
{
    companion object {
        val instance: EconomyItemHolder by lazy { EconomyItemHolder() }
    }

    var economyItems: MutableList<EconomyItem> = mutableListOf()

    fun IsEconomyItemPresent(category:String) :Boolean
    {
        var isPresent = false
        for (item in economyItems)
        {
            if(item.ItemCategory == category && item.DisplayName.isNotEmpty())
            {
                isPresent = true
                break
            }
        }
        return  isPresent
    }
    fun IsEconomyItemPresentWithID(id:String) :Boolean
    {
        var isPresent = false
        for (item in economyItems)
        {
            if(item.ID == id)
            {
                isPresent = true
                break
            }
        }
        return  isPresent
    }
    fun IsEconomyItemPresentWithHalfDetail(id:String)
    {
        var index = -1
        economyItems.forEachIndexed { ind, economyItem ->
            if(economyItem.ID == id && economyItem.DisplayName.isEmpty())
            {
                index = ind
            }
        }
        if(index > -1){
            economyItems.removeAt(index = index)
        }
    }
    fun GetEconomyItem(id:String) :EconomyItem
    {
        var economyItem = EconomyItem()
        for (item in economyItems)
        {
            if(item.ID == id)
            {
                economyItem = item
                break
            }
        }
        return  economyItem
    }
    fun  AddEconomItem(result: GetEconomyItemsResult,onComplete : () -> Unit){

        for (item in result.data!!)
        {
            val economyItem = EconomyItem()
            economyItem.ID = item.ID
            economyItem.Style = item.style
            economyItem.Status = item.status
            economyItem.ItemSkin = item.itemSkin
            economyItem.Artifacts = item.artifacts
            economyItem.CoreBucket =item.coreBucket
            economyItem.ItemGender = item.itemGender
            economyItem.TemplateID = item.templateID
            economyItem.IsStackable = item.isStackable
            economyItem.DisplayName = item.displayName
            economyItem.Description = item.description
            economyItem.ItemCategory = item.itemCategory
            economyItem.RealCurrency = item.realCurrency
            economyItem.CustomMetaData = item.customMetaData
            economyItem.ItemSubCategory = item.itemSubCategory
            economyItem.IsLimitedEdition = item.isLimitedEdition
            economyItem.LimitedEditionIntialCount = item.limitedEditionIntialCount
            if(item.conflictingBuckets.isNotEmpty())
            {
                val conflictBucket =  Gson().fromJson(item.conflictingBuckets, Array<ConflictName>::class.java)
                if(conflictBucket.isNotEmpty())
                {
                    repeat(conflictBucket.count()) {
                        economyItem.ConflictingBuckets.add(conflictBucket[it])
                    }
                }
            }
            if(item.config.isNotEmpty())
            {
                economyItem.Config = Gson().fromJson(item.config, Config::class.java)
            }
            if(item.tags.isNotEmpty())
            {
                economyItem.tags = Gson().fromJson(item.tags, Array<Tag> ::class.java).toMutableList()
            }
            if(item.entitlement.isNotEmpty())
            {
                economyItem.Entitlement  = Gson().fromJson(item.entitlement, Entitlements ::class.java)
            }
            if(item.virtualCurrency.isNotEmpty())
            {
                economyItem.VirtualCurrency = Gson().fromJson(item.virtualCurrency,Array<VirtualCurrencyResult> ::class.java).toMutableList()
            }
            if(item.itemThumbnailsUrl.isNotEmpty())
            {
                economyItem.ItemThumbnailsUrl = Gson().fromJson(item.itemThumbnailsUrl, Array<ItemThumUrl>::class.java).toMutableList()
            }
            if(item.blendshapeKeys.isNotEmpty())
            {
                economyItem.BlendshapeKeys = Gson().fromJson(item.blendshapeKeys, Array<BlendShapeValue>::class.java).toMutableList()
            }
            IsEconomyItemPresentWithHalfDetail(item.ID)

            var isThumbPresent = false
            economyItem.ItemThumbnailsUrl.forEach {
                if(it.device == 0){
                    isThumbPresent = true;
                }
            }
            if(isThumbPresent && IsArtifactPresents(economyItem)){
                economyItems.add(economyItem)
            }
        }

        onComplete()
    }

    fun IsArtifactPresents(economyItem:EconomyItem):Boolean
    {
        var isArtifactPresent = false
        if( economyItem.ItemCategory == "SkinTone" )
        {
            val artifactList = Gson().fromJson(economyItem.Artifacts, Array<SkinToneArtifact>::class.java)
            repeat(artifactList.count()) {
                if(artifactList[it].device?.toInt() == 0)
                {
                 isArtifactPresent =true
                }
           }
        }
        else if(AvataryugData.instance.isTattooCategory(economyItem.ItemCategory))
        {
        }
        else if(economyItem.ItemCategory == "Top" || economyItem.ItemCategory == "Wristwear"
            ||economyItem.ItemCategory == "Bottom" || economyItem.ItemCategory == "Outfit"
            || economyItem.ItemCategory == "Footwear" || economyItem.ItemCategory == "Handwear"
            || economyItem.ItemCategory =="Eyewear" || economyItem.ItemCategory =="Headwear"
            || economyItem.ItemCategory =="Mouthwear" || economyItem.ItemCategory =="Earwear"
            || economyItem.ItemCategory =="Eyebrowswear" || economyItem.ItemCategory =="Facewear"
            || economyItem.ItemCategory =="Neckwear" || economyItem.ItemCategory == "Nosewear")
        {
            val artifactList = Gson().fromJson(economyItem.Artifacts, Array<ThreeDArtifact>::class.java)
            for (artifact in artifactList)
            {
                if(artifact.device == 0)
                {
                    artifact.url?.let {
                            isArtifactPresent = true
                    }
                }
            }
        }
            else if( economyItem.ItemCategory == "Eyeball" || economyItem.ItemCategory == "Lips"
            ||economyItem.ItemCategory == "Eyebrow" )
            {
                val artifactList = Gson().fromJson(economyItem.Artifacts, Array<ThreeDArtifact>::class.java)
                for (artifact in artifactList)
                {
                    if(artifact.device == 0)
                    {
                        artifact.url?.let {  isArtifactPresent = true}
                    }
                }
            }
            else if(economyItem.ItemCategory == "HeadTattoo" )
            {
                isArtifactPresent =true
            }
            else if(economyItem.ItemCategory == "Hair" )
            {
                isArtifactPresent =true
            }
            else if(economyItem.ItemCategory == "Facialhair" )
            {
                isArtifactPresent =true
            }else{
            isArtifactPresent =true
        }

        return isArtifactPresent
    }

    fun  GetEconomyItemWithCategory(category: String,onComplete : (MutableList<EconomyItem>) -> Unit){
        var tempList: MutableList<EconomyItem> = mutableListOf()
        Log.v("=======>>>GetEconomyItemWithCategory",category)
        for (item in economyItems)
        {
            if(item.ItemCategory == category)
            {
                tempList.add(item)
            }
        }
        onComplete(tempList)
    }

    fun  AddEconomItemFromAvatarData(result: GetUserAvatarAllDataResult, onComplete : () -> Unit){

        for (item in result.data!!)
        {
            val economyItem= EconomyItem()
            economyItem.ID = item.ID
            economyItem.Artifacts = item.Artifacts
            economyItem.ItemCategory = item.ItemCategory
            if(item.ConflictingBuckets.isNotEmpty())
            {
                economyItem.ConflictingBuckets =   Gson().fromJson(item.ConflictingBuckets, Array<ConflictName>::class.java).toMutableList()
            }
            if(item.Config.isNotEmpty()) {
                economyItem.Config = Gson().fromJson(item.Config, Config::class.java)
            }
            economyItem.TemplateID = item.TemplateID
            economyItem.ItemSkin = item.ItemSkin
            economyItem.CoreBucket =item.CoreBucket
            if(item.BlendshapeKeys.isNotEmpty())
            {
            economyItem.BlendshapeKeys = Gson().fromJson(item.BlendshapeKeys, Array<BlendShapeValue>::class.java).toMutableList()
                }
            economyItems.add(economyItem)
        }

        onComplete()
    }
}