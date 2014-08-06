package com.gwtskeleton.client.animation;

import java.math.BigDecimal;
import java.util.logging.Logger;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;

public class HighlightAnimation extends Animation{
	
	Logger logger = Logger.getLogger(HighlightAnimation.class.getName());
	
	private Element element;
	private double opacityIncrement;
	private double targetOpacity;
	private double baseOpacity;

	public HighlightAnimation(Element element) {
		this.element = element;
	}

	@Override
	protected void onUpdate(double progress) {
		logger.fine(String.valueOf(progress));
		element.getStyle().setOpacity(baseOpacity + progress * opacityIncrement);
	}

	@Override
	protected void onComplete() {
		super.onComplete();
		element.getStyle().setOpacity(targetOpacity);
	}

	public void fade(int duration, double targetOpacity) {
		if(targetOpacity > 1.0) {
			targetOpacity = 1.0;
		}
		if(targetOpacity < 0.0) {
			targetOpacity = 0.0;
		}
		this.targetOpacity = targetOpacity;
		
		try {
			baseOpacity = new BigDecimal("1").doubleValue();
			opacityIncrement = targetOpacity - baseOpacity;
			run(duration);
		} catch(NumberFormatException e) {
			e.printStackTrace();
			// set opacity directly
			onComplete();
		}
	}

}
