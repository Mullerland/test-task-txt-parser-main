package ru.rsh12.parser.util;
/*
 * Date: 09.02.2022
 * Time: 4:50 AM
 * */

/**
 * Represents a node in a binary tree.
 */
public class Node {

    protected Long section;
    protected String text;
    protected Node left;
    protected Node right;

    protected Node(Long section, String text) {
        this.section = section;
        this.text = text;
        right = null;
        left = null;
    }

    public Long getSection() {
        return section;
    }

    public void setSection(Long section) {
        this.section = section;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

}
