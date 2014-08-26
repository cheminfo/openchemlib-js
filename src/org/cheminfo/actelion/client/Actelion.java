package org.cheminfo.actelion.client;

import org.timepedia.exporter.client.ExporterUtil;

import com.google.gwt.core.client.EntryPoint;

public class Actelion implements EntryPoint {

	@Override
	public void onModuleLoad(){
		ExporterUtil.exportAll();
	}

}
