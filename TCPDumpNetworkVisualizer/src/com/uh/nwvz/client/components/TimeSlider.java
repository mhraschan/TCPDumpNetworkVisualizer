package com.uh.nwvz.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.kiouri.sliderbar.client.view.SliderBarHorizontal;

public class TimeSlider extends SliderBarHorizontal {
	
	TimeSliderImages images = GWT.create(TimeSliderImages.class);
	
	public TimeSlider(int maxValue, String width) {
        setLessWidget(new Image(images.less()));
        setMoreWidget(new Image(images.more()));
        setScaleWidget(new Image(images.scale().getUrl()), 16);
        setMoreWidget(new Image(images.more()));
        setDragWidget(new Image(images.drag()));
        this.setWidth(width);
        this.setMaxValue(maxValue);
    }
            
    interface TimeSliderImages extends ClientBundle {
        
            @Source("timesliderdrag.png")
            ImageResource drag();

            @Source("timesliderless.png")
            ImageResource less();

            @Source("timeslidermore.png")
            ImageResource more();

            @Source("timesliderscale.png")
            DataResource scale();
    }    
    
}
