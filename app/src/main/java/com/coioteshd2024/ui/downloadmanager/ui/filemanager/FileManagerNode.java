/**
 * EasyPlex - Movies - Live Streaming - TV Series, Anime
 *
 * @author @Y0bEX
 * @package  EasyPlex - Movies - Live Streaming - TV Series, Anime
 * @copyright Copyright (c) 2021 Y0bEX,
 * @license http://codecanyon.net/wiki/support/legal-terms/licensing-terms/
 * @profile https://codecanyon.net/user/yobex
 * @link yobexd@gmail.com
 * @skype yobexd@gmail.com
 **/

package com.coioteshd2024.ui.downloadmanager.ui.filemanager;

import androidx.annotation.NonNull;

/*
 * The class encapsulates a node and properties, that determine whether he is a file or directory.
 */

public class FileManagerNode implements FileNode<FileManagerNode>
{
    public static final String PARENT_DIR = "..";
    public static final String ROOT_DIR = "/";

    private String node;
    private int nodeType;
    private boolean enabled;

    public FileManagerNode(String item, int itemType, boolean enabled)
    {
        node = item;
        nodeType = itemType;
        this.enabled = enabled;
    }

    @Override
    public String getName()
    {
        return node;
    }

    @Override
    public void setName(String name)
    {
        node = name;
    }

    @Override
    public int getType()
    {
        return nodeType;
    }

    @Override
    public void setType(int type)
    {
        nodeType = type;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isDirectory()
    {
        return nodeType == Type.DIR;
    }

    @Override
    public int compareTo(@NonNull FileManagerNode another)
    {
        return node.compareTo(another.getName());
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof FileManagerNode))
            return false;

        if (o == this)
            return true;

        FileManagerNode fileManagerNode = (FileManagerNode)o;

        return (node == null || node.equals(fileManagerNode.node)) &&
                nodeType == fileManagerNode.nodeType &&
                enabled == fileManagerNode.enabled;
    }

    @Override
    public String toString()
    {
        return "FileManagerNode{" +
                "node='" + node + '\'' +
                ", nodeType=" + nodeType +
                ", enabled=" + enabled +
                '}';
    }
}
