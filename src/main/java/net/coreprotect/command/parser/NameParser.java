package net.coreprotect.command.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NameParser {
    /**
     * Parse restricted item and entity names from command arguments
     *
     * @param inputArguments
     *            The command arguments
     * @return A list of restricted item and entity names
     */
    public static List<String> parseRestricted(String[] inputArguments) {
        String[] argumentArray = inputArguments.clone();
        List<String> restricted = new ArrayList<>();
        int count = 0;
        int next = 0;
        for (String argument : argumentArray) {
            if (count > 0) {
                argument = argument.trim().toLowerCase(Locale.ROOT);
                argument = argument.replaceAll("\\\\", "");
                argument = argument.replaceAll("'", "");

                if (argument.equals("in:") || argument.equals("include-name:") || argument.equals("name:") || argument.equals("names:")) {
                    next = 4;
                }
                else if (next == 4 || argument.startsWith("in:") || argument.startsWith("include-name:") || argument.startsWith("name:") || argument.startsWith("names:")) {
                    argument = argument.replaceAll("include-name:", "");
                    argument = argument.replaceAll("in:", "");
                    argument = argument.replaceAll("names:", "");
                    argument = argument.replaceAll("name:", "");
                    if (argument.contains(",")) {
                        String[] i2 = argument.split(",");
                        for (String i3 : i2) {
                            restricted.add(i3);
                        }
                        if (argument.endsWith(",")) {
                            next = 4;
                        }
                        else {
                            next = 0;
                        }
                    }
                    else {
                        restricted.add(argument);
                        next = 0;
                    }
                }
                else {
                    next = 0;
                }
            }
            count++;
        }
        return restricted;
    }
    
}
