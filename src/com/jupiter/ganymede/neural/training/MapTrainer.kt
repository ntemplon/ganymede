package com.jupiter.ganymede.neural.training

import com.jupiter.ganymede.math.vector.Vector
import com.jupiter.ganymede.neural.Exemplar
import com.jupiter.ganymede.neural.SelfOrganizingMap

/**
 * Created by nathan on 7/10/15.
 */
public abstract class MapTrainer<T : SelfOrganizingMap<U>, U : Exemplar> {

    public fun train(vector: Vector, trainingRate: Double) {

    }

}