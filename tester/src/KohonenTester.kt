import com.jupiter.ganymede.math.vector.Vector
import com.jupiter.ganymede.neural.KohonenMap

/**
 * Created by nathan on 7/11/15.
 *
 */

fun main(args: Array<String>) {
    val map = KohonenMap(
            { first, second ->
                var sum = 0.0

                for (i in 1..first.dimension) {
                    sum += (first[i] - second[i]) * (first[i] - second[i])
                }

                Math.sqrt(sum)
            },
            listOf(Vector(1.0, 0.0, 0.0), Vector(0.0, 1.0, 0.0), Vector(0.0, 0.0, 1.0)))

    println(map.categorize(Vector(0.1, 0.9, 0.2)))
}