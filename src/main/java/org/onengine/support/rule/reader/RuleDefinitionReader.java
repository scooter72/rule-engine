package org.onengine.support.rule.reader;

import org.onengine.support.rule.reader.RuleDefinition;

import java.io.Reader;
import java.util.List;

/**
 * Strategy interface for RuleDefinition readers.
 */
@FunctionalInterface
public interface RuleDefinitionReader {

    /**
     * Read a list of rule definitions from a rule descriptor.
     *
     * <strong> The descriptor is expected to contain a collection of rule definitions
     * even for a single rule.</strong>
     *
     * @param reader of the rules descriptor
     * @return a list of rule definitions
     * @throws Exception if a problem occurs during rule definition reading
     */
    List<RuleDefinition> read(Reader reader) throws Exception;

}
