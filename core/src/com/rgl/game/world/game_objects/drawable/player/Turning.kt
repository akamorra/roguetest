package com.rgl.game.world.game_objects.drawable.player

open class Turning {
    var observers = mutableSetOf<Observer>()

    fun callObservers() {
        for(it in observers) it.update()
    }

    fun bigCallObservers(){
        for (it in observers) it.turn()
    }

    fun attach(it : Observer) {
        observers.add(it)
    }

    fun detach(it : Observer) {
        observers.remove(it)
    }
}
