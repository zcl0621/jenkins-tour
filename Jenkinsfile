pipeline {
    agent any
    options {
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
        // 保留最近 10 次构建的日志
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    
    environment {
        GIT_COMMIT_SHORT = sh(
            script: "git rev-parse --short HEAD",
            returnStdout: true
        ).trim()
        VUE_IMAGE_NAME = "vue-app:${GIT_COMMIT_SHORT}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build Applications') {
            parallel {
                    stage('Build Vue Project') {
                        steps {
                            sh 'cd vue-project'
                            sh 'docker build -t ${VUE_IMAGE_NAME} .'
                        }
                    }
                
            }
        }
    }
}