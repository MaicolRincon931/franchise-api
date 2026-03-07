terraform {
  required_providers {
    mongodbatlas = {
      source = "mongodb/mongodbatlas"
      version = "1.15.0"
    }
  }
}

provider "mongodbatlas" {
  public_key  = var.mongodb_atlas_public_key
  private_key = var.mongodb_atlas_private_key
}

resource "mongodbatlas_project" "project" {
  name   = "Franchise-API-Project"
  org_id = var.mongodb_atlas_org_id
}

resource "mongodbatlas_cluster" "cluster" {
  project_id   = mongodbatlas_project.project.id
  name         = "franchise-cluster"
  cluster_type = "REPLICASET"
  replication_specs {
    num_shards = 1
    regions_config {
      region_name     = "US_EAST_1"
      electable_nodes = 3
      priority        = 7
      read_only_nodes = 0
    }
  }
  cloud_backup      = true
  auto_scaling_compute_enabled = true
  provider_name               = "AWS"
  provider_instance_size_name = "M0" # Free Tier
}

resource "mongodbatlas_database_user" "db_user" {
  username           = "franchiseUser"
  password           = "securePassword123"
  project_id         = mongodbatlas_project.project.id
  auth_database_name = "admin"

  roles {
    role_name     = "readWrite"
    database_name = "franchise_db"
  }
}

resource "mongodbatlas_project_ip_access_list" "ip_access" {
  project_id = mongodbatlas_project.project.id
  ip_address = "0.0.0.0/0"
  comment    = "Allow access from anywhere"
}
