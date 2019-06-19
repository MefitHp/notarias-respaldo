/**
 * Basic sample plugin inserting abbreviation elements into CKEditor editing area.
 *
 * Created out of the CKEditor Plugin SDK:
 * http://docs.ckeditor.com/#!/guide/plugin_sdk_sample_1
 */

// Register the plugin within the editor.

CKEDITOR.plugins.add( 'guardar', {

	// Register the icons.
	icons: 'guardar',

	// The plugin initialization logic goes inside this method.
	init: function( editor ) {

		// Define an editor command that opens our dialog.
		editor.addCommand( 'guardar', new CKEDITOR.dialogCommand( 'guardar' ) );
                
		// Create a toolbar button that executes the above command.
		editor.ui.addButton( 'guardar', {

			// The text part of the button (if available) and tooptip.
			label: 'Guardar',

			// The command to execute on click.
			command: 'guardar',

			// The button placement in the toolbar (toolbar group name).
			toolbar: 'insert',
                        
		});
                
		// Register our dialog file. this.path is the plugin folder path.
                CKEDITOR.dialog.add( 'guardar', this.path + 'dialogs/guardar.js' );
                
                if (editor.addMenuItems)
                    editor.addMenuItem("guardar", {
                    label: 'Guardar',
                    command: 'guardar',
                    group: 'clipboard', order: 10
                    });
                    if (editor.contextMenu)
                    editor.contextMenu.addListener(function() {
                    return { "guardar": CKEDITOR.TRISTATE_OFF };
                    });


	}
});

