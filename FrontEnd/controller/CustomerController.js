let customerId;
let customerName;
let customerAddress;
let customerContact;

let txtCustomerId = $("#txtCustomerId");
let txtCustomerName = $("#txtCustomerName");
let txtAddress = $("#txtAddress");
let txtContact = $("#txtContact");

let txtSearchId = $("#txtSearchCustomer");

let nextID;
let lastId;

disableButton(".btnSaveCustomer");
disableButton("#btnEditCustomer");
disableButton("#btnDeleteCustomer");

/* ---------------Initially Hide the Error Indicators----------*/

$("#customerForm p.errorText").hide();

/* -----------------------------------------------------------------CRUD Operation---------------------------------------------------*/

function addCustomer() {
    txtCustomerId.removeAttr("disabled");

    $.ajax({
        url: "http://localhost:8080/pos/customer",
        method: "POST",
        data: $("#customerForm").serialize(),
        success: function (resp) {
            if (resp.status === 200) {
                toastr.success(resp.message);
                loadAllCustomers();
                getCustomerCount();
                reset_CustomerForm();
                // generateNextCustomerID();

            } else {
                toastr.error(resp.data);
                generateNextCustomerID();
            }
        },
        error: function (ob, textStatus, error) {
            console.log(ob);
        }
    });
}

function updateCustomer() {
    let custObj = {
        id: txtCustomerId.val(),
        name: txtCustomerName.val(),
        address: txtAddress.val(),
        contact: txtContact.val()
    }

    $.ajax({
        url: "http://localhost:8080/pos/customer",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(custObj),
        success: function (resp) {
            if (resp.status === 200) {
                toastr.success(resp.message);

                loadAllCustomers();
                reset_CustomerForm();
                // generateNextCustomerID();

                clearCustomerFields();
                load_TblCustomerOrder();
                select_OrderDetailRow();

            } else if (resp.status === 400) {
                toastr.error(resp.message);
                generateNextCustomerID();
            } else {
                toastr.error(resp.message);
                generateNextCustomerID();
            }
        },
        error: function (ob, textStatus, error) {
            console.log(ob);
        }
    });
}

function deleteCustomer(row) {
    customerId = $(row).children(':nth-child(1)').text();

    swal({
        title: 'Are you sure you want to delete this Customer..?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        buttons: ["Cancel", "Delete"],
        dangerMode: true,
        closeModal: true,
        closeOnClickOutside: false,

    }).then(result => {
        if (result) {
            email = localStorage.getItem("email");
            pwd = localStorage.getItem("pwd");
            $.ajax({
                url: "http://localhost:8080/pos/customer?customerID=" + customerId + "&email=" + email + "&pwd=" + pwd,
                method: "DELETE",
                success: function (resp) {
                    if (resp.status === 200) {
                        swal({
                            title: 'Deleted!',
                            text: "Customer  " + customerId + "  Deleted.",
                            icon: 'success',
                            buttons: ["OK"],
                            timer: 2000,
                            closeModal: true,
                        });

                        loadAllCustomers();
                        getCustomerCount();
                        reset_CustomerForm();
                        // generateNextCustomerID();

                        select_OrderDetailRow();
                        clearInvoiceFields();
                        clearInvoiceTable();

                        clearCustomerFields();

                    } else if (resp.status === 400) {
                        toastr.error(resp.message);
                        generateNextCustomerID();
                    } else {
                        toastr.error(resp.message);
                        generateNextCustomerID();
                    }
                },
                error: function (ob, status, t) {
                    console.log(ob);
                }
            });
        }
    })
}

function loadAllCustomers() {
    $("#tblCustomer-body").empty();

    $.ajax({
        url: "http://localhost:8080/pos/customer?option=GETALL",
        method: "GET",
        success: function (resp) {
            for (let c of resp.data) {
                let customer = new Customer(c.id, c.name, c.address, c.contact);

                newRow = `<tr>
                    <td>${customer.getCustomerID()}</td>
                    <td>${customer.getCustomerName()}</td>
                    <td>${customer.getCustomerAddress()}</td>
                    <td>0${customer.getCustomerContact()}</td>
                </tr>`;

                $("#tblCustomer-body").append(newRow);
            }
            select_CustomerRow();
        },

        error: function (ob, textStatus, error) {
            alert(textStatus);
            console.log(ob);
        }
    });
}

function searchCustomer(searchValue) {
    $.ajax({
        url: "http://localhost:8080/pos/customer?option=SEARCH&customerID=" + searchValue + "&customerName=",
        method: "GET",
        success: function (resp) {
            response = resp;
            let obj = resp.data;
            obj = new Customer(obj.id, obj.name, obj.address, obj.contact);

            if (JSON.stringify(resp.data) !== "{}") { // if resp.data = '{"id":"C00-005","name":"Ramal","address":"Jaffna","contact":"716455455"}'
                txtCustomerId.val(obj.getCustomerID());
                txtCustomerName.val(obj.getCustomerName());
                txtAddress.val(obj.getCustomerAddress());
                txtContact.val("0" + obj.getCustomerContact());

                validate_CustomerForm();
                return true;

            } else { // if resp.data = '{}'
                swal({
                    title: "Customer " + searchValue + " doesn't exist...",
                    text: "\n",
                    icon: 'warning',
                    buttons: false,
                    timer: 2000,
                    closeModal: true,
                });
                reset_CustomerForm();
                return false;
            }
        },
        error: function (ob, textStatus, error) {
            console.log(ob);
        }
    });
}

/* ------------------Save Customer------------*/

/* When/after a new Customer is Saved:
    1. add Customer to table
    2. fill input fields when a row is selected
    3. delete the selected Customer from the table
*/

$(".btnSaveCustomer").click(function (e) {
    Swal.fire({
        text: "Are you sure you want to Save this Customer..?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Save',
        confirmButtonColor: '#1abc9c',
        customClass: {
            cancelButton: 'order-1 right-gap',
            confirmButton: 'order-2',
        },
        allowOutsideClick: false,
        // allowEnterKey: false,
        // keydownListenerCapture: false,
        // stopKeydownPropagation: false,
        returnFocus: false,

    }).then(result => {
        if (result.isConfirmed) {
            addCustomer();
            // reset_CustomerForm();

            $("#tblCustomer-body>tr").off("dblclick");
            delete_CustomerRowOnDblClick();
        }
    });
});

/* ------------------Update Customer------------*/

$("#btnEditCustomer").click(function (e) {
    select_CustomerRow();
    Swal.fire({
        text: "Are you sure you want to Update this Customer..?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Update',
        confirmButtonColor: '#3498db',
        customClass: {
            cancelButton: 'order-1 right-gap',
            confirmButton: 'order-2',
        },
        allowOutsideClick: false,
        returnFocus: false,

    }).then(result => {
        if (result.isConfirmed) {
            updateCustomer();
            // reset_CustomerForm();

            $("#tblCustomer-body>tr").off("dblclick");
            delete_CustomerRowOnDblClick();
        }
    });
});

/* ------------------Search Customer------------*/

$("#txtSearchCustomer").keyup(function (e) {
    searchValue = $(this).val();

    $("#btnSearchCustomer").off("click");
    $("#btnSearchCustomer").click(function (e) {
        // e.preventDefault();
        searchCustomer(searchValue);

    });

    if (e.key == "Enter") {
        e.preventDefault();
        searchCustomer(searchValue);
    }

    $("#tblCustomer-body>tr").each(function () {
        let isFound = false;
        $(this).each(function () {  // search td of each tr one by one
            if ($(this).text().toLowerCase().indexOf(searchValue.toLowerCase()) >= 0) {
                isFound = true;
            }
        });
        if (isFound) {
            $(this).show();

        } else {
            $(this).hide();
        }
    });
});

/* -------------------------------------------------------------------Validation--------------------------------------------------- */

/* --------------------------Validate & Jump to Next Field On Enter---------------------------------*/

var regExCusID = /^(C00-)[0-9]{3,4}$/;
var regExCusName = /^[A-Z][a-z ]{4,9}[A-z]{1,10}$|^[A-Z][a-z ]{3,20}$/;
var regExCusAddress = /^[A-z0-9 \.]{5,}$/;
var regExCusContact = /^[0-9]{10}$/

function select_CustomerRow() {
    $("#tblCustomer-body>tr").click(function () {
        rowSelected = this;
        customerId = $(this).children(':nth-child(1)').text();

        searchCustomer(customerId);

        disableButton("#btnSaveCustomer");
        enableButton("#btnEditCustomer");
        enableButton("#btnDeleteCustomer");

        $("#btnDeleteCustomer").off("click");

        /* ------------------Delete Customer------------*/

        $("#btnDeleteCustomer").click(function () {
            deleteCustomer(rowSelected);
        });

        $("#tblCustomer-body>tr").off("dblclick");
        delete_CustomerRowOnDblClick();
    });
}

function delete_CustomerRowOnDblClick() {
    $("#tblCustomer-body>tr").dblclick(function () {
        rowSelected = $(this);
        deleteCustomer(rowSelected);
    });
}

function validate_CustomerID(input, txtField) {

    if (regExCusID.test(input)) {
        changeBorderColor("valid", txtField);

        // once the current input field is green change the the border of next input field to red
        if (!validate_CustomerName(txtCustomerName.val(), txtCustomerName)) {
            changeBorderColor("invalid", txtCustomerName);
            $("#customerForm p.errorText").eq(1).show();
            $("#errorName").text("*Required Field* Min 5, Max 20, Spaces Allowed");
        }

        $("#customerForm p.errorText").eq(0).hide();
        return true;

    } else {
        changeBorderColor("invalid", txtField);
        $("#customerForm p.errorText").eq(0).show();
        $("#errorID").text("*Required Field* Format : C00-000");

        disableButton(".btnSaveCustomer");
        disableButton("#btnEditCustomer");
        return false;
    }
}

function validate_CustomerName(input, txtField) {

    if (regExCusName.test(input)) {
        changeBorderColor("valid", txtField);

        // once the current input field is green change the the border of next input field to red
        if (!validate_CustomerAddress(txtAddress.val(), txtAddress)) {
            changeBorderColor("invalid", txtAddress);
            $("#customerForm p.errorText").eq(2).show();
            $("#errorAddress").text("*Required Field* Minimum 5");
        }

        $("#customerForm p.errorText").eq(1).hide();
        return true;

    } else {
        changeBorderColor("invalid", txtField);
        $("#customerForm p.errorText").eq(1).show();
        $("#errorName").text("*Required Field* Min 5, Max 20, Spaces Allowed");

        disableButton(".btnSaveCustomer");
        disableButton("#btnEditCustomer");
        return false;
    }
}

function validate_CustomerAddress(input, txtField) {

    if (regExCusAddress.test(input)) {
        changeBorderColor("valid", txtField);

        // once the current input field is green change the the border of next input field to red
        if (!validate_CustomerContact(txtContact.val(), txtContact)) {
            changeBorderColor("invalid", txtContact);
            $("#customerForm p.errorText").eq(3).show();
            $("#errorContact").text("*Required Field* Min 10, Max 10, Only Numbers");
        }

        $("#customerForm p.errorText").eq(2).hide();
        return true;

    } else {
        changeBorderColor("invalid", txtField);
        $("#customerForm p.errorText").eq(2).show();
        $("#errorAddress").text("*Required Field* Minimum 5");

        disableButton(".btnSaveCustomer");
        disableButton("#btnEditCustomer");
        return false;
    }
}

function validate_CustomerContact(input, txtField) {
    if (regExCusContact.test(txtContact.val())) {
        changeBorderColor("valid", txtField);
        enableButton(".btnSaveCustomer");
        enableButton("#btnEditCustomer");

        $("#customerForm p.errorText").eq(3).hide();

        customerId = txtCustomerId.val();
        $.ajax({
            url: "http://localhost:8080/pos/customer?option=CHECK_FOR_DUPLICATE&customerId=" + customerId + "&input=" + input,
            method: "GET",
            success: function (resp) {
                response = resp;
                if (resp.message === "Duplicate") { // if a duplicate Contact No
                    changeBorderColor("invalid", txtField);
                    $("#customerForm p.errorText").eq(3).show();
                    $("#errorContact").text("*Required Field* A Customer with this Contact already exist..");

                    disableButton(".btnSaveCustomer");
                    disableButton("#btnEditCustomer");
                    return false;

                } else if (resp.message === "Match") { // if its the Contact of the selected Customer
                    return true;

                } else { // if not a duplicate Contact No
                    return true;
                }
            }
        });

        if (rowSelected != null) {
            disableButton(".btnSaveCustomer");
        }
        return true;

    } else {
        changeBorderColor("invalid", txtField);
        $("#customerForm p.errorText").eq(3).show();
        $("#errorContact").text("*Required Field* Min 10, Max 10, Only Numbers");

        disableButton(".btnSaveCustomer");
        disableButton("#btnEditCustomer");
        return false;
    }
}

function reset_CustomerForm() {
    txtCustomerId.val("").css('border', '1px solid rgb(206, 212, 218)');
    txtCustomerName.val("").css('border', '1px solid rgb(206, 212, 218)');
    txtAddress.val("").css('border', '1px solid rgb(206, 212, 218)');
    txtContact.val("").css('border', '1px solid rgb(206, 212, 218)');

    $("#customerForm p.errorText").hide();

    txtCustomerId.attr("disabled", "disabled");
    txtCustomerName.focus();

    disableButton(".btnSaveCustomer");
    disableButton("#btnEditCustomer");
    disableButton("#btnDeleteCustomer");

    select_CustomerRow();

    rowSelected = null;
    customerId = null;

    generateNextCustomerID();
}

function validate_CustomerForm() {

    customerId = txtCustomerId.val();
    customerName = txtCustomerName.val();
    customerAddress = txtAddress.val();
    customerContact = txtContact.val();

    validate_CustomerID(customerId, txtCustomerId);
    validate_CustomerName(customerName, txtCustomerName);
    validate_CustomerAddress(customerAddress, txtAddress);
    validate_CustomerContact(customerContact, txtContact);
}

$("#txtCustomerId, #txtCustomerName, #txtAddress, #txtContact").keydown(function (e) {
    $("#btnSearchCustomer").off("click");
    if (e.key === "Tab") {
        e.preventDefault();
    }

    if (e.code === "Enter") {
        e.preventDefault();

    }
});

$("#txtCustomerId").keyup(function (e) {
    input = txtCustomerId.val();
    validate_CustomerID(input, this);

    if (e.code === "Enter" && isBorderGreen(this)) {
        $("#txtCustomerName").focus();
    }
});

$("#txtCustomerName").keyup(function (e) {
    input = txtCustomerName.val();
    validate_CustomerName(input, this);

    if (e.code === "Enter" && isBorderGreen(this)) {
        $("#txtAddress").focus();
    }
});

$("#txtAddress").keyup(function (e) {
    input = txtAddress.val();
    validate_CustomerAddress(input, this);

    if (e.code === "Enter" && isBorderGreen(this)) {
        $("#txtContact").focus();
    }
});

$("#txtContact").keyup(function (e) {
    input = txtContact.val();
    validate_CustomerContact(input, this);

    if (e.code == "Enter" && isBorderGreen(this)) {
        select_CustomerRow();
    }
    $("#tblCustomer-body>tr").off("dblclick");
    delete_CustomerRowOnDblClick();
});

/* -----Clear Fields-------*/

$("#btnClearCustomerFields").click(function () {
    reset_CustomerForm();
    generateNextCustomerID();
    loadAllCustomers();

    txtSearchId.val("");
});




