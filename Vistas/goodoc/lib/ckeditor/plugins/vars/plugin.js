/**
 * Basic sample plugin inserting abbreviation elements into CKEditor editing area.
 *
 * Created out of the CKEditor Plugin SDK:
 * http://docs.ckeditor.com/#!/guide/plugin_sdk_sample_1
 */

// Register the plugin within the editor.
var lista;
var urlJson = 'variables/listar';
var lista = {};
lista.vars = new Array();
lista.gpos = new Array();
lista.frms = new Array();

function pidoDatos() {

    angular.element($("body").scope().$apply(function(scope) {
        scope.refreshListVars(function(){parsea_variables(scope)});
    }))
}

function parsea_variables(scope) {
    _.each(scope.lista.variableList, function(variable, i) {
        lista.vars.push({"nombre": "", "desc": variable.dsnombre, "data": ""})
    });
    _.each(scope.lista.varFormDinamicos, function(variable, i) {
        lista.frms.push({"nombre": "", "desc": variable.nombre, "data": ""})
    });
}

CKEDITOR.plugins.add('vars', {
    // Register the icons.
    icons: 'vars',
    // The plugin initialization logic goes inside this method.
    init: function(editor) {

        // Define an editor command that opens our dialog.
        editor.addCommand('vars', new CKEDITOR.dialogCommand('varsDialog'));

        // Create a toolbar button that executes the above command.
        editor.ui.addButton('vars', {
            // The text part of the button (if available) and tooptip.
            label: 'Insertar variables',
            // The command to execute on click.
            command: 'vars',
            // The button placement in the toolbar (toolbar group name).
            toolbar: 'insert'
        });

        // Register our dialog file. this.path is the plugin folder path.
        pidoDatos();
        CKEDITOR.dialog.add('varsDialog', this.path + 'dialogs/vars.js');

        if (editor.addMenuItems)
            editor.addMenuItem("vars", {
                label: 'Insertar grupo o variable',
                command: 'vars',
                group: 'clipboard', order: 9
            });
        if (editor.contextMenu)
            editor.contextMenu.addListener(function() {
                return {"vars": CKEDITOR.TRISTATE_OFF};
            });


    }
});

