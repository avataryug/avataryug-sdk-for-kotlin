package Avataryug.Class

class AvataryugData {
    companion object { val instance: AvataryugData by lazy { AvataryugData() }  }

    val defaultFacialHairColor = "#3b3b3b"
    val defaultEyebrowColor = "#272727"
    val defaultHairColor = "#2a2a2a"
    val defaultLipColor = "#ff9e7e"
    private val bodyPartCategory = listOf( "Top","Bottom","Outfit","Footwear","Handwear", "Wristwear" )

    fun isBodyCategory(category:String) : Boolean {
        var isPresent = false
        for (tattoo in bodyPartCategory) {
            if(tattoo == category){
                isPresent = true
                break
            }
        }
        return  isPresent
    }
    private val bodyTattoos = listOf(
        "FrontBodyTattoo", "BackBodyTattoo", "RightArmTattoo", "LeftArmTattoo",
        "FrontRightLegTattoo", "FrontLeftLegTattoo", "BackRightLegTattoo", "BackLeftLegTattoo",
        "RightHandTattoo", "LeftHandTattoo", "RightFootTattoo", "LeftFootTattoo"
    )

    fun  isTattooCategory(category:String) : Boolean {
        var isPresent = false
        for (tattoo in bodyTattoos) {
            if(tattoo == category){
                isPresent = true
                break
            }
        }
        return  isPresent
    }

    private val bodywearCategory = listOf("Wristwear")

    fun  isBodywearCategory(category:String) : Boolean {
        var isPresent = false
        for (tattoo in bodywearCategory) {
            if(tattoo == category){
                isPresent = true
                break
            }
        }
        return  isPresent
    }

    fun isCommonGender(category: String): Boolean {
        var isCommon = true
         if (category == "Top" || category == "Bottom" || category == "Outfit") {
             isCommon  = false
        }
        return isCommon
    }
}