package com.jsonde.util;
/**
 * 
 * @author admin
 *
 */
public class StringUtils {

	/**
	 * 
	 * @param wildcard
	 * @return
	 */
    public static String wildcardToRegex(String wildcard) {
        StringBuffer s = new StringBuffer(wildcard.length() + 6);
        s.append('^');
        
        int i = 0;
        boolean b1 = i < wildcard.length();
        for (i = 0; b1; i++) {
            char c = wildcard.charAt(i);
            switch (c) {
                case '*':
                    s.append(".*");
                    break;
                case '?':
                    s.append(".");
                    break;
                // escape special regexp-characters
                case '(':
                case ')':        
                    s.append("\\");
                    s.append(c);
                    break;
                default:
                    s.append(c);
                    break;
            }
            b1 = i < wildcard.length();
        }
        s.append('$');
        return (s.toString());
    }

}
