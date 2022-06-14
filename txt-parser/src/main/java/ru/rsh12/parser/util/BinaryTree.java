package ru.rsh12.parser.util;
/*
 * Date: 09.02.2022
 * Time: 4:09 AM
 * */

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents the implementation of a binary tree.
 */
public class BinaryTree {

    @JsonValue
    Node root;

    public void add(Long section, String text) {
        root = addRecursive(root, section, text);
    }

    private Node addRecursive(Node current, Long section, String text) {
        if (current == null) {
            return new Node(section, text);
        }

        if (section < current.section) {
            current.left = addRecursive(current.left, section, text);
        } else if (section > current.section) {
            current.right = addRecursive(current.right, section, text);
        }

        return current;
    }

}
