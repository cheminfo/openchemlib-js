package com.actelion.research.gwt.core;

import java.io.StringReader;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.*;

@JsType
@JsNamespace("$wnd.actchem")
@JsExport
public class SDFileParser {

	private com.actelion.research.chem.io.SDFileParser parser;
	
	@JsExport
	public SDFileParser(String sdf, String[] fields) {
		parser = new com.actelion.research.chem.io.SDFileParser(new StringReader(sdf), fields);
	}
	
	public boolean next() {
		return parser.next();
	}
	
	public Molecule getMolecule() {
		return new Molecule(parser.getMolecule());
	}
	
	public String getMolfile() {
		return parser.getNextMolFile();
	}
	
	public String getField(String name) {
		String[] names = parser.getFieldNames();
		for(int i = 0; i < names.length; i++) {
			if(names[i].equals(name)) {
				return parser.getFieldData(i);
			}
		}
		return null;
	}
	
	public String[] getFieldNames() {
		return parser.getFieldNames();
	}
	
	public String getFieldData(int idx) {
		return parser.getFieldData(idx);
	}
	
	public static JavaScriptObject parseString(String sdfile, JavaScriptObject options) {
		return parseNative(sdfile, options);
	}
	
	private static native JavaScriptObject parseNative(String sdfile, JavaScriptObject options) /*-{
		
		options = options || {
			includeMolfile: true,
			includeLabels: true
		};
		
		var header=sdfile.substr(0,1000);
		var crlf="\n";
		if (header.indexOf("\r\n")>-1) {
 			crlf="\r\n";
		} else if (header.indexOf("\r")>-1) {
			crlf="\r";
		}
		var sdfParts=sdfile.split(crlf+"$$$$"+crlf);
		var molecules=[], i = 0, l = sdfParts.length;
		for ( ; i < l ; i++) {
			var sdfPart=sdfParts[i];
			var parts=sdfPart.split(crlf+">");
			if (parts.length>0 && parts[0].length>10) {
				var molecule={};
				molecules[molecules.length] = molecule;
				var molfile = parts[0]+crlf;
				if(options.includeMolfile)
					molecule.molfile=molfile;
				molecule.molecule = $wnd.actelion.Molecule.fromMolfile(molfile);
				if(options.includeLabels) {
					var labels = {};
					molecule.labels = labels;
					for (var j=1, jj=parts.length; j<jj; j++) {
						var lines=parts[j].split(crlf);
						var from=lines[0].indexOf("<");
						var to=lines[0].indexOf(">");
						var label=lines[0].substring(from+1,to);
						for (var k=1; k<lines.length-1; k++) {
							if (labels[label]) {
								labels[label]+=crlf+lines[k];
							} else {
								labels[label]=lines[k];
							}
						}
					}
				}
			}
		}
		return molecules;
	}-*/;
	

	
}
