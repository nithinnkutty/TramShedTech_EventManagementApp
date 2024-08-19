output "instance_public_ip" {
  description = "The public IP address of the instance"
  value       = aws_eip.ip.public_ip
}

output "instance_id" {
  description = "The ID of the AWS instance"
  value       = aws_instance.web.id
}

output "instance_ami" {
  description = "The AMI used for the instance"
  value       = aws_instance.web.ami
}
