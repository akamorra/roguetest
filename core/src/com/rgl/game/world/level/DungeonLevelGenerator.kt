package com.rgl.game.world.level

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.Textures
import com.rgl.game.world.MapCFG
import com.rgl.game.world.MapCFG.MULTIPLIER
import com.rgl.game.world.MapCFG.ROOMSIZE
import com.rgl.game.world.level.Level.update
import java.util.LinkedList
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

object DungeonLevelGenerator {
    private var renderPos: Vector2 = Vector2(0.0f, 0.0f)
    private var pointer: Int = 0
    private var pointerX: Int = 0
    private var pointerY: Int = 0
    private var data: Array<Array<Tile>> =
        Array(1) { Array(1) { Tile(Vector2(1.0f, 1.0f), 0, Tile.Index(0, 0), false) } }
    private var listOfRooms: ArrayList<Room> = ArrayList()
    private var graphOfRooms: Graph = Graph()
    private var spawned: Boolean = false
    private var breakFlag: Boolean = false
    private var weightMap: Array<Array<Float>> = Array(1) { Array(1) { 1.0f } }

    fun getDungeonLevel(rooms: Int): Array<Array<Tile>> {
        clearData()
        addRooms(rooms)
        addToGraph()
        setIndexes()
        setRenderPoses()
        setCenters()
        Thread {
            makeConnections()
        }.start()
        prepare()
        return data
    }

    fun get(): Array<Array<Tile>>{
        return data
    }

    //adding rooms as a vertexes to graph
    private fun addToGraph() {
        for (i in 0..<listOfRooms.size) graphOfRooms.add(i.toString(), listOfRooms[i])
        //graphOfRooms.get().forEach { entry -> System.out.println(entry.toString() + "\n") }
    }


    //debug feature to render connections between rooms centers
    fun renderLinks(batch: ShapeRenderer) {
        graphOfRooms.get().forEach { entry ->
            entry.value.edgesList.forEach {
                batch.color = entry.value.color
                batch.line(it.start.room.getCenter(), it.end.room.getCenter())
            }
        }
    }

    //erases array before making a new level
    private fun clearData() {
        data = Array(MapCFG.MAPSIZE) {
            Array(MapCFG.MAPSIZE) {
                Tile(
                    Vector2(1.0f, 1.0f),
                    0,
                    Tile.Index(0, 0),
                    false
                )
            }
        }
        for (it in data) {
            for (it1 in it) {
                it1.isObstacle = false
            }
        }
        weightMap = Array(MapCFG.MAPSIZE) {
            Array(MapCFG.MAPSIZE) {
                (0.0f)
            }
        }
    }

    //debug output to terminal
    private fun print() {
        for (it in listOfRooms) println(it.toString())
    }

    //making doors for every room towards its neighbor rooms
    private fun addRooms(rooms: Int) {
        for (it in 0..rooms) {
            listOfRooms.add(Room.generator.getNextRandom(MapCFG.ROOMSIZE, MapCFG.ROOMSIZE))
        }
        for (it in listOfRooms) {
            spawned = false
            while (!spawned) {

                breakFlag = false
                pointerX = Random.nextInt(
                    MapCFG.MAPSIZE / 2 - ROOMSIZE * MULTIPLIER + ROOMSIZE,
                    MapCFG.MAPSIZE / 2 + ROOMSIZE * MULTIPLIER + ROOMSIZE
                )
                pointerY = Random.nextInt(
                    MapCFG.MAPSIZE / 2 - ROOMSIZE * MULTIPLIER + ROOMSIZE,
                    MapCFG.MAPSIZE / 2 + ROOMSIZE * MULTIPLIER + ROOMSIZE
                )
                if (pointerX <= 0 || pointerX >= MapCFG.MAPSIZE) pointerX =
                    Random.nextInt(MapCFG.MAPSIZE) + 2
                if (pointerY <= 0 || pointerY >= MapCFG.MAPSIZE) pointerY =
                    Random.nextInt(MapCFG.MAPSIZE) + 2
                if ((pointerX + it.getSrc().size + 4) > data.size - 2) pointerX -= (pointerX + it.getSrc().size) - data[0].size + 1
                if ((pointerY + it.getSrc()[0].size + 4) > data[0].size - 2) pointerY -= (pointerY + it.getSrc()[0].size) - data[0].size + 1
                if (pointerX <= 1) pointerX + 2
                if (pointerY <= 1) pointerY + 2

                for (i in 0..<it.getSrc().size + 2) {
                    if (!breakFlag) for (j in 0..<it.getSrc()[0].size + 2) {
                        //System.out.print((pointerX + i - 1).toString() + " : "+(pointerY + j - 1).toString()+"; ")
                        if (data[pointerX + i - 1][pointerY + j - 1].textureID.toInt() != 0) {
                            breakFlag = true
                            break
                        }

                    }
                    //println()
                }
                if (!breakFlag) spawned = true
            }

            for (i in 0..<it.getSrc().size) {
                for (j in 0..<it.getSrc()[0].size) {
                    it.getSrc()[i][j].index.x = pointerX + i
                    it.getSrc()[i][j].index.y = pointerY + j
                    data[pointerX + i][pointerY + j] = it.getSrc()[i][j]
                }
            }

        }
    }

    //filling tiles indexes to make it equal to their position at map array
    private fun setIndexes() {
        for (it in data) {
            for (it1 in it) {
                it1.index = Tile.Index(
                    data.indexOf(
                        it
                    ), it.indexOf(it1)
                )
            }
        }
    }

    //calculates tiles render positions at global coordinates system
    private fun setRenderPoses() {
        renderPos.x = 0.0f
        renderPos.y = 0.0f
        pointer = 0
        for (it in data) {
            for (it1 in it) {
                renderPos.x -= MapCFG.TILESIZE / 2
                renderPos.y -= (MapCFG.TILESIZE / 2 - MapCFG.TILESIZE / 6) + 5
                it1.renderPos.x = renderPos.x
                it1.renderPos.y = renderPos.y
            }
            pointer++
            renderPos.y = 0 - pointer * (MapCFG.TILESIZE / 2 - MapCFG.TILESIZE / 6 + 5)
            renderPos.x = 0 + pointer * MapCFG.TILESIZE / 2
        }

        for (it in listOfRooms) {
            for (it1 in it.getSrc()) {
                for (it2 in it1) {
                    it2.renderPos.x = data[it2.index.x][it2.index.y].renderPos.x
                    it2.renderPos.y = data[it2.index.x][it2.index.y].renderPos.y
                    it2.isDrawable = true
                }
            }
        }
    }

    //calculates centers of rooms in global coordinates system
    private fun setCenters() {
        for (it in listOfRooms) {
            it.setCenter()
        }
    }

    //making doors&&making corridors between connected rooms
    private fun makeConnections() {

        graphOfRooms.check()
        graphOfRooms.get().forEach {
            graphOfRooms.neighbors(it.key).forEach { it2 ->
                it.value.room.makeDoor(it2.room)
                update()
            }
        }
        setObstacles()
        println("Start")
        makeCorridors()
        println("End")
    }

    //fill weights map
    private fun prepareWeights(end: Tile) {
        for (row in weightMap.indices) {
            for (col in 0..<weightMap[0].size) {
                if (data[row][col].isObstacle)
                    weightMap[row][col] = 99999.0f else
                    weightMap[row][col] =
                        sqrt(
                            (end.index.x - data[row][col].index.x).toFloat()
                                .pow(2) + (end.index.y - data[row][col].index.y).toFloat().pow(
                                2
                            )
                        )
                //System.out.print(" "+ weightMap[row][col])
            }
            //System.out.println()
        }
    }

    //making corridors using weight map
    private fun makeCorridors() {

        graphOfRooms.get().forEach { vertex ->
            graphOfRooms.neighbors(vertex.key).forEach { it ->
                if (!it.edgesList.contains(Graph.Vertex.edge(vertex.value, it))) {
                    val start = vertex.value.room.getDoor(it.room)
                    val end = it.room.getDoor(vertex.value.room)
                    val path = path(start, end)
                    var flag = false
                    //System.out.println()
                    var p = path.size

                    path.asReversed().forEach {
                        //System.out.print(""+it.index.x+";"+it.index.y+" -> ")
                        if (!flag) {
                            if (it == start) {
                                flag = true
                            } else {
                                it.isDrawable = true
                                it.textureID = (Random.nextInt(3) + 1).toByte()
                                it.isObstacle = false
                                if (data[it.index.x + 1][it.index.y + 1].isObstacle)
                                    data[it.index.x + 1][it.index.y + 1].textureID =
                                        Textures.STONEWALL_TRANSPARENT.id
                                if (data[it.index.x + 2][it.index.y + 2].isObstacle) data[it.index.x + 2][it.index.y + 2].textureID =
                                    Textures.STONEWALL_TRANSPARENT.id
                                if (data[it.index.x + 1][it.index.y].isObstacle) if (!Textures.listOfWalkable.contains(
                                        data[it.index.x + 1][it.index.y].textureID
                                    )
                                )
                                    if (!Textures.listOfWalkable.contains(data[it.index.x + 1][it.index.y + 1].textureID)) data[it.index.x + 1][it.index.y].textureID =
                                        Textures.STONEWALL_TRANSPARENT_E.id
                                if (data[it.index.x][it.index.y + 1].isObstacle) if (!Textures.listOfWalkable.contains(
                                        data[it.index.x][it.index.y + 1].textureID
                                    )
                                )
                                    if (!Textures.listOfWalkable.contains(data[it.index.x + 1][it.index.y + 1].textureID)) data[it.index.x][it.index.y + 1].textureID =
                                        Textures.STONEWALL_TRANSPARENT_W.id
                            }
                        }
                    }
                    it.room.fillW()
                    vertex.value.room.fillW()
                    it.room.listOfDoors.forEach {

                        data[it.x][it.y].textureID = Textures.DOOR.id
                        data[it.x][it.y].isObstacle = false
                        data[it.x][it.y].isDrawable = true

                    }
                    vertex.value.room.listOfDoors.forEach {

                        data[it.x][it.y].textureID = Textures.DOOR.id
                        data[it.x][it.y].isObstacle = false
                        data[it.x][it.y].isDrawable = true
                    }
                    if (!vertex.value.edgesList.contains(
                            Graph.Vertex.edge(
                                it,
                                vertex.value
                            )
                        )
                    ) vertex.value.edgesList.add(Graph.Vertex.edge(it, vertex.value))
                    if (!it.edgesList.contains(
                            Graph.Vertex.edge(
                                it,
                                vertex.value
                            )
                        )
                    ) it.edgesList.add(Graph.Vertex.edge(it, vertex.value))
                }
                    update()

            }
        }
    }

    //A* algorithm for Array[][] of weights
    private fun path(start: Tile, end: Tile): LinkedList<Tile> {
        prepareWeights(end)
        var currentNode = start.index
        val reachable: LinkedList<Tile.Index> = LinkedList()
        var newReachable: MutableSet<Tile.Index> = mutableSetOf()
        val explored: MutableSet<Tile.Index> = mutableSetOf()
        val path: LinkedList<Tile> = LinkedList<Tile>()
        var neighbors: MutableSet<Tile.Index>
        reachable.add(start.index)
        var min = 9999.0f
        while (reachable.isNotEmpty()) {

            neighbors = mutableSetOf()
            if (currentNode.x < MapCFG.MAPSIZE - 1 && currentNode.y < MapCFG.MAPSIZE - 1 && currentNode.x > 1 && currentNode.y > 1) neighbors.add(
                data[currentNode.x + 1][currentNode.y].index
            )
            if (currentNode.x < MapCFG.MAPSIZE - 1 && currentNode.y < MapCFG.MAPSIZE - 1 && currentNode.x > 1 && currentNode.y > 1) neighbors.add(
                data[currentNode.x - 1][currentNode.y].index
            )
            if (currentNode.x < MapCFG.MAPSIZE - 1 && currentNode.y < MapCFG.MAPSIZE - 1 && currentNode.x > 1 && currentNode.y > 1) neighbors.add(
                data[currentNode.x][currentNode.y - 1].index
            )
            if (currentNode.x < MapCFG.MAPSIZE - 1 && currentNode.y < MapCFG.MAPSIZE - 1 && currentNode.x > 1 && currentNode.y > 1) neighbors.add(
                data[currentNode.x][currentNode.y + 1].index
            )
            reachable.remove(currentNode)


            neighbors.forEach {
                if (weightMap[it.x][it.y] <= min && !explored.contains(it)) {
                    //System.out.println("Contains in neighbor"+!explored.contains(it))
                    min = weightMap[it.x][it.y]
                    currentNode = it
                } else if (explored.contains(currentNode)) {
                    min = 9998.0f
                    weightMap[it.x][it.y] = min
                }

            }
            println("en")
            explored.forEach {
                newReachable.remove(it)
            }
            if (data[currentNode.x][currentNode.y] == end
            ) {
                path.add(data[currentNode.x][currentNode.y])
                break
            }

            /*println("Doors:" + start.index.x + " " + start.index.y + " -> " + end.index.x + " " + end.index.y)
            println(
                "CurrentNode: $currentNode\n Explored:$explored\n Contained" + explored.contains(
                    currentNode
                ).toString() + " Weight: " + weightMap[currentNode.x][currentNode.y]
            )*/
            newReachable = neighbors.toMutableSet()
            //newReachable.remove(currentNode)
            explored.forEach {
                newReachable.remove(it)
            }

            neighbors.forEach {
                if (data[it.x][it.y].isObstacle)
                    newReachable.remove(it)
            }

            newReachable.forEach {
                if (it !in explored) {
                    data[it.x][it.y].prev = currentNode
                    path.add(data[currentNode.x][currentNode.y])
                    reachable.add(it)
                    explored.add(currentNode)
                }
            }
            //System.out.println("egg")
        }
        //System.out.println("egg")

        path.removeLast()
        return path
    }

    //set colors of lines between centers
    private fun prepare() {
        graphOfRooms.get().forEach { entry ->
            entry.value.color = Color(Color.RED)
        }
    }

    private fun setObstacles() {
        for (it in data)
            for (it2 in it)
                Textures.listOfObstacles.forEach { it3 ->
                    if (it2.textureID == it3) {
                        it2.isObstacle = true
                    }
                }
    }
}

