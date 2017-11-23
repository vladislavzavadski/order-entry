package com.netcracker.orderentry.catalog.domain.to;

import java.util.List;

public class UserDTO {

    private String username;
    private String password;

    private List<PermissionDTO> permissionDTOList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PermissionDTO> getPermissionDTOList() {
        return permissionDTOList;
    }

    public void setPermissionDTOList(List<PermissionDTO> permissionDTOList) {
        this.permissionDTOList = permissionDTOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (username != null ? !username.equals(userDTO.username) : userDTO.username != null) return false;
        if (password != null ? !password.equals(userDTO.password) : userDTO.password != null) return false;
        return permissionDTOList != null ? permissionDTOList.equals(userDTO.permissionDTOList) : userDTO.permissionDTOList == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (permissionDTOList != null ? permissionDTOList.hashCode() : 0);
        return result;
    }
}
