package Avataryug.Class.AvatarLoader

import com.google.android.filament.utils.Float3

class FacewearPoints  {

    companion object {
        val instance: FacewearPoints by lazy { FacewearPoints() }
    }

    val facewearPointList: List<FacewearPoint> = listOf(

    /////////////////
        FacewearPoint().apply {
            bucketName = "eyes_full_right"
            position = Float3(0.04480796f, 1.87573f, 0.1001057f)
        },
        FacewearPoint().apply {
            bucketName = "eyes_full_left"
            position = Float3(0.03535601f, 1.87475f, 0.1031998f)
        },
        FacewearPoint().apply {
            bucketName = "eyes_full_both"
            position = Float3(0.0f, 1.9f, 0.1f)
        },
        FacewearPoint().apply {
            bucketName = "eyes_glasses_both"
            position = Float3(0.0f, 1.885142f, 0.1191775f)
        },
        FacewearPoint().apply {
            bucketName = "eyes_goggles_both"
            position = Float3(0.0f, 1.885142f, 0.1191775f)
        },
        ///////////////
        FacewearPoint().apply {
            bucketName = "neck_lower"
            position = Float3(0.0f, 1.67726f, 0.03718648f)
        },
        FacewearPoint().apply {
            bucketName = "neck_upper"
            position = Float3(0.0f, 1.72376f, 0.04584944f)
        },
        ////////////
        FacewearPoint().apply {
            bucketName = "mouth_full"
            position = Float3(0.0f, 1.812255f, 0.1264537f)
        },
        FacewearPoint().apply {
            bucketName = "mouth_inside_left"
            position = Float3(0.01346636f, 1.812777f, 0.1255389f)
        },
        FacewearPoint().apply {
            bucketName = "mouth_inside_right"
            position = Float3(-0.02135512f, 1.80532f, 0.1146502f)
        },
        FacewearPoint().apply {
            bucketName = "mouth_lowerlip_center"
            position = Float3(0.0f, 1.79748f, 0.1249959f)
        },
        FacewearPoint().apply {
            bucketName = "mouth_lowerlip_both"
            position = Float3(0.0f, 1.79748f, 0.1250091f)
        },
        FacewearPoint().apply {
            bucketName = "mouth_lowerlip_left"
            position = Float3(0.01755477f, 1.8034f, 0.1194832f)
        },
        FacewearPoint().apply {
            bucketName = "mouth_inside_center"
            position = Float3(0.0f, 1.807475f, 0.1205306f)
        },
        FacewearPoint().apply {
            bucketName = "mouth_lowerlip_right"
            position = Float3(0.02612815f, 1.804848f, 0.1108605f)
        },
        //////////
        FacewearPoint().apply {
            bucketName = "eyebrows_left"
            position = Float3(0.05400464f, 1.906529f, 0.09925438f)
        },
        FacewearPoint().apply {
            bucketName = "eyebrows_both"
            position = Float3(0.1f, 1.9f, 0.1f)
        },
        FacewearPoint().apply {
            bucketName = "eyebrows_right"
            position = Float3(-0.05400464f, 1.906529f, 0.09925438f)
        },
        ///////////

        FacewearPoint().apply {
            bucketName = "face_temple_left"
            position = Float3(-0.07802264f, 1.913671f, 0.0472863f)
        },
        FacewearPoint().apply {
            bucketName = "face_chick_left"
            position = Float3(-0.06020734f, 1.838332f, 0.09243751f)
        },
        FacewearPoint().apply {
            bucketName = "face_jaw_right"
            position = Float3(0.06293139f, 1.816183f, 0.07861941f)
        },
        FacewearPoint().apply {
            bucketName = "face_jaw_left"
            position = Float3(-0.07067037f, 1.814803f, 0.06036519f)
        },
        FacewearPoint().apply {
            bucketName = "face_chin"
            position = Float3(0.0f, 1.762461f, 0.1175185f)
        },
        FacewearPoint().apply {
            bucketName = "face_forehead"
            position = Float3(0.0f, 1.928396f, 0.1158291f)
        },
        FacewearPoint().apply {
            bucketName = "face_lower_half"
            position = Float3(0.0f, 1.847273f, 0.1430508f)
        },
        FacewearPoint().apply {
            bucketName = "face_temple_right"
            position = Float3(0.06782477f, 1.928257f, 0.06957133f)
        },
        FacewearPoint().apply {
            bucketName = "face_full"
            position = Float3(0.0f, 1.844847f, 0.1435997f)
        },
        FacewearPoint().apply {
            bucketName = "face_chick_right"
            position = Float3(0.0692194f, 1.840809f, 0.07662593f)
        },
        ////
        FacewearPoint().apply {
            bucketName = "hair"
            position = Float3(0.0f, 2.003292f, 0.01483637f)
        },
        FacewearPoint().apply {
            bucketName = "hair_center"
            position = Float3(0.0f, 1.844847f, 0.1435997f)
        },
        /////////


        FacewearPoint().apply {
            bucketName = "head_back"
            position = Float3(0.0f, 1.905743f, -0.09807719f)
        },

        FacewearPoint().apply {
            bucketName = "head_upper_vertical"
            position = Float3(0.0f, 2.003292f, 0.01483637f)
        },
        FacewearPoint().apply {
            bucketName = "head_wear_face_closed"
            position = Float3(0.0f, 2.003292f, 0.01483637f)
        },
        FacewearPoint().apply {
            bucketName = "head_wear_face_open"
            position = Float3(0.0f, 2.003292f, 0.01483637f)
        },
        FacewearPoint().apply {
            bucketName = "head_upper_horizontal"
            position = Float3(0.0f, 1.928396f, 0.1158291f)
        },
        FacewearPoint().apply {
            bucketName = "head_upper_full"
            position = Float3(0.0f, 2.003292f, 0.01483637f)
        },

        FacewearPoint().apply {
            bucketName = "head_upper_vertical"
            position = Float3(0.0f, 2.003292f, 0.01483637f)
        },
        //////////

        FacewearPoint().apply {
            bucketName = "nose_wing_both"
            position = Float3(0.0f, 1.8f, 0.1f)
        },
        FacewearPoint().apply {
            bucketName = "nose_wing_right"
            position = Float3(-0.01748555f, 1.843287f, 0.125792f)
        },
        FacewearPoint().apply {
            bucketName = "nose_wing_left"
            position = Float3(0.01748555f, 1.843287f, 0.125792f)
        },

        FacewearPoint().apply {
            bucketName = "nose_full"
            position = Float3(0.0f, 1.853414f, 0.1384686f)
        },
        FacewearPoint().apply {
            bucketName = "nose_nostril_center"
            position = Float3(0.0f, 1.834557f, 0.1329287f)
        },
    ////////

        FacewearPoint().apply {
            bucketName = "ears_above_left"
            position = Float3(0.08996313f, 1.882554f, -0.006150621f)
        },
        FacewearPoint().apply {
            bucketName = "ears_above_right"
            position = Float3(-0.08996313f, 1.882554f, -0.006150621f)
        },

        FacewearPoint().apply {
            bucketName = "ears_helix_top_right"
            position = Float3(0.08940706f, 1.874975f, -0.01094203f)
        },

        FacewearPoint().apply {
            bucketName = "ears_helix_mid_left"
            position = Float3(-0.09424222f, 1.854794f, -0.01049476f)
        },
        FacewearPoint().apply {
            bucketName = "ears_full_right"
            position = Float3(-0.09741928f, 1.882652f, -0.0003701492f)
        },
        FacewearPoint().apply {
            bucketName = "ears_above_both"
            position = Float3(0.1f, 1.8f, 0.0f)
        },
        FacewearPoint().apply {
            bucketName = "ears_lobule_left"
            position = Float3(0.08484065f, 1.833932f, 0.009323696f)
        },
        FacewearPoint().apply {
            bucketName = "ears_lobule_right"
            position = Float3(-0.08484065f, 1.84524f, 0.008834872f)
        },
        FacewearPoint().apply {
            bucketName = "ears_canal_right"
            position = Float3(-0.07920154f, 1.850971f, 0.003529507f)
        },
        FacewearPoint().apply {
            bucketName = "ears_canal_left"
            position = Float3(0.07920154f, 1.850971f, 0.003036305f)
        },
        FacewearPoint().apply {
            bucketName = "ears_lobule_both"
            position = Float3(-0.1f, 1.8f, 0.0f)
        },

        FacewearPoint().apply {
            bucketName = "ears_helix_mid_right"
            position = Float3(0.0929708f, 1.884777f, 0.00385407f)
        },
        FacewearPoint().apply {
            bucketName = "ears_helix_top_both"
            position = Float3(-0.1f, 1.9f, 0.0f)
        },
        FacewearPoint().apply {
            bucketName = "ears_helix_top_both"
            position = Float3(0.1f, 1.9f, 0.0f)
        },
        FacewearPoint().apply {
            bucketName = "ears_canal_both"
            position = Float3(-0.1f, 1.9f, 0.0f)
        },

        FacewearPoint().apply {
            bucketName = "ears_full_left"
            position = Float3(0.07922198f, 1.850971f, 0.003036305f)
        },
        FacewearPoint().apply {
            bucketName = "ears_helix_mid_both"
            position = Float3(-0.1f, 1.9f, 0.0f)
        },

        FacewearPoint().apply {
            bucketName = "ears_helix_top_left"
            position = Float3(-0.09488613f, 1.8838f, -0.008768208f)
        },
        FacewearPoint().apply {
            bucketName = "ears_over"
            position = Float3(0.0f, 2.003292f, 0.01483637f)
        },
///////
        FacewearPoint().apply {
            bucketName = "facialhair"
            position = Float3(0.0f, 1.825794f, 0.1255424f)
        },
        )
}