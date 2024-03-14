package Avataryug.Class.AvatarLoader

import android.view.Choreographer
import com.google.android.filament.utils.AutomationEngine


class AvatarViewerClass {

    companion object {
        val instance: AvatarViewerClass by lazy { AvatarViewerClass() }
    }

     lateinit var choreographer: Choreographer
     val viewerContent = AutomationEngine.ViewerContent()

}