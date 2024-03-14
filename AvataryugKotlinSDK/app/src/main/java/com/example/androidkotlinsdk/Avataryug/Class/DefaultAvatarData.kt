package Avataryug.Class

import Avataryug.Class.AvatarLoader.Gender
import Avataryug.Client.Models.ModelData

class DefaultAvatarData {
    companion object {
        val instance: DefaultAvatarData by lazy { DefaultAvatarData() }
    }

    val modelFemaleData = listOf(
        ModelData("Top", "upperbody_sleeve_short", "https://aystorage.b-cdn.net/standard/nonwebp_female_standard_top.glb",
            "{" + "\"conflits\"" + ":" + "[{\"name\":\"upperbody_shoulder_left\"},{\"name\":\"upperbody_sleeveless\"},{\"name\":\"upperbody_arm_right\"},{\"name\":\"upperbody_shoulder_both\"},{\"name\":\"upperbody_back\"},{\"name\":\"upperbody_sleeve_full\"},{\"name\":\"upperbody_arm_left\"},{\"name\":\"upperbody_wrist_both\"},{\"name\":\"upperbody_arm_both\"},{\"name\":\"upperbody_stomach\"},{\"name\":\"upperbody_hand_right\"},{\"name\":\"upperbody_shoulder_right\"},{\"name\":\"upperbody_wrist_right\"},{\"name\":\"upperbody_forearm_right\"},{\"name\":\"upperbody_forearm_both\"},{\"name\":\"upperbody_forearm_left\"},{\"name\":\"upperbody_wrist_left\"},{\"name\":\"upperbody_chest\"},{\"name\":\"upperbody_sleeve_short\"},{\"name\":\"upperbody_hand_left\"},{\"name\":\"upperbody_front\"},{\"name\":\"fullbody_without_foot\"},{\"name\":\"fullbody_with_head_foot\"},{\"name\":\"fullbody_without_head\"},{\"name\":\"fullbody_without_head_foot\"}]" + "}"
        , gender = Gender.Female),
        ModelData("Bottom","lowerbody_without_foot","https://aystorage.b-cdn.net/standard/nonwebp_female_standard_bottom.glb",
            "{" + "\"conflits\"" + ":" + "[{\"name\":\"lowerbody_foot_right\"},{\"name\":\"lowerbody_knee_right\"},{\"name\":\"lowerbody_without_foot\"},{\"name\":\"lowerbody_leg_both\"},{\"name\":\"lowerbody_knee_left\"},{\"name\":\"lowerbody_foot_left\"},{\"name\":\"lowerbody_leg_left\"},{\"name\":\"lowerbody_knee_both\"},{\"name\":\"lowerbody_leg_right\"},{\"name\":\"lowerbody_till_knee\"},{\"name\":\"lowerbody_thigh_both\"},{\"name\":\"lowerbody_thigh_right\"},{\"name\":\"lowerbody_thigh_left\"},{\"name\":\"fullbody_without_foot\"},{\"name\":\"fullbody_with_head_foot\"},{\"name\":\"fullbody_without_head\"},{\"name\":\"fullbody_without_head_foot\"}]" + "}"
                    ,gender = Gender.Female),
        ModelData("Footwear","lowerbody_foot_both","https://aystorage.b-cdn.net/standard/nonwebp_standard_footwear.glb",
            "{" + "\"conflits\"" + ":" + "[{\"name\":\"lowerbody_foot_both\"}]" + "}"   ,gender = Gender.Female     ),
        ModelData("Handwear","upperbody_hand_both","https://aystorage.b-cdn.net/standard/nonwebp_standard_hand.glb",
            "{" + "\"conflits\"" + ":" + "[{\"name\":\"upperbody_hand_both\"}]" + "}"   ,gender = Gender.Female   )
    )

    val modelMaleData = listOf(
        ModelData("Top","upperbody_sleeve_short","https://aystorage.b-cdn.net/standard/nonwebp_male_standard_top.glb",
            "{" + "\"conflits\"" + ":" + "[{\"name\":\"upperbody_shoulder_left\"},{\"name\":\"upperbody_sleeveless\"},{\"name\":\"upperbody_arm_right\"},{\"name\":\"upperbody_shoulder_both\"},{\"name\":\"upperbody_back\"},{\"name\":\"upperbody_sleeve_full\"},{\"name\":\"upperbody_arm_left\"},{\"name\":\"upperbody_wrist_both\"},{\"name\":\"upperbody_arm_both\"},{\"name\":\"upperbody_stomach\"},{\"name\":\"upperbody_hand_right\"},{\"name\":\"upperbody_shoulder_right\"},{\"name\":\"upperbody_wrist_right\"},{\"name\":\"upperbody_forearm_right\"},{\"name\":\"upperbody_forearm_both\"},{\"name\":\"upperbody_forearm_left\"},{\"name\":\"upperbody_wrist_left\"},{\"name\":\"upperbody_chest\"},{\"name\":\"upperbody_sleeve_short\"},{\"name\":\"upperbody_hand_left\"},{\"name\":\"upperbody_front\"},{\"name\":\"fullbody_without_foot\"},{\"name\":\"fullbody_with_head_foot\"},{\"name\":\"fullbody_without_head\"},{\"name\":\"fullbody_without_head_foot\"}]" + "}"
            ,gender = Gender.Male),
        ModelData("Bottom","lowerbody_without_foot","https://aystorage.b-cdn.net/standard/nonwebp_male_standard_bottom.glb",
            "{" + "\"conflits\"" + ":" + "[{\"name\":\"lowerbody_foot_right\"},{\"name\":\"lowerbody_knee_right\"},{\"name\":\"lowerbody_without_foot\"},{\"name\":\"lowerbody_leg_both\"},{\"name\":\"lowerbody_knee_left\"},{\"name\":\"lowerbody_foot_left\"},{\"name\":\"lowerbody_leg_left\"},{\"name\":\"lowerbody_knee_both\"},{\"name\":\"lowerbody_leg_right\"},{\"name\":\"lowerbody_till_knee\"},{\"name\":\"lowerbody_thigh_both\"},{\"name\":\"lowerbody_thigh_right\"},{\"name\":\"lowerbody_thigh_left\"},{\"name\":\"fullbody_without_foot\"},{\"name\":\"fullbody_with_head_foot\"},{\"name\":\"fullbody_without_head\"},{\"name\":\"fullbody_without_head_foot\"}]" + "}"
            ,gender = Gender.Male),
        ModelData("Footwear","lowerbody_foot_both","https://aystorage.b-cdn.net/standard/nonwebp_standard_footwear.glb",
            "{" + "\"conflits\"" + ":" + "[{\"name\":\"lowerbody_foot_both\"}]" + "}"
            ,gender = Gender.Male),
        ModelData("Handwear","upperbody_hand_both","https://aystorage.b-cdn.net/standard/nonwebp_standard_hand.glb",
            "{" + "\"conflits\"" + ":" + "[{\"name\":\"upperbody_hand_both\"}]" + "}"
            ,gender = Gender.Male)
    )
}