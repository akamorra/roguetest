package com.rgl.game.world.game_objects.drawable.player

import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.Textures
import com.rgl.game.world.MapCFG
import com.rgl.game.world.level.Tile
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class PathFinder {
    private var data: Array<Array<Tile>> = Array(1) {
        Array(1) {
            Tile(
                Vector2(1.0f, 1.0f), Textures.EMPTY_TEXTURE.id,
                Tile.Index(0, 0), false
            )
        }
    }
    private var weightMap: Array<Array<Int>> = Array(1) { Array(1) { 1 } }

    init {
        weightMap = Array(data.size) { Array(data[0].size) { 1 } }
    }

    private fun prepareWeights(end: Tile, data: Array<Array<Tile>>) {
        weightMap = Array(data.size) { Array(data[0].size) { 1 } }
        for (row in weightMap.indices) {
            for (col in 0..<weightMap[0].size) {
                if (!Textures.listOfWalkable.contains(data[row][col].textureID))
                    weightMap[row][col] = 99999 else
                    weightMap[row][col] =
                        abs(row - end.index.x) + abs(col - end.index.y)
            }
        }
    }

    fun findPath(start: Tile, end: Tile, data: Array<Array<Tile>>): MutableSet<Tile> {
        this.data = data
        prepareWeights(end, data)
        var tempIndex = Tile.Index(0, 0)
        var min = 9999.0

        var currentNode: Tile.Index
        val openSet: MutableSet<Tile.Index> = mutableSetOf()
        val closedSet: MutableSet<Tile.Index> = mutableSetOf()
        var path: MutableSet<Tile> = mutableSetOf()
        var neighbors: MutableSet<Tile.Index>
        var neighborsUnexplored: MutableSet<Tile.Index> = mutableSetOf()
        openSet.add(start.index)
        currentNode = start.index
        while (openSet.isNotEmpty()) {

            neighbors = mutableSetOf()
            openSet.forEach {
                if (Textures.listOfWalkable.contains(data[it.x][it.y].textureID)) {
                    var min2 = sqrt(
                        (end.index.x - it.x).toDouble().pow(2) + (end.index.y - it.y).toDouble()
                            .pow(2) + weightMap[it.x][it.y]
                    )
                    if (min2 <= min) {
                        min = min2
                        tempIndex = it
                    } else {
                      if(currentNode==tempIndex)  tempIndex=openSet.first()
                    }
                }
            }

            currentNode = tempIndex
            if (currentNode == end.index) {
                path.add(data[currentNode.x][currentNode.y])
                path = mutableSetOf()

                while (data[currentNode.x][currentNode.y].prev != start.index) {
                    currentNode = data[currentNode.x][currentNode.y].prev!!
                    path.add(data[currentNode.x][currentNode.y])
                }
                path.add(start)
                return path
            }
            openSet.remove(currentNode)
            closedSet.add(currentNode)
            if (currentNode.x < MapCFG.MAPSIZE - 3 && currentNode.y < MapCFG.MAPSIZE - 3 && currentNode.x > 1 && currentNode.y > 1

            ) neighbors.add(
                data[currentNode.x + 1][currentNode.y].index
            )
            if (currentNode.x < MapCFG.MAPSIZE - 3 && currentNode.y < MapCFG.MAPSIZE - 3 && currentNode.x > 1 && currentNode.y > 1

            ) neighbors.add(
                data[currentNode.x - 1][currentNode.y].index
            )
            if (currentNode.x < MapCFG.MAPSIZE - 1 && currentNode.y < MapCFG.MAPSIZE - 1 && currentNode.x > 1 && currentNode.y > 1

            ) neighbors.add(
                data[currentNode.x][currentNode.y - 1].index
            )
            if (currentNode.x < MapCFG.MAPSIZE - 1 && currentNode.y < MapCFG.MAPSIZE - 1 && currentNode.x > 1 && currentNode.y > 1

            ) neighbors.add(
                data[currentNode.x][currentNode.y + 1].index
            )

            neighborsUnexplored = mutableSetOf()
            neighbors.forEach {
                if (!closedSet.contains(it)) {
                    neighborsUnexplored.add(it)
                }
                neighborsUnexplored.forEach {
                    if (!openSet.contains(it) && Textures.listOfWalkable.contains(data[it.x][it.y].textureID)) {
                        data[it.x][it.y].prev = currentNode
                        openSet.add(it)
                    }
                }
            }

        }
        return path
    }
}
