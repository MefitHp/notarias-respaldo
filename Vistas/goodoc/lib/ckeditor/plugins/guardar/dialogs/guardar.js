/**
 * The abbr dialog definition.
 *
 * Created out of the CKEditor Plugin SDK:
 * http://docs.ckeditor.com/#!/guide/plugin_sdk_sample_1
 */

// Our dialog definition.
 

CKEDITOR.dialog.add( 'guardar', function( editor ) {
	return {
                title: 'Guardar documento',
                height: 40,
                width:100,
                contents:[{
                id: 'guardar',
                padding: 0,
                expand: false,
                elements:
                [{
                type: "html",
                html: "<p>¿Desea guardar la escritura en este momento?</p>"
                }]
                }],

		// This method is invoked once a user clicks the OK button, confirming the dialog.
		onOk: function() {
                       guardar_escritura(0);
		}
	};
});