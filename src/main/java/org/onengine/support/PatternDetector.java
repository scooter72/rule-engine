package org.onengine.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternDetector {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatternDetector.class);

    public boolean find(String data, String regex) {
        LOGGER.debug("find pattern regex: {}", regex);
        Pattern p = java.util.regex.Pattern.compile(regex);
        Matcher m = p.matcher(data);
        int count = 0;

        while (m.find()) {
            count++;
            LOGGER.debug("Match number {}", count);
            LOGGER.debug("start(): {}, end(): {} -> {}" , m.start(), m.end(), data.substring(m.start(), m.end()));
        }

        LOGGER.debug("{} matches for regex: {}",count, regex);
        return count > 0;
    }
}
