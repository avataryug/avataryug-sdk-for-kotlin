package Avataryug.Class.AvatarLoader

import android.R
import com.google.android.filament.gltfio.FilamentAsset
import com.google.android.filament.utils.Float3
import com.squareup.moshi.Json

enum class Gender {
    Male,
    Female
}

class LoadedFacewearAssets {
    var asset: FilamentAsset? = null
    var bucketname: String = ""
    var id:String = ""
}

class LoadedPreset {
    var asset: FilamentAsset? = null
    var presetID: String  = ""
}

class  FacewearPoint {
    var bucketName:String = ""
    var position : Float3 = Float3(0.0f,0.0f,0.0f)
}
data class Config(
    val isGrid: Int = 0,
    val isTwoD: Int= 0,
    val isZipUpload: Int= 0,
    val isDiffuse: Int= 0,
    val isNormalUpload: Int= 0,
    val isOpacityUpload: Int= 0,
    val isMetallicUpload: Int= 0,
    val isTransparent: Int= 0,
    val isEmissionUpload: Int= 0,
    val isRoughnessUpload: Int = 0
)
data class ConflictName(
    @Json(name ="name")
    val name: String = ""
)



data class Wallet(
    @Json(name ="VirtualCurrency")
    val VirtualCurrency: String,
    @Json(name ="Amount")
    val Amount: String,
)
data class Accounts(
    @Json(name ="Platform")
    val Platform: String,
    @Json(name ="PlatformUserID")
    val PlatformUserID: String
)

class UserAvatar {
    var AvatarID: String = ""
    var AvatarUrls: MutableList<AvatarUrlData> = mutableListOf()
    var ThumbUrls: MutableList<AvatarThumbData> = mutableListOf()
    var AvatarData: AvatarData = AvatarData()
}
class AvatarUrlData {
    var Platform: String = ""
    var MeshURL: String = ""
}
class AvatarThumbData {
    var Platform: String = ""
    var ImageURL: String = ""
}
class AvatarData {
    var Race: String = ""
    var AgeRange: String = ""
    var Gender = 0
    var CustomMetaData: String = "[]"
    var MetaData: String = ""
    var BucketData: MutableList<Prop> = mutableListOf()
    var ColorMeta: PropColors = PropColors("","","","","")
    var Blendshapes: MutableList<Blendshape> = mutableListOf()
}

class Prop {
    @Json(name = "CoreBucket")
    var CoreBucket: String = ""

    @Json(name = "ID")
    var ID: String = ""
}
class  PropColors(
    @Json(name = "HairColor")
    var HairColor: String,
    @Json(name = "EyebrowColor")
    var EyebrowColor: String,
    @Json(name = "FacialHairColor")
    var FacialHairColor: String,
    @Json(name = "LipColor")
    var LipColor: String,
    @Json(name = "FaceColor")
    var FaceColor: String = "#ffffff"
)
class Blendshape {
    var shapekeys: String = ""
    var value: Float = 0.0f
}

class AvatarPresetData {

    var displayName: String = ""
    var description: String= ""
    var virtualCurrency: String= ""
    var realCurrency: Int= 0
    var customMetaData: String= ""
    var blendshapeKeys: Array<BlendshapeKeyPreset> = emptyArray()
    var tags: String = ""
    var gender: Int= 0
    val color: String= ""
    var metadata: String= ""
    var status: Int= 0
    var race: String= ""
    var ageRange: String= ""
    var userID: String= ""
    var imageArtifacts: Array<ImageArtifactPreset> = emptyArray()
    var meshArtifacts: Array<MeshArtifactPreset> = emptyArray()
    var bodyColors : PropColors = PropColors("","","","","")
    var props: MutableList<Prop> = mutableListOf()
}


data class ImageArtifactPreset (
    @Json(name = "device")
    val device: Int,
    @Json(name = "quality")
    val quality: Int,
    @Json(name = "texture")
    val texture: Int,
    @Json(name = "thumbnail_url")
    val thumbnail_url: String
)
data class MeshArtifactPreset (
    @Json(name = "device")
    val device: Int,
    @Json(name = "format")
    val format: Int,
    @Json(name = "lod")
    val lod: Int,
    @Json(name = "normals")
    val normals: Int,
    @Json(name = "texture")
    val texture: Int,
    @Json(name = "url")
    val url: String
)

data class BodyColors (
    @Json(name = "LipColor")
    val lipColor: String,

    @Json(name = "FaceColor")
    val faceColor: String,

    @Json(name = "HairColor")
    val hairColor: String,

    @Json(name = "EyebrowColor")
    val eyebrowColor: String,

    @Json(name = "FacialHairColor")
    val facialHairColor: String
)

data class PropData (
    @Json(name = "ID")
    val id: String,

    @Json(name = "CoreBucket")
    val coreBucket: String
)

data class BlendshapeKeyPreset (
    val shapekeys: String,
    val value: Float
)

data class Style (
    @Json(name = "ClipID")
    val ClipID: String,

    @Json(name = "ClipID")
    val ExpressionID: String
)
data class ClipExpressionData (
    @Json(name = "Style")
    val Style: Style,

    @Json(name = "CoreBucket")
    val gender: Int
)

data class StoreContent(
    @Json(name ="CustomData")
    val CustomData: String,
    @Json(name ="DisplayName")
    val DisplayName: String,
    @Json(name ="Type")
    val Type: String,
    @Json(name ="UserID")
    val UserID: String,
    @Json(name ="VirtualCurrency")
    val VirtualCurrency: List<VirtualCurrency>,
)

data class VirtualCurrency(
    @Json(name ="Amount")
    val Amount: String,
    @Json(name ="DisplayName")
    val DisplayName: String,
    @Json(name ="UserID")
    val UserID: String,
)

data class ContainerBase(
    @Json(name ="Code")
    var Code: R.string? = null,
    @Json(name ="Quantity")
    var Quantity: R.string? = null,
    @Json(name ="UserID")
    var UserID: R.string? = null,
    @Json(name ="containerType")
    var containerType: R.string? = null
)

data class ContainerBundleContents(
    @Json(name ="Bundles")
    val Bundles: List<Any?>,
    @Json(name ="Container")
    val Container: List<Any?>,
    @Json(name ="Currencies")
    val Currencies: List<Currency>,
    @Json(name ="Droptable")
    val Droptable: List<Any?>,
    @Json(name ="Items")
    val Items: List<Any?>,
)


data class Currency(
    @Json(name ="Code")
    val Code: String,
    @Json(name ="Quantity")
    val Quantity: String,
    @Json(name ="UserID")
    val UserID: String,
)

data class Entitlements  (
    @Json(name ="ByCount")
    val ByCount: String = "",
    @Json(name ="ByTime")
    val ByTime: String = "",
    @Json(name ="TimeData")
    val TimeData: String = "",
    @Json(name ="Type")
    val Type: String = ""
)


data class Tag (
    @Json(name ="name")
    val name: String = ""
)

data class BlendShapeValue  (
    @Json(name ="shapekeys")
    val shapekeys: String  = "",
    @Json(name ="sliderValue")
    val sliderValue: Float = 0.0f
)

data class ItemThumUrl  (
    @Json(name ="device")
    val device: Int = 0,
    @Json(name ="selected")
    val selected: Boolean = false,
    @Json(name ="texture")
    val texture: String = "",
    @Json(name ="thumbnail_url")
    val thumbnail_url: String = ""
)

data class VirtualCurrencyResult  (
    @Json(name ="UserID")
    val UserID: String = "",
    @Json(name ="Selected")
    val Selected: Boolean = false,
    @Json(name ="DisplayName")
    val DisplayName: String = "",
    @Json(name ="Amount")
    val Amount: String = ""
)

class EconomyItem {
    var ItemCategory: String = ""
    var ID: String = ""
    var TemplateID: String = ""
    var ItemSubCategory: String = ""
    var DisplayName: String = ""
    var Description: String = ""
    var CustomMetaData: String = ""
    var Style: String = ""
    var Artifacts: String = ""
    var ItemSkin: String = ""
    var CoreBucket: String = ""
    var OccupiedBuckets: String = ""
    var Blendshapes: String = ""
    var MeshData: String = ""
    var BonesPhysics: String = ""
    var BoneAdjustments: String = ""
    var ItemGender = 0
    var IsStackable = 0
    var IsLimitedEdition = 0
    var LimitedEditionIntialCount = 0
    var Status = 0
    var RealCurrency = 0
    var ConflictingBuckets: MutableList<ConflictName> = mutableListOf()
    var Config: Config = Config(0,0,0,0,0,0,0,0,0,0)
    var Entitlement: Entitlements = Entitlements("","","","")
    var tags: MutableList<Tag> = mutableListOf()
    var BlendshapeKeys: MutableList<BlendShapeValue> = mutableListOf()
    var ItemThumbnailsUrl: MutableList<ItemThumUrl> = mutableListOf()
    var VirtualCurrency: MutableList<VirtualCurrencyResult> = mutableListOf()
}

