/**
 *
 */
function queryRole() {

}

/**
 *
 */
function addRole() {
    openRoleDialog('add');
}

/**
 *
 */
function updateRole() {

}

/**
 *
 */
function deleteRole() {

}

/**
 *
 */
function saveRoleForm() {
    console.log($('#roleGroupCode').combobox('getText'));
    console.log($('#roleGroupCode').combobox('getValue'));
}

/**
 *
 * @type {undefined}
 */
var flag=undefined;
function openRoleDialog(type) {
    flag = type;
    $('#role-dlg').dialog('open');
    $('#roleCode').textbox('setValue', '');
    $('#roleDesc').textbox('setValue', '');
}

/**
 *
 */
function closeRoleDialog() {
    $('#roleCode').textbox('setValue', '');
    $('#roleDesc').textbox('setValue', '');
    $('#role-dlg').dialog('close');
}
