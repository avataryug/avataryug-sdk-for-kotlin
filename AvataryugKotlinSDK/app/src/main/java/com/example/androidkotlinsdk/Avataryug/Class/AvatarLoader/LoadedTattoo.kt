package Avataryug.Class.AvatarLoader

import android.graphics.Bitmap

class LoadedTattoo {
    var ItemCategory: String
    var Tattooid: String
    var TattooTex: Bitmap

    constructor(itemCategory: String, tattooid: String,tattooTex: Bitmap) {
        ItemCategory = itemCategory
        Tattooid = tattooid
        TattooTex = tattooTex
    }
}