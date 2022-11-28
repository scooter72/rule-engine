package org.onengine;

import org.mvel2.ParserContext;
import org.onengine.api.Facts;
import org.onengine.api.Rule;
import org.onengine.mvel.MVELRule;
import org.onengine.reports.RuleReporter;
import org.onengine.support.EmailSender;
import org.onengine.support.PatternDetector;
import org.onengine.support.rule.reader.RuleDefinition;
import org.onengine.support.rule.reader.YamlRuleDefinitionReader;

import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.List;


public class Main {

    public static void main(String[] args) throws Exception {
        String url = args.length == 0
                ? "https://www.protecto.ai/wp-content/uploads/2020/08/Book1.xlsx-us-social-security-22-cvs.csv"
                : args[0];
        String data = readURL(url);
        Facts facts = new Facts();
        facts.put("data", data);
        facts.put("ssn_regex", "\\d{3}-?\\d{2}-?\\d{4}");
        facts.put("iban_regex", "\\b[A-Z]{2}[0-9]{2}(?:[ ]?[0-9]{4}){4}(?!(?:[ ]?[0-9]){3})(?:[ ]?[0-9]{1,2})?\\b");
        facts.put("cc_regex", "\\b4[0-9]{12}(?:[0-9]{3})?\\b");
        facts.put("emailSender", new EmailSender());
        facts.put("patternDetector", new PatternDetector());

        Reader ruleDescriptor = getRuleDescriptor();
        List<RuleDefinition> ruleDefinitions = getRuleDefinitions(ruleDescriptor);
        ParserContext parserContext = new ParserContext();
        HashSet<Rule> rules = new HashSet<>();

        for (RuleDefinition ruleDefinition : ruleDefinitions) {
            rules.add(MVELRule.createSimpleRule(ruleDefinition, parserContext));
        }

        RulesEngine rulesEngine = new RulesEngine();
        RuleReporter reporter = new RuleReporter();
        reporter.writeReport(System.out, rulesEngine.fire(rules, facts));

    }

    private static List<RuleDefinition> getRuleDefinitions(Reader ruleDescriptor) {
        List<RuleDefinition> ruleDefinitions;
        try {
            ruleDefinitions = (new YamlRuleDefinitionReader()).read(ruleDescriptor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (ruleDefinitions.isEmpty()) {
            throw new IllegalArgumentException("rule descriptor is empty");
        }
        return ruleDefinitions;
    }

    private static Reader getRuleDescriptor() throws FileNotFoundException {
        Reader ruleDescriptor;
        InputStream in = Main.class.getResourceAsStream("/rules.yml");

        if (in == null) {
            throw new FileNotFoundException("rules.yml");
        }

        try {
            ruleDescriptor = new BufferedReader(new InputStreamReader(in));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ruleDescriptor;
    }

    private static String readURL(String spec) throws Exception {
        StringBuilder buffer = new StringBuilder();
        URL url = new URL(spec);
        BufferedReader read = new BufferedReader(
                new InputStreamReader(url.openStream()));
        String i;
        while ((i = read.readLine()) != null)
            buffer.append(i);
        read.close();
        return buffer.toString();
    }
}