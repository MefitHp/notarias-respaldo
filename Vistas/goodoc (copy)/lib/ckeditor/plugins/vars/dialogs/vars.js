/**
 * The abbr dialog definition.
 *
 * Created out of the CKEditor Plugin SDK:
 * http://docs.ckeditor.com/#!/guide/plugin_sdk_sample_1
 */

// Our dialog definition.

function llenaVars(vars) {
    var l = [];
    _.each(vars, function(d) {
        var n = [];
        n.push(d.desc);
        n.push(d.data);
        l.push(n);
    })
    return l;
}

var variablesJSON = llenaVars(lista.vars);
var gruposJSON = llenaVars(lista.gpos);
var frmvarsJSON = llenaVars(lista.frms);
var vg = "";
var selector = "";
CKEDITOR.dialog.add('varsDialog', function(editor) {
    return {
        // Basic properties of the dialog window: title, minimum size.
        title: 'Insertar variable y grupos',
        minWidth: 400,
        minHeight: 220,
        // Dialog window contents definition.
        contents: [
            {
                // Definition of the Basic Settings dialog tab (page).
                id: 'tab-vars',
                label: 'Variables',
                // The tab contents.
                elements: [
                    {
                        type: 'select',
                        id: 'var',
                        multiple: true,
                        label: 'Agregar variable',
                        style: 'width:390px; height:200px;',
                        items: variablesJSON,
                        'default': variablesJSON[0],
                        onClick: function(api) {
                            var CurrObj = CKEDITOR.dialog.getCurrent();
                            console.log(CurrObj.definition.dialog._.currentTabId);
                            //alert( 'Current value: ' + this.getValue() );
                        }
                    }

                ]
            },
            // Definition of the Advanced Settings dialog tab (page).

            //CAMPO DE GRUPOS
            {
                id: 'tab-gpos',
                label: 'Grupos',
                elements: [
                    {
                        type: 'select',
                        id: 'gpo',
                        multiple: true,
                        label: 'Agregar grupo',
                        style: 'width:390px; height:200px;',
                        items: gruposJSON,
                        'default': gruposJSON[0],
                        onClick: function(api) {

                        }
                    }

                ]
            },
            {
                id: 'tab-frm',
                label: 'Variables formulario',
                elements: [
                    {
                        type: 'select',
                        id: 'frm',
                        multiple: true,
                        label: 'Agregar variable formulario',
                        style: 'width:390px; height:200px;',
                        items: frmvarsJSON,
                        'default': frmvarsJSON[0],
                        onClick: function(api) {
                            var CurrObj = CKEDITOR.dialog.getCurrent();
                        }
                    }

                ]


            }
        ],
        // This method is invoked once a user clicks the OK button, confirming the dialog.
        onOk: function() {
            // The context of this function is the dialog object itself.
            // http://docs.ckeditor.com/#!/api/CKEDITOR.dialog

            var dialog = this;
            var tabFocus = this.definition.dialog._.currentTabId;
            switch (tabFocus) {

                case "tab-vars":
                    selector = "var";
                    vg = editor.document.createElement('vars');

                    break;

                case "tab-gpos":
                    selector = "gpo";
                    var vg = editor.document.createElement('gpos');
                    break;

                case "tab-frm":
                    selector = "frm";
                    var vg = editor.document.createElement('frm');
                    break;
                    break;

                default :

            }

            idvar_Sel = dialog.getContentElement(tabFocus, selector)._.inputId;

            var strVars = "";
            //var datosVars =""
            $("#" + idvar_Sel + " option:selected").each(function() {
                strVars += "${" + selector + ":" + $(this).text() + "}";
                strVars = strVars.replace(" ", "");
                //datosVars += $(this).val()+"||";
            });
            // Creates a new <abbr> element.
            vg.setText(strVars);
            //vg.setAttribute('tipoDatos',datosVars);
            editor.insertElement(vg);
        }
    };
});