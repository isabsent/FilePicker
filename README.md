# FilePicker
File and folder picker dialog

## Gradle:

    dependencies {
      compile 'com.github.isabsent:filepicker:1.1.01'
    }

## Maven:

    <dependency>
      <groupId>com.github.isabsent</groupId>
      <artifactId>filepicker</artifactId>
      <version>1.1.01</version>
      <type>pom</type>
    </dependency>

![FILE_ONLY_SINGLE_CHOICE](https://github.com/isabsent/FilePicker/blob/master/screenshot/Screenshot_01.png)
![FOLDER_ONLY_MULTI_CHOICE](https://github.com/isabsent/FilePicker/blob/master/screenshot/Screenshot_02.png)
![FILE_OR_FOLDER_DIRECT_CHOICE_SELECTION](https://github.com/isabsent/FilePicker/blob/master/screenshot/Screenshot_03.png)

You can explore **internal memory file system** with any of 12 modes: 

        FILE_ONLY_SINGLE_CHOICE
        FILE_ONLY_MULTI_CHOICE
        FILE_ONLY_DIRECT_CHOICE_IMMEDIATE
        FILE_ONLY_DIRECT_CHOICE_SELECTION

        FOLDER_ONLY_SINGLE_CHOICE
        FOLDER_ONLY_MULTI_CHOICE
        FOLDER_ONLY_DIRECT_CHOICE_IMMEDIATE
        FOLDER_ONLY_DIRECT_CHOICE_SELECTION

        FILE_OR_FOLDER_SINGLE_CHOICE
        FILE_AND_FOLDER_MULTI_CHOICE
        FILE_OR_FOLDER_DIRECT_CHOICE_IMMEDIATE
        FILE_OR_FOLDER_DIRECT_CHOICE_SELECTION


To use this library in your Activity class you have to realize `SimpleFilePickerDialog.InteractionListenerInt` interface if you are defying dialog title as an `int` resource ID:

    @Override
    public void showListItemDialog(int titleResId, String folderPath, SimpleFilePickerDialog.CompositeMode mode, String dialogTag){
        SimpleFilePickerDialog.build(folderPath, mode)
                .title(titleResId)
                .show(this, dialogTag);
    }

or `SimpleFilePickerDialog.InteractionListenerString` interface if you are defying dialog title as a `String`:
   
    @Override
    public void showListItemDialog(String title, String folderPath, SimpleFilePickerDialog.CompositeMode mode, String dialogTag){
        SimpleFilePickerDialog.build(folderPath, mode)
                .title(title)
                .show(this, dialogTag);
    }
or any of two above if you are not specifying a dialog title. A result of selection is available in `onResult` callback:

    @Override
    public boolean onResult(@NonNull String dialogTag, int which, @NonNull Bundle extras) {
        switch (dialogTag) {
            case PICK_DIALOG:
                if (extras.containsKey(SimpleFilePickerDialog.SELECTED_SINGLE_PATH)) 
		        //Do what you want with single selection		
                else if (extras.containsKey(SimpleFilePickerDialog.SELECTED_PATHS))
		        //Do what you want with multiple selection	  
                break;
        }
        return false;
    }

[The example of usage](https://github.com/isabsent/FilePicker/blob/master/app/src/main/java/com/github/isabsent/filepickerdemo/MainActivity.java)

This library is an extension of [**SimpleDialogFragments**](https://github.com/eltos/SimpleDialogFragments)

## License

Copyright 2018 Lev Popovich (github.com/isabsent)

Licensed under the Apache License 2.0

You may only use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software in compliance with the License. For more information visit http://www.apache.org/licenses/LICENSE-2.0
The above copyright notice alongside a copy of the Apache License shall be included in all copies or substantial portions of the Software not only in source code but also in a license listing accessible by the user.

I would appreciate being notified when you release an application that uses this library. Thanks!
