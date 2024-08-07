output "instance_ip" {
  value = openstack_networking_floatingip_v2.floating_ip_1.address
  description = "The floating IP address associated with the instance."
}
