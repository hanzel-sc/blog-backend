variable "region" {
    default = "eu-north-1"
  
}

variable "key_name" {
    description = "EC2 key pair"
    type = string
    default     = "test-aws"   
  
}

