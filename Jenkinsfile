pipeline {
   agent any
   environment {
       APP_ENV = 'dev'   // ✅ Good use of environment variable
   }
   stages {
       stage('Clone') {
           steps {
               git 'https://github.com/KiranPatil30/SauceDemo-capstone-project.git'  // ✅ Correct Git repo
           }
       }

       stage('Build') {
           steps {
               echo 'Building the project...'
               bat 'mvn clean install'   // ⚠️ Works only on Windows agents
           }
       }

       stage('Test') {
           steps {
               echo 'Running tests...'
               bat 'mvn test'  // ⚠️ Works only on Windows agents
           }
       }

       stage('Deploy') {
           steps {
               echo "Deploying to ${env.APP_ENV} environment..."
               // ✅ You can add your deploy logic here
           }
       }
   }

   post {
       success {
           echo 'Pipeline completed successfully.'
       }
       failure {
           echo 'Pipeline failed.'
       }
   }
}
