provider "aws" {
    region = var.region
}


resource "aws_instance" "blog-terraform" {

    ami = "ami-0c1ac8a41498c1a9c"
    instance_type = "t3.micro"
    key_name = var.key_name
    
    user_data = file("install.sh")

    tags = {
      Name = "jenkins-sonarqube"
    }

    vpc_security_group_ids = [aws_security_group.allow_ssh_http.id]

}

resource "aws_security_group" "allow_ssh_http" {
  name        = "allow_ssh_http"
  description = "Allow SSH, HTTP, Jenkins and SonarQube ports"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080 #For Jenkins
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] 
  }

  ingress {
    from_port   = 9000 #For SonarQube
    to_port     = 9000
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] 
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

