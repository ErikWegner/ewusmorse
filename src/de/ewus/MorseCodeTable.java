package de.ewus;

/**
 *
 * @author Erik Wegner
 */
public class MorseCodeTable {
    public static String getMorse(char character) {
        String r = "";
        int i = character;
        switch (i) {
            // 0 .. 9
            case 48 : r = "− − − − −"; break;
            case 49 : r = ". − − − −"; break;
            case 50 : r = ". . − − −"; break;
            case 51 : r = ". . . − −"; break;
            case 52 : r = ". . . . −"; break;
            case 53 : r = ". . . . ."; break;
            case 54 : r = "- . . . ."; break;
            case 55 : r = "− − . . ."; break;
            case 56 : r = "− − − . ."; break;
            case 57 : r = "− − − − ."; break;
                
            // A .. Z
            case 65 : r = "· −"; break;
            case 66 : r = "− · · ·"; break;
            case 67 : r = "− · − ·"; break;
            case 68 : r = "− · ·"; break;
            case 69 : r = "·"; break;
            case 70 : r = "· · − ·"; break;
            case 71 : r = "− − ·"; break;
            case 72 : r = "· · · ·"; break;
            case 73 : r = "· ·"; break;
            case 74 : r = "· − − −"; break;
            case 75 : r = "− · −"; break;
            case 76 : r = "· − · ·"; break;
            case 77 : r = "− −"; break;
            case 78 : r = "− ·"; break;
            case 79 : r = "− − −"; break;
            case 80 : r = "· − − ·"; break;
            case 81 : r = "− − · −"; break;
            case 82 : r = "· − ·"; break;
            case 83 : r = "· · ·"; break;
            case 84 : r = "−"; break;
            case 85 : r = "· · −"; break;
            case 86 : r = "· · · −"; break;
            case 87 : r = "· − −"; break;
            case 88 : r = "− · · −"; break;
            case 89 : r = "− · − −"; break;
            case 90 : r = "− − · ·"; break;
        }
        return r;
    }
}
