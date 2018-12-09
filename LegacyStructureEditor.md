# Legacy Structure Editor API

You can test it on line:

- <a href="https://cheminfo.github.io/openchemlib-js/examples/Editor.html" target="_blank">Editor</a>
- <a href="https://cheminfo.github.io/openchemlib-js/examples/ShowStructures.html" target="_blank">showStructures</a>
- <a href="https://cheminfo.github.io/openchemlib-js/examples/SVG.html" target="_blank">SVG</a>

## Usage in your page

load this script:

     src="openchemlib-full.js"

create a div with the following attributes:

     id="editor"
     style="width:100%;height:400px;border:solid;border-width:2px"
     view-only="false"
     show-idcode="true"
     data-idcode="fhvacFGXhDFICDPx@fCHW@@UDhdmdCVZ``J@@@ !BLsicOgDjrHKHwW{@rHJW`lbBrMu~pG{@rHI[E\}bup}"

execute the following JS:

        editorCtrl = window.OCL.StructureEditor.createEditor("editor");

## Attributes (defaults are in UPPERCASE):

     view-only="FALSE" | "true"     = Shows the toolbar

     show-idcode="FALSE" | "true"   = Shows the input control containing the current IDCode

     data-idcode=""                 = This allows you to specify the molecule IDCode in the editor

     is-fragment="FALSE" | "true"   = Sets the editor into fragment (query) mode.
                                      Please note that the data-icode attribute overrules this behavior:
                                      If specified, the mode depends on the passed molecule

     show-fragment-indicator= "FALSE" | "true"
                                    = Shows a "Q" on the right lower if the Molecule is in fragment (query) mode

     ignore-stereo-errors= "FALSE" | "true"
                                    = If true, then shows no magenta-colored atom/bonds on stereo errors

     no-stereo-text= "FALSE" | "true"
                                    = If true, does not show stereo information text
