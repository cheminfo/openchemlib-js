package com.actelion.research.gui.generic;


public interface GenericDialog {
	int PREFERRED = -2;
	int FILL = -1;

	void setLayout(int[] hLayout, int[] vLayout);
	void add(GenericComponent c, int x, int y);
	void add(GenericComponent c, int x1, int y1, int x2, int y2);
	GenericCheckBox createCheckBox(String text);
	GenericComboBox createComboBox();
	GenericLabel createLabel(String text);
	GenericTextField createTextField(int width, int height);
	void setEventConsumer(GenericEventListener<GenericActionEvent> consumer);
	void showDialog();  // must wait until OK or Cancel is pressed
	void disposeDialog();
	void showMessage(String message);
}
