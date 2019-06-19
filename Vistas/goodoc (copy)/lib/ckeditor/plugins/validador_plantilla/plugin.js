/**
 * Basic sample plugin inserting abbreviation elements into CKEditor editing area.
 *
 * Created out of the CKEditor Plugin SDK:
 * http://docs.ckeditor.com/#!/guide/plugin_sdk_sample_1
 */

// Register the plugin within the editor.

CKEDITOR.plugins.add( 'validador_plantilla', {

	// Register the icons.
	icons: 'val_plan',

	// The plugin initialization logic goes inside this method.
	init: function( editor ) {

		// Define an editor command that opens our dialog.
		editor.addCommand( 'val_plan', new CKEDITOR.dialogCommand( 'val_plan' ) );
                
		// Create a toolbar button that executes the above command.
		editor.ui.addButton( 'val_plan', {

			// The text part of the button (if available) and tooptip.
			label: 'Validador de plantillas',

			// The command to execute on click.
			command: 'val_plan',

			// The button placement in the toolbar (toolbar group name).
			toolbar: 'insert',
                        
		});
                
		// Register our dialog file. this.path is the plugin folder path.
                CKEDITOR.dialog.add( 'val_plan', this.path + 'dialogs/val_plan.js' );
                
                if (editor.addMenuItems)
                    editor.addMenuItem("val_plan", {
                    label: 'Validador de plantillas',
                    command: 'val_plan',
                    group: 'clipboard', order: 10
                    });
                    if (editor.contextMenu)
                    editor.contextMenu.addListener(function() {
                    return { "val_plan": CKEDITOR.TRISTATE_OFF };
                    });


	}
});

