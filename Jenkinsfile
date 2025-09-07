pipeline {
   agent any
   environment {
       APP_ENV = 'dev'
   }
   stages {
       stage('Build') {
           steps {
               echo 'Building the project...'
               bat 'mvn clean install'
           }
       }
       stage('Test') {
           steps {
               echo 'Running tests...'
               bat 'mvn test'
           }
       }
       stage('Deploy') {
           steps {
               echo "Deploying to ${env.APP_ENV} environment..."
               // your deployment logic here
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
