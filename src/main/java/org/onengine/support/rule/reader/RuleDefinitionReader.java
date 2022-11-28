package org.onengine.support.rule.reader;

import java.io.Reader;
import java.util.List;

/**
 * Interface for RuleDefinition readers.
 */
@FunctionalInterface
public interface RuleDefinitionReader {

    /**
     * Read a list of rule definitions from a rule descriptor.
     * The descriptor is expected to contain a collection of rule definitions
     * even for a single rule.
     *
     * @param reader of the rules descriptor
     * @return a list of rule definitions
     * @throws Exception if a problem occurs during rule definition reading
     */
    List<RuleDefinition> read(Reader reader) throws Exception;

}
