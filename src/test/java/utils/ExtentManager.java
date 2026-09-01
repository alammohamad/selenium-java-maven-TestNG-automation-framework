package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExtentManager {

    private static ExtentReports extent;
    private static final List<ExecutionRecord> records =
            Collections.synchronizedList(new ArrayList<>());

    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    public static synchronized void recordStart(
            String testName, String browser, String environment, String userId) {
        records.add(new ExecutionRecord(
                testName,
                browser,
                environment,
                userId,
                LocalDateTime.now()));
    }

    public static synchronized void recordEnd(
            String testName, String browser, String status) {
        for (int i = records.size() - 1; i >= 0; i--) {
            ExecutionRecord record = records.get(i);
            if (record.testName.equals(testName)
                    && record.browser.equalsIgnoreCase(browser)
                    && record.endTime == null) {
                record.endTime = LocalDateTime.now();
                record.status = status;
                break;
            }
        }
    }

    public static synchronized ExtentReports getInstance() {

        if (extent == null) {

            String reportPath =
                    System.getProperty("user.dir")
                            + File.separator
                            + "target"
                            + File.separator
                            + "extent-report"
                            + File.separator
                            + "index.html";

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(reportPath);

            sparkReporter.config().setReportName(
                    "Hybrid QA Automation Framework"
            );

            sparkReporter.config().setDocumentTitle(
                    "Test Execution Report"
            );

            extent = new ExtentReports();

            extent.attachReporter(sparkReporter);

            extent.setSystemInfo(
                    "Framework",
                    "Selenium + TestNG"
            );

            extent.setSystemInfo(
                    "Project",
                    "Hybrid QA Automation Framework"
            );

            extent.setSystemInfo(
                    "Environment",
                    System.getProperty("env", "QA").toUpperCase()
            );
        }

        return extent;
    }

    public static synchronized void flush() {

        if (extent != null) {
            extent.flush();
        }

        generateManagerSummary();
    }

    private static void generateManagerSummary() {

        String reportPath = System.getProperty("user.dir")
                + File.separator + "target"
                + File.separator + "extent-report"
                + File.separator + "manager-summary.html";

        File reportFile = new File(reportPath);
        reportFile.getParentFile().mkdirs();

        String environment = System.getProperty("env", "QA").toUpperCase();
        int total = records.size();
        int passed = 0;
        int failed = 0;
        int skipped = 0;

        StringBuilder rows = new StringBuilder();

        synchronized (records) {
            for (ExecutionRecord record : records) {
                if ("PASS".equals(record.status)) passed++;
                else if ("FAIL".equals(record.status)) failed++;
                else if ("SKIPPED".equals(record.status)) skipped++;

                String end = record.endTime == null ? "-" : record.endTime.format(DATE_TIME_FORMAT);
                String duration = record.endTime == null ? "-"
                        : formatDuration(Duration.between(record.startTime, record.endTime));

                rows.append("<tr>")
                    .append("<td>").append(environment).append("</td>")
                    .append("<td>").append(record.startTime.toLocalDate()).append("</td>")
                    .append("<td>").append(record.startTime.format(DATE_TIME_FORMAT)).append("</td>")
                    .append("<td>").append(end).append("</td>")
                    .append("<td>").append(record.testName).append("</td>")
                    .append("<td>").append(record.browser.toUpperCase()).append("</td>")
                    .append("<td>").append(duration).append("</td>")
                    .append("<td class=\"").append(record.status.toLowerCase()).append("\">")
                    .append(record.status).append("</td>")
                    .append("</tr>");
            }
        }

        String html = "<!DOCTYPE html><html><head><meta charset='UTF-8'>"
                + "<title>Hybrid QA Execution Summary</title>"
                + "<style>body{font-family:Arial,sans-serif;margin:30px}"
                + "h1{margin-bottom:5px}.summary{display:flex;gap:20px;margin:20px 0}"
                + ".card{padding:12px 20px;border:1px solid #ddd;border-radius:6px}"
                + "table{border-collapse:collapse;width:100%;font-size:13px}"
                + "th,td{border:1px solid #ddd;padding:9px;text-align:left}"
                + "th{background:#f2f2f2}.pass{font-weight:bold}.fail{font-weight:bold}"
                + ".skipped{font-weight:bold}</style></head><body>"
                + "<h1>Hybrid QA Automation - Execution Summary</h1>"
                + "<div>Environment: <b>" + environment + "</b></div>"
                + "<div class='summary'><div class='card'>Total: <b>" + total + "</b></div>"
                + "<div class='card'>Passed: <b>" + passed + "</b></div>"
                + "<div class='card'>Failed: <b>" + failed + "</b></div>"
                + "<div class='card'>Skipped: <b>" + skipped + "</b></div></div>"
                + "<table><thead><tr><th>Environment</th><th>Date</th>"
                + "<th>Start Timestamp</th><th>End Timestamp</th><th>Test Case</th>"
                + "<th>Browser</th><th>Duration</th><th>Test Status</th></tr></thead>"
                + "<tbody>" + rows + "</tbody></table></body></html>";

        try (FileWriter writer = new FileWriter(reportFile)) {
            writer.write(html);
        } catch (IOException e) {
            System.err.println("Unable to generate manager summary: " + e.getMessage());
        }
    }

    private static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        return seconds + " sec";
    }

    private static class ExecutionRecord {
        private final String testName;
        private final String browser;
        private final String environment;
        private final String userId;
        private final LocalDateTime startTime;
        private LocalDateTime endTime;
        private String status = "RUNNING";

        private ExecutionRecord(String testName, String browser,
                                String environment, String userId,
                                LocalDateTime startTime) {
            this.testName = testName;
            this.browser = browser;
            this.environment = environment;
            this.userId = userId;
            this.startTime = startTime;
        }
    }
}

