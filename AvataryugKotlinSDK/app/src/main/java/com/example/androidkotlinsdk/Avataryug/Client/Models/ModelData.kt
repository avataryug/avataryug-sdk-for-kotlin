package Avataryug.Client.Models

import Avataryug.Class.AvatarLoader.Gender


class ModelData {
    var MainCatID: String = ""
    var GlbPath: String = ""
    var CoreBucket: String = ""
    var ConflictingBuckets: String = ""
    var genderType:Gender = Gender.Male
    constructor(mainCatID: String, coreBucket: String, glbPath: String, conflictingBuckets: String,gender: Gender) {
        MainCatID = mainCatID
        GlbPath = glbPath
        CoreBucket = coreBucket
        ConflictingBuckets = conflictingBuckets
        genderType = gender
    }
}