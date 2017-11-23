package com.netcracker.orderentry.inventory.domain.to;

public class PermissionDTO {

    private String permissionName;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public PermissionDTO(String permissionName) {
        this.permissionName = permissionName;
    }

    public PermissionDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionDTO that = (PermissionDTO) o;

        return permissionName != null ? permissionName.equals(that.permissionName) : that.permissionName == null;
    }

    @Override
    public int hashCode() {
        return permissionName != null ? permissionName.hashCode() : 0;
    }
}
