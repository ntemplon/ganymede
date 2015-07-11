package com.jupiter.ganymede.util

import java.util.*
import java.util.stream.Collectors

/**
 * Created by nathan on 5/21/15.
 */
public class CategorizedTree<T : com.jupiter.ganymede.util.Categorized<C>, C : Category<C>>(root: C) {

    // Static Methods
    private fun <U : Category<U>> decompose(finalCategory: U): List<U> {
        val list = ArrayList<U>()
        var cat: U? = finalCategory
        do {
            list.add(0, cat)
            cat = cat?.parent
        } while (cat != null)
        return list
    }


    // Fields
    public val root: Node<T, C>


    init {
        this.root = Node<T, C>(root)
    }


    // Public Methods
    public fun add(item: T) {
        this.nodeFor(item.category)?.addValue(item)
    }

    public fun remove(item: T) {
        this.nodeFor(item.category)?.removeValue(item)
    }

    public fun getItems(category: C): Set<T> {
        val node = this.nodeFor(category)
        if (node != null) {
            return node.values
        } else {
            return setOf()
        }
    }

    public fun getChildren(category: C): Set<C> {
        val node = this.nodeFor(category)
        return if (node != null) {
            node.getChildren()
                    .map { it.category }
                    .toSet()
        } else {
            setOf()
        }
    }


    // Private Methods
    private fun nodeFor(category: C): Node<T, C>? {
        val categories = decompose(category)
        val index = categories.indexOf(this.root.category)
        return if (index != -1) {
            var node = this.root
            for (i in index + 1..categories.size() - 1) {
                node = node.getChild(categories.get(i))
            }
            node
        } else {
            null
        }
    }


    // Nested Classes
    public class Node<T : Categorized<C>, C : Category<C>>(public val category: C) {
        public val values: Set<T>
            get() { return this.valuesInternal.toSet() }

        private val children = LinkedHashMap<C, Node<T, C>>()
        private val valuesInternal = LinkedHashSet<T>()

        public fun getChild(category: C): Node<T, C> {
            if (!this.children.containsKey(category)) {
                this.children.put(category, Node<T, C>(category))
            }
            return this.children.get(category)
        }

        public fun getChildren(): Collection<Node<T, C>> = this.children.values().toSet()


        // Private Methods
        internal fun addChild(node: Node<T, C>) {
            this.children.put(node.category, node)
        }

        internal fun addValue(value: T) {
            this.valuesInternal.add(value)
        }

        internal fun removeValue(value: T) {
            this.valuesInternal.remove(value)
        }
    }
}
