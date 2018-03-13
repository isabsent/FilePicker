package com.github.isabsent.filepicker.entity;

import android.graphics.Color;
import android.text.Spannable;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.isabsent.filepicker.R;
import com.github.isabsent.filepicker.SimpleFilePickerDialog;

import static eltos.simpledialogfragment.list.CustomListDialog.MULTI_CHOICE;
import static eltos.simpledialogfragment.list.CustomListDialog.SINGLE_CHOICE;

public class ItemViewHolder {
    private View itemView;
    private TextView textView;
    private ImageView imageView;

    public ItemViewHolder(View itemView) {
        this.itemView = itemView;
        textView = (TextView) itemView.findViewById(R.id.text1);
        imageView = (ImageView) itemView.findViewById(R.id.icon);
    }

    public void bind(Item item, SimpleFilePickerDialog.CompositeMode mode, boolean isItemChecked, Spannable text) {
        textView.setText(text);
        imageView.setImageResource(item.isFile() ? R.mipmap.ic_insert_drive_file_black_24dp : R.mipmap.ic_folder_black_24dp);

        int choiceMode = mode.getChoiceMode();
        if (choiceMode == SINGLE_CHOICE || choiceMode == MULTI_CHOICE)
            ((Checkable) textView).setChecked(isItemChecked);
        else if (!SimpleFilePickerDialog.CompositeMode.isImmediate(mode))
            itemView.setBackgroundColor(isItemChecked ? Color.LTGRAY : Color.TRANSPARENT);
    }
}
