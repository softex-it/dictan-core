package info.softex.dictionary.core.utils;

import java.util.HashMap;

/**
 * 
 * @since version 4.8, 05/01/2015 
 * 
 * @author Dmitry Viktorov
 *
 */
public class ArticleTextFormatter {
	
	public static String removeXmlHtml(String input, int maxLength) {
		return new XmlHtmlRemover(input, maxLength).removeXmlHtml();
	}
	
	public static String removeXmlHtml(String input) {
		return removeXmlHtml(input, -1);
	}

    /**
     * Remove all xml tags as well as html containers script, style, and comments.
     * It also replaces all html-specific characters with their string values.
     */
	protected static class XmlHtmlRemover {

        protected static final int MIN_ESCAPE = 2;
        protected static final int MAX_ESCAPE = 6;
		
		protected final static String STYLE = "style";
		protected final static String SCRIPT = "script";
		protected final static String COMMENT_OPEN = "!--";
		protected final static String COMMENT_CLOSE = "--";

        protected final static String[][] ESCAPES = {
            {"\"",     "quot"}, // " - double-quote
            {"&",      "amp"}, // & - ampersand
            {"<",      "lt"}, // < - less-than
            {">",      "gt"}, // > - greater-than

            // Mapping to escape ISO-8859-1 characters to their named HTML 3.x equivalents.
            {"\u00A0", "nbsp"}, // non-breaking space
            {"\u00A1", "iexcl"}, // inverted exclamation mark
            {"\u00A2", "cent"}, // cent sign
            {"\u00A3", "pound"}, // pound sign
            {"\u00A4", "curren"}, // currency sign
            {"\u00A5", "yen"}, // yen sign = yuan sign
            {"\u00A6", "brvbar"}, // broken bar = broken vertical bar
            {"\u00A7", "sect"}, // section sign
            {"\u00A8", "uml"}, // diaeresis = spacing diaeresis
            {"\u00A9", "copy"}, // © - copyright sign
            {"\u00AA", "ordf"}, // feminine ordinal indicator
            {"\u00AB", "laquo"}, // left-pointing double angle quotation mark = left pointing guillemet
            {"\u00AC", "not"}, // not sign
            {"\u00AD", "shy"}, // soft hyphen = discretionary hyphen
            {"\u00AE", "reg"}, // ® - registered trademark sign
            {"\u00AF", "macr"}, // macron = spacing macron = overline = APL overbar
            {"\u00B0", "deg"}, // degree sign
            {"\u00B1", "plusmn"}, // plus-minus sign = plus-or-minus sign
            {"\u00B2", "sup2"}, // superscript two = superscript digit two = squared
            {"\u00B3", "sup3"}, // superscript three = superscript digit three = cubed
            {"\u00B4", "acute"}, // acute accent = spacing acute
            {"\u00B5", "micro"}, // micro sign
            {"\u00B6", "para"}, // pilcrow sign = paragraph sign
            {"\u00B7", "middot"}, // middle dot = Georgian comma = Greek middle dot
            {"\u00B8", "cedil"}, // cedilla = spacing cedilla
            {"\u00B9", "sup1"}, // superscript one = superscript digit one
            {"\u00BA", "ordm"}, // masculine ordinal indicator
            {"\u00BB", "raquo"}, // right-pointing double angle quotation mark = right pointing guillemet
            {"\u00BC", "frac14"}, // vulgar fraction one quarter = fraction one quarter
            {"\u00BD", "frac12"}, // vulgar fraction one half = fraction one half
            {"\u00BE", "frac34"}, // vulgar fraction three quarters = fraction three quarters
            {"\u00BF", "iquest"}, // inverted question mark = turned question mark
            {"\u00C0", "Agrave"}, // А - uppercase A, grave accent
            {"\u00C1", "Aacute"}, // Б - uppercase A, acute accent
            {"\u00C2", "Acirc"}, // В - uppercase A, circumflex accent
            {"\u00C3", "Atilde"}, // Г - uppercase A, tilde
            {"\u00C4", "Auml"}, // Д - uppercase A, umlaut
            {"\u00C5", "Aring"}, // Е - uppercase A, ring
            {"\u00C6", "AElig"}, // Ж - uppercase AE
            {"\u00C7", "Ccedil"}, // З - uppercase C, cedilla
            {"\u00C8", "Egrave"}, // И - uppercase E, grave accent
            {"\u00C9", "Eacute"}, // Й - uppercase E, acute accent
            {"\u00CA", "Ecirc"}, // К - uppercase E, circumflex accent
            {"\u00CB", "Euml"}, // Л - uppercase E, umlaut
            {"\u00CC", "Igrave"}, // М - uppercase I, grave accent
            {"\u00CD", "Iacute"}, // Н - uppercase I, acute accent
            {"\u00CE", "Icirc"}, // О - uppercase I, circumflex accent
            {"\u00CF", "Iuml"}, // П - uppercase I, umlaut
            {"\u00D0", "ETH"}, // Р - uppercase Eth, Icelandic
            {"\u00D1", "Ntilde"}, // С - uppercase N, tilde
            {"\u00D2", "Ograve"}, // Т - uppercase O, grave accent
            {"\u00D3", "Oacute"}, // У - uppercase O, acute accent
            {"\u00D4", "Ocirc"}, // Ф - uppercase O, circumflex accent
            {"\u00D5", "Otilde"}, // Х - uppercase O, tilde
            {"\u00D6", "Ouml"}, // Ц - uppercase O, umlaut
            {"\u00D7", "times"}, // multiplication sign
            {"\u00D8", "Oslash"}, // Ш - uppercase O, slash
            {"\u00D9", "Ugrave"}, // Щ - uppercase U, grave accent
            {"\u00DA", "Uacute"}, // Ъ - uppercase U, acute accent
            {"\u00DB", "Ucirc"}, // Ы - uppercase U, circumflex accent
            {"\u00DC", "Uuml"}, // Ь - uppercase U, umlaut
            {"\u00DD", "Yacute"}, // Э - uppercase Y, acute accent
            {"\u00DE", "THORN"}, // Ю - uppercase THORN, Icelandic
            {"\u00DF", "szlig"}, // Я - lowercase sharps, German
            {"\u00E0", "agrave"}, // а - lowercase a, grave accent
            {"\u00E1", "aacute"}, // б - lowercase a, acute accent
            {"\u00E2", "acirc"}, // в - lowercase a, circumflex accent
            {"\u00E3", "atilde"}, // г - lowercase a, tilde
            {"\u00E4", "auml"}, // д - lowercase a, umlaut
            {"\u00E5", "aring"}, // е - lowercase a, ring
            {"\u00E6", "aelig"}, // ж - lowercase ae
            {"\u00E7", "ccedil"}, // з - lowercase c, cedilla
            {"\u00E8", "egrave"}, // и - lowercase e, grave accent
            {"\u00E9", "eacute"}, // й - lowercase e, acute accent
            {"\u00EA", "ecirc"}, // к - lowercase e, circumflex accent
            {"\u00EB", "euml"}, // л - lowercase e, umlaut
            {"\u00EC", "igrave"}, // м - lowercase i, grave accent
            {"\u00ED", "iacute"}, // н - lowercase i, acute accent
            {"\u00EE", "icirc"}, // о - lowercase i, circumflex accent
            {"\u00EF", "iuml"}, // п - lowercase i, umlaut
            {"\u00F0", "eth"}, // р - lowercase eth, Icelandic
            {"\u00F1", "ntilde"}, // с - lowercase n, tilde
            {"\u00F2", "ograve"}, // т - lowercase o, grave accent
            {"\u00F3", "oacute"}, // у - lowercase o, acute accent
            {"\u00F4", "ocirc"}, // ф - lowercase o, circumflex accent
            {"\u00F5", "otilde"}, // х - lowercase o, tilde
            {"\u00F6", "ouml"}, // ц - lowercase o, umlaut
            {"\u00F7", "divide"}, // division sign
            {"\u00F8", "oslash"}, // ш - lowercase o, slash
            {"\u00F9", "ugrave"}, // щ - lowercase u, grave accent
            {"\u00FA", "uacute"}, // ъ - lowercase u, acute accent
            {"\u00FB", "ucirc"}, // ы - lowercase u, circumflex accent
            {"\u00FC", "uuml"}, // ь - lowercase u, umlaut
            {"\u00FD", "yacute"}, // э - lowercase y, acute accent
            {"\u00FE", "thorn"}, // ю - lowercase thorn, Icelandic
            {"\u00FF", "yuml"}, // я - lowercase y, umlaut
        };

        protected final static HashMap<String, Character> LOOKUP_MAP = new HashMap<>();
        static {
            for (final CharSequence[] seq : ESCAPES) {
                LOOKUP_MAP.put(seq[1].toString(), seq[0].charAt(0));
            }
        }
		
		protected final String input;
		protected final int maxLength;
		protected boolean isResultOutput = true;
		protected boolean isLastTagOutput = false;
		
		public XmlHtmlRemover(String inInput, int inMaxLength) {
			this.input = inInput;
			this.maxLength = inMaxLength;
		}
		
		public String removeXmlHtml() {

			// Don't process blank input
			if (StringUtils.isBlank(input)) {
				return input;
			}

            boolean specialCharAppended;
			
			StringBuffer result = new StringBuffer();
			StringBuffer lastTag = new StringBuffer();
			
			char[] chars = input.toCharArray();
            for (int i = 0; i < chars.length; i++) {
            	
                if (maxLength > 0 && result.length() >= maxLength) {
                	break;
                }

                specialCharAppended = false;

                char curChar = chars[i];
                if (curChar == '<') {
                    isResultOutput = false;
                    lastTag.setLength(0);
                    isLastTagOutput = true;
                } else if (curChar == '>') {
                	String lastTagString = lastTag.toString();
                	if (STYLE.equalsIgnoreCase(lastTagString) ||
                			SCRIPT.equalsIgnoreCase(lastTagString) ||
                				COMMENT_OPEN.equalsIgnoreCase(lastTagString)) {
                		i = readFullTag(lastTagString, chars, i);
                	}

                	isResultOutput = true;
                	isLastTagOutput = false;
                	lastTag.setLength(0);
                } else if (curChar == '&') {
                    char[] htmlCharWithEnding = readHtmlSpecialCharacter(chars, i);
                    if (htmlCharWithEnding != null) {
                        char[] htmlChar = copyOfRange(htmlCharWithEnding, 0, htmlCharWithEnding.length - 1);
                        String resolvedChars = resolveHtmlSpecialCharacter(htmlChar);
                        if (resolvedChars != null) {
                            result.append(resolvedChars);
                            i += htmlChar.length;
                            if (htmlCharWithEnding[htmlCharWithEnding.length - 1] != ';') {
                            	i--;
                            }
                            specialCharAppended = true;
                        }
                    }

                } else if (Character.isWhitespace(curChar)) {
                	isLastTagOutput = false;
                }

                // Append only if special char was not appended
                if (!specialCharAppended) {
                    if (isResultOutput && curChar != '>') {
                        result.append(curChar);
                    } else if (isLastTagOutput && curChar != '<') {
                        lastTag.append(curChar);
                    }
                }
                
            }

            // Length is never expected to be more than max
            // This check is only for safety
            if (maxLength > 0) {
            	result.setLength(maxLength);
            }
            
            return result.toString();
		}
		
		protected int readFullTag(String openTag, char[] chars, int pointer) {
			
			String closeTag = openTag;
			if (openTag.equals(COMMENT_OPEN)) {
				closeTag = COMMENT_CLOSE;
			} else {
				closeTag = "/" + openTag;
			}
			
			int i = pointer;
			//System.out.println("in " + i);
			for (; i < chars.length; i++) {
				char curChar = chars[i];
				if (curChar == '>') {
					char[] realCloseTag = new char[closeTag.length()];
					for (int j = 0; j < realCloseTag.length; j++) {
						realCloseTag[realCloseTag.length - j - 1] = chars[i - j - 1];
					}
					String realCloseTagString = new String(realCloseTag);
					//System.out.println("AN " + realCloseTagString + " " + closeTag);
					if (closeTag.equalsIgnoreCase(realCloseTagString)) {
						break;
					}
				}
			}
			
			//System.out.println("out "+i);
			return i;
		}

        protected static char[] readHtmlSpecialCharacter(char[] chars, int pointer) {

            // found '&', look for ';' or ' '
            int j = pointer;
            while (j < chars.length && j < pointer + MAX_ESCAPE + 1 && 
            		chars[j] != ';' && !Character.isWhitespace(chars[j])) {
                j++;
            }

            // Special character is not found, return
            if (j == chars.length || j < pointer + MIN_ESCAPE || j == pointer + MAX_ESCAPE + 1) {
                return null;
            }
            char[] result = copyOfRange(chars, pointer, j + 1);

            return result;
        }

        protected static String resolveHtmlSpecialCharacter(char[] htmlChars) {

            StringBuffer result = null;

            int pointer = 1;

            // found escape
            if (htmlChars[pointer] == '#') {
                // numeric escape
                int k = pointer + 1;
                int radix = 10;

                final char firstChar = htmlChars[k];
                if (firstChar == 'x' || firstChar == 'X') {
                    k++;
                    radix = 16;
                }

                try {

                	String entityString = new String(htmlChars, k, htmlChars.length - k);
                    int entityValue = Integer.parseInt(entityString, radix);

                    result = new StringBuffer();

                    if (entityValue > 0xFFFF) {
                        final char[] chrs = Character.toChars(entityValue);
                        result.append(chrs[0]);
                        result.append(chrs[1]);
                    } else {
                        result.append((char)entityValue);
                    }

                } catch (NumberFormatException ex) {
                    return null;
                }
            } else {
                // named escape
                Character value = LOOKUP_MAP.get(new String(htmlChars, 1, htmlChars.length - 1));
                if (value == null) {
                    return null;
                }

                result = new StringBuffer();
                result.append(value);
            }

            return result != null ? result.toString() : null;

        }

	}

    /**
     * Temporary method. Android API below 9 doesn't support System.copyOfRange.
     */
    protected static char[] copyOfRange(char[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        int copyLength = Math.min(resultLength, originalLength - start);
        char[] result = new char[resultLength];
        System.arraycopy(original, start, result, 0, copyLength);
        return result;
    }

}
