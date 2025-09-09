pipeline {

    agent any
 
    environment {

        BRANCH_NAME = 'main'

        ECLIPSE_WORKSPACE = 'D:\\Capstone Project\\SauceDemo-capstone-project'

        COMMIT_MESSAGE = 'Jenkins: Auto-commit after build'

    }
 
    // Auto-trigger every 5 mins on Git changes

    triggers {

        pollSCM('H H * * *')

    }
 
    stages {

        stage('Checkout from Git') {

            steps {

                git branch: "${env.BRANCH_NAME}",

                    url: 'https://github.com/KiranPatil30/SauceDemo-capstone-project.git'

            }

        }
 
        stage('Copy Files from Eclipse Workspace') {

            steps {

                bat """

                echo Copying files from Eclipse workspace...

                xcopy /E /Y /I "${ECLIPSE_WORKSPACE}\\*" "."

                """

            }

        }
 
        stage('Build & Test') {

            steps {

                bat 'mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml'

            }

        }
 
        stage('Commit & Push Changes') {

            steps {

                script {

                    echo 'Checking for changes to push...'

                    withCredentials([usernamePassword(

                        credentialsId: 'ea8d2ef0-e28f-483d-a3f7-14af3d75864f',

                        usernameVariable: 'GIT_USER',

                        passwordVariable: 'GIT_TOKEN')]) {
 
                        bat """

                            git config user.email "jenkins@pipeline.com"

                            git config user.name "KiranPatil30"
 
 							REM Checkout the correct branch to avoid detached HEAD state
                    		git checkout ${BRANCH_NAME}
                    		
                    		REM STatus see the 
                            git status
                            
                            REM Pull latest changes from origin
                    		git pull origin ${BRANCH_NAME} --rebase

                            git add .
 
                            REM Commit only if there are changes

                            git diff --cached --quiet || git commit -m "${COMMIT_MESSAGE}"
 
                            REM Push using token

                            git push https://%GIT_USER%:%GIT_TOKEN%@github.com/KiranPatil30/SauceDemo-capstone-project.git ${BRANCH_NAME}

                        """

                    }

                }

            }

        }

    }
 
    post {

        always {

            // Archive screenshots

            archiveArtifacts artifacts: 'reports/screenshots/*', fingerprint: true
 
            // Publish Cucumber Report

            publishHTML(target: [

                allowMissing: false,

                alwaysLinkToLastBuild: true,

                keepAll: true,

                reportDir: 'reports/cucumber-reports',

                reportFiles: 'cucumber-report.html',

                reportName: 'Cucumber Report'

            ])
 
            // Publish Extent Report

            publishHTML(target: [

                allowMissing: false,

                alwaysLinkToLastBuild: true,

                keepAll: true,

                reportDir: 'reports/extent-reports',

                reportFiles: 'index.html',

                reportName: 'Extent Report'

            ])

        }

    }

}
 