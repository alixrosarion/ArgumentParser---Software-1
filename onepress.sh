#!/bin/sh
gradle clean build jacocoTestReport
explorer "file:///%~dp0build/reports/tests/index.html"
explorer "file:///%~dp0build/reports/jacoco/test/html/index.html"
"Running acceptance tests..."
explorer "file:///%~dp0acceptance/report.html"