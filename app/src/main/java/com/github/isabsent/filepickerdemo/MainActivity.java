package com.github.isabsent.filepickerdemo;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.isabsent.filepicker.SimpleFilePickerDialog;

import java.util.List;

import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FILE_AND_FOLDER_MULTI_CHOICE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FILE_ONLY_DIRECT_CHOICE_IMMEDIATE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FILE_ONLY_DIRECT_CHOICE_POSTPONED;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FILE_ONLY_MULTI_CHOICE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FILE_ONLY_SINGLE_CHOICE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FILE_OR_FOLDER_DIRECT_CHOICE_IMMEDIATE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FILE_OR_FOLDER_DIRECT_CHOICE_POSTPONED;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FILE_OR_FOLDER_SINGLE_CHOICE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FOLDER_ONLY_DIRECT_CHOICE_IMMEDIATE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FOLDER_ONLY_DIRECT_CHOICE_POSTPONED;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FOLDER_ONLY_MULTI_CHOICE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FOLDER_ONLY_SINGLE_CHOICE;

public class MainActivity extends AppCompatActivity implements
        SimpleFilePickerDialog.InteractionListenerString,
        SimpleFilePickerDialog.InteractionListenerInt {

    private static final String          //If you need to show a few different mode pickers in one activity,
            PICK_DIALOG = "PICK_DIALOG";  //you can distinguish them by this tag in onResult callback.

    @Override
    public void showListItemDialog(int titleResId, String folderPath, SimpleFilePickerDialog.CompositeMode mode, String dialogTag){
        SimpleFilePickerDialog.build(folderPath, mode)
                .title(titleResId)
//                .neut("hoch")    //Some customization if you need
//                .neg("eröffnen")
//                .pos("wählen")
//                .choiceMin(1);
//                .filterable(true, true)
                .show(this, dialogTag);
    }

    @Override
    public void showListItemDialog(String title, String folderPath, SimpleFilePickerDialog.CompositeMode mode, String dialogTag){
        SimpleFilePickerDialog.build(folderPath, mode)
                .title(title)
//                .neut("hoch")    //Some customization if you need
//                .neg("eröffnen")
//                .pos("wählen")
//                .choiceMin(1);
//                .filterable(true, true)
                .show(this, dialogTag);
    }

    @Override
    public boolean onResult(@NonNull String dialogTag, int which, @NonNull Bundle extras) {
        switch (dialogTag) {
            case PICK_DIALOG:
                if (extras.containsKey(SimpleFilePickerDialog.SELECTED_SINGLE_PATH)) {
                    String selectedSinglePath = extras.getString(SimpleFilePickerDialog.SELECTED_SINGLE_PATH);
                    Toast.makeText(this, "Path selected:\n" + selectedSinglePath, Toast.LENGTH_LONG).show();
                } else if (extras.containsKey(SimpleFilePickerDialog.SELECTED_PATHS)){
                    List<String> selectedPaths = extras.getStringArrayList(SimpleFilePickerDialog.SELECTED_PATHS);
                    showSelectedPathsToast(selectedPaths);
                }
                break;
//            case PICK_DIALOG_OTHER:
//                //Do what you want here
//                break;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

//Button clicks with all available modes as an usage example:

//File picker modes
        findViewById(R.id.file_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_file, rootPath, FILE_ONLY_SINGLE_CHOICE, PICK_DIALOG);
            }
        });

        findViewById(R.id.file_multi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(getString(R.string.dialog_title_pick_files), rootPath, FILE_ONLY_MULTI_CHOICE, PICK_DIALOG);
            }
        });

        findViewById(R.id.file_direct_immediate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_file, rootPath, FILE_ONLY_DIRECT_CHOICE_IMMEDIATE, PICK_DIALOG);
            }
        });

        findViewById(R.id.file_direct_postponed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_file, rootPath, FILE_ONLY_DIRECT_CHOICE_POSTPONED, PICK_DIALOG);
            }
        });

//Folder picker modes
        findViewById(R.id.folder_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(getString(R.string.dialog_title_pick_folder), rootPath, FOLDER_ONLY_SINGLE_CHOICE, PICK_DIALOG);
            }
        });

        findViewById(R.id.folder_multi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_folders, rootPath, FOLDER_ONLY_MULTI_CHOICE, PICK_DIALOG);
            }
        });

        findViewById(R.id.folder_direct_immediate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(getString(R.string.dialog_title_pick_folder), rootPath, FOLDER_ONLY_DIRECT_CHOICE_IMMEDIATE, PICK_DIALOG);
            }
        });

        findViewById(R.id.folder_direct_postponed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(getString(R.string.dialog_title_pick_folder), rootPath, FOLDER_ONLY_DIRECT_CHOICE_POSTPONED, PICK_DIALOG);
            }
        });

//Mixed picker modes
        findViewById(R.id.file_or_folder_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_file_or_folder, rootPath, FILE_OR_FOLDER_SINGLE_CHOICE, PICK_DIALOG);
            }
        });

        findViewById(R.id.file_and_folder_multi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(getString(R.string.dialog_title_pick_files_and_folders), rootPath, FILE_AND_FOLDER_MULTI_CHOICE, PICK_DIALOG);
            }
        });

        findViewById(R.id.file_or_folder_direct_immediate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_file_or_folder, rootPath, FILE_OR_FOLDER_DIRECT_CHOICE_IMMEDIATE, PICK_DIALOG);
            }
        });

        findViewById(R.id.file_or_folder_direct_postponed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_file_or_folder, rootPath, FILE_OR_FOLDER_DIRECT_CHOICE_POSTPONED, PICK_DIALOG);
            }
        });
    }

    private void showSelectedPathsToast(List<String> selectedPaths){
        if (selectedPaths != null && !selectedPaths.isEmpty()){
            String selectedPathsString = "\n";
            for (String path : selectedPaths)
                selectedPathsString += path + "\n";
            Toast.makeText(this, "Paths selected:" + selectedPathsString, Toast.LENGTH_LONG).show();
        }
    }
}
