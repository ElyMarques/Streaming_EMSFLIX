/* * EasyPlex - Movies - Live Streaming - TV Series, Anime * * @author @Y0bEX * @package EasyPlex - Movies - Live Streaming - TV Series, Anime * @copyright Copyright (c) 2021 Y0bEX, * @license http://codecanyon.net/wiki/support/legal-terms/licensing-terms/ * @profile https://codecanyon.net/user/yobex * @link yobexd@gmail.com * @skype yobexd@gmail.com **/

package com.coioteshd2024.ui.downloadmanager.core.utils;

import android.content.Context;
import android.text.format.Formatter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.coioteshd2024.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BindingAdapterUtils
{
    @BindingAdapter(value = {"fileSize", "formatFileSize"}, requireAll = false)
    public static void formatFileSize(@NonNull TextView view,
                                      long fileSize,
                                      @Nullable String formatFileSize)
    {
        Context context = view.getContext();
        String sizeStr = getFileSize(context, fileSize);
        view.setText((formatFileSize == null ? sizeStr : String.format(formatFileSize, sizeStr)));
    }

    @BindingAdapter({"formatDate"})
    public static void formatDate(@NonNull TextView view, long date)
    {
        view.setText(SimpleDateFormat.getDateTimeInstance()
                .format(new Date(date)));
    }

    public static String getFileSize(@NonNull Context context,
                                     long fileSize)
    {
        return fileSize >= 0 ? Formatter.formatFileSize(context, fileSize) :
                context.getString(R.string.not_available);
    }

    public static int getProgress(long downloaded, long total)
    {
        return (total == 0 ? 0 : (int)((downloaded * 100) / total));
    }

    public static String formatProgress(@NonNull Context context,
                                        long downloaded,
                                        long total,
                                        @NonNull String fmt)
    {
        return String.format(fmt,
                getFileSize(context, downloaded),
                getFileSize(context, total),
                getProgress(downloaded, total));
    }
}
