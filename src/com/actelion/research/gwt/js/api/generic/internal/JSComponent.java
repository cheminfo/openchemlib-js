package com.actelion.research.gwt.js.api.generic.internal;

import com.google.gwt.core.client.JavaScriptObject;
import java.util.ArrayList;

import com.actelion.research.gui.generic.*;

public class JSComponent implements GenericComponent {
  private JavaScriptObject mJsComponent;
  private ArrayList<GenericEventListener<GenericActionEvent>> mConsumerList;

  public JSComponent(JavaScriptObject jsComponent) {
    mJsComponent = jsComponent;
    mConsumerList = new ArrayList<>();
    setEventHandler(jsComponent);
  }

  private native void setEventHandler(JavaScriptObject jsComponent)
  /*-{
    var component = this;
    jsComponent.setEventHandler(function(what, value) {
      component.@com.actelion.research.gwt.js.api.generic.internal.JSComponent::fireEventFromJs(II)(what, value);
    });
  }-*/;

  private void fireEventFromJs(int what, int value) {
    fireEvent(new GenericActionEvent(this, what, value));
  }

  public JavaScriptObject getJsComponent() {
    return mJsComponent;
  }

  @Override
  public native void setEnabled(boolean b)
  /*-{
    var component = this.@com.actelion.research.gwt.js.api.generic.internal.JSComponent::getJsComponent()();
    return component.setEnabled(b);
  }-*/;

  @Override
  public void addEventConsumer(GenericEventListener<GenericActionEvent> consumer) {
    mConsumerList.add(consumer);
  }

  @Override
  public void removeEventConsumer(GenericEventListener<GenericActionEvent> consumer) {
    mConsumerList.remove(consumer);
  }
  
  @Override
  public void fireEvent(GenericActionEvent event) {
    for (GenericEventListener<GenericActionEvent> consumer:mConsumerList) {
      consumer.eventHappened(event);
    }
  }
}
