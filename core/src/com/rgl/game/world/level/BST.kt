package com.rgl.game.world.level

// Define the Node class
class Node(var data: Float) {
    var left: Node? = null
    var right: Node? = null

}

class BST {
    var root: Node? = null

    // Insert data into the BST
    fun insert(value: Float) {
        root = insertRecursive(root, value)
    }

    private fun insertRecursive(current: Node?, value: Float): Node {
        if (current == null) {
            return Node(value)
        }
        if (value < current.data) {
            current.left = insertRecursive(current.left, value)
        } else if (value > current.data) {
            current.right = insertRecursive(current.right, value)
        }
        return current
    }

    // Search for a value in the tree
    fun search(value: Float): Boolean {
        return searchRecursive(root, value)
    }

    private fun searchRecursive(current: Node?, value: Float): Boolean {
        if (current == null) {
            return false
        }
        if (value == current.data) {
            return true
        }
        return if (value < current.data) {
            searchRecursive(current.left, value)
        } else {
            searchRecursive(current.right, value)
        }
    }

    // Delete a value from the tree
    fun delete(value: Float) {
        root = deleteRecursive(root, value)
    }

    private fun deleteRecursive(current: Node?, value: Float): Node? {
        if (current == null) {
            return null
        }
        if (value == current.data) {
            if (current.left == null && current.right == null) {
                return null
            }
            if (current.right == null) {
                return current.left
            }
            if (current.left == null) {
                return current.right
            }
            val smallestValue = findSmallestValue(current.right!!)
            current.data = smallestValue
            current.right = deleteRecursive(current.right, smallestValue)
            return current
        }
        if (value < current.data) {
            current.left = deleteRecursive(current.left, value)
            return current
        }
        current.right = deleteRecursive(current.right, value)
        return current
    }

    private fun findSmallestValue(root: Node): Float {
        return root.left?.let { findSmallestValue(it) } ?: root.data
    }

    // Find the minimum value in the tree
    fun findMin(): Float? {
        return findMinRecursive(root)
    }

    private fun findMinRecursive(node: Node?): Float? {
        return node?.left?.let { findMinRecursive(it) } ?: node?.data
    }

    // Find the maximum value in the tree
    fun findMax(): Float? {
        return findMaxRecursive(root)
    }

    private fun findMaxRecursive(node: Node?): Float? {
        return node?.right?.let { findMaxRecursive(it) } ?: node?.data
    }

    // Tree traversal methods
    fun inOrderTraversal(node: Node?) {
        if (node != null) {
            inOrderTraversal(node.left)
            println(node.data)
            inOrderTraversal(node.right)
        }
    }

    fun preOrderTraversal(node: Node?) {
        if (node != null) {
            println(node.data)
            preOrderTraversal(node.left)
            preOrderTraversal(node.right)
        }
    }

    fun postOrderTraversal(node: Node?) {
        if (node != null) {
            postOrderTraversal(node.left)
            postOrderTraversal(node.right)
            println(node.data)
        }
    }
}