package com.rgl.game.world.level

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.rgl.game.graphics.Textures
import com.rgl.game.world.MapCFG
import com.rgl.game.world.MapCFG.MAPSIZE
import com.rgl.game.world.MapCFG.MULTIPLIER
import com.rgl.game.world.MapCFG.ROOMSIZE
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

object DungeonLevelGenerator {
    private var renderPos: Vector2 = Vector2(0.0f, 0.0f)
    private var pointer: Int = 0
    private var pointerX: Int = 0
    private var pointerY: Int = 0
    private var itemsGenerator = ItemsGenerator()
    private var monsterSpawner = MonstersSpawner()
    private var data: Array<Array<Tile>> =
        Array(1) {
            Array(1) {
                Tile(
                    Vector2(1.0f, 1.0f),
                    Textures.EMPTY_TEXTURE.id,
                    Tile.Index(0, 0),
                    false
                )
            }
        }
    private var listOfRooms: ArrayList<Room> = ArrayList()
    private var graphOfRooms: Graph = Graph()
    private var spawned: Boolean = false
    private var breakFlag: Boolean = false
    private var weightMap: Array<Array<Int>> = Array(1) { Array(1) { 1 } }

    fun getSpawnPoint(): Tile.Index {
        var flag=true
        do {
            val rand = Random.nextInt(0, listOfRooms.count())
            val row =
                Random.nextInt(3, graphOfRooms.get(rand.toString())!!.room.getSrc().count() - 1) - 1
            val col = Random.nextInt(
                3,
                graphOfRooms.get(rand.toString())!!.room.getSrc()[row].count() - 1
            ) - 1
            if(!graphOfRooms[rand.toString()]!!.room.getSrc()[row][col].containsInstance) {
                graphOfRooms[rand.toString()]!!.isSpawnPoint = true
                return graphOfRooms[rand.toString()]!!.room.getSrc()[row][col].index
                flag=false
            }
        } while(flag)
        return Tile.Index(0, 0)
    }

    fun getEndPoint(): Tile.Index {
        var flag = true
        while (flag) {
            val rand = Random.nextInt(0, listOfRooms.count())
            if (!graphOfRooms[rand.toString()]!!.isSpawnPoint) {
                val row =
                    Random.nextInt(
                        4,
                        graphOfRooms.get(rand.toString())!!.room.getSrc().count() + 1
                    ) - 2
                val col = Random.nextInt(
                    4,
                    graphOfRooms.get(rand.toString())!!.room.getSrc()[row].count() + 1
                ) - 2
                graphOfRooms[rand.toString()]!!.room.getSrc()[row][col].index
                graphOfRooms[rand.toString()]!!.isEndPoint = true
                return graphOfRooms[rand.toString()]!!.room.getSrc()[row][col].index
            }
        }
        return Tile.Index(0, 0)
    }


    fun getDungeonLevel(rooms: Int, lvl: Level): Array<Array<Tile>> {
        clearData()
        addRooms(rooms)
        setIndexes()
        setRenderPoses()
        setCenters()
        addToGraph()
        Thread {
            makeConnections(lvl)
        }.start()
        itemsGenerator.generate(
            MapCFG.ROOMCOUNT*8,
            MapCFG.ROOMCOUNT*8,
            lvl.objectsManager,
            graphOfRooms, lvl.player
        )
        monsterSpawner.generate(
            MapCFG.ROOMCOUNT,
            lvl.monstersManager,
            graphOfRooms,
            lvl.player,
            lvl
        )
        prepare()
        return data
    }

    fun get(): Array<Array<Tile>> {
        return data
    }

    //adding rooms as a vertexes to graph
    private fun addToGraph() {
        for (i in 0..<listOfRooms.size) graphOfRooms.add(i.toString(), listOfRooms[i])
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
        listOfRooms = ArrayList()
        graphOfRooms = Graph()
        spawned = false
        breakFlag = false
        renderPos = Vector2(0.0f, 0.0f)
        pointer = 0
        pointerX = 0
        pointerY = 0
        itemsGenerator = ItemsGenerator()
        monsterSpawner = MonstersSpawner()
        data = Array(MAPSIZE) {
            Array(MAPSIZE) {
                Tile(
                    Vector2(1.0f, 1.0f),
                    Textures.EMPTY_TEXTURE.id,
                    Tile.Index(0, 0),
                    false
                )
            }
        }
        weightMap = Array(MAPSIZE) { Array(MAPSIZE) { 0 } }
        for (it in data) {
            for (it1 in it) {
                it1.isObstacle = false
            }
        }
    }

    //making doors for every room towards its neighbor rooms
    private fun addRooms(rooms: Int) {
        for (it in 0..rooms) {
            listOfRooms.add(Room.generator.getNextRandom(ROOMSIZE, ROOMSIZE))
        }
        for (it in listOfRooms) {
            spawned = false
            while (!spawned) {

                breakFlag = false
                pointerX = Random.nextInt(
                    MAPSIZE / 2 - ROOMSIZE * MULTIPLIER + ROOMSIZE,
                    MAPSIZE / 2 + ROOMSIZE * MULTIPLIER + ROOMSIZE
                )
                pointerY = Random.nextInt(
                    MAPSIZE / 2 - ROOMSIZE * MULTIPLIER + ROOMSIZE,
                    MAPSIZE / 2 + ROOMSIZE * MULTIPLIER + ROOMSIZE
                )
                if (pointerX <= 0 || pointerX >= MAPSIZE) pointerX =
                    Random.nextInt(MAPSIZE) + 2
                if (pointerY <= 0 || pointerY >= MAPSIZE) pointerY =
                    Random.nextInt(MAPSIZE) + 2
                if ((pointerX + it.getSrc().size + 5) > data.size - 2) pointerX -= (pointerX + it.getSrc().size) - data[0].size + 1
                if ((pointerY + it.getSrc()[0].size + 5) > data[0].size - 2) pointerY -= (pointerY + it.getSrc()[0].size) - data[0].size + 1
                if (pointerX <= 1) pointerX + 2
                if (pointerY <= 1) pointerY + 2

                for (i in 0..<it.getSrc().size + 4) {
                    if (!breakFlag) for (j in 0..<it.getSrc()[0].size + 4) {
                        if (data[pointerX + i][pointerY + j].textureID != Textures.EMPTY_TEXTURE.id) {
                            breakFlag = true
                            break
                        }
                    }
                }



                if (!breakFlag) spawned = true
            }

            for (i in 1..<it.getSrc().size + 1) {
                for (j in 1..<it.getSrc()[0].size + 1) {
                    it.getSrc()[i - 1][j - 1].index.x = pointerX + i
                    it.getSrc()[i - 1][j - 1].index.y = pointerY + j
                    data[pointerX + i][pointerY + j] = it.getSrc()[i - 1][j - 1]
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
    private fun makeConnections(lvl: Level) {
        graphOfRooms.check()
        graphOfRooms.get().forEach {
            graphOfRooms.neighbors(it.key).forEach { it2 ->
                it.value.room.makeDoor(it2.room)
                lvl.update()
            }
        }
        setObstacles()
        makeCorridors(lvl)
        makeCorridors(lvl)
        graphOfRooms.get().forEach {
            it.value.room.fillW()
        }
        lvl.update()
    }

    //fill weights map
    private fun prepareWeights(end: Tile) {
        for (row in weightMap.indices) {
            for (col in 0..<weightMap[0].size) {
                if (data[row][col].isObstacle)
                    weightMap[row][col] = 99999 else
                    weightMap[row][col] = abs(row - end.index.x) + abs(col - end.index.y)
            }
        }
    }

    //making corridors using weight map
    private fun makeCorridors(lvl: Level) {

        graphOfRooms.get().forEach { vertex ->
            graphOfRooms.neighbors(vertex.key).forEach { ngb ->
                if (ngb.edgesList.contains(Graph.Vertex.edge(vertex.value, ngb))) {
                    val start = vertex.value.room.getDoor(ngb.room)
                    val end = ngb.room.getDoor(vertex.value.room)
                    val path = path(start, end)
                    path.forEach { it ->

                        it.isDrawable = true
                        it.textureID = (Random.nextInt(123, 127)).toByte()
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
                ngb.edgesList.remove(Graph.Vertex.edge(ngb, vertex.value))
                vertex.value.edgesList.remove(Graph.Vertex.edge(ngb, vertex.value))
                lvl.update()
                lvl.update()

            }
        }
    }

    //A* algorithm for Array[][] of weights
    private fun path(start: Tile, end: Tile): MutableSet<Tile> {
        prepareWeights(end)
        var tempIndex = Tile.Index(0, 0)
        var min = 9999.0

        var currentNode: Tile.Index
        val openSet: MutableSet<Tile.Index> = mutableSetOf()
        val closedSet: MutableSet<Tile.Index> = mutableSetOf()
        var path: MutableSet<Tile> = mutableSetOf()
        var neighbors: MutableSet<Tile.Index>
        var neighborsUnexplored: MutableSet<Tile.Index>
        openSet.add(start.index)
        currentNode = start.index
        while (openSet.isNotEmpty()) {

            neighbors = mutableSetOf()
            openSet.forEach {
                val min2 = sqrt(
                    (end.index.x - it.x).toDouble().pow(2) + (end.index.y - it.y).toDouble()
                        .pow(2) + weightMap[it.x][it.y]
                )
                if (min2 <= min) {
                    min = min2
                    tempIndex = it
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
                return path
            }
            openSet.remove(currentNode)
            closedSet.add(currentNode)
            if (currentNode.x < MAPSIZE - 3 && currentNode.y < MAPSIZE - 3 && currentNode.x > 1 && currentNode.y > 1) neighbors.add(
                data[currentNode.x + 1][currentNode.y].index
            )
            if (currentNode.x < MAPSIZE - 3 && currentNode.y < MAPSIZE - 3 && currentNode.x > 1 && currentNode.y > 1) neighbors.add(
                data[currentNode.x - 1][currentNode.y].index
            )
            if (currentNode.x < MAPSIZE - 1 && currentNode.y < MAPSIZE - 1 && currentNode.x > 1 && currentNode.y > 1) neighbors.add(
                data[currentNode.x][currentNode.y - 1].index
            )
            if (currentNode.x < MAPSIZE - 1 && currentNode.y < MAPSIZE - 1 && currentNode.x > 1 && currentNode.y > 1) neighbors.add(
                data[currentNode.x][currentNode.y + 1].index
            )
            neighborsUnexplored = mutableSetOf()
            neighbors.forEach {
                if (!closedSet.contains(it)) {
                    neighborsUnexplored.add(it)
                }
                neighborsUnexplored.forEach {
                    if (!openSet.contains(it)) {
                        data[it.x][it.y].prev = currentNode
                        openSet.add(it)
                    }
                }
            }
        }

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

