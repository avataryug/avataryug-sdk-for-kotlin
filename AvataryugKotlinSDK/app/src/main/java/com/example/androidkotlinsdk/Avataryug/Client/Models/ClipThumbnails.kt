package com.Avataryug.client.Models

import java.util.Objects

class ClipThumbnails
{
    // Private fields
    private var device: Int? = null
    private var textureSize: Int? = null
    private var thumbnailUrl: String? = null

    // Constructor
    fun ClipThumbnails(device: Int?, textureSize: Int?, thumbnailUrl: String?) {
        this.device = device
        this.textureSize = textureSize
        this.thumbnailUrl = thumbnailUrl
    }

    // Getters and Setters
    fun getDevice(): Int? {
        return device
    }

    fun setDevice(device: Int?) {
        this.device = device
    }

    fun getTextureSize(): Int? {
        return textureSize
    }

    fun setTextureSize(textureSize: Int?) {
        this.textureSize = textureSize
    }

    fun getThumbnailUrl(): String? {
        return thumbnailUrl
    }

    fun setThumbnailUrl(thumbnailUrl: String?) {
        this.thumbnailUrl = thumbnailUrl
    }

    // Override equals and hashCode methods
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ClipThumbnails
        return device == this.device && textureSize == this.textureSize && thumbnailUrl == this.thumbnailUrl
    }

    override fun hashCode(): Int {
        return Objects.hash(device, textureSize, thumbnailUrl)
    }
}