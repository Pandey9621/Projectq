package com.ideabytes.commonService;

import org.springframework.stereotype.Service;
import com.ideabytes.constants.Constants;

@Service
public class GetLang {
	/**getLanguage This function will check the language if its null or empty. If its null the English will assign to lang
	 * 
	 * @param lang Its a String formate.
	 * @return lang It will return String formate.
	 * */
	
	public String getLanguage(String lang) {
		if (lang == null || lang.isEmpty()) {
			lang = Constants.ENGLISH;
		}
		return lang;
	}
	
}
