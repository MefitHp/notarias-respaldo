/**
 * The abbr dialog definition.
 *
 * Created out of the CKEditor Plugin SDK:
 * http://docs.ckeditor.com/#!/guide/plugin_sdk_sample_1
 */

// Our dialog definition.
 

CKEDITOR.dialog.add( 'val_plan', function( editor ) {
	return {
                title: 'Validador de sintaxis de plantilla',
                height: 40,
                width:100,
                contents:[{
                id: 'val_plan',
                padding: 0,
                expand: false,
                elements:
                [{
                    type: "html",
                    html: "<p>¿Desea validar la plantilla en este momento?</p>"
                }]
                }],

		// This method is invoked once a user clicks the OK button, confirming the dialog.
		onOk: function() {
                       validar_plantilla(0);
		}
	};
});