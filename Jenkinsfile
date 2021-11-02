pipeline {

    agent any

    tools {
        maven "maven"
        nodejs "nodejs"
    }

    options { skipDefaultCheckout(false) }

    stages{
        stage('Preparation'){
            steps{
                script{
                    env.ymd = sh (returnStdout: true, script: ''' echo date '+%Y%m%d-%H%M%S' ''')
                }
                echo("params : ${env.ymd} " + params.tag)
            }
        }

        stage('FE Build'){
            steps {
                sh "cd ./frontend && npm install"
                sh "cd ./frontend && npm run build"
            }
        }

        stage('BE Build'){
            steps {
                sh "cd ./backend && mvn clean package"
            }
        }

        stage('Deploy && Docker build'){
            steps {
                sh "docker rm -f front"
                sh "docker rmi front"
                sh "docker rm -f back"
                sh "docker rmi back"
                sh "docker build -t front ./frontend"
                sh 'docker build -t back ./backend'
                sh 'docker run -d --name front -p 80:80 -v /home/ubuntu/images:/images front'
                sh 'docker run -d --name back -p 8080:8080 -v /home/ubuntu/images:/images back'
            }
        }
        
    }
}