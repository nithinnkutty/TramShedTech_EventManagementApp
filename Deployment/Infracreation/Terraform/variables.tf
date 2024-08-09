variable "flavor" { default = "m1.large" }
variable "image" { default = "Rocky Linux 9 20220830" }
variable "name1" { default = "TT_EventManagementSystem" }

variable "keypair" { default = "TTProject" } # you may need to change this
variable "network" { default = "TTProject_network" }   # you need to change this

variable "pool" { default = "cscloud_private_floating" }
#variable "server1_script" { default = "./server1.sh" }
variable "security_description" { default = "Terraform security group" }
variable "security_name" { default = "tf_security_TT" }
