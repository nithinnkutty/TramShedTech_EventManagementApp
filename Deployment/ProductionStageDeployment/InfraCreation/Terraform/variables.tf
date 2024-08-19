variable "region" {
  description = "The AWS region to deploy in"
  default     = "eu-north-1"
}

variable "ami" {
  description = "AMI ID for the instance"
  default     = "ami-079c4d86630be4b6a"  # Amazon Linux 2 in eu-north-1
}

variable "instance_type" {
  description = "Type of the instance"
  default     = "t3.micro"
}

variable "key_name" {
  description = "Name of the SSH key pair"
  default     = "TTProject"
}

variable "name" {
  description = "Name tag for the instance"
  default     = "TramShedTechProject"
}

variable "subnet_id" {
  description = "Subnet ID where the instance will be launched"
  default     = "subnet-01179dfa47c9043a1"
}

variable "security_group_name" {
  description = "Security group name"
  default     = "tf_security_group"
}

#variable "user_data_script" {
#  description = "Path to the user data script"
#  default     = "./serverSetup.sh"
#}
