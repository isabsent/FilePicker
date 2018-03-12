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
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FILE_ONLY_DIRECT_CHOICE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FILE_ONLY_MULTI_CHOICE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FILE_ONLY_SINGLE_CHOICE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FILE_OR_FOLDER_DIRECT_CHOICE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FILE_OR_FOLDER_SINGLE_CHOICE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FOLDER_ONLY_DIRECT_CHOICE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FOLDER_ONLY_MULTI_CHOICE;
import static com.github.isabsent.filepicker.SimpleFilePickerDialog.CompositeMode.FOLDER_ONLY_SINGLE_CHOICE;

public class MainActivity extends AppCompatActivity implements SimpleFilePickerDialog.OnInteractionListener {
    private static final String  PICK_DIALOG = "PICK_DIALOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        //File picker modes
        findViewById(R.id.file_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_file, rootPath, FILE_ONLY_SINGLE_CHOICE);
            }
        });

        findViewById(R.id.file_multi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_files, rootPath, FILE_ONLY_MULTI_CHOICE);
            }
        });

        findViewById(R.id.file_direct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_file, rootPath, FILE_ONLY_DIRECT_CHOICE);
            }
        });

        //Folder picker modes
        findViewById(R.id.folder_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_folder, rootPath, FOLDER_ONLY_SINGLE_CHOICE);
            }
        });

        findViewById(R.id.folder_multi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_folders, rootPath, FOLDER_ONLY_MULTI_CHOICE);
            }
        });

        findViewById(R.id.folder_direct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_folder, rootPath, FOLDER_ONLY_DIRECT_CHOICE);
            }
        });

        //Mixed picker modes
        findViewById(R.id.file_or_folder_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_file_or_folder, rootPath, FILE_OR_FOLDER_SINGLE_CHOICE);
            }
        });

        findViewById(R.id.file_and_folder_multi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_files_and_folders, rootPath, FILE_AND_FOLDER_MULTI_CHOICE);
            }
        });

        findViewById(R.id.file_or_folder_direct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemDialog(R.string.dialog_title_pick_file_or_folder, rootPath, FILE_OR_FOLDER_DIRECT_CHOICE);
            }
        });
    }

    @Override
    public void showListItemDialog(String title, String folderPath, SimpleFilePickerDialog.CompositeMode mode){
        buildListItemDialog(folderPath, mode)
                .title(title)
                .show(this, PICK_DIALOG);
    }

    @Override
    public void showListItemDialog(int titleResId, String folderPath, SimpleFilePickerDialog.CompositeMode mode){
        buildListItemDialog(folderPath, mode)
                .title(titleResId)
                .show(this, PICK_DIALOG);
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
        }
        return false;
    }

    private SimpleFilePickerDialog buildListItemDialog(String folderPath, SimpleFilePickerDialog.CompositeMode mode){
        return SimpleFilePickerDialog.build()
                .path(folderPath, mode)
                .choiceMin(1)
                .pos(R.string.button_deeper)
                .neg(R.string.button_up)
                .neut(R.string.button_pick);
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
