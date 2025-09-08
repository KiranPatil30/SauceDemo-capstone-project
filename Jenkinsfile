pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying to dev environment...'
            }
        }

        stage('Commit & Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'ea8d2ef0-e28f-483d-af37-14af3d75864f',
                    usernameVariable: 'GIT_USER',
                    passwordVariable: 'GIT_TOKEN'
                )]) {
                    bat """
                        git config --global user.email "kiranspatil686@gmail.com"
                        git config --global user.name "%GIT_USER%"

                        git add .
                        git status
                        git diff --cached --quiet || git commit -m "Auto-commit from Jenkins pipeline"

                        git remote set-url origin https://%GIT_USER%:%GIT_TOKEN%@github.com/KiranPatil30/SauceDemo-capstone-project.git
                        git push origin main
                    """
                }
            }
        }

        stage('Check Git Config') {
            steps {
                bat 'git config --list'
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed.'
        }
    }
}
