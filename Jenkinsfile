
pipeline {
    agent any

    tools {
        jdk 'jdk21'
    }

    triggers {
        pollSCM('H/2 * * * *')
    }

    options {
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '15'))
    }

    environment {
        STUDENT_NAME = 'Yernar Temirkhan'
        STUDENT_ID   = '37722'
        IMAGE_NAME   = 'temirkhan-yernar-student-api'
        IMAGE_TAG    = 'v1.1-temirkhan-yernar-37722'
        IMAGE        = "${IMAGE_NAME}:${IMAGE_TAG}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo "Student: ${STUDENT_NAME} (ID ${STUDENT_ID})"
                echo "Docker image to build: ${IMAGE}"
                sh 'git log -1 --oneline'
            }
        }

        stage('Build') {
            steps {
                echo "Building Student API for ${STUDENT_NAME} with Gradle"
                sh 'chmod +x gradlew && ./gradlew --no-daemon clean bootJar -x test'
                sh 'ls -l build/libs'
            }
        }

        stage('Test') {
            steps {
                echo "Running automated tests for ${STUDENT_NAME}"
                sh './gradlew --no-daemon test'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo "Building Docker image ${IMAGE}"
                sh '''
                    export PATH="/usr/local/bin:/opt/homebrew/bin:/Applications/Docker.app/Contents/Resources/bin:$PATH"
                    docker build -t "$IMAGE" .
                '''
            }
        }

        stage('Verify') {
            steps {
                sh '''
                    export PATH="/usr/local/bin:/opt/homebrew/bin:/Applications/Docker.app/Contents/Resources/bin:$PATH"
                    docker image inspect "$IMAGE" --format 'Image: {{.RepoTags}} | Size: {{.Size}} bytes | Created: {{.Created}}'
                    docker images "$IMAGE_NAME"
                    echo "VERIFY OK: image $IMAGE exists"
                '''
            }
        }
    }

    post {
        success { echo "Pipeline finished successfully for ${STUDENT_NAME}" }
        failure { echo "Pipeline FAILED for ${STUDENT_NAME}" }
    }
}
