package com.lagecompany.infinity.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.lagecompany.infinity.world.VoxelType
import java.io.FileNotFoundException
import kotlin.math.sqrt


data class BlockImage(val type: VoxelType, val pixmap: Pixmap)

object BlocksTextureLoader {
    private const val IMG_EXT = "png"
    const val SIZE = 16

    private val blockTileMap = mutableMapOf<VoxelType, Pair<Int, Int>>()
    var tileSize: Float = 0f
        private set

    fun getVoxelTile(type: VoxelType): Pair<Int, Int> {
        return blockTileMap[type] ?: error { "Unable to find tile mapping for voxel type $type" }
    }

    fun loadBlocksTexture(): Texture {
        val images = loadBlocksImages()

        val grid = (sqrt(images.size.toFloat()) + 0.5f).toInt()

        var width = grid * SIZE
        var height = grid * SIZE

        val atlas = Pixmap(width, height, Pixmap.Format.RGBA8888)

        var x = 0
        var y = 0
        for (image in images) {
            atlas.drawPixmap(image.pixmap, x * SIZE, y * SIZE)
            blockTileMap[image.type] = Pair(x, y)

            x++
            if (x >= grid) {
                x = 0
                y++
            }
            assert(y <= grid)
        }

        tileSize = 1f / (x + 1)

        images.forEach {
            it.pixmap.dispose()
        }

//        Debug.ifEnabled {
//            PixmapIO.writePNG(Gdx.files.absolute("core/assets/blocks/atlas.png"), atlas)
//        }

        val texture = Texture(atlas)
        return texture
    }

    private fun loadBlocksImages(): Array<BlockImage> {
        val images = mutableListOf<BlockImage>()

        for (type in VoxelType.types) {
            if (VoxelType.metaTypes.contains(type))
                continue

            val fileName = "blocks/${type.toString().toLowerCase()}.$IMG_EXT"
            val fileHandle = Gdx.files.internal(fileName) ?: error(FileNotFoundException("File $fileName not found!"))

            val image = Pixmap(fileHandle)

            if (image.width != SIZE) error { "Failed to load file $fileName. Image width must be $SIZE." }
            if (image.height != SIZE) error { "Failed to load file $fileName. Image height must be $SIZE." }

            images.add(BlockImage(type, image))
        }
        return images.toTypedArray()
    }
}