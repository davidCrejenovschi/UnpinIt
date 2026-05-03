package org.example.project.ui.home

// Dummy implementation for iOS to satisfy the KMP compiler
actual class LocalGalleryManager actual constructor() {

    actual fun getImagesPaths(): List<String> {
        // We will implement iOS Photos framework (PHAsset) later
        return emptyList()
    }
}