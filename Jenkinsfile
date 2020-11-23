pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'chmod +x gradlew'
        sh './gradlew clean build'
      }
    }

    stage('Archive') {
      steps {
        archiveArtifacts(artifacts: 'build/libs/*.jar', fingerprint: true, onlyIfSuccessful: true)
        archiveArtifacts(artifacts: '**/build/libs/*.jar', fingerprint: true, onlyIfSuccessful: true)
      }
    }

  }
}