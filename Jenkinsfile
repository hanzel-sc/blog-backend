pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'Maven 3'
        SONARQUBE = 'SonarQubeServer'
        SONAR_PROJECT_KEY = 'blog-backend'
        SONAR_HOST_URL = 'http://192.168.25.214:9000'
        SONAR_TOKEN = 'squ_17ee82724b4d37a85039727c8de2c2091391f904'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'develop', url: 'https://github.com/hanzel-sc/blog-backend.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh "${MAVEN_HOME}/bin/mvn test"
                        sh "${MAVEN_HOME}/bin/mvn clean verify"
                        sh "${MAVEN_HOME}/bin/mvn clean install"
                    } else {
                        bat "${MAVEN_HOME}/bin/mvn test"
                        bat "${MAVEN_HOME}/bin/mvn clean verify"
                        bat "${MAVEN_HOME}/bin/mvn clean install"
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${env.SONARQUBE}") {
                    script {
                        if (isUnix()) {
                            sh "${MAVEN_HOME}/bin/mvn sonar:sonar -Dsonar.projectKey=${env.SONAR_PROJECT_KEY} -Dsonar.host.url=${env.SONAR_HOST_URL} -Dsonar.login=${env.SONAR_TOKEN} -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml"
                        } else {
                            bat "${MAVEN_HOME}\\bin\\mvn sonar:sonar -Dsonar.projectKey=${env.SONAR_PROJECT_KEY} -Dsonar.host.url=${env.SONAR_HOST_URL} -Dsonar.login=${env.SONAR_TOKEN} -Dsonar.coverage.jacoco.xmlReportPaths=target\\site\\jacoco\\jacoco.xml"
                        }
                    }
                }
            }
        }


        stage('Check Sonar Issues') {
            steps {
                script {
                    def response = bat(
                        script: """
                            curl -s -u ${env.SONAR_TOKEN}: "${env.SONAR_HOST_URL}/api/issues/search?componentKeys=${env.SONAR_PROJECT_KEY}&severities=BLOCKER,CRITICAL"
                        """,
                        returnStdout: true
                    ).trim()

                    // Simple regex to extract the "total" field from the JSON response
                    def matcher = response =~ /"total"\s*:\s*(\d+)/
                    if (matcher.find()) {
                        def count = matcher.group(1).toInteger()
                        echo "Found $count critical/blocker issues"
                        if (count > 0) {
                            error "Build stopped: Found $count critical Sonar issues"
                        }
                    } else {
                        error "Failed to parse SonarQube response"
                    }
                }
            }
        }

        /*stage('Deploy') {
            steps {
                bat '''
                taskkill /F /IM java.exe || echo "No running java process found"
                copy target\\blog-backend.jar C:\\Jenkins\\deployed\\blog-backend.jar
                start java -jar C:\\Jenkins\\deployed\\blog-backend.jar > C:\\Jenkins\\deployed\\log.txt 2>&1
                '''
            }
        }*/
    }

    post {
        success {
           emailext (
                  subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                  body: "Build completed successfully.\n\n",
                  from: "ighdprogeny@gmail.com",
                  to: "marcusfitzerbach@gmail.com",
                  attachmentsPattern: "target/jacoco-report/index.html"
                  attachmentsPattern: "target/jacoco-report/jacoco.xml"
                )
            }
        failure {
            emailext (
                subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Build failed during SonarQube analysis.\n\n",
                from: "ighdprogeny@gmail.com",
                to: "marcusfitzerbach@gmail.com",
                attachmentsPattern: "target/jacoco-report/index.html"
                attachmentsPattern: "target/jacoco-report/jacoco.xml"
            )
        }
    }
}
