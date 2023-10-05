package com.github.gribanoveu.auth.controllers.endpoints;

import com.github.gribanoveu.auth.base.BaseMockMvcTest;
import com.github.gribanoveu.auth.controllers.dtos.request.PermissionDto;
import com.github.gribanoveu.auth.controllers.dtos.request.UpdatePermissionDto;
import com.github.gribanoveu.auth.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.auth.entities.enums.ResponseCode;
import com.github.gribanoveu.auth.entities.services.contract.PermissionService;
import com.github.gribanoveu.auth.entities.tables.Permission;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Evgeny Gribanov
 * @version 03.10.2023
 */
class PermissionControllerTest extends BaseMockMvcTest {
    @Autowired private PermissionService permissionService;


    @Test
    void testCreatePermission_validData_createPermission() throws Exception {
        var permissionName = "TT_PERMISSION";

        this.mockMvc.perform(post("/v1/permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", adminToken)
                        .content(testJsonUtils.convertDtoToJson(new PermissionDto(permissionName))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.PERMISSION_CREATED.getCode()))
                .andExpect(jsonPath("$..details[0].message").value(ResponseCode.PERMISSION_CREATED.getMessage()));

        var savedPermission = permissionService.findPermissionByName(permissionName);
        Assertions.assertThat(savedPermission).isNotNull();
        Assertions.assertThat(savedPermission.getName()).isEqualTo(permissionName);
    }

    @Test
    void testCreatePermission_withoutPermissionToCreate_throwsException() throws Exception {
        var request = new PermissionDto("TT_PERMISSION");
        this.mockMvc.perform(post("/v1/permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", userToken)
                        .content(testJsonUtils.convertDtoToJson(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.ACCESS_DENIED.getCode()))
                .andExpect(jsonPath("$..details[0].message").value(ResponseCode.ACCESS_DENIED.getMessage()));
    }

    @Test
    void testCreatePermission_existName_throwsException() throws Exception {
        var request = new PermissionDto("AU_MAIN_INFO_VIEW");
        this.mockMvc.perform(post("/v1/permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", adminToken)
                        .content(testJsonUtils.convertDtoToJson(request)))
                .andExpect(status().is(ResponseCode.PERMISSION_EXIST.getHttpCode().value()))
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.PERMISSION_EXIST.getCode()))
                .andExpect(jsonPath("$..details[0].message").value(ResponseCode.PERMISSION_EXIST.getMessage()));
    }

    @Test
    void testCreatePermission_invalidName_throwsException() throws Exception {
        var request = new PermissionDto("TT");
        this.mockMvc.perform(post("/v1/permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", adminToken)
                        .content(testJsonUtils.convertDtoToJson(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.VALIDATION_ERROR_DETAIL.getCode()))
                .andExpect(jsonPath("$..details[0].message").value("Field 'permissionName' - must match the expected format"));
    }

    @Test
    void testCreatePermission_emptyName_throwsException() throws Exception {
        var request = new PermissionDto("");
        this.mockMvc.perform(post("/v1/permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", adminToken)
                        .content(testJsonUtils.convertDtoToJson(request)))
                .andExpect(status().is(ResponseCode.VALIDATION_ERROR_DETAIL.getHttpCode().value()))
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.VALIDATION_ERROR_DETAIL.getCode()))
                .andExpect(jsonPath("$..details[0].message").isNotEmpty());
    }

    @Test
    void testCreatePermission_nullName_throwsException() throws Exception {
        var request = new PermissionDto(null);
        this.mockMvc.perform(post("/v1/permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", adminToken)
                        .content(testJsonUtils.convertDtoToJson(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.VALIDATION_ERROR_DETAIL.getCode()))
                .andExpect(jsonPath("$..details[0].message").value("Field 'permissionName' - cannot be empty"));
    }

    @Test
    void testCreatePermission_withoutAuthorization_throwsException() throws Exception {
        var request = new PermissionDto("TT_PERMISSION");
        this.mockMvc.perform(post("/v1/permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJsonUtils.convertDtoToJson(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.ACCESS_DENIED.getCode()));
    }

    @Test
    void testCreatePermission_asUser_throwsException() throws Exception {
        var request = new PermissionDto("TT_PERMISSION");
        this.mockMvc.perform(post("/v1/permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", userToken)
                        .content(testJsonUtils.convertDtoToJson(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.ACCESS_DENIED.getCode()))
                .andExpect(jsonPath("$..details[0].message").value(ResponseCode.ACCESS_DENIED.getMessage()));
    }

    @Test
    void testCreatePermission_invalidJson_throwsException() throws Exception {
        this.mockMvc.perform(post("/v1/permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", adminToken)
                        .content(String.valueOf(123)))
                .andExpect(status().is(ResponseCode.VALIDATION_ERROR.getHttpCode().value()))
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.VALIDATION_ERROR.getCode()))
                .andExpect(jsonPath("$..details[0].message").value(ResponseCode.VALIDATION_ERROR.getMessage()));
    }

    @Test
    void testGetAllPermissions_withPaginationTwoPageSize_returnTwoPermission() throws Exception {
        this.mockMvc.perform(get("/v1/permission")
                        .param("pageNumber", "0")
                        .param("pageSize", "2")
                .header("Authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..permissions[0].id").value(1))
                .andExpect(jsonPath("$..permissions[0].name").value("AU_MAIN_INFO_VIEW"))
                .andExpect(jsonPath("$..permissions[1].id").value(2))
                .andExpect(jsonPath("$..permissions[1].name").value("AU_PERMISSIONS_MANAGEMENT"));
    }

    @Test
    void testGetAllPermissions_withPaginationOnePageSize_returnOnePermission() throws Exception {
        this.mockMvc.perform(get("/v1/permission")
                        .param("pageNumber", "0")
                        .param("pageSize", "1")
                .header("Authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..permissions[0].id").value(1))
                .andExpect(jsonPath("$..permissions[0].name").value("AU_MAIN_INFO_VIEW"));
    }

    @Test
    void testGetAllPermissions_withoutPagination_throwsException() throws Exception {
        this.mockMvc.perform(get("/v1/permission")
                        .header("Authorization", adminToken))
                .andExpect(status().is(ResponseCode.MISSING_PARAM.getHttpCode().value()))
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.MISSING_PARAM.getCode()))
                .andExpect(jsonPath("$..details[0].message").value(ResponseCode.MISSING_PARAM.getMessage()));
    }

    @Test
    void testGetAllPermissions_asUser_throwsException() throws Exception {
        this.mockMvc.perform(get("/v1/permission")
                        .header("Authorization", userToken))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.ACCESS_DENIED.getCode()))
                .andExpect(jsonPath("$..details[0].message").value(ResponseCode.ACCESS_DENIED.getMessage()));
    }

    @Test
    void testGetAllPermissions_withoutAuthorization_throwsException() throws Exception {
        this.mockMvc.perform(get("/v1/permission")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.ACCESS_DENIED.getCode()));
    }

    @Test
    void testDeletePermission_asAdmin_permissionDeleted() throws Exception {
        var permissionName = "BE_DELETED";
        permissionService.save(new Permission(permissionName));

        var savedPermission = permissionService.findPermissionByName(permissionName);
        Assertions.assertThat(savedPermission.getName()).isEqualTo(permissionName);

        this.mockMvc.perform(delete("/v1/permission")
                        .param("permissionName", permissionName)
                        .header("Authorization", adminToken))
                .andExpect(status().is(ResponseCode.PERMISSION_DELETED.getHttpCode().value()))
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.PERMISSION_DELETED.getCode()))
                .andExpect(jsonPath("$..details[0].message").value(ResponseCode.PERMISSION_DELETED.getMessage()));

        assertThrows(CredentialEx.class,
                () -> permissionService.findPermissionByName(permissionName));
    }

    @Test
    void testDeletePermission_asUser_throwsException() throws Exception {
        var permissionName = "BE_NOT_DELETED";
        permissionService.save(new Permission(permissionName));

        var savedPermission = permissionService.findPermissionByName(permissionName);
        Assertions.assertThat(savedPermission.getName()).isEqualTo(permissionName);

        this.mockMvc.perform(delete("/v1/permission")
                        .param("permissionName", permissionName)
                        .header("Authorization", userToken))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.ACCESS_DENIED.getCode()))
                .andExpect(jsonPath("$..details[0].message").value(ResponseCode.ACCESS_DENIED.getMessage()));
    }

    @Test
    void testDeletePermission_notExist_throwsException() throws Exception {
        this.mockMvc.perform(delete("/v1/permission")
                        .param("permissionName", "NOT_EXIST_PERMISSION")
                        .header("Authorization", adminToken))
                .andExpect(status().is(ResponseCode.PERMISSION_NOT_EXIST.getHttpCode().value()))
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.PERMISSION_NOT_EXIST.getCode()))
                .andExpect(jsonPath("$..details[0].message").value(ResponseCode.PERMISSION_NOT_EXIST.getMessage()));
    }

    @Test
    void updatePermission_asAdmin_permissionUpdated() throws Exception {
        var permissionName = "BE_DELETED";
        var updatedPermissionName = "BE_UPDATED_SUCCESS";
        var updatePermissionDto = new UpdatePermissionDto(permissionName, updatedPermissionName);

        permissionService.save(new Permission(permissionName));
        var savedPermission = permissionService.findPermissionByName(permissionName);
        Assertions.assertThat(savedPermission.getName()).isEqualTo(permissionName);

        this.mockMvc.perform(put("/v1/permission")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJsonUtils.convertDtoToJson(updatePermissionDto)))
                .andExpect(status().is(ResponseCode.PERMISSION_UPDATED.getHttpCode().value()))
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.PERMISSION_UPDATED.getCode()))
                .andExpect(jsonPath("$..details[0].message").value(ResponseCode.PERMISSION_UPDATED.getMessage()));

        var updatedPermission = permissionService.findPermissionByName(updatedPermissionName);
        Assertions.assertThat(updatedPermission.getName()).isEqualTo(updatedPermissionName);
    }

    @Test
    void updatePermission_asUser_throwsException() throws Exception {
        var permissionName = "BE_NOT_UPDATED";
        permissionService.save(new Permission(permissionName));

        var updatePermissionDto = new UpdatePermissionDto(permissionName, "BE_NOT_UPDATED_UPD");


        this.mockMvc.perform(put("/v1/permission")
                        .content(testJsonUtils.convertDtoToJson(updatePermissionDto))
                        .header("Authorization", userToken))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.ACCESS_DENIED.getCode()))
                .andExpect(jsonPath("$..details[0].message").value(ResponseCode.ACCESS_DENIED.getMessage()));
    }

    @Test
    void updatePermission_notExist_throwsException() throws Exception {
        var permissionName = "BE_NOT_EXIST";
        var updatePermissionDto = new UpdatePermissionDto(permissionName, "BE_NOT_EXIST_UPD");

        assertThrows(CredentialEx.class,
                () -> permissionService.findPermissionByName(permissionName));

        this.mockMvc.perform(put("/v1/permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJsonUtils.convertDtoToJson(updatePermissionDto))
                        .header("Authorization", adminToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.PERMISSION_NOT_EXIST.getCode()))
                .andExpect(jsonPath("$..details[0].message").value(ResponseCode.PERMISSION_NOT_EXIST.getMessage()));
    }

    @Test
    void testUpdatePermission_withoutAuthorization_throwsException() throws Exception {
        var updatePermissionDto = new UpdatePermissionDto("AU_PERMISSIONS_MANAGEMENT", "AU_PERMISSIONS_MANAGEMENT_NP");

        this.mockMvc.perform(put("/v1/permission")
                        .param("permissionName", "BE_NOT_UPDATED"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.ACCESS_DENIED.getCode()))
                .andExpect(jsonPath("$..details[0].message").value("Full authentication is required to access this resource"));

    }

    @Test
    void testUpdatePermission_withoutNewPermissionName_throwsException() throws Exception {
        var permissionName = "AU_PERMISSIONS_MANAGEMENT";
        var updatePermissionDto = new UpdatePermissionDto(permissionName, "");

        this.mockMvc.perform(put("/v1/permission")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJsonUtils.convertDtoToJson(updatePermissionDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..details[0].code").isNotEmpty())
                .andExpect(jsonPath("$..details[0].message").isNotEmpty())
                .andExpect(jsonPath("$..details[1].code").isNotEmpty())
                .andExpect(jsonPath("$..details[1].message").isNotEmpty());

        var updatedPermission = permissionService.findPermissionByName(permissionName);
        Assertions.assertThat(updatedPermission.getName()).isEqualTo(permissionName);
    }

    @Test
    void testUpdatePermission_withNull_throwsException() throws Exception {
        var permissionName = "AU_PERMISSIONS_MANAGEMENT";
        var updatePermissionDto = new UpdatePermissionDto(permissionName, null);

        this.mockMvc.perform(put("/v1/permission")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJsonUtils.convertDtoToJson(updatePermissionDto)))
                .andExpect(status().is(ResponseCode.VALIDATION_ERROR_DETAIL.getHttpCode().value()))
                .andExpect(jsonPath("$..details[0].code").value(ResponseCode.VALIDATION_ERROR_DETAIL.getCode()))
                .andExpect(jsonPath("$..details[0].message").value("Field 'newName' - cannot be empty"));

        var updatedPermission = permissionService.findPermissionByName(permissionName);
        Assertions.assertThat(updatedPermission.getName()).isEqualTo(permissionName);
    }
}