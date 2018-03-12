package com.github.isabsent.filepicker.comparator;

import java.io.File;
import java.text.Collator;

public class FileNameComparator extends FileComparator {
    public FileNameComparator(boolean asc) {
        super(asc);
    }

    @Override
    protected int comp(File f1, File f2) {
//        return f1.getName().toLowerCase().compareTo(f2.getName().toLowerCase());
        return compareNatural(f1.getName().toLowerCase(), f2.getName().toLowerCase());
    }

//    http://stackoverflow.com/questions/104599/sort-on-a-string-that-may-contain-a-number
//    http://www.davekoelle.com/files/AlphanumComparator.java
//    http://www.eekboom.com/java/compareNatural/src/com/eekboom/utils/Strings.java
    public static int compareNatural(String s, String t) {
        return compareNatural(s, t, false, Collator.getInstance());
    }

    /**
     * @param s             first string
     * @param t             second string
     * @param caseSensitive treat characters differing in case only as equal - will be ignored if a collator is given
     * @param collator      used to compare subwords that aren't numbers - if null, characters will be compared
     *                      individually based on their Unicode value
     * @return zero iff <code>s</code> and <code>t</code> are equal,
     *         a value less than zero iff <code>s</code> lexicographically precedes <code>t</code>
     *         and a value larger than zero iff <code>s</code> lexicographically follows <code>t</code>
     */
    private static int compareNatural(String s, String t, boolean caseSensitive, Collator collator) {
        int sIndex = 0;
        int tIndex = 0;

        int sLength = s.length();
        int tLength = t.length();

        while(true) {
            // both character indices are after a subword (or at zero)

            // Check if one string is at end
            if(sIndex == sLength && tIndex == tLength) {
                return 0;
            }
            if(sIndex == sLength) {
                return -1;
            }
            if(tIndex == tLength) {
                return 1;
            }

            // Compare sub word
            char sChar = s.charAt(sIndex);
            char tChar = t.charAt(tIndex);

            boolean sCharIsDigit = Character.isDigit(sChar);
            boolean tCharIsDigit = Character.isDigit(tChar);

            if(sCharIsDigit && tCharIsDigit) {
                // Compare numbers

                // skip leading 0s
                int sLeadingZeroCount = 0;
                while(sChar == '0') {
                    ++sLeadingZeroCount;
                    ++sIndex;
                    if(sIndex == sLength) {
                        break;
                    }
                    sChar = s.charAt(sIndex);
                }
                int tLeadingZeroCount = 0;
                while(tChar == '0') {
                    ++tLeadingZeroCount;
                    ++tIndex;
                    if(tIndex == tLength) {
                        break;
                    }
                    tChar = t.charAt(tIndex);
                }
                boolean sAllZero = sIndex == sLength || !Character.isDigit(sChar);
                boolean tAllZero = tIndex == tLength || !Character.isDigit(tChar);
                if(sAllZero && tAllZero) {
                    continue;
                }
                if(sAllZero && !tAllZero) {
                    return -1;
                }
                if(tAllZero) {
                    return 1;
                }

                int diff = 0;
                do {
                    if(diff == 0) {
                        diff = sChar - tChar;
                    }
                    ++sIndex;
                    ++tIndex;
                    if(sIndex == sLength && tIndex == tLength) {
                        return diff != 0 ? diff : sLeadingZeroCount - tLeadingZeroCount;
                    }
                    if(sIndex == sLength) {
                        if(diff == 0) {
                            return -1;
                        }
                        return Character.isDigit(t.charAt(tIndex)) ? -1 : diff;
                    }
                    if(tIndex == tLength) {
                        if(diff == 0) {
                            return 1;
                        }
                        return Character.isDigit(s.charAt(sIndex)) ? 1 : diff;
                    }
                    sChar = s.charAt(sIndex);
                    tChar = t.charAt(tIndex);
                    sCharIsDigit = Character.isDigit(sChar);
                    tCharIsDigit = Character.isDigit(tChar);
                    if(!sCharIsDigit && !tCharIsDigit) {
                        // both number sub words have the same length
                        if(diff != 0) {
                            return diff;
                        }
                        break;
                    }
                    if(!sCharIsDigit) {
                        return -1;
                    }
                    if(!tCharIsDigit) {
                        return 1;
                    }
                } while(true);
            }
            else {
                // Compare words
                if(collator != null) {
                    // To use the collator the whole subwords have to be compared - character-by-character comparision
                    // is not possible. So find the two subwords first
                    int aw = sIndex;
                    int bw = tIndex;
                    do {
                        ++sIndex;
                    } while(sIndex < sLength && !Character.isDigit(s.charAt(sIndex)));
                    do {
                        ++tIndex;
                    } while(tIndex < tLength && !Character.isDigit(t.charAt(tIndex)));

                    String as = s.substring(aw, sIndex);
                    String bs = t.substring(bw, tIndex);
                    int subwordResult = collator.compare(as, bs);
                    if(subwordResult != 0) {
                        return subwordResult;
                    }
                }
                else {
                    // No collator specified. All characters should be ascii only. Compare character-by-character.
                    do {
                        if(sChar != tChar) {
                            if(caseSensitive) {
                                return sChar - tChar;
                            }
                            sChar = Character.toUpperCase(sChar);
                            tChar = Character.toUpperCase(tChar);
                            if(sChar != tChar) {
                                sChar = Character.toLowerCase(sChar);
                                tChar = Character.toLowerCase(tChar);
                                if(sChar != tChar) {
                                    return sChar - tChar;
                                }
                            }
                        }
                        ++sIndex;
                        ++tIndex;
                        if(sIndex == sLength && tIndex == tLength) {
                            return 0;
                        }
                        if(sIndex == sLength) {
                            return -1;
                        }
                        if(tIndex == tLength) {
                            return 1;
                        }
                        sChar = s.charAt(sIndex);
                        tChar = t.charAt(tIndex);
                        sCharIsDigit = Character.isDigit(sChar);
                        tCharIsDigit = Character.isDigit(tChar);
                    } while(!sCharIsDigit && !tCharIsDigit);
                }
            }
        }
    }
}