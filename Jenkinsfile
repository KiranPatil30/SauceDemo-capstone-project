pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Change this to your repo URL or Jenkins configured Git repo
                git branch: 'main', url: 'https://github.com/KiranPatil30/SauceDemo-capstone-project.git'
            }
        }

        stage('Build & Test') {
            steps {
                // Run Maven clean and test
                sh 'mvn clean test'
            }
        }

        stage('Publish Reports') {
            steps {
                // Publish HTML report from cucumber
                publishHTML([
                    reportDir: 'target',
                    reportFiles: 'cucumber-report.html',
                    reportName: 'Cucumber HTML Report',
                    alwaysLinkToLastBuild: true,
                    keepAll: true
                ])

                // Publish JUnit reports (TestNG + Cucumber XML)
                junit 'target/*.xml'
            }
        }
    }

    post {
        always {
            // Archive all cucumber reports and screenshots if you have them
            archiveArtifacts artifacts: 'target/cucumber-report.*', fingerprint: true

            // Optional: Archive TestNG reports if they exist
            archiveArtifacts artifacts: 'target/surefire-reports/*.xml', allowEmptyArchive: true

            // Clean workspace after build (optional)
            cleanWs()
        }
    }
}
