package playwright;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CRSScoreCalculator extends Parent {
    
	@BeforeTest
    public void setUp() {
		super.setUp();
        page = browser.newPage();
        page.navigate("https://ircc.canada.ca/english/immigrate/skilled/crs-tool.asp");
    }

    @Test
    public void calculateCRSScore() {
        // Fill out the form with example data
        page.selectOption("#q1", "F"); // Single
        page.selectOption("#q3", "M"); // 29 years of age
        page.selectOption("#q4", "E"); // Bachelor's degree
        page.selectOption("#q4b", "A"); // No Canadian degree

        // Language test results
        page.selectOption("#q5i", "A"); // Test less than two years old
        page.selectOption("#q5i-a", "B"); // IELTS
        page.selectOption("#q5i-b-speaking", "F"); // Speaking score
        page.selectOption("#q5i-b-listening", "F"); // Listening score
        page.selectOption("#q5i-b-reading", "F"); // Reading score
        page.selectOption("#q5i-b-writing", "F"); // Writing score

        // Additional form fields
        page.selectOption("#q5ii", "C"); // Other language results - not applicable
        page.selectOption("#q6i", "A"); // No Canadian work experience
        page.selectOption("#q6ii", "A"); // No foreign work experience
        page.selectOption("#q7", "A"); // No certificate of qualification
        page.selectOption("#q8", "A"); // No valid job offer
        page.selectOption("#q9", "A"); // No provincial nomination
        page.selectOption("#q10i", "A"); // No sibling in Canada

        // Submit the form
        page.click("#submit");

        // Extract the score
        String scoreText = page.innerText("//p[strong[contains(text(), 'Comprehensive Ranking System formula grand total')]]");
        String number = scoreText.replaceAll("[^0-9]", "");

        // Output based on score
        if (Integer.parseInt(number) > 500) {
            System.out.println("PR is possible");
        } else {
            System.out.println("PR not possible");
        }
    }
    
    @AfterMethod
    public void closePage() {
        if (page != null) {
            page.close();
        }
    }
}
