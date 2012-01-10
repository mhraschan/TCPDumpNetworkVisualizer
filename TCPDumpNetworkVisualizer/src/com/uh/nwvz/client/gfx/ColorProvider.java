package com.uh.nwvz.client.gfx;

import com.google.gwt.canvas.dom.client.CssColor;
import com.uh.nwvz.client.gfx.commons.Colors;
import com.uh.nwvz.client.network.Protocol;

public class ColorProvider {

	public static CssColor getNormalColor(Protocol protocol) {
		switch (protocol) {
		case HTTP: 
			return Colors.LightRedNormal;
		case ICMP:
			return Colors.LightBlueNormal;
		case MIXED:
			return Colors.LightGreenNormal;
		case OTHER:
			return Colors.LightGreyNormal;
		case LOCALHOST:
			return Colors.LightCyanNormal;
		case TCP:
			return Colors.LightYellowNormal;
		case UDP:
			return Colors.LightPinkNormal;
		}
		
		return CssColor.make(0,0,0);
	}
	
	public static CssColor getMouseOverColor(Protocol protocol) {
		switch (protocol) {
		case HTTP: 
			return Colors.LightRedMouseOver;
		case DNS:
			return Colors.LightBlueMouseOver;
		case MIXED:
			return Colors.LightGreenMouseOver;
		case OTHER:
			return Colors.LightGreyMouseOver;
		case LOCALHOST:
			return Colors.LightCyanMouseOver;
		case TCP:
			return Colors.LightYellowMouseOver;
		case UDP:
			return Colors.LightPinkMouseOver;
		}
		
		return CssColor.make(0,0,0);
	}
	
	public static CssColor getClickColor(Protocol protocol) {
		switch (protocol) {
		case HTTP: 
			return Colors.LightRedClick;
		case DNS:
			return Colors.LightBlueClick;
		case MIXED:
			return Colors.LightGreenClick;
		case OTHER:
			return Colors.LightGreyClick;
		case LOCALHOST:
			return Colors.LightCyanClick;
		case TCP:
			return Colors.LightYellowClick;
		case UDP:
			return Colors.LightPinkClick;
		}
		
		return CssColor.make(0,0,0);
	}
}
