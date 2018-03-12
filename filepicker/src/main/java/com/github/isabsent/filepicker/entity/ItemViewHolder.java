package com.github.isabsent.filepicker.entity;

import android.text.Spannable;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.isabsent.filepicker.R;

import static eltos.simpledialogfragment.list.CustomListDialog.MULTI_CHOICE;
import static eltos.simpledialogfragment.list.CustomListDialog.SINGLE_CHOICE;

public class ItemViewHolder {
    private TextView textView;
    private ImageView imageView;

    public ItemViewHolder(View itemView) {
        textView = itemView.findViewById(R.id.text1);
        imageView = itemView.findViewById(R.id.icon);
    }

    public void bind(Item item, int choiceMode, boolean isItemChecked, Spannable text) {
        textView.setText(text);
        imageView.setImageResource(item.isFile() ? R.mipmap.ic_insert_drive_file_black_24dp : R.mipmap.ic_folder_black_24dp);

        if (choiceMode == SINGLE_CHOICE || choiceMode == MULTI_CHOICE)
            ((Checkable) textView).setChecked(isItemChecked);
    }
}
