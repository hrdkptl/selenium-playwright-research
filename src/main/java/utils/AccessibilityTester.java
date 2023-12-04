package utils;

import com.deque.axe.AXE;
import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import com.deque.html.axecore.results.Check;
import com.deque.html.axecore.results.CheckedNode;
import com.deque.html.axecore.results.Rule;
import com.microsoft.playwright.Page;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class AccessibilityTester {

	private static final Boolean ACCESSIBILITY_TESTING = Boolean.TRUE;
	// wcag21a not available in playwright and selenium
	// wcag2a not available in selenium
    private static final String[] PLAYWRIGHT_ACCESSIBILITY_STANDARDS = {"wcag2a", "wcag2aa", "wcag21aa"};
    private static final String SELENIUM_ACCESSIBILITY_STANDARDS = "{runOnly: ['wcag2a', 'wcag2aa', 'wcag21aa']}";
    
	
	// For Playwright
    public static void checkAccessibility(Page page, String testClassName, String testMethodName) {
        if(ACCESSIBILITY_TESTING) {
        	System.out.println("********** Accessibility Testing Report : PLAYWRIGHT **********");
            AxeResults results = new AxeBuilder(page)
                    .withTags(Arrays.asList(PLAYWRIGHT_ACCESSIBILITY_STANDARDS))
                    .analyze();

            String htmlReport = generateHTMLReport(results.getViolations(), testClassName, testMethodName);
            String filename = testClassName + testMethodName;
            saveHTMLReport(htmlReport, filename+".html");
        }
    }
    	
    // Method for Selenium
    public static void checkAccessibility(WebDriver driver, String testClassName, String testMethodName) {
        if(ACCESSIBILITY_TESTING) {
        	System.out.println("********** Accessibility Testing Report : SELENIUM **********");
            URL axeScriptUrl = AccessibilityTester.class.getResource("/axe.min.js");
            AXE.Builder builder = new AXE.Builder(driver, axeScriptUrl).options(SELENIUM_ACCESSIBILITY_STANDARDS);
            JSONObject report = builder.analyze();
            JSONArray violationsArray = report.getJSONArray("violations");

            String htmlReport = generateHTMLReport(violationsArray, testClassName, testMethodName);
            String filename = testClassName + testMethodName;
            saveHTMLReport(htmlReport, filename+".html");
        }
    }
    
    private static String generateHTMLReport(List<Rule> rules, String testClassName, String testMethodName) {
        StringBuilder html = new StringBuilder();
        startHTMLReport(html, testClassName, testMethodName);

        for (Rule rule : rules) {
            appendViolation(html, rule);
        }

        endHTMLReport(html);
        return html.toString();
    }
    
    private static String generateHTMLReport(JSONArray violations, String testClassName, String testMethodName) {
        StringBuilder html = new StringBuilder();
        startHTMLReport(html, testClassName, testMethodName);

        for (int i = 0; i < violations.length(); i++) {
            JSONObject violation = violations.getJSONObject(i);
            System.out.println(violation.toString());
            appendViolation(html, violation);
        }

        endHTMLReport(html);
        return html.toString();
    }
    
    private static void startHTMLReport(StringBuilder html, String testClassName, String testMethodName) {
        html.append("<html><head><title>Accessibility Test Report" + testClassName +"."+ testMethodName+ "</title>");
        html.append("<style>")
            .append("body { font-family: Arial, sans-serif; margin: 20px; }")
            .append("h1 { color: #333; }")
            .append(".violation { border: 1px solid #ccc; padding: 10px; margin-bottom: 20px; }")
            .append(".node { background-color: #ffecb3; padding: 10px; margin-top: 10px; }")
            .append("ul { margin: 10px 0; }")
            .append("a { color: #06c; }")
            .append("</style></head><body>");
        html.append("<h1>Accessibility Violations</h1>");
    }

    private static void appendViolation(StringBuilder html, Rule rule) {
    	html.append("<div class='violation'>"); // Start of violation
        html.append("<h2>").append(rule.getDescription()).append("</h2>");
        html.append("<p>Impact: ").append(rule.getImpact()).append("</p>");
        html.append("<p>Help: <a href='").append(rule.getHelpUrl()).append("'>").append(rule.getHelpUrl()).append("</a></p>");
        for (CheckedNode node : rule.getNodes()) {
        	html.append("<div class='node'>");  // Start of node
            html.append("<p>HTML Element: ").append(node.getHtml()).append("</p>");
            html.append("<p>Failure Summary:");//.append(node.getFailureSummary());
            appendChecks(html, "Fix all of the following:", node.getAll());
            appendChecks(html, "Fix any of the following:", node.getAny());
            html.append("</div>"); // End of node
        }
        html.append("</div>"); // End of violation
    }

    private static void appendViolation(StringBuilder html, JSONObject violation) {
    	html.append("<div class='violation'>"); // Start of violation
        html.append("<h2>").append(violation.getString("description")).append("</h2>");
        html.append("<p>Impact: ").append(violation.getString("impact")).append("</p>");
        html.append("<p>Help: <a href='").append(violation.getString("helpUrl")).append("'>").append(violation.getString("helpUrl")).append("</a></p>");
        
        JSONArray nodes = violation.getJSONArray("nodes");
        for (int i = 0; i < nodes.length(); i++) {
            JSONObject node = nodes.getJSONObject(i);
            html.append("<div class='node'>");  // Start of node
            html.append("<p>HTML Element: ").append(node.getString("html")).append("</p>");
            html.append("<p>Failure Summary: ");//.append(node.getString("failureSummary")).append("</p>");

            // Append details for 'any' checks
            appendNodeChecks(html, "Fix any of the following:", node.getJSONArray("any"));

            // Append details for 'all' checks
            appendNodeChecks(html, "Fix all of the following:", node.getJSONArray("all"));

            html.append("</div>"); // End of node
        }

        html.append("</div>"); // End of violation
    }

    private static void appendNodeChecks(StringBuilder html, String title, JSONArray checks) {
        if (checks.length() > 0) {
            html.append("<p>").append(title).append("</p><ul>");
            for (int j = 0; j < checks.length(); j++) {
                JSONObject check = checks.getJSONObject(j);
                html.append("<li>").append(check.getString("message")).append("</li>");
            }
            html.append("</ul>");
        }
    }
    private static void appendChecks(StringBuilder html, String title, List<Check> checks) {
        if (!checks.isEmpty()) {
            html.append("<p>").append(title).append("</p><ul>");
            for (Check check : checks) {
                html.append("<li>").append(check.getMessage()).append("</li>");
            }
            html.append("</ul>");
        }
    }

    private static void endHTMLReport(StringBuilder html) {
        html.append("</body></html>");
    }
    
    private static void saveHTMLReport(String htmlReport, String filePath) {
    	filePath = "C:\\\\Users\\\\hrdkp\\\\Git\\\\selenium-playwright-research\\\\" + filePath;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(htmlReport);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
