package com.epps.framework.notification.mail;

import java.util.ArrayList;

public class PIDsWithoutSubScreens {
	
	public static final ArrayList<String> PIDs_WITHOUT_SUB_SCREENS = new ArrayList<String>(){
		{
			add("NA");
		}
	};
		
		/**
		 * @return the isPIDWithoutSubScreen
		 */
		public static Boolean isPIDWithoutSubScreen(String pid) {
			if(PIDs_WITHOUT_SUB_SCREENS.contains(pid)){
				return true;
			}else{
				return false;
			}
		}

}
