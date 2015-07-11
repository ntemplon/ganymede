package com.jupiter.ganymede.neural

import com.jupiter.ganymede.math.vector.Vector

/**
 * Created by nathan on 7/10/15.
 */
public interface SelfOrganizingMap<T : Exemplar> {
    public val dimension: Int
    public val exemplars: Collection<T>
    public fun categorize(vector: Vector): T
}

public interface Exemplar {
    public val vector: Vector
}