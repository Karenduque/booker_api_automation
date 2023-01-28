#!/usr/bin/env groovy

pipeline {
    agent { label 'awsjenklinux' }

    tools {
        maven 'Maven 3.2.5'
        jdk 'OpenJDK 11.0.5'
    }

    stages {
        stage('Prepare') {
            steps {
                sh '''
                    echo "BRANCH_NAME= ${BRANCH_NAME}"
                    echo "JAVA_HOME = ${JAVA_HOME}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage('Test') {
            steps {
                script {
                    //General MVN command
                    sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/suites/finametrica/non-prod/DEV_FinametricaProfilerSuite.xml"
                }
            }
        }
        stage('Report'){
            steps{
                allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            }
        }
    }
}
