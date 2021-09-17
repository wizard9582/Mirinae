pipeline {
	agent none
	options { skipDefaultCheckout(false) }
	stages {
		stage('git pull') { 
			agent any
			steps {
				checkout scm
			}
		}
		stage('Docker build') {
			agent any
			steps {
				sh 'docker build -t frontend:latest ./frontend' 
				
				sh 'docker build -t backend:latest ./backend' 
				

			}
		}
		stage('Docker run') {
			agent any
			steps {
				
				sh 'docker ps -f name=frontend -q \
        | xargs --no-run-if-empty docker container stop'
				sh 'docker ps -f name=backend -q \
		| xargs --no-run-if-empty docker container stop'

				
				sh 'docker container ls -a -f name=frontend -q \
        | xargs -r docker container rm'
				sh 'docker container ls -a -f name=backend -q \
		| xargs -r docker container rm'

				
				sh 'docker images -f dangling=true && \
				docker rmi -f $(docker images -f dangling=true -q)' 

				
				sh 'docker run -d --name frontend \
				-p 80:80 \
				-p 443:443 \
				-v /etc/letsencrypt:/cert \
				-v /etc/localtime:/etc/localtime:ro \
				--network jenkinsnetwork \
				frontend:latest'

				sh 'docker run -d -p 8080 --name backend \
		--network jenkinsnetwork backend:latest'
			}
		}
	}
}