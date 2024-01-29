package com.coioteshd2024.ui.downloadmanager.ui;

public interface Selectable<T>
{
    T getItemKey(int position);

    int getItemPosition(T key);
}
