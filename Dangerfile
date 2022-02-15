github.dismiss_out_of_range_messages
 
# for PR
if github.pr_title.include?('[WIP]') || github.pr_labels.include?('WIP')
  warn('PR is classed as Work in Progress')
end

# Warn when there is a big PR
warn('a large PR') if git.lines_of_code > 300

Dir.glob("**//build/reports/detekt-checkstyle.xml").each { |report|
  checkstyle_format.base_path = Dir.pwd
  checkstyle_format.report report.to_s
}
ktlint.lint
# ktlint
Dir.glob("**//build/reports/ktlint-results.xml").each { |report|
    checkstyle_format.base_path = Dir.pwd
    checkstyle_format.report report.to_s
}

# Android Lint
Dir.glob("**//build/reports/lint-results*.xml").each { |report|
    android_lint.skip_gradle_task = true
    android_lint.report_file = report.to_s
    android_lint.filtering = true
    android_lint.lint(inline_mode: true)
}